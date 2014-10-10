/**
 * 
 */
package rmi.client;

import rmi.RMIException;
import rmi.RMIMessage;
import rmi.RMIObjectReference;
import rmi.RMIMessage.RMIMsgType;

/**
 * @author PY
 *
 */
public class RMIClientRegistry {
	public String _svrIP = ClientConst.SvrIP;
	public int _svrPort = ClientConst.SvrPort;
	
	public RMIObjectReference lookup (String objName) throws RMIException {
		RMIMessage msg = new RMIMessage();
		msg._type = RMIMsgType.LOOKUP;
		msg._content = objName;
		RMIMessage inMsg = RMIClientNetworkMgr.sendAndReceive(_svrIP, _svrPort, msg);
		if (inMsg._content == null || !(inMsg._content instanceof RMIObjectReference)) {
			throw new RMIException("Invalid respond from server");
		}
		RMIObjectReference ror = (RMIObjectReference) inMsg._content;
	
		return ror;
	}
}
