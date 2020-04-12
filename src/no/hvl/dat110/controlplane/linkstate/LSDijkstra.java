package no.hvl.dat110.controlplane.linkstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;

public class LSDijkstra {
	
	private Integer u; // node for which least-cost paths are being computed
	
	private NetworkGraph graph;

	private ArrayList<Integer> Nprime;
	private ArrayList<Integer> N;

	private HashMap<Integer, LSEntry> entries; 
	private HashMap<Integer,Integer> forwardingtable;

	private static int INF = Integer.MAX_VALUE;
	
	public LSDijkstra(Integer u, NetworkGraph graph) {
		this.u = u;

		Nprime = new ArrayList<Integer>();
		N = new ArrayList<Integer>(graph.getNodes()); 

		this.graph = graph;
		entries = new HashMap<Integer, LSEntry>();
		forwardingtable = new HashMap<Integer,Integer>();
	}

	private void displayEntries() {

		Logger.lg(LogLevel.LS, "Entries v[p(v),D(v)]:");
		entries.forEach((v, entry) ->

		{
			Logger.lg(LogLevel.LS, " " + v + entry.toString());

		});

		Logger.log(LogLevel.LS, "");
	}

	// find one of the nodes in N with smallest estimed distance to u
	private Integer findMinNodeN() {
		
		assert(N.size() != 0); // assume not-empty since the method is called
		
		Integer n = N.get(0); // TODO: check - is this correect?
		int minD =  entries.get(n).getD();
		
		Iterator<Integer> it = N.iterator();
		
		while (it.hasNext()) {
		
			Integer v = it.next();
			LSEntry ventry = entries.get(v);
			
			if (ventry.getD() < minD) {
				
				n = v;
				
			}
		}
		
		return n;
	}

	// initialisation step of Dijkstra's algorithm
	private void init() {

		Logger.log(LogLevel.LS, "Initialisation step [u=" + u + "]");
		
		// TODO: complete implementation of initialsiation setup in algorithm
		
		// display entries after initialisation
		displayEntries();
	}
	
	// utility method to get D(v) for a node v
	private int D(Integer v) {
		return entries.get(v).getD();
	}
	
	private void setD(Integer v, int distance) {
		entries.get(v).setD(distance);
	}
	
	// utility method to get D(v) for a node v
	
	public Integer p(Integer v) {
		return entries.get(v).getPrev();
	}
	
	public void setp(Integer v, Integer n) {
		entries.get(v).setPrev(n);
	}
	
	// iteration in Dikstra's algorithm
	private void loop() {

		Logger.log(LogLevel.LS, "Iteration step");
			
			// TODO - complete implementation of loop in Dikjstra's algorithm
			
			// Logger.log(LogLevel.LS,"Selected w=" + w + " |N|=" + N.size() + " |N'|=" + Nprime.size());
			
			// display entries after loop
			displayEntries();
			
		}

	private int findNextHop(int destnode) {
		
		int nexthop = 0;
		int prevnode = destnode; 
			
		do {
			
			nexthop = prevnode;
			prevnode = p(prevnode);
				
		} while (prevnode != u);
		
		return nexthop;
	}
	
	public void constructForwardingTable() {
		
		Logger.lg(LogLevel.LS, "Constructing forwarding table ...");
		 
		// TODO: complete construction of forwarding table based on distance vector, D(v), and p(v) information
	
		Logger.log(LogLevel.LS, "done");
		
	}
	
	public void displayForwardingTable() {
		
		Logger.log(LogLevel.LS, "R" + u + ": LS Forwarding table (dest -> next-hop)");
		
		forwardingtable.forEach(
				(v,nexthop) -> {
					
					Logger.log(LogLevel.LS,v + "->" + nexthop);
					
				});
		
	}
	public void compute() {

		init();
		
		loop();
				
	}
}
