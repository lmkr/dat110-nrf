package no.hvl.dat110.nfr.examples;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.network.Host;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Router;

class SimpleNetwork {

	private Network network;
	private Host H1, H4;
	private Router R2, R3;

	@BeforeEach
	void setUp() throws Exception {

		// Adressing scheme for interface on N1: N1.N2.N1.IFID
		network = new Network("Example Network");

		// hosts
		H1 = new Host(1);
		H1.ifconfig(1, new IPAddress("1.2.1.1"));

		H4 = new Host(4);
		H4.ifconfig(1, new IPAddress("3.4.4.1"));

		network.addNode(H1);
		network.addNode(H4);

		// routers
		R2 = new Router(2);
		R2.ifconfig(1, new IPAddress("1.2.2.1"));
		R2.ifconfig(2, new IPAddress("2.3.2.2"));

		// routers
		R3 = new Router(3);
		R3.ifconfig(1, new IPAddress("2.3.3.1"));
		R3.ifconfig(2, new IPAddress("3.4.3.2"));
				
		network.addNode(R2);
		network.addNode(R3);
		
		// communication links
		network.connect(H1, 1, R2, 1);
		network.connect(R2, 2, R3, 1);
		network.connect(R3, 2, H4, 1);

		// routes / forwarding tables
		R2.addRoute(H1.getIPAddress(), 1);
		R2.addRoute(H4.getIPAddress(), 2);
		
		R3.addRoute(H1.getIPAddress(), 1);
		R3.addRoute(H4.getIPAddress(), 2);
		
		network.display();
		network.start();
	}

	@AfterEach
	void tearDown() throws Exception {

		network.stop();

	}

	@Test
	void testh1h3() {

		RoutingTestBase.test(H1, H4);
	}

	@Test
	void testh3h1() {

		RoutingTestBase.test(H4, H1);
	}
}
