package guiPractice;

import  java.util.regex.*;
import javax.swing.JOptionPane;

public class UserInff{
	private String userName;
	private String PIN;
	private String Acc_NO;
	private int Balance;
	private int lastTransaction;
	private String lastTransactionType;
	
	
	
	public boolean initialize() {
        // Loop to get the user's name
        do {
            userName = JOptionPane.showInputDialog("Enter your Name (8-16 characters):");
            if (userName == null) {
                JOptionPane.showMessageDialog(null, "Action canceled.");
                return false; // Indicate cancellation
            }
        } while (!isValidName(userName));

        // Loop to get the user's PIN
        do {
            PIN = JOptionPane.showInputDialog("Set PIN (4 digits):");
            if (PIN == null) {
                JOptionPane.showMessageDialog(null, "Action canceled.");
                return false; // Indicate cancellation
            }
        } while (!isValidPIN(PIN));

        // Loop to get the account number
        do {
            Acc_NO = JOptionPane.showInputDialog("Enter 12 Digit Account No:");
            if (Acc_NO == null) {
                JOptionPane.showMessageDialog(null, "Action canceled.");
                return false; // Indicate cancellation
            }
        } while (!isValidAcc(Acc_NO));

        return true; // Indicate successful initialization
    }
	
	private boolean isValidAcc(String acNum) {
        if (Pattern.matches("\\d{12}", acNum)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "A/C No. must be 12 digits.");
            return false;
        }
    }
	
	private boolean isValidPIN(String pin) {
        if (Pattern.matches("\\d{4}", pin)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "PIN must be 4 digits.");
            return false;
        }
    }
	
	private boolean isValidName(String name) {
		if (Pattern.matches("^[A-Za-z]{8,16}$", name)) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Name Should be 8-16 Character(Only Alphabets)");
			return false;
		}
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPIN() {
		return PIN;
	}
	
	
	public String getAcc_NO() {
		return Acc_NO;
	}
	
	public int getBalance() {
		return Balance;
	}
	
	public void showBalance() {
		JOptionPane.showMessageDialog(null,"Balance : $" + getBalance());
	}
	public void setBalance(int balance) {
		Balance = balance;
	}
	public void setPIN(String pIN) {
		if (Pattern.matches("\\d{4}", pIN)) {
			PIN = pIN;
			JOptionPane.showMessageDialog(null, "PIN Updated..");
        } else {
            JOptionPane.showMessageDialog(null, "PIN must be 4 digits.");
        }		
	}
	
	public void Deposit(int amount) {
    	if(amount>=100) {
	        Balance += amount;
	        JOptionPane.showMessageDialog(null, "Amount successfully credited to account " + getAcc_NO());
	        JOptionPane.showMessageDialog(null, "Updated Balance: $" + getBalance());
	        setLastTransaction(amount, "Credited");
    	}else {
    		JOptionPane.showMessageDialog(null, "Minimum Deposit must be $100");
    	}
    }
	
	public void Withdraw(int amount2) {
        if (Balance >= amount2) {
            Balance -= amount2;
            JOptionPane.showMessageDialog(null, "Amount successfully debited from account " + getAcc_NO());
            JOptionPane.showMessageDialog(null, "Remaining Balance: $" + getBalance());
            setLastTransaction(amount2, "Debited");
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient balance.\nRemaining Balance: $" + getBalance());
        }
    }
	
	public void setLastTransaction(int lastTransaction,String lastTransactionType) {
		this.lastTransaction = lastTransaction;
		this.lastTransactionType = lastTransactionType;
	}
	
	public void lastTransaction() {
		if (lastTransactionType != null) {
			JOptionPane.showMessageDialog(null, "Last Transaction : " + lastTransaction + " has been "+ lastTransactionType);
		} else {
			JOptionPane.showMessageDialog(null, "No Transactions has been made Yet");
		}
		
	}
	
	
}