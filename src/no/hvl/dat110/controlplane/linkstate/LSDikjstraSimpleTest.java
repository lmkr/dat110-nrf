package no.hvl.dat110.controlplane.linkstate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Router;

class LSDikjstraSimpleTest {

	private Network network;
	private Router R1, R2, R3;
	
	private NetworkGraph graph;
	private LSDijkstra ls;
	
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
	
	}

	void tearDown() throws Exception {

		
	}
	
	private void testNode(Integer node) {
		
		graph = new NetworkGraph(network);
		
		graph.display();
		
		ls = new LSDijkstra(node,graph);
		
		ls.compute();
		
		ls.constructForwardingTable();
		
		ls.displayForwardingTable();
		
	}
	
	@Test
	void test() {

		for (int i = 1; i<=3; i++) {
			
			testNode(i);
		}
	}
	
}
