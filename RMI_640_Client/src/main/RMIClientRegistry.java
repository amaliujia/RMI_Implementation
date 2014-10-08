/**
 * 
 */
package main;

import network.NetworkMgr;
import rmi.RMIException;
import rmi.RMIMessage;
import rmi.RMIObjectReference;
import rmi.StubBase;
import rmi.RMIMessage.RMIMsgType;
import rmi.RMIRegistry;
import rmi.RMIService;

/**
 * @author PY
 *
 */
public class RMIClientRegistry implements RMIRegistry {
	public String _svrIP = ClientConst.SvrIP;
	public int _svrPort = ClientConst.SvrPort;
	
	@Override
	public RMIObjectReference lookup (String objName) throws RMIException {
		RMIMessage msg = new RMIMessage();
		msg._type = RMIMsgType.LOOKUP;
		msg._content = objName;
		RMIMessage inMsg = NetworkMgr.sendAndReceive(_svrIP, _svrPort, msg);
		if (inMsg._content == null || !(inMsg._content instanceof RMIObjectReference)) {
			throw new RMIException("Invalid respond from server");
		}
		RMIObjectReference ror = (RMIObjectReference) inMsg._content;
	
		return ror;
	}

	@Override
	public void bind(String name, RMIService obj) throws RMIException {
		// TODO Auto-generated method stub
		
	}
}
