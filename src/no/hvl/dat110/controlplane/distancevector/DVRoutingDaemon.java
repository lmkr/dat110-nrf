package no.hvl.dat110.controlplane.distancevector;

import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.controlplane.RoutingDaemon;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;

import com.google.gson.*;

public class DVRoutingDaemon extends RoutingDaemon {

	protected static int INF = 10000; // TODO: 10000 is considered infinite distance 
	protected static int NONEXTHOP = -1; // value used for when not having a next hop
	
	protected DynamicRouter router;

	protected DVEntry[] distancevector; 

	public DVRoutingDaemon(DynamicRouter router, int N) {
		
		super("DV:" + router.getName());
		this.router = router;

		distancevector = new DVEntry[N];
	}

	@Override
	public void starting() {
		
		// TODO
		// setup initial distance vector for router
		
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
	
	protected int D(int v) {
		return distancevector[v].getDistance();
	}
	
	protected void updatedv(int v, int dist, int dest) {
		distancevector[v].update(dist, dest);
	}

	protected void updateDV(int dest, int[] dv) {

		// TODO
		
		// implement update the distance vector for node based on received distance vector dv
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
