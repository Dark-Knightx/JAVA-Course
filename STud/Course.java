package sdREG;

import java.util.Scanner;

public class Course {
	int courseID;
	String courseName;
	
	public Course() {
		Scanner userIn = new Scanner(System.in);
		System.out.println("\nEnter CourseID : ");
		courseID = userIn.nextInt();
		userIn.nextLine();
		System.out.println("Enter CourseName : ");
		courseName = userIn.nextLine();
	}
	
	@Override
	public String toString() {
		return courseID + " " + courseName;
	}
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Reference check
        if (obj == null || getClass() != obj.getClass()) return false; // Null and class check
        Course course = (Course) obj; // Cast to Course
        return courseID == course.courseID && courseName.equalsIgnoreCase(course.courseName); // Case-insensitive name check
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(courseID); // Hash code for courseID
        result = 31 * result + (courseName != null ? courseName.toLowerCase().hashCode() : 0); // Combine with courseName hash (case-insensitive)
        return result; // Return the combined hash code
    }


}
