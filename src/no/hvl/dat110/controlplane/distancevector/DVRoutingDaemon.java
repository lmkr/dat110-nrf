package no.hvl.dat110.controlplane.distancevector;

import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.controlplane.RoutingDaemon;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;

import com.google.gson.*;

public class DVRoutingDaemon extends RoutingDaemon {

	private static int INF = 10000; // TODO: 10000 is considered infinite distance 
	private static int NONEXTHOP = -1; // value used for when not having a next hop
	
	private DynamicRouter router;

	private DVEntry[] distancevector; 

	public DVRoutingDaemon(DynamicRouter router, int N) {
		
		super("DV:" + router.getName());
		this.router = router;

		distancevector = new DVEntry[N];
	}

	@Override
	public void starting() {
		
		// setup initial distance vector for router
		for (int i = 0; i < distancevector.length; i++) {
			
			int dist = INF;
			int nexthop = NONEXTHOP;
			
			if (i == router.nid) { // next-hop to the node itself is known
				dist = 0;
				nexthop = i;
			}
			
			distancevector[i] = new DVEntry(dist, nexthop);
		}
	}
	
	@Override
	public void display() {
		
		Logger.log(LogLevel.DV,"Distance Vector (dest | distance | next-hop)");
		
		
		for (int i = 0; i<distancevector.length; i++) {
			
			DVEntry dventry = distancevector[i];
			
			int dist = dventry.getDistance();
			int nexthop = dventry.getNexthop();
					
			String distStr = Integer.toString(dist);
			if (dist == INF) {
				distStr = "INF";
			}
			
			String nexthopStr = Integer.toString(nexthop);
			if (nexthop == NONEXTHOP) {
				nexthopStr = "-";
			}
			
			Logger.log(LogLevel.DV, i + " | " + distStr + " | " + nexthopStr);
		}
	}
	
	private int D(int v) {
		return distancevector[v].getDistance();
	}
	
	private void updatedv(int v, int dist, int dest) {
		distancevector[v].update(dist, dest);
	}

	
	private void updateDV(int dest, int[] dv) {

		for (int v = 0; v < distancevector.length; v++) {

			if (D(v) > dv[v] + 1) {

				updatedv(v,dv[v] + 1, dest);

			}
		}
	}

	private DVMsg convertDVtoDVMsg() {

		int vector[] = new int[distancevector.length];

		for (int i = 0; i < distancevector.length; i++) {
			vector[i] = distancevector[i].getDistance();
		}

		DVMsg dv = new DVMsg(router.nid, vector);

		return dv;

	}

	public void doProcess() {

		byte[] data = convertDVToJson();

		router.broadcastAllInterfaces(DatagramType.DV, data);

		try {
			// let the forwarding of the segment take place
			Thread.sleep(100);

		} catch (InterruptedException ex) {

			System.out.println("Main thread - DV router daemon " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private byte[] convertDVToJson() {
		
		DVMsg dv = convertDVtoDVMsg();

		Gson gson = new Gson();

		byte[] data = gson.toJson(dv).getBytes();
		
		return data;
	}
	
	private DVMsg convertDVFromJson(byte[] data) {
	
		String jsonmsg = new String(data);

		JsonParser jsonParser = new JsonParser();
		JsonObject json = jsonParser.parse(jsonmsg).getAsJsonObject();
		Gson gson = new Gson();

		DVMsg dvmsg = gson.fromJson(json, DVMsg.class);
		
		return dvmsg;
	}
	
	// invoked when a distance vector is received from a neighbour
	public void recv(byte[] data) {

		DVMsg dv = convertDVFromJson(data);		

		int dest = dv.getNode();
		int[] vector = dv.getVector();

		updateDV(dest, vector);

	}
}
