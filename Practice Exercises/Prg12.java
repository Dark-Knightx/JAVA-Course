package Exercisess;

interface MyPred{
    boolean test(int val);
}
public class Prg12 {
    public static void main(String[] args) {
        MyPred m = (v) -> v%2==0;
        System.out.println(m.test(40));
    }
}
