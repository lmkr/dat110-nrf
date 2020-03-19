package no.hvl.dat110.nrf.components;

public abstract class Node {

	private String name;

	public Node(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void start();
	
	public abstract void stop();
	
	public abstract void route(Datagram datagram);
}
