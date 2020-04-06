package no.hvl.dat110.controlplane.linkstate;

public class LSNeighbourMsg {

	private int node;
	private int[] neighbours;
	
	public LSNeighbourMsg(int node, int[] neighbours) {
		super();
		this.node = node;
		this.neighbours = neighbours;
	}

	public int getNode() {
		return node;
	}

	public int[] getNeighbours() {
		return neighbours;
	}
	
}
