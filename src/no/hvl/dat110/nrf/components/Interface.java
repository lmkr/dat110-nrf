package no.hvl.dat110.nrf.components;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.common.Stopable;

public class Interface extends Stopable {

	private Node node;
	
	private int id;
	private IPAddress ipadr;

	private Link incoming, outgoing;

	protected LinkedBlockingQueue<Datagram> inqueue;

	public Interface(int id, String name) {
		super(name + ": if " + id);
		this.id = id;
	}

	public int getIfId() {
		return this.id;
	}
	
	public IPAddress getIPadr() {
		return ipadr;
	}

	public void ipconfig(IPAddress ipadr) {
		this.ipadr = ipadr;
	}

	public void connect(Link incoming, Link outgoing) {
		this.incoming = incoming;
		this.outgoing = outgoing;
	}

	public void transmit(Datagram datagram) {
		outgoing.transmit(datagram);
	}

	public void receive(Datagram datagram) {

		try {

			inqueue.put(datagram);

		} catch (InterruptedException ex) {
			Logger.log(node.getName() + "if: " + id + ex.getMessage());
			ex.printStackTrace();
		}

	}

	public void doProcess() {

		Datagram datagram = null;

		try {

			datagram = inqueue.poll(2, TimeUnit.SECONDS);

		} catch (InterruptedException ex) {
			Logger.log(node.getName() + "if: " + id + ex.getMessage());
			ex.printStackTrace();
		}

		if (datagram != null) {
			node.forward(datagram);
		}
	}
}
