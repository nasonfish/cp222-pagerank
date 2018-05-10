// An interface for directed, unweighted graphs

public interface GraphInterface {
	
	/**
	 * Creates a link from the first given vertex to the second given vertex 
	 * @param i the number that represents the vertex at the tail of the link
	 * @param j the number that represents the vertex at the head of the link
	 */
	public void link(int i, int j);
	
	/**
	 * Returns the number of vertices in this graph (the size of the graph)
	 * @return the size of this graph
	 */
	public int getSize();
	
	/**
	 * Provides an array of booleans representing whether each vertex in this graph is linked TO the given vertex
	 * @param j the number representing the vertex at the head of the links in question
	 * @return an boolean array that denotes which vertices are linked towards the given vertex
	 */
	public boolean[] getLinksIn(int j);
	
	/**
	 * Provides the number of vertices to which the given vertex is linked
	 * @param i the number representing the vertex at the tail of the links in question
	 * @return the number of outgoing connections from the given vertex
	 */
	public int getNumLinksOut(int i);
}
