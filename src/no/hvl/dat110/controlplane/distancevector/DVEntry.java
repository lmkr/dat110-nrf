package no.hvl.dat110.controlplane.distancevector;

public class DVEntry {

	private int dist;
	private int nexthop;
	
	public DVEntry (int dist, int nexthop) {
		
		this.dist = dist;
		this.nexthop = nexthop;
	}
	
	public void update(int dist, int nexthop) {
		this.dist = dist;
		this.nexthop = nexthop;
	}
	
	public int getDistance() {
		return dist;
	}
	
	public int getNexthop() {
		return nexthop;
	}
}
