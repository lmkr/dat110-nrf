package no.hvl.dat110.nfr.examples;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.addressing.Segment;
import no.hvl.dat110.nrf.network.Host;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Router;

class ExampleNetwork {

	private Network network;
	private Host H1,H2,H3;
	private Router R4, R5, R6, R7, R8;
	
	@BeforeEach
	void setUp() throws Exception {

		// Adressing scheme for interface on N1: N1.N2.N1.IFID
		network = new Network("Example Network");

		// hosts
		H1 = new Host("H1");
		H1.ifconfig(1, new IPAddress("1.1.1.1"));

		H2 = new Host("H2");
		H2.ifconfig(1, new IPAddress("2.3.2.1"));

		H3 = new Host("H3");
		H3.ifconfig(1, new IPAddress("3.4.3.1"));

		network.add(H1);
		network.add(H2);
		network.add(H3);

		// routers
		R4 = new Router("R4");
		R4.ifconfig(1, new IPAddress("1.1.4.1"));
		R4.ifconfig(2, new IPAddress("4.5.4.2"));
		R4.ifconfig(3, new IPAddress("4.7.4.3"));

		R5 = new Router("R5");
		R5.ifconfig(1, new IPAddress("4.5.5.1"));
		R5.ifconfig(2, new IPAddress("5.8.8.2"));

		R6 = new Router("R6");
		R6.ifconfig(1, new IPAddress("2.6.6.1"));
		R6.ifconfig(2, new IPAddress("6.7.6.2"));

		R7 = new Router("R7");
		R7.ifconfig(1, new IPAddress("4.7.7.1"));
		R7.ifconfig(2, new IPAddress("7.8.7.2"));
		R7.ifconfig(3, new IPAddress("6.7.7.3"));

		R8 = new Router("R8");
		R8.ifconfig(1, new IPAddress("5.8.5.1"));
		R8.ifconfig(2, new IPAddress("8.3.8.2"));
		R8.ifconfig(3, new IPAddress("7.8.8.3"));

		network.add(R4);
		network.add(R5);
		network.add(R6);
		network.add(R7);
		network.add(R8);

		// communication links
		network.connect(H1, 1, R4, 1);

		network.connect(R4, 2, R5, 1);
		network.connect(R4, 3, R7, 1);

		network.connect(R5, 2, R8, 1);
		network.connect(R8, 2, H3, 1);

		network.connect(R8, 3, R7, 2);
		network.connect(R7, 3, R6, 1);

		network.connect(R6, 1, H2, 1);

		network.connect(R4, 2, R5, 1);
		network.connect(R4, 2, R5, 1);
		network.connect(R4, 2, R5, 1);
		
		network.start();
	}

	@AfterEach
	void tearDown() throws Exception {

		network.stop();
	}

	@Test
	void test() {

		Segment segment = new Segment("En melding");
		
		H1.udt_send(segment, H3.getIPAddress());
		
		try {
			
			// let the forwarding of the segment take place
			Thread.sleep(10000);
			
		} catch (InterruptedException ex) {

			System.out.println("Main test thread - example network " + ex.getMessage());
			ex.printStackTrace();
		}
		
		assertEquals(segment.getPayload(),H3.udt_recv());
		
	}

}
