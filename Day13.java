import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Day 13 Homework Assignment-- PageRank.
 * 
 * Given an input of a URL with containing a graph graph
 * (in notation with columns and rows where each slot is a binary digit
 * in a visual representation of a 2d array), we compute the PageRank of each column with
 * the assumption that (x, y) is considered a link.
 * 
 * @author Daniel Barnes '21 and Ely Merenstein '21
 *
 */
public class Day13 {
	
	/**
	 * Graph Interface object-- the backbone of the operations
	 * we do. A GraphInterface object reads in from the scanner and allows
	 * us to call methods to interact with the data.
	 */
	private final GraphInterface graph;
	/**
	 * This keeps track of the page ranks for each page.
	 * We iterate through the algorithm until pageRanks
	 * is unchanging after an entire iteration.
	 */
	private final float[] pageRanks;
	/**
	 * A variable for timing-- we time from the beginning of
	 * the PageRank algorithm until the end, and compute running_time for
	 * output.
	 */
	private long running_time;
	
	/**
	 * Main method for program execution
	 * @param args Command line args.
	 * 
	 * java Day13 [optional_url]
	 */
	public static void main(String[] args) {
		
		// Graph source: whatever URL is provided as an argument during execution
		// If none provided, use the smallest graph.
		String site = "http://cs.coloradocollege.edu/~mwhitehead/courses/2017_2018/CP222/Assignments/10/test.txt";
		if(args.length >= 1) {
			site = args[0];
		}
		
		Scanner scanner = null;
		try {
			scanner = Utils.pullText(new URL(site));
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + site);
			System.exit(1);
			return;
		}
		
		// Create and fill in a graph using the data from the selected site
		GraphInterface graph = MyGraph.newInstance(scanner);
		scanner.close();
		
		// Calculate and display PageRanks for the graph
		Day13 instance = new Day13(graph);
		instance.doPageRanks();
		instance.printPageRanks();
	}
	
	/**
	 * Instantiates Day13 for running the calculation and display methods
	 * @param graph the graph being used for this execution of the program
	 */
	public Day13(GraphInterface graph) {
		this.graph = graph;
		
		// Set up and initialize an array to store the PageRanks
		this.pageRanks = new float[graph.getSize()];
		for(int i = 0; i < graph.getSize(); i++) {
			this.pageRanks[i] = 1.0F / graph.getSize();
		}
	}
	
	/**
	 * Calculate the PageRanks of all the vertices in the graph.
	 */
	public void doPageRanks() {
		long start = System.currentTimeMillis();
		// Loop until the PageRanks aren't changing anymore
		boolean converged = false;
		final float DAMPENING = 0.85F;
		while(!converged) {
			converged = true;
			// Loop through each "node"
			for(int j = 0; j < graph.getSize(); j++) {
				// The formula for a PageRank:
				float sum = 0;
				boolean[] in = graph.getLinksIn(j);
				for(int i = 0; i < graph.getSize(); i++) {
					if(in[i]) {
						sum += pageRanks[i] / graph.getNumLinksOut(i);
					}
				}
				sum = ((1 - DAMPENING) / graph.getSize()) + DAMPENING * sum;
				
				if(pageRanks[j] != sum) { // note we have NO BUFFER-- the program will run until the change
					                      // is smaller than float can handle.
					converged = false; // we weren't converged during this iteration because something changed during the iteration.
					pageRanks[j] = sum;
				}
			}
		}
		System.out.println(pageRanks.length);
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
			System.out.println(String.format("%d's page rank is %f", i, pageRanks[i]));
		}
		
		// Print total sum of PageRanks and amount of time calculations took
		System.out.println(String.format("%f is the total sum; calculated in %d ms", totalSum, running_time));
	}
	// MyGraph: (small, bigger, big)
	// 1.000000 is the total sum; calculated in 3 ms
	// 0.993709 is the total sum; calculated in 5990 ms
	// 0.993267 is the total sum; calculated in 96088 ms
	// MyGraphLL: (small, bigger, big)
	// 1.000000 is the total sum; calculated in 8 ms
	// 0.993709 is the total sum; calculated in 7697 ms
	// 0.993267 is the total sum; calculated in 105205 ms
}
