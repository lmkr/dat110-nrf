package no.hvl.dat110.nrf.network;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.dataplane.ForwardingTable;

public class Router extends Node {

	private ArrayList<Interface> interfaces;

	protected ForwardingTable forwardingtable;

	public Router(String name) {
		super(name);
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

		int nifid = forwardingtable.match(dest);

		Interface ninterface = getInterface(nifid);

		if (ninterface != null) {
			Logger.log(LogLevel.FORWARD,super.name + "[forwarding[" + ninterface.getIfId() + "]]:" + datagram.toString());
			ninterface.transmit(datagram);
		} else {
			Logger.log(LogLevel.FORWARD,super.name + "[no route found]:" + datagram.toString());
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
		Logger.log(LogLevel.NETWORK, "Host: " + super.name);
		displayInterfaces();
		displayForwardingTable();
	}
}
