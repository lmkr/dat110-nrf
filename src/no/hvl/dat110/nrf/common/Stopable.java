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

		System.out.println(name + " starting");
		
		starting();
		
		System.out.println(name + " running");
		
		while (doCont()) {

			doProcess();
			
		}

		System.out.println(name + " stopping");

		starting();
		
		System.out.println(name + " stopped");
	}
}
