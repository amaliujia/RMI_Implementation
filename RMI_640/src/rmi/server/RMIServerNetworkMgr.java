/**
 * 
 */
package rmi.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import rmi.RMIMessage;

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
	
	public static RMIMessage sendAndReceive(String ipAddr, int port, RMIMessage msg) {
		RMIMessage rmiMsg = null;
		try {
			Socket socket = new Socket(ipAddr, port);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(msg);
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Object inMsg = in.readObject();
			if (inMsg instanceof RMIMessage) {
				rmiMsg = (RMIMessage) inMsg;
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rmiMsg;
		
	}
	
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
