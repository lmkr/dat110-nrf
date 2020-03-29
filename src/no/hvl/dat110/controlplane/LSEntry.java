package no.hvl.dat110.controlplane;

import no.hvl.dat110.nrf.network.Node;

public class LSEntry {

	private Node v;
	private Node prev;
	private int d;
	
	public Node getV() {
		return v;
	}

	public void setV(Node v) {
		this.v = v;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		d = d;
	}

	public LSEntry(Node v, Node prev, int d) {
		super();
		this.v = v;
		this.prev = prev;
		d = d;
	}
	
	
}
