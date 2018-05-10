import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

// A program to find the PageRanks of all the nodes in a given graph

public class Day13 {
	
	/**
	 * Main method for program execution
	 * @param args Command line args.
	 * 
	 * java Day13 [optional_url]
	 */
	public static void main(String[] args) {
		
		// Graph source: whatever URL is entered into CLI, or the smallest of the three graphs if CLI is unused
		String site = "http://cs.coloradocollege.edu/~mwhitehead/courses/2017_2018/CP222/Assignments/10/test.txt";
		if(args.length == 1) {
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
		
		// Read in the given data and store it in a MyGraph object
		GraphInterface graph = null;
		for(int i = 0; scanner.hasNextLine(); i++) {
			String[] line = scanner.nextLine().split(" ");
			if(graph == null) {
				graph = new MyGraph(line.length); // we assume the length and the height
			} 									  // of the file are the same.
			for(int j = 0; j < line.length; j++) {
				if(line[j].equals("1")) {
					graph.link(i,j);
				}
			}
		}
		
		// Set up and initialize an array to store the PageRanks
		double[] pageRanks = new double[graph.getSize()];
		for(int i = 0; i < graph.getSize(); i++) {
			pageRanks[i] = 1.0 / graph.getSize();
		}
		
		// Loop until the PageRanks aren't changing anymore
		boolean converged = false;
		double dampening = 0.85;
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
				
				sum = ((1 - dampening) / graph.getSize()) + dampening * sum;
				if(pageRanks[j] != sum) {
					converged = false; // we weren't converged during this iteration because something had to change.
					pageRanks[j] = sum;
				}
			}
		}
		
		// Print out the PageRanks
		double totalSum = 0;
		for(int i = 0; i < graph.getSize(); i++) {
			totalSum += pageRanks[i];
			System.out.println(String.format("%d's page rank is %f", i, pageRanks[i]));
		}
		System.out.println(totalSum);
	}
}
