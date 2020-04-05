package no.hvl.dat110.controlplane.linkstate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.network.Host;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Router;

class LSRoutingExample {

	private Network network;
	private Host H1,H4;
	private Router R2, R3, R5, R6;
	
	@BeforeEach
	void setUp() throws Exception {

		// Addressing scheme for interface on N1: N1.N2.N1.IFID
		network = new Network("LS Example Network");

		// nodes
		H1 = new Host(1);
		H1.ifconfig(1, new IPAddress("1.6.1.1"));
		H1.ifconfig(2, new IPAddress("1.2.1.2"));
		H1.ifconfig(3, new IPAddress("1.3.1.3"));
		
		R2 = new Router(2);
		R2.ifconfig(1, new IPAddress("2.3.2.1"));
		R2.ifconfig(2, new IPAddress("2.6.2.2"));
		R2.ifconfig(3, new IPAddress("1.2.2.3"));
		
		R3 = new Router(3);
		R3.ifconfig(1, new IPAddress("3.5.3.1"));
		R3.ifconfig(2, new IPAddress("3.4.3.2"));
		R3.ifconfig(3, new IPAddress("3.1.3.3"));
		R3.ifconfig(4, new IPAddress("2.3.3.4"));
		
		H4 = new Host(4);
		H4.ifconfig(1, new IPAddress("3.4.4.1"));
		H4.ifconfig(2, new IPAddress("4.5.4.2"));
		
		R5 = new Router(5);
		R5.ifconfig(1, new IPAddress("4.5.5.1"));
		R5.ifconfig(2, new IPAddress("3.5.5.2"));
		R5.ifconfig(3, new IPAddress("5.6.5.3"));
		
		R6 = new Router(6);
		R6.ifconfig(1, new IPAddress("2.6.6.1"));
		R6.ifconfig(2, new IPAddress("5.6.6.2"));
		R6.ifconfig(3, new IPAddress("1.3.6.3"));
		
		network.add(H1);
		network.add(R2);
		network.add(R3);
		network.add(H4);
		network.add(R5);
		network.add(R6);

		// communication links
		network.connect(H1, 1, R6, 3);
		network.connect(H1, 2, R2, 3);
		network.connect(H1, 3, R3, 3);
		
		network.connect(R2, 1, R3, 4);
		network.connect(R2, 2, R6, 1);

		network.connect(R3, 1, R5, 2);
		network.connect(R3, 2, H4, 1);
		
		network.connect(H4, 2, R5, 1);
		
		network.connect(R5, 3, R6, 2);
		
	}

	@AfterEach
	void tearDown() throws Exception {

		
	}
	
	@Test
	void test() {

		NetworkGraph graph = new NetworkGraph();
		
		graph.buildGraph(network);
		graph.printGraph();
		
		LSRouting ls = new LSRouting(H1,network);
		
		ls.compute();
	
	}
}
