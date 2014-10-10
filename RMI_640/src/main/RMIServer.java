/**
 * 
 */
package main;

import java.net.Socket;

import rmi.RMIException;
import rmi.server.RMIServerNetworkMgr;
import rmi.server.RMIServerRegistry;
import services.Hello;

/**
 * RMIServer
 * 
 * The main entrance of the server program. 
 * 
 * @author Yang Pan (yangpan)
 * @author Kailiang Chen (kailiangc)
 *
 */
public class RMIServer {
	
	/**
	 * Everything in a server starts from here.
	 * 
	 * @param args No use.
	 */
	public static void main(String[] args) {
		
		Hello hello = new Hello();
		try {
			RMIServerRegistry.sharedRegistry().bind("Hello", hello);
		} catch (RMIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RMIServerNetworkMgr netmgr = RMIServerNetworkMgr.sharedNetworkMgr();
		
		while (true) {
			Socket client = netmgr.waitForClient();
			new RMISvrHandler(client).start();
		}
	}

}
