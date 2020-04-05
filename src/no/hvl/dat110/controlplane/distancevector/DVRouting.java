package no.hvl.dat110.controlplane.distancevector;

import no.hvl.dat110.nrf.addressing.DatagramType;
import no.hvl.dat110.nrf.common.Stopable;
import no.hvl.dat110.nrf.network.Node;
import no.hvl.dat110.nrf.network.Router;

import com.google.gson.*;

public class DVRouting extends Stopable {

	private static int INF = Integer.MAX_VALUE;

	private Router router;

	private DVEntry[] ftable; // forwarding table

	public DVRouting(Router router, int N) {
		super("DV:" + router.getName());
		this.router = router;

		ftable = new DVEntry[N];

		for (int i = 0; i < N; i++) {
			int dist = -1;

			// TODO: if it is the node itslef, then distance is 0
			ftable[i] = new DVEntry(INF, dist);
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

		// TODO: fix node id
		DV dv = new DV(0, vector);

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

	// TODO: where to do the json conversion?
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
