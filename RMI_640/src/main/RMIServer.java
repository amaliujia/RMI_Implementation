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
import services.Hello;
import services.RMIService;
import network.NetworkMgr;

/**
 * @author PY
 *
 */
public class RMIServer {

	static HashMap <String, RMIService> _registeredServices;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// TODO: register local services
		_registeredServices = new HashMap<String, RMIService>();
		Hello hello = new Hello();
		_registeredServices.put("Hello", hello);
		
		NetworkMgr netmgr = NetworkMgr.sharedNetworkMgr();
		Socket client = netmgr.waitForClient();
		System.out.println("client connected!");
		
		
		while (true) {
			RMIMessage msg = null;
			try {
				msg = netmgr.receiveMsg(client);
			} catch (ClassNotFoundException | IOException e1) {
				System.out.println("Client exit!");
				try {
					netmgr.close(client);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			if (msg._type == RMIMsgType.LOOKUP) {
				String name = (String)msg._content;
				RMIMessage ret = new RMIMessage();
				ret._type = RMIMsgType.LOOKUP_RESPOND;
				ret._content = _registeredServices.get(name);
				netmgr.sendMsg(client, ret);
			}
			else if (msg._type == RMIMsgType.LIST) {
				ArrayList<String> names = new ArrayList<String>();
				Iterator<String> kit = _registeredServices.keySet().iterator();
				while (kit.hasNext()) {
					String key = kit.next();
					names.add(key);
				}
				
				RMIMessage ret = new RMIMessage();
				ret._type = RMIMsgType.LIST_RESPOND;
				ret._content = names;
				netmgr.sendMsg(client, ret);
			}
			else if (msg._type == RMIMsgType.CALL) {
				// objName, funName, arg
				Object[] content = (Object[])msg._content;
				String objName = (String) content[0];
				String funName = (String) content[1];
				String arg = (String) content[2];
				RMIService obj = _registeredServices.get(objName);
				try {
					Method m = obj.getClass().getMethod(funName, String.class);
					Object retVal = m.invoke(_registeredServices.get(objName), arg);
					
					RMIMessage ret = new RMIMessage();
					ret._type = RMIMsgType.CALL_RESPOND;
					ret._content = retVal;
					netmgr.sendMsg(client, ret);
					
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
