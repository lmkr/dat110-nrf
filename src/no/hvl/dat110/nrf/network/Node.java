package no.hvl.dat110.nrf.network;

import no.hvl.dat110.nrf.addressing.Datagram;

public abstract class Node {

	protected String name;
	public int nid; 
	
	public Node(String name, int nid) {
		super();
		this.name = name;
		this.nid = nid;
	}

	public String getName() {
		return name;
	}
	
	public abstract Interface getInterface(int id);
		
	public abstract void start();
	
	public abstract void stop();
	
	public abstract void deliver(Datagram datagram);
	
	public abstract void display();
	
}
