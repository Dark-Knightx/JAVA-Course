//Write a Java program to create a base class Shape with a method called calculateArea(). Create three subclasses: Circle, Rectangle, and Triangle. Override the calculateArea() method in each subclass to calculate and return the shape's area.

class Shape {
    double l = 5.0;
    double b = 7.0;
    double h = 8.0;
    double r = 3.0;
    double calculateArea(){
        return l*b*h;
    }
}

class Circle extends Shape {
    @Override
    double calculateArea(){
        return 3.14*(r*r);
    }
}

class Triangle extends Shape {
    @Override
    double calculateArea(){
        return l*b*h;
    }
}

class Rectangle extends Shape {
    @Override
    double calculateArea(){
        return l*b;
    }
}
public class exer {
    public static void main(String[] args) {
        Circle c1 = new Circle();
        Triangle t1 = new Triangle();
        Rectangle r1 = new Rectangle();

        System.out.println(c1.calculateArea());
        System.out.println(t1.calculateArea());
        System.out.println(r1.calculateArea());
    }
}
