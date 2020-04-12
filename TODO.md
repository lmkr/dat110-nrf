### TODO

Areas of further development for the NRF framework.

- introduce ability of datagrams to be lost in the network
- support for multiple network interfaces on hosts and routing on hosts to select correct interface
- support (dynamic) link changes and effects on dynamic routing algorithms
- determine license type of rdt, nfr and vine frameworks
- implement better critera for stop of test than timeouts
- integrate dynamic routing such that they impact content of routing/forwarding tables
- add graphical visualisation and interaction in relation to forwarding and routing.
- Use of Before Class in unit tests to avoid starting up the network each time. For routing which does not have side-effects that should be unproblematic
- support for link weights other than 1.
- make distance vector independent of numbering from 0 and support also hosts as parts of the networks
- integrate IP address into the routing algorithms
- add support for sending more than a single IP datagram as part of the tests
- Support for broadcast lans
- consider remodelling the relationship betwen ports and links
- make LSRouter and DVROuter not having to rely on N
- remodel entries i LS to make code close to suing D(v) and p(v) notation directly.
