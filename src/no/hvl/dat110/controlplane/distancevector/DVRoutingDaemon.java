package no.hvl.dat110.controlplane.distancevector;

import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.common.LogLevel;
import no.hvl.dat110.nrf.common.Logger;
import no.hvl.dat110.nrf.common.Stopable;

import com.google.gson.*;

public class DVRoutingDaemon extends Stopable {

	private static int INF = 1000; // TODO: fix proper handling of INF-distance

	private DynamicRouter router;

	private DVEntry[] ftable; // forwarding table

	public DVRoutingDaemon(DynamicRouter router, int N) {
		
		super("DV:" + router.getName());
		this.router = router;

		ftable = new DVEntry[N];

		for (int i = 0; i < N; i++) {
			
			int dist = INF;
			int nexthop = -1;
			
			if (i == router.getId()) {
				dist = 0;
				nexthop = i;
			}
			
			ftable[i] = new DVEntry(dist, nexthop);
		}

	}

	public void display() {
		
		for (int i = 0; i<ftable.length; i++) {
			DVEntry dventry = ftable[i];
			Logger.log(LogLevel.DV, i + "|" + dventry.getDistance() + "|" + dventry.getNexthop());
		}
	}
	
	private void update(int dest, int[] dv) {

		for (int i = 0; i < ftable.length; i++) {

			DVEntry dventry = ftable[i];

			if (dventry.getDistance() > dv[i] + 1) {

				dventry.update(dv[i] + 1, dest);

			}
		}
	}

	private DV DVEntrytoDV() {

		int vector[] = new int[ftable.length];

		for (int i = 0; i < ftable.length; i++) {
			vector[i] = ftable[i].getDistance();
		}

		DV dv = new DV(router.getId(), vector);

		return dv;

	}

	public void doProcess() {

		DV dv = DVEntrytoDV();

		Gson gson = new Gson();

		byte[] data = gson.toJson(dv).getBytes();

		router.broadcastAllInterfaces(DatagramType.DV, data);

		try {
			// let the forwarding of the segment take place
			Thread.sleep(100);

		} catch (InterruptedException ex) {

			System.out.println("Main thread - DV router daemon " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// will be invoked whenever there is a distance vecrot is received from a
	// neighbour
	public void dv_recv(byte[] data) {

		Logger.log(LogLevel.DV, "DV_recv:" + router.getName());
		String jsonmsg = new String(data);

		JsonParser jsonParser = new JsonParser();
		JsonObject json = jsonParser.parse(jsonmsg).getAsJsonObject();
		Gson gson = new Gson();

		DV dv = gson.fromJson(json, DV.class);

		int dest = dv.getNode();
		int[] vector = dv.getVector();

		update(dest, vector);

	}
}
