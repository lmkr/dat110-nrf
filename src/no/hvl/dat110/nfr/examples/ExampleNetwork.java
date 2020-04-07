package no.hvl.dat110.nfr.examples;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.network.Host;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Router;

class ExampleNetwork {

	private Network network;
	private Host H1,H2,H3;
	private Router R4, R5, R6, R7, R8;
	
	@BeforeEach
	void setUp() throws Exception {

		// Addressing scheme for interface on N1: N1.N2.N1.IFID
		network = new Network("Example Network");

		// hosts
		H1 = new Host(1);
		H1.ifconfig(1, new IPAddress("1.4.1.1"));

		H2 = new Host(2);
		H2.ifconfig(1, new IPAddress("2.6.2.1"));

		H3 = new Host(3);
		H3.ifconfig(1, new IPAddress("3.8.3.1"));

		network.addNode(H1);
		network.addNode(H2);
		network.addNode(H3);

		// routers
		R4 = new Router(4);
		R4.ifconfig(1, new IPAddress("1.4.4.1"));
		R4.ifconfig(2, new IPAddress("4.5.4.2"));
		R4.ifconfig(3, new IPAddress("4.7.4.3"));

		R5 = new Router(5);
		R5.ifconfig(1, new IPAddress("4.5.5.1"));
		R5.ifconfig(2, new IPAddress("5.8.5.2"));

		R6 = new Router(6);
		R6.ifconfig(1, new IPAddress("2.6.6.1"));
		R6.ifconfig(2, new IPAddress("6.7.6.2"));

		R7 = new Router(7);
		R7.ifconfig(1, new IPAddress("4.7.7.1"));
		R7.ifconfig(2, new IPAddress("7.8.7.2"));
		R7.ifconfig(3, new IPAddress("6.7.7.3"));

		R8 = new Router(8);
		R8.ifconfig(1, new IPAddress("5.8.5.1"));
		R8.ifconfig(2, new IPAddress("3.8.3.2"));
		R8.ifconfig(3, new IPAddress("7.8.8.3"));

		network.addNode(R4);
		network.addNode(R5);
		network.addNode(R6);
		network.addNode(R7);
		network.addNode(R8);

		// communication links
		network.connect(H1, 1, R4, 1);

		network.connect(R4, 2, R5, 1);
		network.connect(R4, 3, R7, 1);

		network.connect(R5, 2, R8, 1);
		
		network.connect(R8, 2, H3, 1);
		network.connect(R8, 3, R7, 2);
		
		network.connect(R7, 3, R6, 2);

		network.connect(R6, 1, H2, 1);
	
		// routes		
		R4.addRoute(H1.getIPAddress(),1);
		R4.addRoute(H2.getIPAddress(),3);
		R4.addRoute(H3.getIPAddress(),2);

		R5.addRoute(H1.getIPAddress(),1);
		R5.addRoute(H2.getIPAddress(),1);
		R5.addRoute(H3.getIPAddress(),2);

		R6.addRoute(H1.getIPAddress(),2);
		R6.addRoute(H2.getIPAddress(),1);
		R6.addRoute(H3.getIPAddress(),2);

		R7.addRoute(H1.getIPAddress(),1);
		R7.addRoute(H2.getIPAddress(),3);
		R7.addRoute(H3.getIPAddress(),2);

		R8.addRoute(H1.getIPAddress(),1);
		R8.addRoute(H2.getIPAddress(),3);
		R8.addRoute(H3.getIPAddress(),2);

		network.display();
		network.start();
	}

	@AfterEach
	void tearDown() throws Exception {

		network.stop();
	}
	
	@Test
	void test_h1h2() {

		RoutingTestBase.test(H1,H2);
	}
	
	@Test
	void test_h2h1() {

		RoutingTestBase.test(H2,H1);
	}
	
	@Test
	void test_h1h3() {

		RoutingTestBase.test(H1,H3);
	}
	
	@Test
	void test_h3h1() {

		RoutingTestBase.test(H3,H1);
	}
	
	
	@Test
	void test_h2h3() {

		RoutingTestBase.test(H2,H3);
	}
	
	@Test
	void test_h3h2() {

		RoutingTestBase.test(H2,H3);
	}
}
