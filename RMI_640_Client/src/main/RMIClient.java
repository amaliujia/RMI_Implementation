/**
 * 
 */
package main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import stub.Hello_stub;
import network.NetworkMgr;

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
		System.out.println(h.haha("eeeee"));
		
		String arg1 = "aa";
		String arg2 = "bb";
		double[] zzz = {2.2, 3.3};
		System.out.println(h.test(arg1, arg2, 33, zzz));
	}

}
