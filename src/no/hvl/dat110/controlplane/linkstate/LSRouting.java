package no.hvl.dat110.controlplane.linkstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.network.Network;
import no.hvl.dat110.nrf.network.Node;

public class LSRouting {

	// node for which the routing algorithm is executed
	// TODO. change this such that it implement the algorithm using int as nodes - perhaps using generics?
	// make it support both online and office line use such that it can be tested in isolation and still work 
	// as part of the LSDaemon.
	
	private Node u;
	private NetworkGraph graph;

	private ArrayList<Node> Nprime;
	private ArrayList<Node> N;

	private HashMap<Node, LSEntry> current; // TODO: array indexed by node index?

	HashMap<Node,Node> forwardingtable;
	
	public LSRouting(Node u, Network network) {
		this.u = u;
		
		Nprime = new ArrayList<Node>();
		N = new ArrayList<Node>(network.getNodes()); // TODO: change to using priority queue on distance

		graph = new NetworkGraph(network);
		current = new HashMap<Node, LSEntry>();
		forwardingtable = new HashMap<Node,Node>();
	}

	private void showEntries() {

		// TODO: sort by node
		current.forEach((v, entry) ->

		{
			Logger.lg(LogLevel.GRAPH, v.getName() + entry.toString());

		});

		Logger.log(LogLevel.GRAPH, "");
	}

	private void init() {

		Nprime.add(u);
		N.remove(u);

		current.put(u,new LSEntry(u,0));
		
		// TODO: use set for neighbours?
		ArrayList<Node> neighbours = graph.getNeighbours(u);

		N.forEach(v -> {

			int d = 1;
			Node prev = u;
			
			LSEntry entry;

			if (!neighbours.contains(v)) {
				d = Integer.MAX_VALUE; // infinity
				prev = null;
			}

			entry = new LSEntry(prev, d);
			current.put(v, entry);
		});

	}

	private Node findMinNodeN() {
		
		assert(N.size() != 0); // assume not-empty since the method is called
		
		Node n = N.get(0); 
		int minD =  current.get(n).getD();
		
		Iterator<Node> it = N.iterator();
		
		while (it.hasNext()) {
		
			Node v = it.next();
			LSEntry ventry = current.get(v);
			
			if (ventry.getD() < minD) {
				
				n = v;
				
			}
		}
		
		return n;
	}

	private void loop() {

		while (N.size() > 0) {

			Node w = findMinNodeN();

			Logger.log(LogLevel.GRAPH,"SELECT:" + w.getName());
			
			// move node from N to Nprime
			Nprime.add(w);
			N.remove(w);

			graph.getNeighbours(w).forEach(v -> {

				if (!Nprime.contains(v)) {

					Logger.lg(LogLevel.GRAPH, "CHECK: " + v.getName() + "");
					
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

	private Node findNextHop(Node destnode) {
		
		Node nexthop = null;
		Node prevnode = destnode; 
		
		Logger.log(LogLevel.GRAPH,destnode.getName());
		
		do {
			
			nexthop = prevnode;
			prevnode = current.get(prevnode).getPrev();
			
			Logger.log(LogLevel.GRAPH,prevnode.getName() + "->" + nexthop.getName());
			
		
		} while (prevnode != u);
		
		return nexthop;
	}
	
	private HashMap<Node,Node> constructForwardingTable() {
		
		Nprime.forEach(v ->  {
			
			Node nexthop = findNextHop(v);
			
			forwardingtable.put(v, nexthop);
			
		});
		
		return forwardingtable;
		
	}
	
	private void showForwardingTable() {
		
		Logger.log(LogLevel.GRAPH, "Forwarding table");
		
		forwardingtable.forEach(
				(v,nexthop) -> {
					
					Logger.log(LogLevel.GRAPH,v.getName() + "->" + nexthop.getName());
					
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
