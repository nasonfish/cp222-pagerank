import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class to represent a directed, unweighted graph, implemented using an 
 * array of nodes that represent vertices.
 * 
 * @author Daniel Barnes '21 and Ely Merenstein '21
 *
 */
public class MyGraphLL implements GraphInterface {
	
	/**
	 * Amount of nodes in our graph-- stored for convenience.
	 * Identical to nodes.length after class instantiation.
	 */
	private final int size;
	/**
	 * An array of GraphNode objects.
	 */
	private final GraphNode[] nodes;

	/**
	 * Constructs a new graph with the given number of vertices and no links.
	 * @param size the number of vertices/nodes the graph will contain.
	 */
	public MyGraphLL(int size) {
		this.size = size;
		nodes = new GraphNode[size];
		for(int i = 0; i < size; i++) {
			nodes[i] = new GraphNode(i);
		}
	}
	
	/**
	 * Instantiates and fills in a new MyGraphLL using the data stored in the given scanner
	 * @param scanner the source of the data about the links of this graph.
	 * @return the new MyGraphLL object.
	 */
	public static MyGraphLL newInstance(Scanner scanner) {
		MyGraphLL graph = null;
		for(int i = 0; scanner.hasNextLine(); i++) {
			String[] line = scanner.nextLine().split(" ");
			if(graph == null) {
				graph = new MyGraphLL(line.length); // we assume the length and the height
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
	 * @param i the index of the node that represents the vertex at the tail of the link
	 * @param j the index of the node that represents the vertex at the head of the link
	 */
	@Override
	public void link(int i, int j) {
		this.nodes[i].link(this.nodes[j]);
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
	 * @param j the index of the node representing the vertex at the head of the links in question
	 * @return an boolean array that denotes which vertices are linked towards the given vertex
	 */
	@Override
	public boolean[] getLinksIn(int j) {
		boolean[] result = new boolean[size];
		ArrayList<GraphNode> nodes = this.nodes[j].getLinksIn();
		sizeLoop:
		for(int i = 0; i < this.size; i++) {
			for(GraphNode node : nodes) {
				if(node.getId() == i) {
					result[i] = true;
					continue sizeLoop;
				}
			}
			result[i] = false;
		}
		return result;
	}

	/**
	 * Provides the number of vertices to which the given vertex is linked
	 * @param i the index of the node representing the vertex at the tail of the links in question
	 * @return the number of outgoing connections from the given vertex
	 */
	@Override
	public int getNumLinksOut(int i) {
		return this.nodes[i].getNumLinksOut();
	}

}

/**
 * A class to represent a node in a MyGraphLL
 * @author Daniel Barnes '21 and Ely Merenstein '21
 *
 */
class GraphNode {
	
	/**
	 * A list of GraphNodes that point to this GraphNode
	 */
	private final ArrayList<GraphNode> linksIn;
	/**
	 * A list of GraphNodes that this GraphNode points to.
	 */
	private final ArrayList<GraphNode> linksOut;
	/**
	 * The id of this node-- rather, the index it's stored in in the MyGraphLL instance.
	 */
	private final int id;
	
	/**
	 * Constructs a new GraphNode with the given id
	 * @param id the id of the new node
	 */
	public GraphNode(int id) {
		this.linksIn = new ArrayList<GraphNode>();
		this.linksOut = new ArrayList<GraphNode>();
		this.id = id;
	}
	
	/**
	 * Links this node TO another node
	 * @param other the node to which this node is going to be linked
	 */
	public void link(GraphNode other) {
		linksOut.add(other);
		other.addIncomingLink(this);
	}
	
	/**
	 * Adds the given node to this one's list of incoming links
	 * @param other the node at the tail of the new incoming link
	 */
	protected void addIncomingLink(GraphNode other) {
		this.linksIn.add(other);
	}
	
	/**
	 * Returns the number of outgoing links from this node
	 * @return the size of the list of this node's outgoing links
	 */
	public int getNumLinksOut() {
		return this.linksOut.size();
	}
	
	/**
	 * Returns the list of incoming links to this node
	 * @return this node's list of incoming links
	 */
	public ArrayList<GraphNode> getLinksIn() {
		return this.linksIn;
	}
	
	/**
	 * Gets this node's id
	 * @return the id of this node
	 */
	public int getId() {
		return this.id;
	}
}