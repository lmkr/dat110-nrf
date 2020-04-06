package no.hvl.dat110.controlplane.linkstate;

import java.util.HashMap;

import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.common.Stopable;

public class LSRoutingDaemon extends Stopable {

	private HashMap<Integer,int[]> nodes; // nodes from which nieghbour info has been received in teh flooding phase
	
	private int N;
	
	public LSRoutingDaemon(DynamicRouter router, int N) {
		super("DV:" + router.getName());
		nodes = new HashMap<Integer,int[]>();
		this.N = N;
	}
	
	// TODO: perhaps introduce a start and stop hook into the concept of stopable
	// in this case it should sent 
	
	public void doProcess() {
	
		// try to read from blocking queue
		
		// if limit reached, then stop myself itself and invoked dijkstra
		
	}
	
	public void ls_recv(byte[] data) {
		
		// decaprulate json and put into blocking queue
	}
	
	public void display() {
		
		// TODO - display graph + tables
	}

}
