package sdREG;

import java.util.Scanner;

public class CourseRegistration {

    public static void main(String[] args) {
        Student stud = new Student();
        Scanner userInp = new Scanner(System.in);
        String loopInput = "1";
        while (!loopInput.equals("0")) {
            System.out.println("\n1. Add Course\n2. Remove Course\n3. Registered Courses\n0. Exit");
            loopInput = userInp.nextLine();
            userOptionz(loopInput, stud);
        }
        userInp.close();
    }

    private static void userOptionz(String loopInput, Student stud) {
        switch (loopInput) {
            case "1":
                addCourse(stud);
                break;
            case "2":
                removeCourse(stud);
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

    private static void addCourse(Student stud) {
        Scanner userInp = new Scanner(System.in);
        System.out.println("\nEnter CourseID: ");
        int courseID = userInp.nextInt();
        userInp.nextLine(); // Consume the newline
        System.out.println("Enter CourseName: ");
        String courseName = userInp.nextLine();

        Course course = new Course(courseID, courseName);
        stud.addCourse(course);
    }

    private static void removeCourse(Student stud) {
        Scanner userInp = new Scanner(System.in);
        System.out.println("Enter Course ID to remove: ");
        int courseIdToRemove = userInp.nextInt();
        stud.removeCourse(courseIdToRemove);
    }
}
