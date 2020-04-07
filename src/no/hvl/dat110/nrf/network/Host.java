package no.hvl.dat110.nrf.network;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;

public class Host extends Node implements INetworkLayerEntity {
	
	private Interface nif; // a host has a single interface

	byte[] data = null; // transport data received - for now only one datagram for hosts
	
	public Host(int hostid) {
		super("H" + hostid,hostid);
	}
	
	public void ifconfig(int id, IPAddress addr) {
		nif = new Interface(this,id,super.name,addr);
	}
	
	public Interface getInterface(int nifid) {
		return nif; // hosts have only a single interface - so ignore id for now
	}

	public IPAddress getIPAddress() {
		return nif.getIPaddr();
		
	}
	
	public void start () {
		
		Logger.log(LogLevel.STARTSTOP,super.name + ": starting");
		nif.start();
		Logger.log(LogLevel.STARTSTOP,super.name + ": started");
	}
	
	public void stop () {
		
		Logger.log(LogLevel.STARTSTOP,super.name + ": stopping");
		try {
			
			nif.doStop();
			nif.join();
			
		} catch (InterruptedException ex) {

			Logger.log(LogLevel.STARTSTOP,"Host[" + name + "]" + ex.getMessage());
			ex.printStackTrace();
		}
		
		Logger.log(LogLevel.STARTSTOP,super.name + ": stopped");
	}

	// invoked by interface when there is a datagram for the host
	public void deliver (Datagram datagram) {
		
		if (nif.getIPaddr().equals(datagram.getDestination())) {
			Logger.log(LogLevel.DELIVER,super.name + "[deliver]:" + datagram.toString());
			data = datagram.getData();
		} else {
			Logger.log(LogLevel.DELIVER,super.name + "[routing error:" + nif.getIPaddr().toString() + "]:" + datagram.toString());
			Logger.log(LogLevel.DELIVER,nif.getIPaddr().toString());
			
		}
			
	}
	
	// invoked by host to send a datagram - data is tramsport layer data
	public void udt_send(byte[] data, IPAddress destip) {
		
		// encapsulate segment from host into datagram
		Datagram datagram = new Datagram(nif.getIPaddr(),destip,DatagramType.TR,data);
			
		Logger.log(LogLevel.UDT,super.name + "[udt_send]:" + datagram.toString() + "[" + new String(data) + "]");
			
		// transmit on the network interface of the host
		nif.transmit(datagram);
			
	}
	
	// incolved by transport layer to obtain the datagram (if any) received by host 
	public byte[] udt_recv() {
		
		Logger.log(LogLevel.UDT,super.name + "[udt_recv]:" + data.toString() + "["+ new String(data) + "]");
		
		return data;
		
	}
	
	public void display() {
		
		Logger.log(LogLevel.NETWORK, "=================");
		Logger.log(LogLevel.NETWORK, "Host: " + super.name);
		displayInterface();
		
	}
	
	private void displayInterface() {
		
		nif.display();
		
	}

}
