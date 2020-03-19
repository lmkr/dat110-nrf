package no.hvl.dat110.nrf.components;

public class Datagram {

	private IPAddress source, destination;	
	private Segment payload;

	public Datagram(IPAddress source, IPAddress destination, Segment payload) {
		super();
		this.source = source;
		this.destination = destination;
		this.payload = payload;
	}

	public IPAddress getSource() {
		return source;
	}

	public IPAddress getDestination() {
		return destination;
	}

	public Segment getPayload() {
		return payload;
	}

	public void setPayload(Segment payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Datagram [src=" + source + ", dest=" + destination + ", [" + payload.toString() + "]";
	}	
	
	public Datagram clone () {
		return new Datagram(this.source,this.destination,this.payload.clone());
		
		//FIXME: clone strings?
	}
}
