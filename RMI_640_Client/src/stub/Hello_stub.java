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
	
	public String haha(String a) {
		System.out.println("In stub");
		RMIMessage outMsg = new RMIMessage();
		outMsg._type = RMIMsgType.CALL;
		Object[] content = {"Hello", "haha", a};
		outMsg._content = content;
		RMIMessage inMsg = NetworkMgr.sendAndReceive(_ip, _port, outMsg);
		if (inMsg == null) {
			System.out.println("WRONG!!");
		}
		return (String)inMsg._content;
	}
}
