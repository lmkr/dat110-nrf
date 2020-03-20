package no.hvl.dat110.nrf.components;

import no.hvl.dat110.nrf.addressing.Datagram;

public abstract class Node {

	protected String name;

	public Node(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract Interface getInterface(int id);
	
	public abstract void start();
	
	public abstract void stop();
	
	public abstract void forward(Datagram datagram);
}
