package no.hvl.dat110.controlplane;

import no.hvl.dat110.nrf.common.Stopable;

public abstract class RoutingDaemon extends Stopable {

	public RoutingDaemon(String name) {
		super(name);
	}
	
	public abstract void display();
	
	public abstract void recv(byte[] data);
}
