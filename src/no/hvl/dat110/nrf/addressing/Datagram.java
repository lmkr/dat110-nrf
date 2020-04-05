package no.hvl.dat110.nrf.addressing;

public class Datagram {

	static int id = 1;
	
	private IPAddress source, destination;	
	private int identifier;
	private byte[] data;

	public Datagram(IPAddress source, IPAddress destination, byte[] data) {
		super();
		this.source = source;
		this.destination = destination;
		this.data = data;
		this.identifier = id;
		id++;
	}
	public Datagram(IPAddress source, IPAddress destination, byte[] data, int id) {
		super();
		this.source = source;
		this.destination = destination;
		this.data = data;
		this.identifier = id;
	}

	
	public IPAddress getSource() {
		return source;
	}

	public IPAddress getDestination() {
		return destination;
	}

	public byte[] getData() {
		return data;
	}
	
	
	@Override
	public String toString() {
		return "Datagram [source=" + source + ", destination=" + destination + ", id=" + identifier + ", data="
				+ data.toString() + "]";
	}

	public Datagram clone () {
		return new Datagram(this.source,this.destination,this.data.clone(),this.identifier);
		
	}
}
