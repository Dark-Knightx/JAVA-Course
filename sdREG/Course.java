package sdREG;

import java.util.Scanner;

public class Course {
    int courseID;
    String courseName;

    public Course() {
        Scanner userIn = new Scanner(System.in);
        System.out.println("\nEnter CourseID : ");
        courseID = userIn.nextInt();
        userIn.nextLine(); // Consume the newline
        System.out.println("Enter CourseName : ");
        courseName = userIn.nextLine();
    }

    public Course(int courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Reference check
        if (obj == null || getClass() != obj.getClass()) return false; // Null and class check
        Course course = (Course) obj;
        return courseID == course.courseID; // Check only courseID
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(courseID); // Hash based on courseID
    }

    @Override
    public String toString() {
        return courseID + " " + courseName;
    }
}
