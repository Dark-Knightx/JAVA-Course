package Exercisess;

interface talk{
    void sayHello(String name);
}
class talkImp implements talk{
    public void sayHello(String name){
        System.out.println("Welcome!! "+name);
    }
}
public class Prg11 {
    static void takeAction(talk ref){
        ref.sayHello("ram");
    }
    public static void main(String[] args) {
        talk t = new talkImp();
        Prg11.takeAction(t);    
    }
}
