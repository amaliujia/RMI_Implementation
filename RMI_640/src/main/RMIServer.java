/**
 * 
 */
package main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import rmi.RMIMessage;
import rmi.RMIMessage.RMIMsgType;
import rmi.RMIServerRegistry;
import services.Hello;
import services.RMIService;
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
		
		Hello hello = new Hello();
		RMIServerRegistry.sharedRegistry().bind("Hello", hello);
		
		NetworkMgr netmgr = NetworkMgr.sharedNetworkMgr();
		
		while (true) {
			Socket client = netmgr.waitForClient();
			System.out.println("client connected!");
			new RMISvrHandler(client).start();
		}
	}

}
