package no.hvl.dat110.nrf.components;

public class Link {

	private String name;
	
	Interface srcif;
	Interface destif;
	
	public Link(String name) {
		this.name = name;
	}
	
	public void transmit(Datagram datagram) {
		
		// TODO: clone the datagram?
		destif.receive(datagram);
	}
}
