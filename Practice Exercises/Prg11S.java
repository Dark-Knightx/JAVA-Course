package Exercisess;

interface talk2{
    void sayHello(String name);
}
public class Prg11S {
    static void takeAction(talk ref){
        ref.sayHello("ram");
    }
    public static void main(String[] args) {
        talk2 t2 = new talk2() {
            public void sayHello(String name){
                System.out.println("hello" + name);
            }
        };
        
    }
}
