package no.hvl.dat110.nrf.common;

public class Logger {
	
	synchronized public static void log (boolean log,String message) {

		if (log) {
			System.out.println(message);
		}
	}
	
	synchronized public static void lg (boolean log,String message) {
		if (log) {
			System.out.print(message);
		}
	}
}
