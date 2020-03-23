package no.hvl.dat110.nrf.network;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import no.hvl.dat110.nrf.addressing.Datagram;
import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.common.Stopable;

public class Interface extends Stopable {

	private Node node;
	
	private int id;
	private IPAddress ipaddr;

	private Port port;
	
	protected LinkedBlockingQueue<Datagram> inqueue;

	public Interface(Node node, int id, String name, IPAddress ipaddr) {
		super(name + ": interface[" + id + "]");
		this.node = node;
		this.id = id;
		this.ipaddr = ipaddr;
		inqueue = new LinkedBlockingQueue<Datagram>();
	}

	public int getIfId() {
		return this.id;
	}
	
	public IPAddress getIPaddr() {
		return ipaddr;
	}

	public Port getPort () {
		
		return port;
	}
	
	public void ipconfig(IPAddress ipadr) {
		this.ipaddr = ipadr;
	}

	public void connect(Link incoming, Link outgoing) {
		this.port = new Port(incoming,outgoing);
	}

	public void transmit(Datagram datagram) {
		port.transmit(datagram);
	}

	public void receive(Datagram datagram) {

		try {

			inqueue.put(datagram);

		} catch (InterruptedException ex) {
			Logger.log(LogLevel.ERROR,node.getName() + "if: " + id + ex.getMessage());
			ex.printStackTrace();
		}

	}

	public void doProcess() {

		Datagram datagram = null;

		try {

			datagram = inqueue.poll(2, TimeUnit.SECONDS);

		} catch (InterruptedException ex) {
			Logger.log(LogLevel.ERROR,node.getName() + "if: " + id + ex.getMessage());
			ex.printStackTrace();
		}

		if (datagram != null) {
			node.deliver(datagram);
		}
	}
	
	public void display() {
						
		IPAddress destip = port.getOutgoing().getDest().getIPaddr();
		
		Logger.log(
				LogLevel.NETWORK, "if[" + id + "][" + ipaddr + "<->" + destip.toString() + "]");
						
	}
}
