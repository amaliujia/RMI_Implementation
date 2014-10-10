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
 * @author PY
 *
 */
public class RMIServer {
	
	/**
	 * @param args
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
			System.out.println("client connected!");
			new RMISvrHandler(client).start();
		}
	}

}
