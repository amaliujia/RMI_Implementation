/**
 * 
 */
package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import main.ServerConst;
import rmi.RMIMessage;

/**
 * @author PY
 *
 */
public class NetworkMgr {
	ServerSocket _svrSocket;
	
	static NetworkMgr _sharedNetworkMgr = null;
	
	NetworkMgr(int port) {
		try {
			_svrSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static NetworkMgr sharedNetworkMgr() {
		if (_sharedNetworkMgr == null) {
			_sharedNetworkMgr = new NetworkMgr(ServerConst.ListenPort);
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
