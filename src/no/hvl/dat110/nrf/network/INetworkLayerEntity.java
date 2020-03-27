package no.hvl.dat110.nrf.network;

import no.hvl.dat110.nfr.examples.Segment;
import no.hvl.dat110.nrf.addressing.IPAddress;

public interface INetworkLayerEntity {

	// assume that only one message will be send from a host;
	public void udt_send(byte[] data, IPAddress destip);
	
	// assume only one message will be received by a host;
	public byte[] udt_recv();
	
}
