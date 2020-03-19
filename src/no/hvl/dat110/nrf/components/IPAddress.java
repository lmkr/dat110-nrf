package no.hvl.dat110.nrf.components;

public class IPAddress {

	private String adr;

	public IPAddress(String adr) {
		super();
		this.adr = adr;
	}

	public String getAdr() {
		return this.adr;
	}

	@Override
	public String toString() {
		return adr;
	}

	public boolean equals(IPAddress adr) {
		return this.adr.equals(adr.getAdr());
	}
}
