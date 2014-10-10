/**
 * 
 */
package main;

import rmi.RMIException;
import rmi.RMIObjectReference;
import rmi.client.RMIClientRegistry;
import stub.Hello_stub;
import stub.MyName_stub;
import stub.StudentList_stub;
import stub.Student_stub;

/**
 * @author PY
 *
 */
public class RMIClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RMIClientRegistry registry = new RMIClientRegistry();
		
		try {
			RMIObjectReference ror = registry.lookup("StudentList");
			StudentList_stub stuList = (StudentList_stub)ror.localize();
			stuList.generateNewStudent("Yan Pan", 100);
			stuList.generateNewStudent("Kailiang Chen", 100);
			stuList.generateNewStudent("Kasden Greg", 59);
			
			Student_stub yp = stuList.getFirstStudent();
			System.out.println("Before name modify: " + yp.getName() + " has score " + yp.getScore());
			yp.setName("Yang Pan");
			System.out.println("After name modify: " + yp.getName() + " has score " + yp.getScore());
			yp.setScore(80); /* :( */
			System.out.println("After score modify: " + yp.getName() + " has score " + yp.getScore());
			
			Student_stub new_yp = stuList.getFirstStudent();
			System.out.println("Finally: " + new_yp.getName() + " has score " + new_yp.getScore());
		} catch (RMIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
	}

}
