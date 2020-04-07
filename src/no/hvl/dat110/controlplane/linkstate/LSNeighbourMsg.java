package no.hvl.dat110.controlplane.linkstate;

public class LSNeighbourMsg {

	private Integer node;
	private Integer[] neighbours;
	
	public LSNeighbourMsg(Integer node, Integer[] neighbours) {
		super();
		this.node = node;
		this.neighbours = neighbours;
	}

	public Integer getNode() {
		return node;
	}

	public Integer[] getNeighbours() {
		return neighbours;
	}
	
}
