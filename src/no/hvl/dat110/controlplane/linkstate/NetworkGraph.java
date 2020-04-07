package no.hvl.dat110.controlplane.linkstate;

import java.util.ArrayList;
import java.util.HashMap;

import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.network.Network;

public class NetworkGraph {
	
	private HashMap<Integer, ArrayList<Integer>> graph;
	
	public NetworkGraph () {

		graph = new HashMap<Integer,ArrayList<Integer>>();
	}
	
	public NetworkGraph(Network network) {
		graph = new HashMap<Integer,ArrayList<Integer>>();
		buildGraph(network);
	}
	
	public ArrayList<Integer> getNodes() {
		return new ArrayList<Integer>(graph.keySet());
	}
	
	public ArrayList<Integer> getNeighbours(Integer node) {
		
		ArrayList<Integer> neighbours = graph.get(node);
		
		assert(neighbours != null);
		
		return neighbours; 
	}
	
	public void addNode(Integer node) {
		graph.put(node,new ArrayList<Integer>());
	}
	
	public void addNeighbours(Integer srcnode,Integer[] destnodes) {
		
		for (Integer destnode : destnodes) {
			graph.get(srcnode).add(destnode);
		}
		
	}
	
	private void buildGraph(Network network) {
		
		network.getNodes().forEach(node -> graph.put(node.nid,new ArrayList<Integer>()));
		
		network.getLinks().forEach(
				link -> {
					
						int nodesrc = link.getSrc().getNode().nid;
						int nodedest = link.getDest().getNode().nid;
					
						graph.get(nodesrc).add(new Integer(nodedest)); // TODO - change nid to Integer?
				});
	}
	
	public void printGraph() {
		
		graph.forEach((node,edges) -> {
		
			Logger.lg(LogLevel.GRAPH,node + "[");
			
			edges.forEach(n -> Logger.lg(LogLevel.GRAPH," " + n));
			
			Logger.log(LogLevel.GRAPH,"]");
			
		});
	}
}