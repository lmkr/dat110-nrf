package no.hvl.dat110.controlplane.distancevector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Router;

public class DVRoutingExample {

	private Network network;

	private Router R0, R1, R2;
	
	@BeforeEach
	void setUp() throws Exception {

		// Addressing scheme for interface on N1: N1.N2.N1.IFID
		network = new Network("DV Example Network");

		int N = 3;
		
		// nodes
	
		R0 = new DVRouter(0,N);
		R0.ifconfig(1, new IPAddress("1.0.0.1"));

		R1 = new DVRouter(1,N);
		R1.ifconfig(1, new IPAddress("1.0.1.1"));
		R1.ifconfig(2, new IPAddress("2.1.1.2"));
			
		R2 = new DVRouter(2,N);
		R2.ifconfig(1, new IPAddress("2.1.2.1"));

		network.add(R0);
		network.add(R1);
		network.add(R2);

		// communication links
		network.connect(R0, 1, R1, 1);
		network.connect(R1, 2, R2, 1);
	
		network.start();
		network.display();
	}

	@AfterEach
	void tearDown() throws Exception {

		network.stop();
		network.display();
	}
	
	@Test
	void test() {
		
		try {
			
			// let the routing run for a while
			Thread.sleep(5000);
			
		} catch (InterruptedException ex) {

			System.out.println("Main test thread - example network " + ex.getMessage());
			ex.printStackTrace();
		}
	
	}
}
