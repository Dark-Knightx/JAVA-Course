package Exercisess;
interface operatioN{
    int sum(int i,int j);
}
public class Prg13 {
    public static void main(String[] args) {
        operatioN d = Prg13::add;
        d.sum(4, 4);
        System.out.println(d);
    }
    static int add(int a,int b){
        return a+b;
    }
}
