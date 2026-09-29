import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

    public class learningHASHCODEinDeep {

        /*
         * ============================================================
         * WHY DO WE OVERRIDE hashCode() WHEN WE OVERRIDE equals()?
         * ============================================================
         *
         * Java's contract:
         *
         * If:
         *
         *      a.equals(b) == true
         *
         * Then:
         *
         *      a.hashCode() == b.hashCode()
         *
         * MUST also be true.
         *
         *
         * HashSet and HashMap use hashCode() to locate a bucket.
         * Then they use equals() to determine whether objects are equal.
         *
         *
         * Conceptually:
         *
         *              Object
         *                 |
         *                 v
         *             hashCode()
         *                 |
         *                 v
         *            Find bucket
         *                 |
         *                 v
         *              equals()
         *                 |
         *                 v
         *       Is this the same object logically?
         *
         *
         * Important:
         *
         * Same hashCode() DOES NOT mean objects are equal.
         *
         * Different objects can have the same hash code.
         * This is called a HASH COLLISION.
         *
         *
         * But:
         *
         * equals() == true
         *        =>
         * same hashCode()
         *
         * This rule MUST be respected.
         */


        // ============================================================
        // 1. EMPLOYEE CLASS
        // ============================================================

        static class Employee {

            private int id;
            private String name;

            public Employee(int id, String name) {
                this.id = id;
                this.name = name;
            }

            /*
             * equals() defines LOGICAL EQUALITY.
             *
             * Here we are saying:
             *
             * Two Employees are considered equal
             * if their IDs are equal.
             */

            @Override
            public boolean equals(Object obj) {

                if (this == obj) {
                    return true;
                }

                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }

                Employee other = (Employee) obj;

                return this.id == other.id;
            }


            /*
             * hashCode() must use the SAME fields
             * that participate in equals().
             *
             * Since equals() uses id,
             * hashCode() also uses id.
             */

            @Override
            public int hashCode() {
                return Integer.hashCode(id);
            }


            @Override
            public String toString() {
                return "Employee{id=" + id + ", name='" + name + "'}";
            }
        }


        // ============================================================
        // 2. BASIC equals() AND hashCode() EXAMPLE
        // ============================================================

        static void basicExample() {

            Employee e1 = new Employee(101, "Alice");
            Employee e2 = new Employee(101, "Bob");

            System.out.println("e1 == e2: " + (e1 == e2));

            System.out.println("e1.equals(e2): "
                    + e1.equals(e2));

            System.out.println("e1.hashCode(): "
                    + e1.hashCode());

            System.out.println("e2.hashCode(): "
                    + e2.hashCode());

            System.out.println("Same hashCode: "
                    + (e1.hashCode() == e2.hashCode()));
        }


        // ============================================================
        // 3. HASHSET EXAMPLE
        // ============================================================

        static void hashSetExample() {

            Employee e1 = new Employee(101, "Alice");
            Employee e2 = new Employee(101, "Bob");
            Employee e3 = new Employee(102, "Charlie");

            Set<Employee> employees = new HashSet<>();

            employees.add(e1);
            employees.add(e2);
            employees.add(e3);

            /*
             * e1 and e2 have the same ID.
             *
             * equals() says:
             *
             * e1 == logically equal to e2
             *
             * hashCode() also returns the same value.
             *
             * Therefore HashSet treats e2 as a duplicate.
             */

            System.out.println("\nHashSet:");
            System.out.println(employees);

            System.out.println("Size: " + employees.size());
        }


        // ============================================================
        // 4. HASHMAP EXAMPLE
        // ============================================================

        static void hashMapExample() {

            Employee e1 = new Employee(101, "Alice");
            Employee e2 = new Employee(101, "Bob");

            Map<Employee, String> employeeRoles = new HashMap<>();

            employeeRoles.put(e1, "Developer");

            /*
             * e2 is a different object,
             * but e2.equals(e1) == true.
             *
             * Because hashCode() is also the same,
             * HashMap can find the same logical key.
             */

            System.out.println("\nHashMap:");

            System.out.println(
                    "Value using e1: "
                            + employeeRoles.get(e1)
            );

            System.out.println(
                    "Value using e2: "
                            + employeeRoles.get(e2)
            );
        }


        // ============================================================
        // 5. WHY hashCode() IS IMPORTANT
        // ============================================================

        /*
         * Imagine we override equals()
         * but DON'T override hashCode().
         *
         * Object's hashCode() would be used.
         *
         * Then we could have:
         *
         * e1.equals(e2) == true
         *
         * but:
         *
         * e1.hashCode() != e2.hashCode()
         *
         * This violates the equals/hashCode contract.
         *
         * HashSet / HashMap can then behave incorrectly.
         */


        // ============================================================
        // 6. HASH COLLISION
        // ============================================================

        static class CollisionExample {

            private int id;

            CollisionExample(int id) {
                this.id = id;
            }

            @Override
            public boolean equals(Object obj) {

                if (this == obj) {
                    return true;
                }

                if (!(obj instanceof CollisionExample)) {
                    return false;
                }

                CollisionExample other =
                        (CollisionExample) obj;

                return this.id == other.id;
            }

            /*
             * Deliberately returning the same hash code
             * for every object.
             *
             * This demonstrates a HASH COLLISION.
             */

            @Override
            public int hashCode() {
                return 1;
            }
        }


        static void collisionExample() {

            CollisionExample a = new CollisionExample(101);
            CollisionExample b = new CollisionExample(102);

            System.out.println("\nHash Collision:");

            System.out.println(
                    "a.hashCode(): " + a.hashCode()
            );

            System.out.println(
                    "b.hashCode(): " + b.hashCode()
            );

            System.out.println(
                    "Same hashCode: "
                            + (a.hashCode() == b.hashCode())
            );

            System.out.println(
                    "a.equals(b): "
                            + a.equals(b)
            );

            /*
             * Output:
             *
             * Same hashCode: true
             * a.equals(b): false
             *
             * This proves:
             *
             * Same hashCode DOES NOT mean equals().
             */
        }


        // ============================================================
        // 7. IMPORTANT RULES TO REMEMBER
        // ============================================================

        /*
         *
         * RULE 1:
         *
         * If a.equals(b) == true
         * then:
         *
         * a.hashCode() == b.hashCode()
         *
         *
         * RULE 2:
         *
         * Same hashCode does NOT guarantee equals().
         *
         *
         * RULE 3:
         *
         * If you override equals(),
         * you should also override hashCode().
         *
         *
         * RULE 4:
         *
         * The fields used in equals()
         * should generally be consistent with
         * the fields used in hashCode().
         *
         *
         * RULE 5:
         *
         * HashSet uses hashCode() + equals().
         *
         * HashMap uses hashCode() + equals()
         * for keys.
         *
         */


        // ============================================================
        // 8. MAIN
        // ============================================================

        public static void main(String[] args) {

            System.out.println("===== BASIC EXAMPLE =====");
            basicExample();

            System.out.println("\n===== HASHSET EXAMPLE =====");
            hashSetExample();

            System.out.println("\n===== HASHMAP EXAMPLE =====");
            hashMapExample();

            System.out.println("\n===== COLLISION EXAMPLE =====");
            collisionExample();
        }
    }



    // ===========================================================================

   // ===========================================================================

    // ===========================================================================

//    Why do we override hashCode() with equals()?
//
//    Because HashMap and HashSet use hashCode() to find where an object belongs, and then use equals() to determine whether two objects are actually equal.
//
//    Java has a contract:
//
//    If a.equals(b) is true, then a.hashCode() and b.hashCode() must be the same.



//    e2
//              ↓
//    hashCode()
//              ↓
//    find hash bucket
//              ↓
//    compare with objects
//              ↓
//    equals()
//
//    If e1 and e2 are equal, they must reach the same hash bucket so that HashSet can recognize them as duplicates.
//
//    If their hash codes are different, they may go to different buckets:
//
//    Bucket 1              Bucket 2
//
//    e1                     e2
//↓                      ↓
//    hash 123456            hash 789012
//
//    The HashSet may never compare them with equals().
//
//    So you can end up with both objects in the set even though equals() says they're equal.
//
//    That's why we override hashCode() consistently.


//    equals()
//   ↓
//           "Are these logically the same?"
//
//    hashCode()
//   ↓
//           "Which bucket should this object go to?"


    //Java's equals() and hashCode() contract requires equal objects to have the same hash code. Hash-based collections like HashMap and HashSet use the hash code to locate a bucket and then use equals() to determine equality. If we override equals() without consistently overriding hashCode(), equal objects may produce different hash codes and hash-based collections may behave incorrectly.”

