package no.hvl.dat110.controlplane;

import java.util.HashSet;

import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Node;

public class LSRouting {

	// node for which the routing algorithm is executed
	
	private Node node;
	private NetworkGraph graph;
	
	private HashSet<Node> N;
	private LSEntry[] entries;
			
	public LSRouting(Node node,Network network) {
		this.node = node;
		graph = new NetworkGraph(network);
	}
	
	public void run() {
		
		
	}
}
