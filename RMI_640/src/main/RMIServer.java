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

import rmi.RMIException;
import rmi.RMIMessage;
import rmi.RMIService;
import rmi.RMIMessage.RMIMsgType;
import services.Hello;
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
		try {
			RMIServerRegistry.sharedRegistry().bind("Hello", hello);
		} catch (RMIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NetworkMgr netmgr = NetworkMgr.sharedNetworkMgr();
		
		while (true) {
			Socket client = netmgr.waitForClient();
			System.out.println("client connected!");
			new RMISvrHandler(client).start();
		}
	}

}
