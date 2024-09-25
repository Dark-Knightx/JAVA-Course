package exercise1;
public class constructor {
    int ID;
    String Date,bName,Checkin,Checkout,msg,name;

    public constructor(){
        name = "ItzSivv";
        bName = "Harry Potter";
        Checkout = "not yet";
        Checkin = "05/05/2024";
        msg = "The Book must return";
    }

    public static void main(String[] args) {

        constructor Book = new constructor();

        // ex1 Card = new ex1();

        // ex1 Reminder = new ex1();
        System.out.println(Book.name);
    }
}
