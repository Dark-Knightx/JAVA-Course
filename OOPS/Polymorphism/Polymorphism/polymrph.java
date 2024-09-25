package Polymorphism;

class Aliens{
    public void AlienSound(){
        System.out.println("Alien Sounds");
    }
}

class Alien1 extends Aliens{
    @Override
    public void AlienSound(){
        System.out.println("Swampfire");
    }
}

class Alien2 extends Aliens{
    @Override
    public void AlienSound(){
        System.out.println("BiggChill");
    }
}

public class polymrph {
    public static void main(String[] args) {
        Aliens a1 = new Aliens();
        Alien1 a2 = new Alien1();
        Alien2 a3 = new Alien2();

        a1.AlienSound();
        a2.AlienSound();
        a3.AlienSound();
    }
}


