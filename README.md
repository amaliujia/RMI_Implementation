RMI_Implementation
==================

Second project of 15-640 Distributed Systems. Design and implement a RMI framework. Developed with @bbfeechen 

We have developed a facility that can be exported to be a framework. Either you want to use it in server (callee) or client (caller), you can import the framework and write your own program in the style as RMI_640 (server). and RMI_640_Client (client). It's fairly easy.

For more details, please refer to `Material/Report of RMI Facility.pdf`.


## Class explanation

### Framework

1. **RMIMessage**: a concrete class defines the message transmitted betweenServer and Client, which is used during service lookup, method invocation and result return. Additional message type and content can be added if necessary.2. **RemoteObjectReference**: a concrete class defines the service instancereference Server provides for Client, and server manages its information.3. **RMIService**: an abstract class defines interface for services and serves as an element of Server’s information table. It supports pass by reference and pass by value depending on whether Client service extends it or not.4. **StubBase**: an abstract class defines interface for services and wrapsRemoteObjectReference for Server/Client communication.5. **RemoteException**: a common exception class defines the specific exception for RMI facility, it can be extended by adding some message display or handling method in the future.6. **RMIServerRegistry/RMIClientRegistry**: registry classes define the basic service lookup and bind functions for Server and Client. A common base class or interface can be added to improve the extensibility, however, we leave it unimplemented, as it is not necessary in our project.7. **RMIServerNetworkMgr/ RMIClientNetworkMgr**: communication classes define the basic message transmission functions between Server and Client. Similarly, we divide it into Server/Client module as we do for registry classes for readability.8. **RMISvrHandler**: Server monitor thread for accepting messages from a client. It accepts message and passes it to RMIServerNetworkMgr to process.
### Sample classes
9. **RMIServer**: Server main thread to bind a new service and launch monitor thread.10. **RMIClient**: Client main thread to lookup, localize and invoke a new service. 11. ServerConst/ClientConst: constant parameters defined12. **Student/StudentList**: concrete service classes of our test cases defined on Server-side13. **Student_stub/StudentList_stub**: stub classes of our test cases used on Client-side