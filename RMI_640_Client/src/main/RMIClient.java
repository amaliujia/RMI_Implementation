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
		// TODO Auto-generated method stub
		
		Hello_stub h = new Hello_stub();
		h._ip = ClientConst.SvrIP;
		h._port = ClientConst.SvrPort;
		System.out.println(h.haha("eeeee"));
		
//		Object a = new NetworkMgr();
//		System.out.println(a.getClass());
//		
//		String arg1 = "aa";
//		String arg2 = "bb";
//		char[] xxx = {'a'};
//		double[] yyy = {2.2, 3.3};
//		Double[] zzz = {2.2, 3.3};
//		if (Double[].class == zzz.getClass()) {
//			System.out.println("sdlkfjlskjflksdjfklsdjfkls");
//		}
//		Object[] arg = {arg1, arg2, 33, zzz};
//		
//		Class<?>[] types = new Class<?>[arg.length];
//		for (int i=0; i<arg.length; ++i) {
//			types[i] = arg[i].getClass();
//			if (types[i] == Integer.class) {
//				types[i] = int.class;
//			} else if (types[i] == Character[].class) {
//				types[i] = char[].class;
//			} else if (types[i] == Double[].class) {
//				types[i] = double[].class;
//				Double[] od = (Double[])arg[i];
//				double[] nd = new double[od.length];
//				for (int j=0; j<od.length; ++j) {
//					nd[j] = od[j];
//				}
//				arg[i] = nd;
//			}
//			System.out.println(types[i]);
//		}
//		
//		try {
//			Method m = NetworkMgr.class.getMethod("haha", types);
//			Object ret = m.invoke(a, arg);
//			System.out.println(ret);
//		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
