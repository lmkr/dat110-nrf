package no.hvl.dat110.nrf.network;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.addressing.Segment;
import no.hvl.dat110.nrf.common.Logger;

public class Host extends Node implements INetworkLayerEntity {

	private Interface nif;
	Segment segment = null;
	
	public Host(String name) {
		super(name);
	}
	
	public void ifconfig(int id, IPAddress ipadr) {
		nif = new Interface(this,id,super.name,ipadr);
	}
	
	public Interface getInterface(int id) {
		// hosts can currently only have one interface
		return nif;
	}
	
	public IPAddress getIPAddress() {
		return nif.getIPadr();
		
	}
	public void start () {
		
		Logger.log(super.name + ": starting");
		nif.start();
		Logger.log(super.name + ": started");
	}
	
	public void stop () {
		
		Logger.log(super.name + ": stopping");
		try {
			
			nif.doStop();
			nif.join();
			
		} catch (InterruptedException ex) {

			Logger.log("Host[" + name + "]" + ex.getMessage());
			ex.printStackTrace();
		}
		
		Logger.log(super.name + ": stopped");
	}
	
	public void send(Segment segment, IPAddress dest) {
		
		// encapsulate segment from host into datagram
		Datagram datagram = new Datagram(nif.getIPadr(),dest,segment);
		
		Logger.log(super.name + "[send]:" + datagram.toString());
		// transmit on the network interface of the host
		nif.transmit(datagram);
	}

	public void forward (Datagram datagram) {
		
		// forwarding on a host is deliver to the transport layer
		if (nif.getIPadr().equals(datagram.getDestination())) {
			Logger.log(super.name + "[forwarding]: " + datagram.toString());
			segment = datagram.getPayload();
		} else {
			Logger.log(super.name + "[routing error:" + nif.getIPadr().toString() + "]:" + datagram.toString());
			Logger.log(nif.getIPadr().toString());
			
		}
			
	}
	
	public void udt_send(Segment segment, IPAddress destip) {
		
		send(segment,destip);
	}
	
	public Segment udt_recv() {
		
		return segment;
		
	}
}
