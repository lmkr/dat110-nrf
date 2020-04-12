package no.hvl.dat110.controlplane;

import java.util.ArrayList;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.network.Interface;
import no.hvl.dat110.nrf.network.Router;

public abstract class DynamicRouter extends Router {
	
	private RoutingDaemon daemon;
	private DatagramType type;
	
	public DynamicRouter(int routerid,DatagramType type) {
		super(routerid);
		nid = routerid;
		this.type = type;
	}
	
	protected void register (RoutingDaemon daemon) {
		this.daemon = daemon;
	
	}
	
	@Override
	public void deliver(Datagram datagram) {

		if (datagram.getType() == type) {
			daemon.recv(datagram.getData());
		} else {
			super.deliver(datagram);
		}
	}

	@Override
	public void display() {
		
		Logger.log(LogLevel.DV || LogLevel.LS,"Router:" + super.getName());
		daemon.display();
	}
	
	@Override
	public void start() {

		super.start();
		daemon.start();

	}

	@Override
	public void stop() {
		super.stop();
		daemon.doStop();

		try {
			daemon.join();
		} catch (InterruptedException ex) {
			Logger.log(LogLevel.ERROR, "Router[" + name + "]" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public ArrayList<Integer> getNeighbours() {
		
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		
		for (Interface intface : interfaces) {
			
			Integer nid = intface.getPort().getOutgoing().getDest().getNode().nid;
			neighbours.add(nid);
		}
		
		return neighbours;
	}
	
	public void broadcastAllInterfaces(DatagramType type, byte[] data) {
		
		for (Interface intface : interfaces) {
			
			IPAddress ipsrc = intface.getIPaddr();
			IPAddress ipdest = intface.getPort().getOutgoing().getDest().getIPaddr();
			
			Datagram datagram = new Datagram(ipsrc, ipdest, type, data);
			intface.transmit(datagram);
		}
	}
	
}