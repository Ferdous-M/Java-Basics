public class learningEqualsAndHashcodes {
    public static void main(String[] args) {

        //  System.out.println("bismillah , amantubillah, alhamdulillah, ya hafizu ");
        Employee e1 = new Employee(101);
        Employee e2 = new Employee(101);

        System.out.println(e1.equals(e2));
        System.out.println(e1.hashCode() == e2.hashCode());

    }
}
class Employee {
    int id;

    Employee(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        Employee other = (Employee) obj;
        return this.id == other.id;
    }
}
