/**
 * 
 */
package main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import network.NetworkMgr;
import rmi.RMIMessage;
import rmi.RMIMessage.RMIMsgType;
import rmi.RMIServerRegistry;
import services.RMIService;

/**
 * @author PY
 *
 */
public class RMISvrHandler extends Thread {
	
	Socket _socket;
	NetworkMgr _netmgr;
	RMIServerRegistry _registry;
	
	RMISvrHandler(Socket socket) {
		_socket = socket;
		_netmgr = NetworkMgr.sharedNetworkMgr();
		_registry = RMIServerRegistry.sharedRegistry();
	}

	@Override
	public void run() {
		while (true) {
			RMIMessage msg = null;
			try {
				msg = _netmgr.receiveMsg(_socket);
			} catch (ClassNotFoundException | IOException e1) {
				System.out.println("Client exit!");
				try {
					_netmgr.close(_socket);
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
				ret._content = _registry.getObj(name);
				_netmgr.sendMsg(_socket, ret);
			}
			else if (msg._type == RMIMsgType.LIST) {
				String[] names = _registry.getBindedList();
				
				RMIMessage ret = new RMIMessage();
				ret._type = RMIMsgType.LIST_RESPOND;
				ret._content = names;
				_netmgr.sendMsg(_socket, ret);
			}
			else if (msg._type == RMIMsgType.CALL) {
				// objName, funName, arg
				Object[] content = (Object[])msg._content;
				String objName = (String) content[0];
				String funName = (String) content[1];
//				String arg = (String) content[2];
				
				Object[] arg = msg.getArguments();
				Class<?>[] argType = msg.getArgType();
				RMIService obj = _registry.getObj(objName);
				
				try {
					Method m = obj.getClass().getMethod(funName, argType);
					Object retVal = m.invoke(obj, arg);
					
					RMIMessage ret = new RMIMessage();
					ret._type = RMIMsgType.CALL_RESPOND;
					ret._content = retVal;
					_netmgr.sendMsg(_socket, ret);
					
				} catch (NoSuchMethodException e) {
					System.out.println("No such method " + funName + ": ");
					for (Class<?> type: argType) {
						System.out.print(type.getName() + " ");
					}
				} catch (SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
