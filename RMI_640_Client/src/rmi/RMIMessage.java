/**
 * 
 */
package rmi;

import java.io.Serializable;

/**
 * type				
 * @author PY
 *
 */
public class RMIMessage implements Serializable {

	private static final long serialVersionUID = 8896593163858381713L;
	public enum RMIMsgType{
		LOOKUP,
		LOOKUP_RESPOND,
		CALL,
		CALL_RESPOND,
		LIST,
		LIST_RESPOND,
	};
	
	public RMIMsgType _type;
	public Object _content;
}
