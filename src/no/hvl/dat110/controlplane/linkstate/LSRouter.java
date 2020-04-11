package no.hvl.dat110.controlplane.linkstate;


import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;

public class LSRouter extends DynamicRouter {

	private LSRoutingDaemon daemon;
	
	// TODO: make LSRouter not having to rely on N
	public LSRouter(int routerid, int N) {
		super(routerid);
		daemon = new LSRoutingDaemon(this,N);
	}

	@Override
	public void deliver(Datagram datagram) {

		if (datagram.getType() == DatagramType.LS) {
			daemon.ls_recv(datagram.getData());
		} else {
			super.deliver(datagram);
		}
	}
	
	@Override
	public void display() {
		
		super.display();
		Logger.log(LogLevel.LS,"LSRouter:" + super.nid);
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
			daemon.display();
			
		} catch (InterruptedException ex) {
			Logger.log(LogLevel.ERROR, "LSRouter[" + name + "]" + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
