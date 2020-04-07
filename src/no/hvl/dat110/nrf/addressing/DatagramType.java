package no.hvl.dat110.nrf.addressing;

public enum DatagramType {

	TR, // datagram carrying transport layer data
	DV, // datagram carrying distance-vector routing information
	LS; // datagram carrying link-state routing information
}
