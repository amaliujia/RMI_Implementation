/**
 * 
 */
package rmi.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import rmi.RMIMessage;
import rmi.RMIObjectReference;
import rmi.RMIMessage.RMIMsgType;

/**
 * @author PY
 *
 */
public class RMIServerNetworkMgr {
	ServerSocket _svrSocket;
	
	static RMIServerNetworkMgr _sharedNetworkMgr = null;
	
	RMIServerNetworkMgr(int port) {
		try {
			_svrSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Port " + ServerConst.ListenPort + " is being used. Please change to"
					+ " another port in ServerConst.java and ClientConst.java in client.");
			System.exit(-1);
		}
	}
	
	public boolean msgReceiveAndHandler(Socket _socket) {
		RMIMessage msg = null;
		try {
			msg = receiveMsg(_socket);
		} catch (ClassNotFoundException | IOException e1) {
			System.out.println("Client exit!");
			try {
				close(_socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		if (msg._type == RMIMsgType.LOOKUP) {
			String name = (String)msg._content;
			RMIService obj = RMIServerRegistry.sharedRegistry().getObj(name);
			
			RMIMessage ret = new RMIMessage();
			ret._type = RMIMsgType.LOOKUP_RESPOND;
			
			if (obj == null) {
				ret._content = null;
			} else {
				RMIObjectReference ror = new RMIObjectReference();
				ror._objName = name;
				ror._remoteID = obj._rorID;
				ror._svrIP = RMIServerNetworkMgr.getLocalIP();
				ror._svrPort = ServerConst.ListenPort;
				
				ret._content = ror;
			}
			sendMsg(_socket, ret);
		}
		else if (msg._type == RMIMsgType.LIST) {
			String[] names = RMIServerRegistry.sharedRegistry().getBindedList();
			
			RMIMessage ret = new RMIMessage();
			ret._type = RMIMsgType.LIST_RESPOND;
			ret._content = names;
			sendMsg(_socket, ret);
		}
		else if (msg._type == RMIMsgType.CALL) {
			// ROR, funName, arg
			
			/* get the object by ror, if the ror is got from lookup, search in _registeredServices,
			 * or got from method invoke, search in _referencedServices (reference by value).
			 */
			RMIService obj = RMIServerRegistry.sharedRegistry().getObjByROR(msg.getROR());
			
			RMIMessage retMsg = new RMIMessage();
			retMsg._type = RMIMsgType.CALL_RESPOND;
			
			if (obj == null) {
				retMsg._content = null;
				sendMsg(_socket, retMsg);
			} else {
				String funName = msg.getMethodName();
				
				Object[] arg = msg.getArguments();
				Class<?>[] argType = msg.getArgType();
				
				try {
					Method m = obj.getClass().getMethod(funName, argType);
					Object retVal = m.invoke(obj, arg);
					
					if (retVal instanceof RMIService) {
						/* if return value is subclass of RMIService, return ROR */
						RMIServerRegistry.sharedRegistry().addReferencedService((RMIService) retVal);
						RMIObjectReference ror = new RMIObjectReference();
						ror._objName = retVal.getClass().getSimpleName();
						ror._remoteID = ((RMIService) retVal)._rorID;
						ror._svrIP = RMIServerNetworkMgr.getLocalIP();
						ror._svrPort = ServerConst.ListenPort;
						retMsg._content = ror;
					} else {
						retMsg._content = retVal;
					}
					sendMsg(_socket, retMsg);
					
				} catch (NoSuchMethodException e) {
					System.out.println("No such method " + funName + ": ");
					for (Class<?> type: argType) {
						System.out.print(type.getName() + " ");
					}
					RMIMessage retMsgEx = new RMIMessage();
					retMsgEx._type = RMIMsgType.EXCEPTION;
					retMsgEx._content = e;
					sendMsg(_socket, retMsgEx);
				} catch (SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					RMIMessage retMsgEx = new RMIMessage();
					retMsgEx._type = RMIMsgType.EXCEPTION;
					retMsgEx._content = e;
					sendMsg(_socket, retMsgEx);
					e.printStackTrace();
				}
			}
			
		}
		return true;
	}
	
//	public static RMIMessage sendAndReceive(String ipAddr, int port, RMIMessage msg) {
//		RMIMessage rmiMsg = null;
//		try {
//			Socket socket = new Socket(ipAddr, port);
//			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//			out.writeObject(msg);
//			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//			Object inMsg = in.readObject();
//			if (inMsg instanceof RMIMessage) {
//				rmiMsg = (RMIMessage) inMsg;
//			}
//			
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return rmiMsg;
//	}
	
	public static String getLocalIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}
	
	public static RMIServerNetworkMgr sharedNetworkMgr() {
		if (_sharedNetworkMgr == null) {
			_sharedNetworkMgr = new RMIServerNetworkMgr(ServerConst.ListenPort);
		}
		return _sharedNetworkMgr;
	}
	
	public Socket waitForClient() {
		try {
			Socket socket = _svrSocket.accept();
			return socket;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public RMIMessage receiveMsg(Socket socket) throws IOException, ClassNotFoundException {
		ObjectInputStream inStream;
		Object inObj;
		
		inStream = new ObjectInputStream(socket.getInputStream());
		inObj = inStream.readObject();
		if (inObj instanceof RMIMessage) {
			RMIMessage msg = (RMIMessage) inObj;
			return msg;
		}
		
		return null;
	}
	
	public void sendMsg(Socket socket, Object msg) {
		if (!(msg instanceof RMIMessage)) {
			return;
		}
		ObjectOutputStream out;
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close (Socket socket) throws IOException {
		socket.close();
	}
}
