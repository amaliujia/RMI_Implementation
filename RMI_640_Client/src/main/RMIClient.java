/**
 * 
 */
package main;

import rmi.RMIException;
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
		
		Hello_stub h = new Hello_stub();
		h._ip = ClientConst.SvrIP;
		h._port = ClientConst.SvrPort;
		try {
			System.out.println(h.haha("eeeee"));
		} catch (RMIException e) {
			e.printStackTrace();
		}
		
		String arg1 = "aa";
		String arg2 = "bb";
		double[] zzz = {2.2, 3.3};
		try {
			System.out.println(h.test(arg1, arg2, 33, zzz));
		} catch (RMIException e) {
			e.printStackTrace();
		}
	}

}
