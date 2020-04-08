package no.hvl.dat110.controlplane.linkstate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Router;

public class LSRoutingExample {

	private Network network;

	private Router R1, R2, R3;
	
	@BeforeEach
	void setUp() throws Exception {

		// Addressing scheme for interface on N1: N1.N2.N1.IFID
		network = new Network("DV Example Network");

		int N = 3;
		
		// nodes
	
		R1 = new LSRouter(1,N);
		R1.ifconfig(1, new IPAddress("1.0.0.1"));

		R2 = new LSRouter(2,N);
		R2.ifconfig(1, new IPAddress("1.0.1.1"));
		R2.ifconfig(2, new IPAddress("2.1.1.2"));
			
		R3 = new LSRouter(3,N);
		R3.ifconfig(1, new IPAddress("2.1.2.1"));

		network.addNode(R1);
		network.addNode(R2);
		network.addNode(R3);

		// communication links
		network.connect(R1, 1, R2, 1);
		network.connect(R2, 2, R3, 1);
	
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
			Thread.sleep(15000);
			
		} catch (InterruptedException ex) {

			System.out.println("Main test thread - example network " + ex.getMessage());
			ex.printStackTrace();
		}
	
	}
}

