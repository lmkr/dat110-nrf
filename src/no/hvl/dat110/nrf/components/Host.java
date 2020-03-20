package no.hvl.dat110.nrf.components;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.addressing.Segment;
import no.hvl.dat110.nrf.common.Logger;

public class Host extends Node {

	private Interface nif;
	
	public Host(String name) {
		super(name);
	}
	
	public void ifconfig(int id, IPAddress ipadr) {
		nif = new Interface(id,super.name,ipadr);
	}
	
	public Interface getInterface(int id) {
		// hosts can currently only have one interface
		return nif;
	}
	
	public void start () {
		
		Logger.log("Node: " + super.name + " - starting");
		nif.run();
		Logger.log("Node: " + super.name + " - started");
	}
	
	public void stop () {
		
		Logger.log("Node: " + super.name + " - stopping");
		try {
			
			nif.doStop();
			nif.join();
			
		} catch (InterruptedException ex) {

			Logger.log("Host[" + name + "]" + ex.getMessage());
			ex.printStackTrace();
		}
		
		Logger.log("Node: " + super.name + " - stopped");
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
