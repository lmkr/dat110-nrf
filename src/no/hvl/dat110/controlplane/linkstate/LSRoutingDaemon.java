package no.hvl.dat110.controlplane.linkstate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.common.Stopable;

public class LSRoutingDaemon extends Stopable {

	private HashSet<Integer> nodes; // nodes from which nieghbour info has been received in teh flooding phase

	private DynamicRouter router;
	private LinkedBlockingQueue<LSNeighbourMsg> recvqueue;
	private NetworkGraph graph;
	
	private int N;
	
	private LSDijkstra ls = null; 
	
	public LSRoutingDaemon(DynamicRouter router, int N) {
		super("LS:" + router.getName());
		
		this.nodes = new HashSet<Integer>();
		this.router = router;
		this.graph = new NetworkGraph();
		this.N = N;
		this.recvqueue = new LinkedBlockingQueue<LSNeighbourMsg>();
	}
	

	@Override
	public void starting() {
		
		ArrayList<Integer> neighbours = router.getNeighbours();
		
		Integer[] nbrs = neighbours.toArray(new Integer[neighbours.size()]);
				
		// router adds itself to the graph
		graph.addNode(router.nid);
		graph.addNeighbours(router.nid, nbrs);
		
		nodes.add(router.nid);
		
		LSNeighbourMsg msg = new LSNeighbourMsg(router.nid,nbrs);
		
		Gson gson = new Gson();

		byte[] data = gson.toJson(msg).getBytes();
		
		router.broadcastAllInterfaces(DatagramType.LS, data);
		
	}
	
	
	@Override
	public void doProcess() {
	
		LSNeighbourMsg msg = null;
		
		try {

			msg = recvqueue.poll(2, TimeUnit.SECONDS);

			if (msg != null) { // something received

				Integer nid = msg.getNode();
				
				if (!nodes.contains(nid)) {
					
					nodes.add(nid);
					
					Integer[] neighbours = msg.getNeighbours();
					graph.addNode(nid);
					graph.addNeighbours(nid, neighbours);
					
					// pass message to all neighbours
					Gson gson = new Gson();

					byte[] data = gson.toJson(msg).getBytes();
					
					router.broadcastAllInterfaces(DatagramType.LS, data);
				}
				
			}

		} catch (InterruptedException ex) {
			System.out.println("LSRoutingDaemon thread " + ex.getMessage());
			ex.printStackTrace();
		}
		
		if (nodes.size() == N) {
			Logger.log(LogLevel.LS,"****Node u = " + router.nid + " flooding completed");
			doStop();
		}
	}
	
	@Override
	public void stopping () {
			
		this.ls = new LSDijkstra(router.nid,graph);
		
		ls.compute();
		
		ls.constructForwardingTable();
		
	}
	
	
	public void ls_recv(byte[] data) {
		
		String jsonmsg = new String(data);

		JsonParser jsonParser = new JsonParser();
		JsonObject json = jsonParser.parse(jsonmsg).getAsJsonObject();
		Gson gson = new Gson();

		LSNeighbourMsg msg = gson.fromJson(json, LSNeighbourMsg.class);
		
		try {

			recvqueue.put(msg);

		} catch (InterruptedException ex) {
			System.out.println("LSRecv thread " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public void display() {
		
		graph.display();
		ls.displayForwardingTable();
	}

}
