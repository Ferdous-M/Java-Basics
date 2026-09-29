public class learningEqualsAndHashcodes {
    public static void main(String[] args) {

        //  System.out.println("bismillah , amantubillah, alhamdulillah, ya hafizu ");

        Employee e1 = new Employee(101);
        Employee e2 = new Employee(101);

        System.out.println(e1.equals(e2));             // true
        System.out.println(e1.hashCode() == e2.hashCode()); // true

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

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}


//If two objects are equal according to equals(), they must have the same hashCode().


//a.equals(b) == true
//        ↓
//        a.hashCode() == b.hashCode()
//MUST be true


//But the reverse is not required:
//
//        a.hashCode() == b.hashCode()
//        ↓
//                a.equals(b)