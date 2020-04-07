package no.hvl.dat110.controlplane;

import java.util.ArrayList;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.network.Interface;
import no.hvl.dat110.nrf.network.Router;

public abstract class DynamicRouter extends Router {
	
	public DynamicRouter(int routerid) {
		super(routerid);
		nid = routerid;
	}
	
	// TODO: collect common parts in LS and DV Routers
	// TODO: collect common parts in LS and DV routing daemons
	
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