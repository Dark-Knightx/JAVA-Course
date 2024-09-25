//Write a Java program to create a base class Animal (Animal Family) with a method called Sound(). Create two subclasses Bird and Cat. Override the Sound() method in each subclass to make a specific sound for each animal.

class Animal{
    void sound(){
        System.out.println("Animal is asound");
    }
}

class Bird extends Animal{
    @Override
    void sound(){
        System.out.println("Bird is bird");
    }
}

class Cat extends Animal{
    @Override
    void sound(){
        System.out.println("cat cat");
    }
}

public class ex1{
    public static void main(String[] args) {
        Cat c = new Cat();

        c.sound();
    }
} 