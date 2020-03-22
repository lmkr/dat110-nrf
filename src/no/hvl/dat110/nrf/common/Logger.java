package no.hvl.dat110.nrf.common;

public class Logger {

	synchronized public static void log (String message) {
		
		System.out.println(message);
	}
}
