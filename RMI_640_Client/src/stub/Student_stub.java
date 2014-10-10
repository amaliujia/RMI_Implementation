/**
 * 
 */
package stub;

import rmi.RMIException;
import rmi.client.StubBase;

/**
 * @author PY
 *
 */
public class Student_stub extends StubBase {
	public void setName(String name) throws RMIException {
		Object[] content = {_ror, "setName", name};
		invokeRemote(content, false);
	}
	
	public void setScore(int score) throws RMIException {
		Object[] content = {_ror, "setScore", score};
		invokeRemote(content, false);
	}
	
	public String getName() throws RMIException {
		Object[] content = {_ror, "getName"};
		Object retVal = invokeRemote(content, true);
		
		if (!(retVal instanceof String)) {
			throw new RMIException("Invalid return value type! String expected, but " + retVal.getClass().getName() + " received.");
		}
		return (String)retVal;
	}
	
	public int getScore() throws RMIException {
		Object[] content = {_ror, "getScore"};
		Object retVal = invokeRemote(content, true);
		if (!(retVal instanceof Integer)) {
			throw new RMIException("Invalid return value type! int expected, but " + retVal.getClass().getName() + " received.");
		}
		return ((Integer)retVal).intValue();
	}
}
