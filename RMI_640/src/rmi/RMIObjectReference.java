/**
 * 
 */
package rmi;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import rmi.client.StubBase;

/**
 * @author PY
 *
 */
public class RMIObjectReference implements Serializable {
	private static final long serialVersionUID = 917239297620546165L;
	public String _svrIP;
	public int _svrPort;
	public String _objName;
	public int _remoteID;
	
	public RMIObjectReference() {
		this._svrIP = "";
		this._svrPort = 0;
		this._objName = "";
		this._remoteID = -1;
	}
	
	public StubBase localize() throws RMIException {
		try {
			Class<?> clazz = Class.forName("stub."+_objName+"_stub");
			Constructor<?> cons = clazz.getConstructor();
			StubBase stub = (StubBase)cons.newInstance(new Object[] {});
			stub._ror = this;
			return stub;
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException 
				| NoSuchMethodException | SecurityException 
				| ClassNotFoundException e) {
			throw new RMIException("Failed to localize for " + _objName);
		}
	}
}
