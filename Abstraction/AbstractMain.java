package Abstraction;

abstract class shape{
    int length;
    int breadth;

    shape(int l,int b){
        length = l;
        breadth = b;
    }
    abstract int area();
}

class Square extends shape{

    Square(int l,int b){
        super(l, b);
    }
    int area(){
        return length*breadth;
    }
}

class Triangle extends shape{
    int height;

    Triangle(int l,int b,int h){
        super(l, b);
        height = h;
    }

    int area(){
        return length*breadth*height;
    }
}

public class AbstractMain {
    public static void main(String[] args) {
        Square sqr = new Square(3, 4);
        Triangle trg = new Triangle(2, 4, 6);

        Square[] sq = new Square[4];

        sq[0] = new Square(3, 5);
        sq[1] = new Square(4, 6);
        sq[2] = new Square(3, 8);
        sq[3] = new Square(5, 7);

        for (int i = 0; i < sq.length; i++) {
            System.out.println("Area of sq index " + i + ": " +sq[i].area());
           
        }
        System.out.println("Area of sqr obj : " + sqr.area());
        System.out.println("Area of trg obj : " + trg.area());
    }
}
