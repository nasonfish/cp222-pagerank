
public class MyGraph {
	private final boolean[][] links;
	private final int size; // for convenience
	
	public MyGraph(int size) {
		links = new boolean[size][size];
		this.size = size;
		for (int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				links[i][j] = false;
			}
		}
	}
	
	public void link(int i, int j) {
		this.links[i][j] = true;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public boolean isLinked(int i, int j) {
		return links[i][j];
	}
	
	public boolean[] getLinksIn(int i) {
		boolean[] in = links[i];
		return in;
	}
	
	public int getNumLinksOut(int i) {
		int out = 0;
		for(int j = 0; j < this.size; j++) {
			if(links[i][j]){
				out++;
			}
		}
		return out;
	}
	
	public static void main(String[] args) {
		MyGraph graph = new MyGraph(4);
		graph.link(1, 2);
		graph.link(2, 3);
		System.out.println(graph.getNumLinksOut(2) == 1);
		System.out.println(graph.getNumLinksOut(1) == 1);
		System.out.println(graph.getNumLinksOut(0) == 0);
		System.out.println(graph.isLinked(2, 3) == true);
		System.out.println(graph.isLinked(3, 1) == false);
	}
}
