package no.hvl.dat110.nrf.network;

import no.hvl.dat110.nrf.addressing.Datagram;

public class Port {

	private Link incoming, outgoing;

	public Port(Link incoming, Link outgoing) {
		super();
		this.incoming = incoming;
		this.outgoing = outgoing;
	}

	public Link getIncoming() {
		return incoming;
	}

	public Link getOutgoing() {
		return outgoing;
	}
	
	public void transmit(Datagram datagram) {
		
		outgoing.transmit(datagram);
		
	}
}
