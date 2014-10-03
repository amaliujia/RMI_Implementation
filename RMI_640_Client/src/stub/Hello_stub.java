/**
 * 
 */
package stub;

import java.util.ArrayList;

import network.NetworkMgr;
import rmi.RMIMessage;
import rmi.RMIMessage.RMIMsgType;

/**
 * @author PY
 *
 */
public class Hello_stub {
	public String _ip;
	public int _port;
	
	RMIMessage invokeRemote(Object[] content) {
		RMIMessage outMsg = new RMIMessage();
		outMsg._type = RMIMsgType.CALL;
		outMsg._content = content;
		return NetworkMgr.sendAndReceive(_ip, _port, outMsg);
	}
	
	public String haha(String a) {
		Object[] content = {"Hello", "haha", a};
		RMIMessage inMsg = invokeRemote(content);
		if (inMsg == null) {
			System.out.println("WRONG!!");
		}
		return (String)inMsg._content;
	}
	
	public int test(String a, String b, int c, double[] d) {
		Object[] content = {"Hello", "test", a, b, c, d};
		RMIMessage inMsg = invokeRemote(content);
		if (inMsg == null) {
			System.out.println("WRONG@@");
		}
		return ((Integer)inMsg._content).intValue();
	}
}
