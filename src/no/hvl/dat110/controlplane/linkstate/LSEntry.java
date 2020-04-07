package no.hvl.dat110.controlplane.linkstate;

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
		String dstr = "INF";

		// TODO: test could be made better - fix when using indexing from R0
		if (prev != 0) {
			prevstr = Integer.toString(prev); 
		}
		
		if (d < Integer.MAX_VALUE) {
			dstr = Integer.toString(d);
		}

		return "[" + prevstr + "," + dstr + "]";

	}

}
