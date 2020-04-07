package no.hvl.dat110.controlplane.linkstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Node;

public class LSDijkstra {

	// node for which the routing algorithm is executed
	// TODO. change this such that it implement the algorithm using int as nodes - perhaps using generics?
	// make it support both online and office line use such that it can be tested in isolation and still work 
	// as part of the LSDaemon.
	
	private Integer u;
	private NetworkGraph graph;

	private ArrayList<Integer> Nprime;
	private ArrayList<Integer> N;

	private HashMap<Integer, LSEntry> current; // TODO: array indexed by node index?

	HashMap<Integer,Integer> forwardingtable;
	
	public LSDijkstra(Integer u, NetworkGraph graph) {
		this.u = u;

		Nprime = new ArrayList<Integer>();
		N = new ArrayList<Integer>(graph.getNodes()); // TODO: change to using priority queue on distance

		this.graph = graph;
		current = new HashMap<Integer, LSEntry>();
		forwardingtable = new HashMap<Integer,Integer>();
	}

	private void showEntries() {

		current.forEach((v, entry) ->

		{
			Logger.lg(LogLevel.GRAPH, v + entry.toString());

		});

		Logger.log(LogLevel.GRAPH, "");
	}

	private void init() {

		Nprime.add(u);
		N.remove(u);

		current.put(u,new LSEntry(u,0));
		
		ArrayList<Integer> neighbours = graph.getNeighbours(u);

		N.forEach(v -> {

			int d = 1;
			int prev = u;
			
			LSEntry entry;

			if (!neighbours.contains(v)) {
				d = Integer.MAX_VALUE; // infinity
				prev = 0;
			}

			entry = new LSEntry(prev, d);
			current.put(v, entry);
		});

	}

	private Integer findMinNodeN() {
		
		assert(N.size() != 0); // assume not-empty since the method is called
		
		Integer n = N.get(new Integer(0)); // TODO: check
		int minD =  current.get(n).getD();
		
		Iterator<Integer> it = N.iterator();
		
		while (it.hasNext()) {
		
			Integer v = it.next();
			LSEntry ventry = current.get(v);
			
			if (ventry.getD() < minD) {
				
				n = v;
				
			}
		}
		
		return n;
	}

	private void loop() {

		while (N.size() > 0) {

			Integer w = findMinNodeN();

			Logger.log(LogLevel.GRAPH,"SELECT:" + w);
			
			// move node from N to Nprime
			Nprime.add(w);
			
			N.remove(w);

			graph.getNeighbours(w).forEach(v -> {

				if (!Nprime.contains(v)) {

					Logger.lg(LogLevel.GRAPH, "CHECK: " + v + "");
					
					int Dw = current.get(w).getD();

					LSEntry ventry = current.get(v);
					int Dv = ventry.getD();

					if (Dv > Dw + 1) {
						Logger.lg(LogLevel.GRAPH, "+");
						ventry.setD(Dw + 1);
						ventry.setPrev(w);
					}
					
					Logger.log(LogLevel.GRAPH, "");
					
				}

			});
			
			showEntries();
			
		}
	}

	private int findNextHop(int destnode) {
		
		int nexthop = 0;
		int prevnode = destnode; 
		
		Logger.log(LogLevel.GRAPH,Integer.toString(destnode));
		
		do {
			
			nexthop = prevnode;
			prevnode = current.get(prevnode).getPrev();
			
			Logger.log(LogLevel.GRAPH,prevnode + "->" + nexthop);
			
		
		} while (prevnode != u);
		
		return nexthop;
	}
	
	private HashMap<Integer,Integer> constructForwardingTable() {
		
		Nprime.forEach(v ->  {
			
			int nexthop = findNextHop(v);
			
			forwardingtable.put(v, nexthop);
			
		});
		
		return forwardingtable;
		
	}
	
	private void showForwardingTable() {
		
		Logger.log(LogLevel.GRAPH, "Forwarding table");
		
		forwardingtable.forEach(
				(v,nexthop) -> {
					
					Logger.log(LogLevel.GRAPH,v + "->" + nexthop);
					
				});
		
	}
	public void compute() {

		init();
		
		showEntries();
		
		loop();
		
		constructForwardingTable();
		
		showForwardingTable();
		
	}
}
