package sdREG;

import java.util.HashSet;
import java.util.Scanner;
public class Student {
    int studentID;
    String studentName;
    HashSet<Course> registeredCourses = new HashSet<>(); // Now holds Course objects

    public Student() {
        Scanner UI = new Scanner(System.in);
        System.out.println("\nEnter ID : ");
        studentID = UI.nextInt();
        UI.nextLine();
        System.out.println("Enter Name : ");
        studentName = UI.nextLine();
    }

    public void addCourse(Course course) {
        // Check if the courseID already exists in the registeredCourses
        for (Course registeredCourse : registeredCourses) {
            if (registeredCourse.courseID == course.courseID) {
                System.out.println("\nCourse with ID " + course.courseID + " Already Exists..");
                return;
            }else if(registeredCourse.courseName.equals(course.courseName)) {
            	System.out.println("\nCourse with Name " + course.courseName + " Already Exists..");
                return;
            }
        }
        registeredCourses.add(course);
        System.out.println("\nCourse Added: " + course);
    }

    public void removeCourse(int courseID) {
        Course courseToRemove = null;

        // Find the course by ID
        for (Course course : registeredCourses) {
            if (course.courseID == courseID) {
                courseToRemove = course;
                break;
            }
        }

        // Remove the course if found
        if (courseToRemove != null) {
            registeredCourses.remove(courseToRemove);
            System.out.println("\nCourse Removed: " + courseToRemove);
        } else {
            System.out.println("\nCourse Doesn't Exist");
        }
    }

    public void viewCourse() {
        System.out.println("Registered Courses: " + registeredCourses);
    }
}
