package no.hvl.dat110.nrf.network;

import java.util.ArrayList;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.dataplane.ForwardingTable;

public class Router extends Node {

	protected ArrayList<Interface> interfaces;
	protected ForwardingTable forwardingtable;

	public Router(int routerid) {
		super("R" + routerid,routerid);
		interfaces = new ArrayList<Interface>();
		forwardingtable = new ForwardingTable();
	}

	public void ifconfig(int id, IPAddress ipaddr) {
		Interface nif = new Interface(this, id, super.name, ipaddr);
		interfaces.add(nif);
	}

	public Interface getInterface(int nifid) {

		Interface ninterface = interfaces.stream().filter(nif -> nif.getIfId() == nifid).findFirst().orElse(null);

		return ninterface;
	}
	
	@Override
	public void start() {

		Logger.log(LogLevel.STARTSTOP,super.name + ": starting");
		
		interfaces.forEach(nif -> nif.start());
		
		Logger.log(LogLevel.STARTSTOP,super.name + ": started");

	}

	@Override
	public void stop() {

		Logger.log(LogLevel.STARTSTOP,super.name + ": stopping");

		// stop all interfaces
		interfaces.forEach(

				nif -> {

					try {

						nif.doStop();
						nif.join();

					} catch (InterruptedException ex) {
						Logger.log(LogLevel.ERROR,"Router[" + name + "]" + ex.getMessage());
						ex.printStackTrace();
					}
				});

		Logger.log(LogLevel.STARTSTOP,super.name + ": stopped");
	}

	public void addRoute(IPAddress ipaddr, int nifid) {

		assert (ipaddr != null);
		
		forwardingtable.addRoute(ipaddr, nifid);

	}

	public void deliver(Datagram datagram) {
		
		Logger.log(LogLevel.DELIVER,super.name + "[deliver]:" + datagram.toString());
		
		forward(datagram);
	}

	private void forward(Datagram datagram) {
		
		IPAddress dest = datagram.getDestination();

		Integer nifid = forwardingtable.match(dest);

		if (nifid != null) {
			
			Interface ninterface = getInterface(nifid);

			if (ninterface != null) {
				Logger.log(LogLevel.FORWARD,super.name + "[forwarding[interface:" + ninterface.getIfId() + "]]:" + datagram.toString());
				ninterface.transmit(datagram);
			} else {
				Logger.log(LogLevel.FORWARD,super.name + "[interface not found]:" + datagram.toString());
			}
		} else {
			Logger.log(LogLevel.FORWARD,super.name + "[no route not found]:" + datagram.toString());
		}
		
	}
	
	private void displayInterfaces() {
				
		interfaces.forEach(
					nif -> {
						nif.display();
					});
	}
	
	private void displayForwardingTable() {
		
		forwardingtable.display();
		
	}
	
	public void display() {
		
		Logger.log(LogLevel.NETWORK, "=================");
		Logger.log(LogLevel.NETWORK, "Router: " + super.name);
		displayInterfaces();
		displayForwardingTable();
	}
}
