package no.hvl.dat110.nrf.components;

public class Host extends Node {

	private Interface nif;
	
	public Host(String name) {
		super(name);
	}
	
	public void start () {
		// start the interface
	}
	
	public void stop () {
		// stop the interface
	}
	
	public void send(Segment segment, IPAddress dest) {
		
	}

	public void route (Datagram datagram) {
		// print out the receive segment
	}
}
