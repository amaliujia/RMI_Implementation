/**
 * 
 */
package rmi.client;

import rmi.RMIException;
import rmi.RMIMessage;
import rmi.RMIObjectReference;
import rmi.RMIMessage.RMIMsgType;
import rmi.server.RMIServerNetworkMgr;

/**
 * @author PY
 *
 */
public class StubBase {
	public RMIObjectReference _ror;
	
	public Object invokeRemote(Object[] content, boolean hasRetVal) throws RMIException {
		RMIMessage outMsg = new RMIMessage();
		outMsg._type = RMIMsgType.CALL;
		outMsg._content = content;
		RMIMessage inMsg = RMIClientNetworkMgr.sendAndReceive(_ror._svrIP, _ror._svrPort, outMsg);
		
		if (hasRetVal == true) {
			if (inMsg == null) {
				throw new RMIException("Invalid return value (null)");
			}
		}
		
		if (inMsg._type == RMIMsgType.EXCEPTION) {
			throw new RMIException("Server internal error");
		}
		return inMsg._content;
	}
}
