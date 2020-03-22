package no.hvl.dat110.nrf.addressing;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		IPAddress other = (IPAddress) obj;
		
		if (adr == null) {
			if (other.adr != null)
				return false;
		} else if (!adr.equals(other.adr))
			return false;
		
		return true;
	}
}
