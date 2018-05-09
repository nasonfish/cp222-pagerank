
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
		boolean[] in = new boolean[this.size];
		in = links[i];
		return in;
	}
	
	public boolean[] getLinksOut(int j) {
		boolean[] out = new boolean[this.size];
		for(int i = 0; i < this.size; i++) {
			out[i] = links[i][j];
		}
		return out;
	}
}
