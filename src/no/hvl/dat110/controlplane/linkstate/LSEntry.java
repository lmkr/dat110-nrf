package no.hvl.dat110.controlplane.linkstate;

import no.hvl.dat110.nrf.network.Node;

public class LSEntry {

	private int prev;
	private int d;

	public LSEntry() {
		prev = 0;
	}
	
	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public LSEntry(int prev, int d) {
		super();
		this.prev = prev;
		this.d = d;
	}

	@Override
	public String toString() {

		String prevstr = "-";
		String dstr = "inf";

		// TODO: test could be made better
		if (prev != 0) {
			prevstr = Integer.toString(prev); // prev.getName();
		}
		
		if (d < Integer.MAX_VALUE) {
			dstr = Integer.toString(d);
		}

		return "[" + prevstr + "," + dstr + "]";

	}

}
