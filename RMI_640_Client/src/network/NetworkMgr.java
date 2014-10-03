/**
 * 
 */
package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import rmi.RMIMessage;

/**
 * @author PY
 *
 */
public class NetworkMgr extends Object {
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
	public int haha(String a, String b, int c, double[] d) {
		System.out.println("in test~: " + a+b);
		for (double ch: d) {
			System.out.println(ch);
		}
		return c+10;
	}
}
