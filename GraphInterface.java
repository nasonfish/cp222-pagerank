
public interface GraphInterface {
	public void link(int i, int j);
	
	/**
	 * Returns the number of vertices in this graph (the size of the graph)
	 * @return the size of this graph
	 */
	public int getSize();
	
	/**
	 * Provides an array of booleans representing whether each vertex in this graph is linked TO the given vertex
	 * @param j the number of the column representing the vertex at the head of the links in question
	 * @return an boolean array that denotes which vertices are linked towards the given vertex (i.e. the jth column of this.links)
	 */
	public boolean[] getLinksIn(int j);
	
	/**
	 * Provides the number of vertices to which the given vertex is linked
	 * @param i the number of the row representing the vertex at the tail of the links in question
	 * @return the number of outgoing connections from the given vertex (i.e. the number of "true"s in the ith row of this.links)
	 */
	public int getNumLinksOut(int i);
}
