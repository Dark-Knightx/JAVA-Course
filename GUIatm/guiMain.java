package guiPractice;

import javax.swing.JOptionPane;
public class guiMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			UserInff userr = new UserInff();
			if (!userr.initialize()) {
	            // User canceled during initialization
	            return; // Exit the main method if canceled
	        }
			String userInp = "1";
			while (!userInp.equals("0")) {
				userInp = JOptionPane.showInputDialog(null,"1.Insert Card\n0.Exit");
				if (userInp == null) {
					break;
				}
				if (userInp.equals("1")) {
					cardOptions(userInp,userr);
				}
			}
	}
	
	private static void cardOptions(String userInp,UserInff userr) {
		switch (userInp) {
		case "1":
			String Pinter = JOptionPane.showInputDialog("Enter PIN");
			if (Pinter == null) {
                JOptionPane.showMessageDialog(null, "Action canceled.");
                break; // Exit the loop
            }
			if (Pinter.equals(userr.getPIN())) {
				String UserOptions = JOptionPane.showInputDialog("1.Withdraw\n2.Deposit\n3.Balance\n4.PIN Change\n5.Last Transaction");
				userOptionsz(userr, UserOptions);
			} else {
				JOptionPane.showMessageDialog(null, "Wrong PIN");
			}
			break;
		case "0":
			JOptionPane.showMessageDialog(null, "Bye cya later");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Skill Issue!!");
			break;
		}
	}
	
	private static void userOptionsz(UserInff userr,String UserOptions) {
		switch (UserOptions) {
		case "1":
			String withdrawAmt = JOptionPane.showInputDialog(null, "Enter Amount to Withdraw:");
			if (withdrawAmt != null) {
				int amountW = Integer.parseInt(withdrawAmt);
				userr.Withdraw(amountW);
			}
			break;
		case "2":
			String amountStr = JOptionPane.showInputDialog(null, "Enter amount to deposit:");
            if (amountStr != null) {
                int amount = Integer.parseInt(amountStr);
                userr.Deposit(amount);
            }
			break;
		case "3":
			userr.showBalance();
			break;
		case "4":
			String PiN = JOptionPane.showInputDialog(null,"New PIN : ");
			if (PiN != null) {
				userr.setPIN(PiN);
			}
			break;
		case "5":
			userr.lastTransaction();
			break;
		default:
			JOptionPane.showMessageDialog(null, "Invalid Input");
			break;
		}
	}
}
