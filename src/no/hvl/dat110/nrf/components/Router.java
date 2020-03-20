package no.hvl.dat110.nrf.components;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.common.Logger;

public class Router extends Node {

	private ArrayList<Interface> interfaces;
	
	protected ConcurrentHashMap<IPAddress, Integer> routingtable;
	
	public Router(String name) {
		super(name);
	}

	public void ifadd (Interface nif) {
		interfaces.add(nif);
	}
	
	@Override
	public void start() {
		
		interfaces.forEach(nif -> nif.start());
	
	}

	@Override
	public void stop() {
		
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

	}

	public void addroute(int nifid, IPAddress ipadr) {
		
		routingtable.put(ipadr, nifid);
		
	}
	
	public void forward(Datagram datagram) {
		
		IPAddress dest = datagram.getDestination();
		
		int nifid = routingtable.get(dest);
		
		Interface ninterface = interfaces.stream().filter(nif -> nif.getIfId() == nifid).findFirst().orElse(null);
		
		if (ninterface != null) {
			ninterface.transmit(datagram);
		} else {
			Logger.log(super.name + "no route to destination" + datagram.toString());
		}
		
		
		// lookup and forward to the appropriate interface
	}
}
