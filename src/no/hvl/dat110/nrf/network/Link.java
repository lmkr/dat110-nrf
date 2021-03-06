package no.hvl.dat110.nrf.network;

import no.hvl.dat110.nrf.addressing.Datagram;

public class Link {

	private String name;
	
	private Interface srcif;
	private Interface destif;
	
	public Link(String name, Interface srcif, Interface destif) {
		this.name = name;
		this.srcif = srcif;
		this.destif = destif;
	}
	
	public Interface getSrc() {
		return srcif;
	}
	
	public Interface getDest() {
		return destif;
	}
	
	public void transmit(Datagram datagram) {
	
		destif.receive(datagram.clone());
		
	}
}
