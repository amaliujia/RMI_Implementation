/**
 * 
 */
package stub;

import rmi.RMIException;
import rmi.RMIMessage;

/**
 * @author PY
 *
 */
public class Hello_stub extends StubBase {
	
	public String haha(String a) throws RMIException {
		Object[] content = {"Hello", "haha", a};
		RMIMessage inMsg = invokeRemote(content);
		if (!(inMsg._content instanceof String)) {
			throw new RMIException("Invalid return value type!");
		}
		return (String)inMsg._content;
	}
	
	public int test(String a, String b, int c, double[] d) throws RMIException {
		Object[] content = {"Hello", "test", a, b, c, d};
		RMIMessage inMsg = invokeRemote(content);
		if (!(inMsg._content instanceof Integer)) {
			throw new RMIException("Invalid return value type!");
		}
		return ((Integer)inMsg._content).intValue();
	}
}
