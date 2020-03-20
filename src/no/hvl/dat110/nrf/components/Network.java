package no.hvl.dat110.nrf.components;

import java.util.ArrayList;

public class Network {

	private String name;
	
	private ArrayList<Node> nodes;
	private ArrayList<Link> links;
		
	public Network(String name) {
		this.name = name;
		nodes = new ArrayList<Node>();
		links = new ArrayList<Link>();
	}
	
	public void add(Node node) {
		nodes.add(node);
	}
	
	public void connect(Node node1, int nifid1, Node node2, int nifid2) {
	
		Interface nif1 = node1.getInterface(nifid1);
		Interface nif2 = node1.getInterface(nifid2);
		
		Link link12 = new Link(node1.getName() + "->" + node2.getName(),nif1,nif2);
		Link link21 = new Link(node2.getName() + "->" + node1.getName(),nif2,nif1);
		
		nif1.connect(link21, link12);
		nif2.connect(link12, link21);
		
		links.add(link12);
		links.add(link21);
	}
	
	public void start() {
		
		nodes.forEach(node -> node.start());
		
	}
	
	public void stop() {
		
		nodes.forEach(node -> node.stop());
		
	}
	
}