/**
 * 
 */
package rmi;

import main.ClientConst;
import network.NetworkMgr;
import rmi.RMIMessage.RMIMsgType;
import stub.StubBase;

/**
 * @author PY
 *
 */
public class RMIRegistry {
	public String _svrIP = ClientConst.SvrIP;
	public int _svrPort = ClientConst.SvrPort;
	
	public StubBase lookup (String objName) throws RMIException {
		RMIMessage msg = new RMIMessage();
		msg._type = RMIMsgType.LOOKUP;
		msg._content = objName;
		RMIMessage inMsg = NetworkMgr.sendAndReceive(_svrIP, _svrPort, msg);
		if (inMsg._content == null || !(inMsg._content instanceof RMIObjectReference)) {
			throw new RMIException("Invalid respond from server");
		}
		RMIObjectReference ror = (RMIObjectReference) inMsg._content;
		
		StubBase stub = ror.localize();
		stub._ror = ror;
	
		return stub;
	}
}
