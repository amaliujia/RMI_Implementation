/**
 * 
 */
package rmi;

/**
 * type				
 * @author PY
 *
 */



public class RMIMessage {
	public enum RMIMsgType{
		LOOKUP,
		CALL,
		LIST
	};
	
	public RMIMsgType _type;
	public Object _content;
//	void setCall()
}
