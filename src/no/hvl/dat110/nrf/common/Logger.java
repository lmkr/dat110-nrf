package no.hvl.dat110.nrf.common;

public class Logger {
	
	synchronized public static void log (boolean log,String message) {

		if (log) {
			System.out.println(message);
		}
	}
}
