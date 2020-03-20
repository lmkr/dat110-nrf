package no.hvl.dat110.nfr.examples;

import no.hvl.dat110.nrf.addressing.IPAddress;
import no.hvl.dat110.nrf.components.*;

public class ExampleNetwork {

	
	public static void main (String[] args) {
	
		// Adressing scheme for interface on N1: N1.N2.N1.IFID
		Network network = new Network("Example Network");
		
		// hosts
		Host H1 = new Host("H1");
		H1.ifconfig(1, new IPAddress("1.1.1.1"));
		
		Host H2 = new Host("H2");
		H2.ifconfig(1, new IPAddress("2.3.2.1"));
		
		Host H3 = new Host("H3");
		H3.ifconfig(1, new IPAddress("3.4.3.1"));
		
		// routers
		Router R4 = new Router("R4");
		R4.ifconfig(1, new IPAddress ("1.1.4.1"));
		R4.ifconfig(2, new IPAddress ("4.5.4.2"));
		R4.ifconfig(2, new IPAddress ("4.7.4.3"));
		
		Router R5 = new Router("R5");
		R5.ifconfig(1, new IPAddress ("4.5.5.1"));
		R5.ifconfig(2, new IPAddress ("5.8.8.2"));
		
		Router R6 = new Router("R6");
		R6.ifconfig(1, new IPAddress ("2.6.6.1"));
		R6.ifconfig(2, new IPAddress ("6.7.6.2"));
		
		Router R7 = new Router("R7");
		R7.ifconfig(1, new IPAddress ("4.7.7.1"));
		R7.ifconfig(2, new IPAddress ("7.8.7.2"));
		R7.ifconfig(3, new IPAddress ("6.7.7.3"));

		Router R8 = new Router("R8");
		R8.ifconfig(1, new IPAddress ("5.8.5.1"));
		R8.ifconfig(2, new IPAddress ("8.3.8.2"));
		R8.ifconfig(3, new IPAddress ("7.8.8.3"));

		// communication links
		network.connect(H1, 1,  R4, 1);
		
		network.connect(R4, 2,  R5, 1);
		network.connect(R4, 3,  R7, 1);
		
		network.connect(R5, 2,  R8, 1);
		network.connect(R8, 2,  H3, 1);
		
		network.connect(R8, 3,  R7, 2);
		network.connect(R7, 3,  R6, 1);
		
		network.connect(R6, 1,  H2, 1);
				
		network.connect(R4, 2,  R5, 1);
		network.connect(R4, 2,  R5, 1);
		network.connect(R4, 2,  R5, 1);
		
		// routes - TODO
		
		try {
			
			network.start();
			
			Thread.sleep(1000); 
			
			network.stop();
			
		} catch (InterruptedException ex) {

			System.out.println("Main thread " + ex.getMessage());
			ex.printStackTrace();
		}
			
	}
}
