package sdREG;

import java.util.HashSet;
import java.util.Scanner;

public class Student {
	int studentID;
	String studentName;
	HashSet<Course> registeredCourses = new HashSet<Course>();
	
	public Student() {
		Scanner UI = new Scanner(System.in);
		System.out.println("\nEnter ID : ");
		studentID = UI.nextInt();
		UI.nextLine();
		System.out.println("Enter Name : ");
		studentName = UI.nextLine();
	}
	
	public void addCourse(Course s) {
		if (registeredCourses.contains(s)) {
			System.out.println("\nCourse ALready Exists..");
		} else {
			registeredCourses.add(s);
			System.out.println("\nCourse Added..");
		}
	}
	
	public void removeCourse(Course s2) {
		if (registeredCourses.contains(s2)) {
			registeredCourses.remove(s2);
			System.out.println("\nCourse Removed..");
		} else {
			System.out.println("\nCourse Doesn't Exist");
		}
	}
	
	
	
	public void viewCourse() {
		System.out.println(registeredCourses);
	}
}
