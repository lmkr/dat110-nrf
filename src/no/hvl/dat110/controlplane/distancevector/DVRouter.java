package no.hvl.dat110.controlplane.distancevector;

import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;

public class DVRouter extends DynamicRouter {

	// TODO: perhaps some of this could be moved into DynamicRouter
	// TODO: make DSRouter not having to rely on N
	
	private DVRoutingDaemon daemon;

	public DVRouter(int routerid, int N) {
		super(routerid);
		daemon = new DVRoutingDaemon(this, N);
	}

	@Override
	public void deliver(Datagram datagram) {

		if (datagram.getType() == DatagramType.DV) {
			daemon.dv_recv(datagram.getData());
		} else {
			super.deliver(datagram);
		}
	}

	@Override
	public void display() {
		
		Logger.log(LogLevel.DV,"Router:" + super.getName());
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
			Logger.log(LogLevel.ERROR, "DVRouter[" + name + "]" + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
