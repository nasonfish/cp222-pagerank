import java.util.Scanner;

// A class to represent a directed, unweighted graph, implemented using a 2D-array of links between conceptual vertices

public class MyGraph implements GraphInterface {
	
	// Instance variables
	/**
	 * Links in this graph representation go only one direction.
	 * 
	 * In this particular implementation, if links[i][j] is true,
	 * that means i is linked to j.
	 * 
	 * This variable use is consistent across this file: (i, j)
	 */
	private final boolean[][] links;
	private final int size; // for convenience
	
	/**
	 * Constructs a new graph with the given number of vertices and no links
	 * @param size the number of vertices/nodes the graph will contain
	 */
	public MyGraph(int size) {
		links = new boolean[size][size];
		this.size = size;
		for (int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				links[i][j] = false; // Nothing is linked yet
			}
		}
	}
	
	/**
	 * Instantiates and fills in a new MyGraph using the data stored in the given scanner
	 * @param scanner the source of the data about the links of this graph
	 * @return the new MyGraph object
	 */
	public static MyGraph newInstance(Scanner scanner) {
		MyGraph graph = null;
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
		return graph;
	}
	
	/**
	 * Creates a link from the first given vertex to the second given vertex 
	 * @param i the number of the row that represents the vertex at the tail of the link
	 * @param j the number of the column that represents the vertex at the head of the link
	 */
	@Override
	public void link(int i, int j) {
		this.links[i][j] = true;
	}
	
	/**
	 * Returns the number of vertices in this graph (the size of the graph)
	 * @return the size of this graph
	 */
	@Override
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Provides an array of booleans representing whether each vertex in this graph is linked TO the given vertex
	 * @param j the number of the column representing the vertex at the head of the links in question
	 * @return an boolean array that denotes which vertices are linked towards the given vertex (i.e. the jth column of this.links)
	 */
	@Override
	public boolean[] getLinksIn(int j) {
		boolean[] in = new boolean[this.size];
		for(int i = 0; i < this.size; i++){
			in[i] = links[i][j];
		}
		return in;
	}
	
	/**
	 * Provides the number of vertices to which the given vertex is linked
	 * @param i the number of the row representing the vertex at the tail of the links in question
	 * @return the number of outgoing connections from the given vertex (i.e. the number of "true"s in the ith row of this.links)
	 */
	@Override
	public int getNumLinksOut(int i) {
		int out = 0;
		for(int j = 0; j < this.size; j++) {
			if(links[i][j]){
				out++;
			}
		}
		return out;
	}
}
