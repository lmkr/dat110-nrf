package no.hvl.dat110.nrf.components;

public class Router extends Node {

	public Router(String name) {
		super(name);
	}
	
	@Override
	public void start() {
		
		// start all the configured interfaces

	}

	@Override
	public void stop() {
		// stop all the configure interfaces

	}

	public void addroute(IPAddress ipadr, int id) {
		
	}
	
	public void route(Datagram datagram) {
		
		// lookup and forward to the appropriate interface
	}
}
