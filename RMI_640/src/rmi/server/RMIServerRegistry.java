/**
 * 
 */
package rmi.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import rmi.RMIException;

/**
 * @author PY
 *
 */
public class RMIServerRegistry {
	static RMIServerRegistry _sharedRegistry = null;
	HashMap <String, RMIService> _registeredServices = null;
	
	public static RMIServerRegistry sharedRegistry() {
		if (_sharedRegistry == null) {
			_sharedRegistry = new RMIServerRegistry();
		}
		return _sharedRegistry;
	}
	
	RMIServerRegistry() {
		_registeredServices = new HashMap<String, RMIService>();
	}
	
	public void bind(String name, RMIService obj) throws RMIException {
		_registeredServices.put(name, obj);
	}
	
	public RMIService getObj(String name) {
		return _registeredServices.get(name);
	}

	public String[] getBindedList() {
		
		ArrayList<String> names = new ArrayList<String>();
		Iterator<String> kit = _registeredServices.keySet().iterator();
		while (kit.hasNext()) {
			String key = kit.next();
			names.add(key);
		}
		return (String[]) names.toArray();
	}
	
}
