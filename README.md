### Network Layer Routing Framework (NRF)

The NRF framework is an implementation in Java of a virtual network layer that makes it possible to experiment with the control- and dataplane of the network layer, including the functionality of addressing, network interfaces, routes, datagram forwarding, forwarding tables, and routing algorithms.

#### Exercise 1 - Getting started and cloning the framework

For an introduction to the basic concepts of the NRF framework you should watch the video:

- *Introduction to the Network Routing Framework (NRF)* available from Canvas under "Emnets mediafiler"

For an introduction to the implementation you should watch the video:

- *Demonstration of the Network Routing Framework (NRF)* available from Canvas under "Emnets mediafiler"

The NRF framework is organised into two git-repositories located at:

- https://github.com/lmkr/dat110-nrf.git (framework implementation)
- https://github.com/lmkr/dat110-nrt-exercises.git (example networks and tests)

Clone the two repositories and import the corresponding Java projects into your IDE.

#### Exercise 2 - Testing an example network

The package `no.hvl.dat110.nrf.staticrouting` in the `dat110-nrf-exercises` project contains several examples of networks with hosts and routers. In this exercise you will consider the network in the class `SimpleNetwork.java` comprised of two hosts connected by two routers. This is the example that was considered in the introduction video:

![](assets/markdown-img-paste-20200413121837719.png)

Select the class `SimpleNetwork.java` (which constitute a unit-test) and run it as a unit test. The test will send a datagram through the network from H1 to H4 and a datagram from H4 to H1 and then check that the datagram is being correctly received at the destination.

Observe the output in the console. Which route through the network does a datagram follow from host H1 to host H4, and from host H4 to host H1.

#### Exercise 3 - Augment the example network

Augment the network simple network from exercise 2 such that it now has the topology shown in the figure below. The labels indicate the IP address to be configured for each of the interfaces of the hosts and routers.

![](assets/markdown-img-paste-2020041312153987.png)

Remember that you also have to add routes to the routers R2-R6 such that datagrams can be transmitted between host H1 and host H4.

Run the test sending datagrams between H1 and H4 and check that datagrams are correctly routed between H1 and H4.

Try to modify the routes such that a datagram from H1 to H4 is send via R2, R3, R5 while a datagram from H4 to H1 is sent via R5, R6, R4, and R3.

#### Exercise 4 - Routing loops and hop count

Misconfiguration of a forwarding table may result in routing-loops where a datagrams keeps being routing along a cycle in the network and never reaches its destination.

Modify the routes configured for the network in Exercise 3 such that a datagram from H1 to H4 will keep being forwarding in a loop comprises of routers R3, R5, R6, R3. Does the datagram reach its destination?

To detect such situation, IP datagrams are equipped with a *hop-count* (time-to-live) value in its header which is decremented each time the datagram is being forwarded by a router.

Augment the class `Datagram.java` such that datagram now also carries a hop count. This hop count should be set to *10* when a hosts sends the datagrams and decremented each time a router forwards the datagram. If a router receives a datagram with a hop count of 0, then it should discard the datagram.

The forwarding of datagram in NRF is implemented in the `forward`-method of the `Router.java` class.

#### Exercise 5 - Routing errors and control messages

A better solution to routing loops than just discarding the datagram is to send a message to the source of the datagram, i.e., the host that send the datagram. This is one of the roles of the Internet Control Message Protocol (ICMP).

Augment the NRF framework such that if a router detects a datagram with a hop count of 0, then it will send an ICMP message back to the source host indicating that the hop-count limit was reached.

#### Exercise 6 - Longest Prefix Matching

Modify the implementation of NRF such that network prefixes can be used in the routing tables and such that longest prefix matching is being used to find the entry in the forwarding table that determines on which interface a datagram is being forwarded.

#### Exercise 7 - Implementation of link-state routing algorithm

The aim of this assignment is to implement Dijkstra's algorithm as specified on page 408 in the networking book.

##### 7.1: Start-code

The NRF framework contains start code for implementing the algorithm via the `no.hvl.dat110.control` package and the `no.hvl.dat110.controlplane.linkstate` package.

The start of an implementation can be found in the class `LSDikjstra.java`:

```java
public class LSDijkstra {

	protected Integer u; // node for which least-cost paths are being computed

	protected NetworkGraph graph;

	protected ArrayList<Integer> Nprime;
	protected ArrayList<Integer> N;

	protected HashMap<Integer, LSEntry> entries;
	protected HashMap<Integer,Integer> forwardingtable;
  ...

```

Nodes in the network graph are represented as integers. The object variable `graph` represent the network graph, and `Nprime` and `N` is used for representing the two accordingly named sets of nodes in the algorithm.

The object variable `entries` is used to keep track of the currently estimated distance to each node in the network and the predecessor node on the path leading to this node.

The `forwardingtable` is to be used for storing the forwarding table calculated as the last step each node.

The class already implements utility methods for accessing and setting `D(v)`` and `p(v)`` from the specification of the algorithm:

```java
protected int D(Integer v) {
  return entries.get(v).getD();
}

protected void setD(Integer v, int distance) {
  entries.get(v).setD(distance);
}

protected Integer p(Integer v) {
  return entries.get(v).getPrev();
}

protected void setp(Integer v, Integer n) {
  entries.get(v).setPrev(n);
}
```

The class in addition contains an implementation of the method `findMinNode()` which can find a node in `N` with the currently smallest estimated distance.

##### 7.2: Implementation

Your first task is to complete the implementation of the methods `protected void init()` and `protected void loop()` corresponding to the initalisation step and the loop step in Dikjstra's algorithm.

All weights of the edges in the graph in the network can be assumed to be 1, i.e., all edges in the network have the same (unit) cost.

The method `graph.getNeighbours(v)`` can be used to obtain the neighbour nodes of a node `v`

##### 7.2: Forwarding table

The second task is to construct the forwarding tables based on the least-cost paths computed by Dikjstra's algorithm.

This is to be done by implementing the following two methods:

```java
protected int findNextHop(int destnode) {

		// TODO search backwards in predecessor to find next hop for node
		return 0;
	}

	public void constructForwardingTable() {

		Logger.lg(LogLevel.LS, "Constructing forwarding table ...");

		// TODO: complete construction of forwarding table based on distance vector, D(v), and p(v) information

		Logger.log(LogLevel.LS, "done");

	}
```

The `findNextHop`-method must use the predecessor information `p(v)` computed for each destination to find the next hop on a route to the destination.

The `constructForwardingTable`-method must insert a next-hop into the forwarding table for each destination (node in the network) using the `put`-method on the forwarding table.

##### 7.3: Testing the Implementation

The class `LSRoutingExample.java` in the NRF exercises project contains a unit-test for testing the implementation.

The test network consists of three routers R1, R2, and R3 where R1 is connected to R2 and R2 is connected to R3.

If the implementation is correct, then the console output when running the test should look similar to the following:

```
LS example network running
Initialisation step [u=2]
Entries v[p(v),D(v)]: 1[2,1] 2[2,0] 3[2,1]
Iteration step
Selected w=1 |N|=1 |N'|=2
Entries v[p(v),D(v)]: 1[2,1] 2[2,0] 3[2,1]
Selected w=3 |N|=0 |N'|=3
Entries v[p(v),D(v)]: 1[2,1] 2[2,0] 3[2,1]
Constructing forwarding table ...done
Initialisation step [u=3]
Entries v[p(v),D(v)]: 1[-,INF] 2[3,1] 3[3,0]
Iteration step
Selected w=2 |N|=1 |N'|=2
Entries v[p(v),D(v)]: 1[2,2] 2[3,1] 3[3,0]
Selected w=1 |N|=0 |N'|=3
Entries v[p(v),D(v)]: 1[2,2] 2[3,1] 3[3,0]
Constructing forwarding table ...done
Initialisation step [u=1]
Entries v[p(v),D(v)]: 1[1,0] 2[1,1] 3[-,INF]
Iteration step
Selected w=2 |N|=1 |N'|=2
Entries v[p(v),D(v)]: 1[1,0] 2[1,1] 3[2,2]
Selected w=3 |N|=0 |N'|=3
Entries v[p(v),D(v)]: 1[1,0] 2[1,1] 3[2,2]
Constructing forwarding table ...done
LS example network stopping
Router:R1
R1: LS Forwarding table (dest -> next-hop)
1->1
2->2
3->2
Router:R2
R2: LS Forwarding table (dest -> next-hop)
1->1
2->2
3->3
Router:R3
R3: LS Forwarding table (dest -> next-hop)
1->2
2->2
3->3
```

#### Exercise 8 - Implementation of distance-vector routing algorithm

This aim of this exercise is to implement the distance-vector routing algorithm as described on page 413 in the networking book.

The start of the implementation can be found in the `DVRoutingDaemon.java` class which is to implement a stopable-thread implementing the distance-vector algorithm:

```java
public class DVRoutingDaemon extends RoutingDaemon {

	protected static int INF = 10000; // TODO: 10000 is considered infinite distance
	protected static int NONEXTHOP = -1; // value used for when not having a next hop

	protected DynamicRouter router;

	protected DVEntry[] distancevector;
  ...

```

The current distance vector is represented by the object variable `distancevector` which contains an entry for each (destination) node in the network. Each entry contains the current distance and the current next-hop to the destination. The representation is such that the current distance and next-hop for node `i` is found at index `i` in the distance-vector array.

##### 8.1 Distance-vector implementation

Complete the implementation of the `starting`-method and the `updateDV`-methods.

The starting methods is to implement the initialisaion step of the algorithm by setting up the initial distance vector.

The `updateDV`-method must update the current distance vector of the node given the distance vector `dv` received from a neighbouring node as described in the loop-part of the distance-vector algorithm. The `dv` parameter will contain a distance for each node in the network as estimated by the neighbour node. The distance for a node `i` is located at index `i` in the distance vector.

The two utility methods `D` and `updatedv` can be used to read and update the distance vector for the node.

The class already implement the functionality for the node to prediodically distribute its distance vector to the neighbouring nodes.

##### 8.2 Testing

The class `DVRoutingExample.java` contains a unit test that can be used to test the implementation. The test network is identical to the test network used for Dikjstra's algorithm above.

If the implementation is correct, running the test should give an output similar to the following:

```
Router:R0
Distance Vector (dest | distance | next-hop)
0 | 0 | 0
1 | INF | -
2 | INF | -
Router:R1
Distance Vector (dest | distance | next-hop)
0 | INF | -
1 | 0 | 1
2 | INF | -
Router:R2
Distance Vector (dest | distance | next-hop)
0 | INF | -
1 | INF | -
2 | 0 | 2
Router:R0
Distance Vector (dest | distance | next-hop)
0 | 0 | 0
1 | 1 | 1
2 | 2 | 1
Router:R1
Distance Vector (dest | distance | next-hop)
0 | 1 | 0
1 | 0 | 1
2 | 1 | 2
Router:R2
Distance Vector (dest | distance | next-hop)
0 | 2 | 1
1 | 1 | 1
2 | 0 | 2
```

#### Exercise 9 - Fragmentation

Implement fragmentation of IP datagrams followed how this is supported by the IPv4 protocol.
