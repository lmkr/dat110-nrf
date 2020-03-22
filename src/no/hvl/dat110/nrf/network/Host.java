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
	
	public void ifconfig(int id, IPAddress addr) {
		nif = new Interface(this,id,super.name,addr);
	}
	
	public Interface getInterface(int id) {
		// hosts can currently only have one interface
		return nif;
	}
	
	public IPAddress getIPAddress() {
		return nif.getIPaddr();
		
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

	public void deliver (Datagram datagram) {
		
		if (nif.getIPaddr().equals(datagram.getDestination())) {
			Logger.log(super.name + "[deliver]:" + datagram.toString());
			segment = datagram.getSegment();
		} else {
			Logger.log(super.name + "[routing error:" + nif.getIPaddr().toString() + "]:" + datagram.toString());
			Logger.log(nif.getIPaddr().toString());
			
		}
			
	}
	
	public void udt_send(Segment segment, IPAddress destip) {
		
		// encapsulate segment from host into datagram
		Datagram datagram = new Datagram(nif.getIPaddr(),destip,segment);
			
		Logger.log(super.name + "[udt_send]:" + datagram.toString());
			
		// transmit on the network interface of the host
		nif.transmit(datagram);
			
	}
	
	public Segment udt_recv() {
		
		Logger.log(super.name + "[udt_recv]:" + segment.toString());
		
		return segment;
		
	}
}
