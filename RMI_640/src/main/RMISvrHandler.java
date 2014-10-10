/**
 * 
 */
package main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import rmi.RMIMessage;
import rmi.RMIMessage.RMIMsgType;
import rmi.server.RMIServerNetworkMgr;
import rmi.server.RMIServerRegistry;
import rmi.server.RMIService;
import rmi.server.ServerConst;
import rmi.RMIObjectReference;

/**
 * @author PY
 *
 */
public class RMISvrHandler extends Thread {
	
	Socket _socket;
	RMIServerNetworkMgr _netmgr;
	RMIServerRegistry _registry;
	
	RMISvrHandler(Socket socket) {
		_socket = socket;
		_netmgr = RMIServerNetworkMgr.sharedNetworkMgr();
		_registry = RMIServerRegistry.sharedRegistry();
	}

	@Override
	public void run() {
		while (RMIServerNetworkMgr.sharedNetworkMgr().msgReceiveAndHandler(_socket)) {
			;
		}
		
	}

}
