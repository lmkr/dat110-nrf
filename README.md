# dat110-nrf
Network layer routing and forwarding framework

- Stoppable oppgave ifm. NRF intro og gjennomgang - også for å teste ut gruppearbeid i Zoom
- eksplisitt representasjon av transport layer i NRF rammeverk?
- introdusere udt_send og udt_recv?
- introdusere muligheten for at pakker går tapt på nettverket.
- oppgave med å sette opp en enkelt netberk via rammeverket
- legge til et eksempel med bare two host on to router forbundet i en linje, kjøre dette eksempel først og så utvide

- Use of Before Class to avoid starting up the network each time. For routing which does not have side effects that should be unproblematic.
- Add support for multiple nifs on hosts + routing on hosts to select correct interface
- check that cloning is done properly upon transmission on links.
- exercise with implementing hop-count + killing of datagrams
- use generics to provide better support for different types of datagrams
- implement better critera for stop of test than timout?
- add graphical visualisation and interaction in relation to forwarding and routing.
- what type of license fo vine, rdt, and nrf frameworks? 
- configure network with a routing loop based on one of the predefined examples.
- exercise to setup network, including routes
- exercuse to change a route
- terminology: forwarding tables vs. routing tables
- (network) interface vs. java interface
- exercise with the stopable abstraction
- introduce explicit transport layer with trl_send, trl_deliver?
- implementere flooding for LS
- integrasjon med oppdatering av forwarding tabeller + etterfølgend test med å sende datagram igjennom nettverket
- søtte for dynamicks link endringer
- støtte for vekte andre en en
- Gjøre distance vector uavhneeig av nummering 0... for rutere og legge til støtte for hosts som kan ha default route
- integrere IP addresser inn i turning algorithmer

 
