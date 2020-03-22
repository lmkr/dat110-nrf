package no.hvl.dat110.nrf.addressing;

public class Datagram {

	private IPAddress source, destination;	
	private Segment segment;

	public Datagram(IPAddress source, IPAddress destination, Segment segment) {
		super();
		this.source = source;
		this.destination = destination;
		this.segment = segment;
	}

	public IPAddress getSource() {
		return source;
	}

	public IPAddress getDestination() {
		return destination;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setPayload(Segment payload) {
		this.segment = payload;
	}

	@Override
	public String toString() {
		return "Datagram [src=" + source + ", dest=" + destination + ", [" + segment.toString() + "]";
	}	
	
	public Datagram clone () {
		return new Datagram(this.source,this.destination,this.segment.clone());
		
		//FIXME: clone strings?
	}
}
