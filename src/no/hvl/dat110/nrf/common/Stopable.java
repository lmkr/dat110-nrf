package no.hvl.dat110.nrf.common;

public abstract class Stopable extends Thread {

	private boolean stop = false;
	protected String name;
	
	public Stopable(String name) {
		this.name = name;
	}
	
	public synchronized void doStop() {
		stop = true;
	}

	private synchronized boolean doCont() {
		return !stop;

	}

	public void starting() {
		
	}
	
	public void stopping() {
		
	}

	public abstract void doProcess();
	
	public void run() {

		Logger.log(LogLevel.STARTSTOP,name + " starting");
		
		starting();
		
		Logger.log(LogLevel.STARTSTOP,name + " running");
		
		while (doCont()) {

			doProcess();
			
		}

		Logger.log(LogLevel.STARTSTOP,name + " stopping");

		stopping();
		
		Logger.log(LogLevel.STARTSTOP,name + " stopped");
	}
}
