package no.hvl.dat110.nrf.addressing;

public class Datagram {

	private IPAddress source, destination;	
	private byte[] data;

	public Datagram(IPAddress source, IPAddress destination, byte[] data) {
		super();
		this.source = source;
		this.destination = destination;
		this.data = data;
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
		return "Datagram [src=" + source + ", dest=" + destination + ", [" + data.toString() + "]";
	}	
	
	public Datagram clone () {
		return new Datagram(this.source,this.destination,this.data.clone());
		
	}
}
