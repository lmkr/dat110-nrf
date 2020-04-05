package no.hvl.dat110.controlplane.distancevector;

public class DV {

	// distance vector exchanged between nodes
	// distance to node i is placed at index i
	
	private int node;
	private int[] vector;
	
	public DV(int node, int[] vector) {
		this.vector = vector;
		this.node = node;
	}	
	
	public int getNode() {
		return node;
	}
	
	public int[] getVector() {
		return vector;
	}
}
