package no.hvl.dat110.controlplane;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.network.Interface;
import no.hvl.dat110.nrf.network.Router;

public abstract class DynamicRouter extends Router {

	private int routerid;
	
	public DynamicRouter(int routerid) {
		super(routerid);
		this.routerid = routerid;
	}
	
	// TODO: also needs routingdaemon adn an object variable
	
	public int getId() {
		return routerid;
	}
	
	public void broadcastAllInterfaces(DatagramType type, byte[] data) {
		
		// TODO: need to set type
		for (Interface intface : interfaces) {
			
			IPAddress ipsrc = intface.getIPaddr();
			IPAddress ipdest = intface.getPort().getOutgoing().getDest().getIPaddr();
			
			Datagram datagram = new Datagram(ipsrc, ipdest, type, data);
			intface.transmit(datagram);
		}
	}
	
}