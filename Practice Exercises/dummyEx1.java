package exercise1;

import java.util.Scanner;
public class dummyEx1 {
    public static void main(String[] args) {
        Scanner jhb = new Scanner(System.in);

        int UI = 1;

        while (UI != 99999) {
            System.out.println("Enter a number to check : , 99999 to Exit");
            UI = jhb.nextInt();
            if (UI>=0) 
                System.out.println("\nThe Provided number is a positive number");
            else
                System.out.println("\nThe Provided number is a negative number");
        }
        jhb.close();
    }
}
