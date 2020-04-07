package no.hvl.dat110.nrf.dataplane;

import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.common.*;

public class ForwardingTable {

	protected ConcurrentHashMap<IPAddress, Integer> table;

	public ForwardingTable() {
		table = new ConcurrentHashMap<IPAddress, Integer>();
	}

	public void addRoute(IPAddress ipaddr, int nifid) {
		table.put(ipaddr, nifid);
	}

	public int match(IPAddress dest) {

		return table.get(dest);
	}

	public void display() {

		Logger.log(LogLevel.NETWORK, "Forwarding table (dest,next-hop interface)");

		table.forEach((ipaddr, nifid) -> Logger.log(LogLevel.NETWORK, ipaddr.toString() + "->" + nifid));
	}

}
