//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

          //  System.out.println("bismillah , amantubillah, alhamdulillah, ya hafizu ");

        String a = new String("hello");
        String b = new String("hello");

        System.out.println(a == b);
        System.out.println(a.equals(b));
        //== → compares references
        //"Are a and b referring to the exact same object?"   →  false


        //equals() → compares values/compares logical content
        //"Do these two String objects contain the same sequence of characters?"  → true
        String ab = "hello";
        String ba = "hello";
        System.out.println("Comparing 2 strings variables"  );
        System.out.println(ab == ba);

        String x = null;
        String y = "hello";

       // System.out.println(x.equals(y)); // This will throw a NullPointerException
        System.out.println("\"hello\".equals(a)"  );
        System.out.println( "hello".equals(x));
        System.out.println( "hello".equals(y));
    }
}