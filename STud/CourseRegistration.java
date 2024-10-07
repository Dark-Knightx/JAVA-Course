package sdREG;

import java.util.Scanner;

public class CourseRegistration {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student stud = new Student();
		Scanner UserInp = new Scanner(System.in);
		String loopInput = "1";
		while (!loopInput.equals("0")) {
			System.out.println("\n1.AddCourse\n2.RemoveCourse\n3.RegisteredCourses\n0.Exit");
			loopInput = UserInp.nextLine();
			userOptionz(loopInput,stud);
		}
		UserInp.close();                           
	}
	
	private static void userOptionz(String loopInput,Student stud) {
		switch (loopInput) {
		case "1":
			Course c = new Course();
			stud.addCourse(c);
			break;
		case "2":
			Course c2 = new Course();
			stud.removeCourse(c2);
			break;
		case "3":
			stud.viewCourse();
			break;
		case "0":
			System.out.println("Thank You!!");
			break;
		default:
			System.out.println("Invalid Input");
			break;
		}
	}

}
