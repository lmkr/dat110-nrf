package no.hvl.dat110.nrf.components;

public abstract class Node {

	protected String name;

	public Node(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract void start();
	
	public abstract void stop();
	
	public abstract void forward(Datagram datagram);
}
