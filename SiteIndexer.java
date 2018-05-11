import java.io.File;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SiteIndexer {
	
	private ArrayList<File> toLoad;
	private MyGraph graph;
	private String[] mappings;
	private long running_time;
	private double[] pageRanks;

	public SiteIndexer(String site) {
		this.toLoad = new ArrayList<File>();
		this.loadFiles("", new File(site));
	}
	
	public static void main(String[] args) {
		
		// Graph source: whatever URL is provided as an argument during execution
		// If none provided, use wildanimalsonline.com
		String site = "wildanimalsonline.com";
		if(args.length >= 1) {
			site = args[0];
		}
		
		SiteIndexer instance = new SiteIndexer(site);
		instance.loadData();
		
		instance.doPageRanks();
		instance.printPageRanks();
	}
	
	/**
	* Load in the files in this directory recursively.
	* @param parent The string at the beginning of the directory name. "" for the initial call.
	* @param parentDir A File object for the file we're recursively loading files into our index from.
	*/
	public void loadFiles(String parent, File parentDir) {
		if(!parentDir.exists()) {
			return;
		}
		if(parentDir.isDirectory()) {
			for(String child : parentDir.list()) {
				loadFiles(parent + parentDir.getName() + "/", new File(parentDir.getAbsolutePath() + "/" + child));
			}
			return;
		}
		assert(parentDir.isFile());
		this.toLoad.add(parentDir);
	}
	
	
	public void loadData() {
		this.graph = new MyGraph(this.toLoad.size());
		this.pageRanks = new double[graph.getSize()];
		for(int i = 0; i < graph.getSize(); i++) {
			this.pageRanks[i] = 1.0F / graph.getSize();
		}
		this.mappings = new String[this.toLoad.size()];
		for(int i = 0; i < mappings.length; i++) {
			this.mappings[i] = this.toLoad.get(i).getPath().replaceAll("^.*wildanimalsonline.com", "").replaceAll("(index)?\\.html", "");
		}
		
		for(int i = 0; i < mappings.length; i++) {
			Document document = DataUtils.pullDocument(this.toLoad.get(i));
			String currDir = this.mappings[i].replaceAll(this.toLoad.get(i).getName(), "");
			Elements anchors = document.getElementsByTag("a");
			for(Element anchor : anchors) {
				String tag = anchor.attr("href");
				for(int j = 0; j < mappings.length; j++) {
					if(mappings[j].replaceAll("^http://www\\.wildanimalsonline\\.com", "").replaceAll(currDir, "").equalsIgnoreCase(tag.replaceAll(currDir, ""))) {
						System.out.println("found link: " + tag + " for " + mappings[j]);
						this.graph.link(i, j);
					}
				}
			}
		}
	}
	
	/**
	 * Calculate the PageRanks of all the vertices in the graph.
	 */
	public void doPageRanks() {
		long start = System.currentTimeMillis();
		// Loop until the PageRanks aren't changing anymore
		boolean converged = false;
		final double dampening = 0.85;
		double[] temp_arr = new double[graph.getSize()];
		while(!converged) {
			converged = true;
			// Loop through each "node"
			for(int j = 0; j < graph.getSize(); j++) {
				// The formula for a PageRank:
				double sum = 0;
				boolean[] in = graph.getLinksIn(j);
				for(int i = 0; i < graph.getSize(); i++) {
					if(in[i]) {
						sum += pageRanks[i] / graph.getNumLinksOut(i);
					}
				}
				sum = ((1.0 - dampening) / graph.getSize()) + dampening * sum;
				
				if(pageRanks[j] != sum) { // note we have NO BUFFER-- the program will run until the change
					                      // is smaller than float can handle.
					converged = false; // we weren't converged during this iteration because something had to change.
					temp_arr[j] = sum;
				}
			}
			pageRanks = temp_arr;
		}
		long end = System.currentTimeMillis();
		
		this.running_time = end - start;
	}
	
	/**
	 * Print the results of the PageRank calculations.
	 */
	public void printPageRanks() {
		// Print the PageRanks
		float totalSum = 0;
		for(int i = 0; i < graph.getSize(); i++) {
			totalSum += pageRanks[i];
			System.out.println(String.format("%s's page rank is %f", this.mappings[i], pageRanks[i]));
		}
		
		// Print total sum of PageRanks and amount of time calculations took
		System.out.println(String.format("%f is the total sum; calculated in %d ms", totalSum, running_time));
		this.graph.print();
	}
}
