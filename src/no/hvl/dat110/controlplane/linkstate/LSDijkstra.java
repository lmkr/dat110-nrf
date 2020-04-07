package no.hvl.dat110.controlplane.linkstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Node;

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

	private void init() {

		Logger.log(LogLevel.LS, "Initialisation step");
		
		Nprime.add(u);
		N.remove(u);

		entries.put(u,new LSEntry(u,0));
		
		ArrayList<Integer> neighbours = graph.getNeighbours(u);

		N.forEach(v -> {

			int d = 1;
			int prev = u;
			
			LSEntry entry;

			if (!neighbours.contains(v)) {
				d = INF;  // infinity
				prev = 0;
			}

			entry = new LSEntry(prev, d);
			entries.put(v, entry);
		});

		displayEntries();
	}

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

	private void loop() {

		Logger.log(LogLevel.LS, "Iteration step " + N.size());
		
		while (N.size() > 0) {

			Integer w = findMinNodeN();
			N.remove(w);
			
			Logger.log(LogLevel.LS,"Selected w=" + w);
			
			// move node from N to Nprime
			Nprime.add(w);
			
			graph.getNeighbours(w).forEach(v -> {

				if (!Nprime.contains(v)) {
					
					int Dw = entries.get(w).getD();

					LSEntry ventry = entries.get(v);
					int Dv = ventry.getD();

					if (Dv > Dw + 1) {
						ventry.setD(Dw + 1);
						ventry.setPrev(w);
					}					
				}

			});
			
			displayEntries();
			
		}
	}

	private int findNextHop(int destnode) {
		
		int nexthop = 0;
		int prevnode = destnode; 
			
		do {
			
			nexthop = prevnode;
			prevnode = entries.get(prevnode).getPrev();
				
		} while (prevnode != u);
		
		return nexthop;
	}
	
	public void constructForwardingTable() {
		
		Logger.lg(LogLevel.LS, "Constructing forwarding table ..." + Nprime.size());
		 
		Nprime.forEach(v ->  {
			
			int nexthop = findNextHop(v);
			
			forwardingtable.put(v, nexthop);
			
		});
	
		Logger.log(LogLevel.LS, "done");
		
	}
	
	public void displayForwardingTable() {
		
		Logger.log(LogLevel.LS, "R" + u + ": Forwarding table (dest -> next-hop)");
		
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
