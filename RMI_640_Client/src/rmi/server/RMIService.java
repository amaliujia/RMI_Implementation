/**
 * 
 */
package rmi.server;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author PY
 *
 */
public abstract class RMIService {
	static volatile AtomicInteger RORID = new AtomicInteger(0);
	public int _rorID;
	
	public RMIService() {
		this._rorID = RORID.getAndDecrement();
	}
}
