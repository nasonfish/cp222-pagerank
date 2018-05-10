import java.util.ArrayList;

public class MyGraphLL implements GraphInterface {
	
	private final int size;
	private final GraphNode[] nodes;

	public MyGraphLL(int size) {
		this.size = size;
		nodes = new GraphNode[size];
		for(int i = 0; i < size; i++) {
			nodes[i] = new GraphNode(i);
		}
	}
	
	@Override
	public void link(int i, int j) {
		this.nodes[i].link(this.nodes[j]);
	}

	@Override
	public int getSize() {
		return this.size;
	}

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

	@Override
	public int getNumLinksOut(int i) {
		return this.nodes[i].getNumLinksOut();
	}

}

class GraphNode {
	ArrayList<GraphNode> linksIn;
	ArrayList<GraphNode> linksOut;
	int id;
	
	public GraphNode(int id) {
		this.linksIn = new ArrayList<GraphNode>();
		this.linksOut = new ArrayList<GraphNode>();
		this.id = id;
	}
	
	public void link(GraphNode other) {
		linksOut.add(other);
		other.addIncomingLink(this);
	}
	
	protected void addIncomingLink(GraphNode other) {
		this.linksIn.add(other);
	}
	
	public int getNumLinksOut() {
		return this.linksOut.size();
	}
	
	public ArrayList<GraphNode> getLinksIn() {
		return this.linksIn;
	}
	
	public int getId() {
		return this.id;
	}
}