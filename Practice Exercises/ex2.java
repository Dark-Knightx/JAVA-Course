// Create a Car object named myCar. Call the fullThrottle() and speed() methods on the myCar object, and run the program:
package exercise1;

public class ex2 {

    public void fullThrottle(){
        System.out.println("Go fullthrottle");
    }
    public void speed(){
        System.out.println("fullSpeed");
    }
    public static void main(String[] args) {
        ex2 myCar = new ex2();

        myCar.speed();
        myCar.fullThrottle();
    }
}
