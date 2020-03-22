package no.hvl.dat110.nrf.network;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.addressing.Segment;

public interface INetworkLayerEntity {

	// assume that only one message will be send from a host;
	public void udt_send(Segment segment, IPAddress destip);
	
	// assume only one message will be received by a host;
	public Segment udt_recv();
	
}
