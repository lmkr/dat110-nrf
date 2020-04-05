package no.hvl.dat110.controlplane.linkstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Node;

public class NetworkGraph {
	
	private HashMap<Node, ArrayList<Node>> graph;
	
	public NetworkGraph () {

		graph = new HashMap<Node,ArrayList<Node>>();
	}
	
	public NetworkGraph(Network network) {
		graph = new HashMap<Node,ArrayList<Node>>();
		buildGraph(network);
	}
	
	// TODO : find a better way to do this - use List/Set?
	public ArrayList<Node> getNodes() {
		return new ArrayList<Node>(graph.keySet());
	}
	
	public ArrayList<Node> getNeighbours(Node node) {
		return graph.get(node);
	}
	
	public void buildGraph(Network network) {
		
		network.getNodes().forEach(node -> graph.put(node,new ArrayList<Node>()));
		
		network.getLinks().forEach(
				link -> {
					
						Node nodesrc = link.getSrc().getNode();
						Node nodedest = link.getDest().getNode();
					
						graph.get(nodesrc).add(nodedest);
				});
	}
	
	public void printGraph() {
		
		graph.forEach((node,edges) -> {
		
			Logger.lg(LogLevel.GRAPH,node.getName() + "[");
			
			edges.forEach(n -> Logger.lg(LogLevel.GRAPH," " + n.getName()));
			
			Logger.log(LogLevel.GRAPH,"]");
			
		});
	}
}
