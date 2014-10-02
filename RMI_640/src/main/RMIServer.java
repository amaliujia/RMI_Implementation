/**
 * 
 */
package main;

import java.net.Socket;

import rmi.RMIMessage;
import rmi.RMIMessage.RMIMsgType;
import network.NetworkMgr;

/**
 * @author PY
 *
 */
public class RMIServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO: register local services
		
		
		Socket client = NetworkMgr.sharedNetworkMgr().waitForClient();
		System.out.println("client connected!");
		
		
		while (true) {
			RMIMessage msg = NetworkMgr.sharedNetworkMgr().receiveMsg(client);
			if (msg._type == RMIMsgType.LOOKUP) {
				
			}
		}
	}

}
