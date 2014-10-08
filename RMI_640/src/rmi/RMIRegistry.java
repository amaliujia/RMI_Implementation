/**
 * 
 */
package rmi;

/**
 * @author PY
 *
 */
public interface RMIRegistry {
	public RMIObjectReference lookup(String objName) throws RMIException;
	public void bind(String name, RMIService obj) throws RMIException;
}
