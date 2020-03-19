package no.hvl.dat110.nrf.components;

import no.hvl.dat110.nrf.common.Logger;

public class Host extends Node {

	private Interface nif;
	
	public Host(String name) {
		super(name);
	}
	
	public void ifconfig(int id) {
		nif = new Interface(id,super.name);
	}
	
	public void start () {
		
		nif.run();
	}
	
	public void stop () {
		
		try {
			
			nif.doStop();
			nif.join();
			
		} catch (InterruptedException ex) {

			Logger.log("Host[" + name + "]" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void send(Segment segment, IPAddress dest) {
		
		// encapsulate segment from host into datagram
		Datagram datagram = new Datagram(nif.getIPadr(),dest,segment);
		
		// transmit on the network interface of the host
		nif.transmit(datagram);
	}

	public void forward (Datagram datagram) {
		
		// forwarding on a host is deliver to the transport layer
		if (nif.getIPadr().equals(datagram.getDestination())) {
			Logger.log("Host[" + name + "]: " + "routing error");
		} else
			Logger.log("Host[" + name + "]: " + datagram.toString());
	}
}
