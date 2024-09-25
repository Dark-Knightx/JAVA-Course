package exercise1;

import java.util.Scanner;
public class emk1 {
    public static void main(String[] args) {
        try (Scanner UserInp = new Scanner(System.in)) {
            System.out.println("Enter name : ");
            String name = UserInp.nextLine();

            System.out.println("Enter score/100 : ");
            int score = UserInp.nextInt()/10;

            UserInp.nextLine();

            System.out.println("Enter dep : ");
            String dep = UserInp.nextLine();


            System.out.println("Name : " + name + "\nDepartment : " + dep + "\nCGPA : " + score + "/10");
        }
    }
}
