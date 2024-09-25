package ATM;
import java.util.Scanner;

abstract class userInf1{
    abstract void ShowUserDetails();
    abstract void setAmount(int amount);
    abstract void setAmount2(int amount2);
    abstract void LastTransaction();
}

class UserInf extends userInf1{
    private String user_Name;
    private int PIN;
    private int Amount;
    private String Account_NO;
    private int lastTransaction;
    private String TransactionType;

    public int getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(int lastTransaction,String TransactionType) {
        this.lastTransaction = lastTransaction;
        this.TransactionType = TransactionType;
    }

    public String getAccount_NO() {
        return Account_NO.substring(Account_NO.length() - 4);
    }
  
    public UserInf() {
        Scanner UI = new Scanner(System.in);
        System.out.println("Enter ur Name : ");
        user_Name = UI.nextLine();
        System.out.println("Set PIN : ");
        PIN = UI.nextInt();
        UI.nextLine();
        System.out.println("Enter 12 Digit Acc No : ");
        Account_NO = UI.nextLine();
    }
    

    public String getUser_Name() {
        return user_Name;
    }

    

    public int getPIN() {
        return PIN;
    }

    public void setPIN(int pIN) {
        PIN = pIN;
    }

    public void setAmount(int amount) {
        Amount = getAmount() + amount;
        
        setLastTransaction(amount,"Deposited");
    }
    public void setAmount2(int amount2){
        if (Amount>=amount2) {
            Amount = getAmount() - amount2;
            System.out.println("\nAmount successfully debited from XXXXXXXXXXXX" + getAccount_NO());
            System.out.println("Remaining Balance : "+ "$" + getAmount());
            setLastTransaction(amount2,"Credited");
        }else{
            System.out.println("Insufficient balance");
            System.out.println("Remaining Balance : "+ "$" + getAmount());
        }
    }

    public void LastTransaction(){
        if (TransactionType!=null) {
            System.out.println("\nLast Transaction : $" + getLastTransaction() + " has been " + TransactionType);
        }else
            System.out.println("\nNo Transactions has been made yet");
    }

    public int getAmount() {
        return Amount;
    }
    
    public void ShowUserDetails(){
        System.out.println("\nAccount Details : ");
        for (int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        System.out.println("\nUserName :" + getUser_Name());
        System.out.println("Account Balance :" + "$" + getAmount());
    }
}
