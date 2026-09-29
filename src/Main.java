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

    }
}