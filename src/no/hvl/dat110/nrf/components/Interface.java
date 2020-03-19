package no.hvl.dat110.nrf.components;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import no.hvl.dat110.nrf.common.Stopable;

public class Interface extends Stopable {

	private int id;
	private IPAddress ipadr;
	private Node node;
	
	private Link incoming, outgoing;

	protected LinkedBlockingQueue<Datagram> inqueue;

	public Interface(int id, String name) {
		super("if:" + name);
		this.id = id;
	}

	public void configure(IPAddress ipadr) {
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
			System.out.println("TransportSender thread " + ex.getMessage());
			ex.printStackTrace();
		}

	}
	
	public void doProcess () {
		
		Datagram datagram = null;
		
		try {
			
			 datagram = inqueue.poll(2, TimeUnit.SECONDS);

		} catch (InterruptedException ex) {
			System.out.println("TransportReceiver RDT3 - doProcess " + ex.getMessage());
			ex.printStackTrace();
		}
		
		if (datagram != null) {
			node.route(datagram);
		}
	}
}
