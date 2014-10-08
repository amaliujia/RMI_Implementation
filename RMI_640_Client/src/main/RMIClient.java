/**
 * 
 */
package main;

import rmi.RMIException;
import rmi.RMIRegistry;
import stub.Hello_stub;

/**
 * @author PY
 *
 */
public class RMIClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RMIRegistry registry = new RMIRegistry();
		Hello_stub h = null;
		
		try {
			h = (Hello_stub)registry.lookup("Hello");
			System.out.println(h.haha("eeeee"));
			String arg1 = "aa";
			String arg2 = "bb";
			double[] zzz = {2.2, 3.3};
			System.out.println(h.test(arg1, arg2, 33, zzz));
		} catch (RMIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
	}

}
