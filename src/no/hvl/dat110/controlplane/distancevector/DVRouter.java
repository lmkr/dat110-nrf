package no.hvl.dat110.controlplane.distancevector;

import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;

public class DVRouter extends DynamicRouter {

	// TODO: perhaps some of this could be moved into DynamicRouter
	// TODO: make DSRouter not having to rely on N
	
	public DVRouter(int routerid, int N) {
		super(routerid,DatagramType.DV);
		register(new DVRoutingDaemon(this, N)); 
	}

}
