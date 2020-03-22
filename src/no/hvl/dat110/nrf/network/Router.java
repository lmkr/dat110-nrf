package no.hvl.dat110.nrf.network;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.common.Logger;

public class Router extends Node {

	private ArrayList<Interface> interfaces;

	protected ConcurrentHashMap<IPAddress, Integer> forwardingtable;

	public Router(String name) {
		super(name);
		interfaces = new ArrayList<Interface>();
		forwardingtable = new ConcurrentHashMap<IPAddress,Integer>();
	}

	public void ifconfig(int id, IPAddress ipadr) {
		Interface nif = new Interface(this, id, super.name, ipadr);
		interfaces.add(nif);
	}

	public Interface getInterface(int nifid) {

		Interface ninterface = interfaces.stream().filter(nif -> nif.getIfId() == nifid).findFirst().orElse(null);

		return ninterface;
	}

	@Override
	public void start() {

		Logger.log(super.name + ": starting");
		interfaces.forEach(nif -> nif.start());
		Logger.log(super.name + ": started");

	}

	@Override
	public void stop() {

		Logger.log(super.name + ": stopping");

		interfaces.forEach(

				nif -> {

					try {

						nif.doStop();
						nif.join();

					} catch (InterruptedException ex) {
						Logger.log("Router[" + name + "]" + ex.getMessage());
						ex.printStackTrace();
					}
				});

		Logger.log(super.name + ": stopped");
	}

	public void addRoute(IPAddress ipadr, int nifid) {

		assert (ipadr != null);
		
		forwardingtable.put(ipadr, nifid);

	}

	public void deliver(Datagram datagram) {
		
		Logger.log(super.name + "[deliver]:" + datagram.toString());
		forward(datagram);
	}

	private void forward(Datagram datagram) {
		
		IPAddress dest = datagram.getDestination();

		int nifid = forwardingtable.get(dest);

		Interface ninterface = getInterface(nifid);

		if (ninterface != null) {
			Logger.log(super.name + "[forwarding[" + ninterface.getIfId() + "]:" + datagram.toString());
			ninterface.transmit(datagram);
		} else {
			Logger.log(super.name + "[no route]:" + datagram.toString());
		}
	}
}
