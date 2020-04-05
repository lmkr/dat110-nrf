package no.hvl.dat110.controlplane.distancevector;

import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.nrf.addressing.Datagram;

public class DVRouter extends DynamicRouter {

	public DVRouter(int routerid) {
		super(routerid);
	}
	
	@Override
	public void deliver(Datagram datagram) {

		// TODO: need to check on type to deliver correctly - may not be forward
		// Maybe move into the specific kind of router
		super.deliver(datagram);
		
	}
}
