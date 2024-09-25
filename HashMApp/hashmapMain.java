package HashMApp;
//javac HashMApp/hashmapMain.java HashMApp.hashmapMain
import java.util.HashMap;
public class hashmapMain {
    public static void main(String[] args) {
        HashMap<Integer,String> hashmp = new HashMap<>();

        hashmp.put(1, "SwampFire");
        hashmp.put(2, "BigChill");
        hashmp.put(3, "DiamondHead");
        hashmp.put(4, "Humangasaur");
        hashmp.put(5, "Rath");

        System.out.println(hashmp);
        System.out.println("\n"+hashmp.size());
    }
}
