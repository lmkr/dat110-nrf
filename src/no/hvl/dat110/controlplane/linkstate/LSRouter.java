package no.hvl.dat110.controlplane.linkstate;


import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.nrf.addressing.DatagramType;

public class LSRouter extends DynamicRouter {
	
	public LSRouter(int routerid, int N) {
		super(routerid,DatagramType.LS);
		register(new LSRoutingDaemon(this,N));
	}
}
