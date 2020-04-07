package no.hvl.dat110.nrf.network;

import java.util.ArrayList;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;

public class Network {

	private String name;
	
	private ArrayList<Node> nodes;
	private ArrayList<Link> links;
		
	public Network(String name) {
		this.name = name;
		nodes = new ArrayList<Node>();
		links = new ArrayList<Link>();
	}
	
	public ArrayList<Node> getNodes() {
	
		return nodes;
		
	}
	
	public ArrayList<Integer> getNodeIds () {
		
		ArrayList<Integer> nids = new ArrayList<Integer>();
		
		nodes.forEach(node -> nids.add(node.nid));
		
		return nids;
	}
	
	public ArrayList<Link> getLinks () {
		return links;
	}
	
	public void addNode(Node node) {
		nodes.add(node);
	}
	
	public void connect(Node node1, int nifid1, Node node2, int nifid2) {
	
		Interface nif1 = node1.getInterface(nifid1);
		Interface nif2 = node2.getInterface(nifid2);
		
		Link link12 = new Link(node1.getName() + "->" + node2.getName(),nif1,nif2);
		Link link21 = new Link(node2.getName() + "->" + node1.getName(),nif2,nif1);
		
		nif1.connect(link21, link12);
		nif2.connect(link12, link21);
		
		links.add(link12);
		links.add(link21);
	}
	
	public void start() {
		
		Logger.log(LogLevel.STARTSTOP,this.name + ": starting");
				
		nodes.forEach(node -> node.start());
		
		Logger.log(LogLevel.STARTSTOP,this.name + ": started");
		
	}
	
	public void stop() {
		
		Logger.log(LogLevel.STARTSTOP,this.name + ": stopping");
		
		nodes.forEach(node -> node.stop());
		
		Logger.log(LogLevel.STARTSTOP,this.name + ": stopped");
	}
	


	public void display() {
		
		nodes.forEach( 
				node -> node.display());
					
	}
}
