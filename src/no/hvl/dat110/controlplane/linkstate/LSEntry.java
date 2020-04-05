package no.hvl.dat110.controlplane.linkstate;

import no.hvl.dat110.nrf.network.Node;

public class LSEntry {

	private Node prev;
	private int d;

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
		this.d = d;
	}

	public LSEntry(Node prev, int d) {
		super();
		this.prev = prev;
		this.d = d;
	}

	@Override
	public String toString() {

		String prevstr = "-";
		String dstr = "inf";

		if (prev != null) {
			prevstr = prev.getName();
		}

		if (d < Integer.MAX_VALUE) {
			dstr = Integer.toString(d);
		}

		return "[" + prevstr + "," + dstr + "]";

	}

}
