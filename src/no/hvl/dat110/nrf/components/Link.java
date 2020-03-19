package no.hvl.dat110.nrf.components;

public class Link {

	private String name;
	
	private Interface srcif;
	private Interface destif;
	
	public Link(String name, Interface srcif, Interface destif) {
		this.name = name;
		this.srcif = srcif;
		this.destif = destif;
	}
	
	public void transmit(Datagram datagram) {
	
		destif.receive(datagram.clone());
		
	}
}
