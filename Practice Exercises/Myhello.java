public class Myhello {
    public static void main(String[] args) {
        int a = 4;
        int b = 6;
        String c = "Add";

        switch (c) {
            case "Add":
                System.out.println(a+b);
                break;

            case "Sub":
                System.out.println(a-b);
                break;

            case "Mul":
                System.out.println(a*b);
                break;

            case "Div":
                System.out.println(a/b);
                break;
        
            default:
                System.out.println("no day");
        }
    }
}
