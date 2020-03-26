package no.hvl.dat110.nfr.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import no.hvl.dat110.nrf.addressing.Segment;
import no.hvl.dat110.nrf.network.Host;

public class RoutingTestBase {

	static public void test(Host n1, Host n2) {
		
		Segment segment12 = new Segment(n1.getName() + "->" + n2.getName() + " melding");
		
		n1.udt_send(segment12.getBytes(), n2.getIPAddress());
		
		try {
			
			// let the forwarding of the segment take place
			Thread.sleep(5000);
			
		} catch (InterruptedException ex) {

			System.out.println("Main test thread - example network " + ex.getMessage());
			ex.printStackTrace();
		}
		
		assertEquals(segment12.getPayload(),n2.udt_recv().getPayload());
	}
}
