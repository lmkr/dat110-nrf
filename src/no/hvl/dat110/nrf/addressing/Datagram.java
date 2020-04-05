package no.hvl.dat110.nrf.addressing;

public class Datagram {

	static int id = 1; // TODO: role of identifier? 
	
	private IPAddress source, destination;	
	private int identifier;
	private DatagramType type;
	
	private byte[] data;

	public Datagram(IPAddress source, IPAddress destination, DatagramType type, byte[] data) {
		super();
		this.source = source;
		this.destination = destination;
		this.data = data;
		this.type = type;

		id++;
	}
	
	public Datagram(IPAddress source, IPAddress destination, int id, DatagramType type, byte[] data) {
		super();
		this.source = source;
		this.destination = destination;
		this.data = data;
		this.type = type;
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
	
	public DatagramType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "Datagram [source=" + source + ", destination=" + destination + ", id=" + identifier + "type =" + type + ", data="
				+ data.toString() + "]";
	}

	public Datagram clone () {
		return new Datagram(this.source,this.destination,this.identifier, this.type, this.data.clone());
		
	}
}
