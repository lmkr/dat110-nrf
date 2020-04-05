package no.hvl.dat110.controlplane.distancevector;

import no.hvl.dat110.controlplane.DynamicRouter;
import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.common.Stopable;

import com.google.gson.*;

public class DVRouting extends Stopable {

	private static int INF = Integer.MAX_VALUE;

	private DynamicRouter router;

	private DVEntry[] ftable; // forwarding table

	public DVRouting(DynamicRouter router, int N) {
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
			Thread.sleep(5000);

		} catch (InterruptedException ex) {

			System.out.println("Main test thread - example network " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// will be invoked whenever there is a distance vecrot is received from a
	// neighbour
	public void dv_recv(byte[] payload) {

		String jsonmsg = new String(payload);

		JsonParser jsonParser = new JsonParser();
		JsonObject json = jsonParser.parse(jsonmsg).getAsJsonObject();
		Gson gson = new Gson();

		DV dv = gson.fromJson(json, DV.class);

		int dest = dv.getNode();
		int[] vector = dv.getVector();

		update(dest, vector);

	}
}
