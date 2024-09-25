package ATM;

import java.util.Scanner;
public class atmMain {
    public static void main(String[] args) {
        UserInf UserObj = new UserInf();
        
        int UserIn = 1;
        try (Scanner User_Input = new Scanner(System.in)) {
            while (UserIn != 0) {    
                printSeparator();
                System.out.println("\n1. Insert Card\n0. Exit");
                UserIn = User_Input.nextInt();
                User_Input.nextLine();
                cardOptions(UserObj, UserIn, User_Input);
            }
        }
    }

    private static void cardOptions(UserInf UserObj,int UserIn,Scanner User_Input){
        switch (UserIn) {
            case 1:
                System.out.println("\nEnter your PIN");
                int UserPIN = User_Input.nextInt();
                if (UserPIN == UserObj.getPIN()) {
                    System.out.println("\nHii " + UserObj.getUser_Name() + ":)");
                    printSeparator();
                    System.out.println("\n1. Withdrawal\n2. Check Account details\n3. Deposit\n4. Update PIN\n5. Last Transaction");
                    int UserIn2 = User_Input.nextInt();
                    diffOptions(UserObj, UserIn2, User_Input);
                }
                else
                    System.out.println("\nWrong PIN");
                break;
            case 0:
                System.out.println("\nThank you for banking");
                break;
            default:
                System.out.println("\nInsert Your card Properly");
        }
    }

    private static void printSeparator() {
        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }
    }

    private static void diffOptions(UserInf UserObj,int UserIn2,Scanner User_Input){
        switch (UserIn2) {
            case 1:
                withdraw(UserObj,User_Input);
                break;
            case 2:
                UserObj.ShowUserDetails();
                break;
            case 3:
                deposit(UserObj, User_Input);
                break;
            case 4:
                updatePIN(UserObj, User_Input);
                break;
            case 5:
                UserObj.LastTransaction();
                break;
            default:
                System.out.println("Invalid Input");
                break;
        }
    }

    private static void withdraw(UserInf UserObj,Scanner User_Input){
        System.out.print("\nEnter amount to withdraw\n$");
        int withdr = User_Input.nextInt();
        UserObj.setAmount2(withdr);
    }

    private static void updatePIN(UserInf UserObj,Scanner User_Input){
        System.out.println("\nEnter new PIN");
        int newPIN = User_Input.nextInt();
        if (newPIN>999 && newPIN<10000) {
            UserObj.setPIN(newPIN);
            System.out.println("\nPIN updated..");
        }else
            System.out.println("PIN should be exactly 4 Digits");
    }

    private static void deposit(UserInf UserObj,Scanner User_Input){
        System.out.print("\nEnter amount to deposit\n$");
        UserObj.setAmount(User_Input.nextInt());
        System.out.println("\nAmount successfully credited to XXXXXXXXXXXX" + UserObj.getAccount_NO());
        System.out.println("Updated Balance : "+ "$" + UserObj.getAmount());
    }
}