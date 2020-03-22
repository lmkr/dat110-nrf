package no.hvl.dat110.nrf.addressing;

public class IPAddress {

	private String addr;

	public IPAddress(String addr) {
		super();
		this.addr = addr;
	}

	public String getAddr() {
		return this.addr;
	}

	@Override
	public String toString() {
		return addr;
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
		
		if (addr == null) {
			if (other.addr != null)
				return false;
		} else if (!addr.equals(other.addr))
			return false;
		
		return true;
	}
}
