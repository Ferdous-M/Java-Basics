# Java `equals()` and `hashCode()` — Deep Interview Notes

> **Core idea:** `equals()` defines **logical equality**, while `hashCode()` helps **hash-based collections locate objects efficiently**.

---

# 1. `==` vs `equals()`

For objects:

```java
== 
```

compares **references**.

```java
equals()
```

compares **logical equality**, provided that the class has overridden `equals()` appropriately.

Example:

```java
String a = new String("hello");
String b = new String("hello");

System.out.println(a == b);
System.out.println(a.equals(b));
```

Output:

```text
false
true
```

Why?

```text
a ───────► ["hello"]   Object 1

b ───────► ["hello"]   Object 2
```

`a` and `b` refer to different objects.

Therefore:

```java
a == b
```

is:

```text
false
```

But the contents are equal:

```java
a.equals(b)
```

is:

```text
true
```

---

# 2. Important String Trap — String Pool

Consider:

```java
String a = "hello";
String b = "hello";

System.out.println(a == b);
```

This can print:

```text
true
```

Why?

Java maintains a **String Pool** for string literals and may reuse the same String object.

Conceptually:

```text
              String Pool
                   |
                   v
              ["hello"]
               ↑     ↑
               |     |
               a     b
```

Therefore:

```java
a == b
```

may be `true`.

But:

```java
String a = new String("hello");
String b = new String("hello");
```

creates separate objects:

```text
a ─────► ["hello"]  Object 1

b ─────► ["hello"]  Object 2
```

Therefore:

```java
a == b          // false
a.equals(b)     // true
```

### Interview rule

Do not use:

```java
==
```

to compare String contents.

Use:

```java
.equals()
```

---

# 3. What is `equals()`?

`equals()` is a method defined in:

```java
java.lang.Object
```

Every Java class ultimately inherits it.

The default implementation from `Object` behaves essentially like reference equality.

A class can **override** it to define its own logical equality.

Example:

```java
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
```

Now:

```java
Employee e1 = new Employee(101);
Employee e2 = new Employee(101);

System.out.println(e1.equals(e2));
```

prints:

```text
true
```

Even though:

```java
e1 == e2
```

is:

```text
false
```

because they are two different objects.

---

# 4. What is `hashCode()`?

`hashCode()` is also defined in:

```java
java.lang.Object
```

It returns an integer:

```java
int
```

Example:

```java
Employee e = new Employee(101);

System.out.println(e.hashCode());
```

Hash-based collections use hash codes to help determine where an object should be searched/stored.

Important collections include:

```text
HashMap
HashSet
Hashtable
```

---

# 5. The Most Important `equals()` / `hashCode()` Contract

The most important rule:

> If two objects are equal according to `equals()`, they MUST have the same hash code.

Formally:

```java
a.equals(b) == true
```

must imply:

```java
a.hashCode() == b.hashCode()
```

Diagram:

```text
a.equals(b)
    |
    | true
    v
a.hashCode() == b.hashCode()
```

---

# 6. The Reverse Is NOT Required

This is a very important interview trick.

If:

```java
a.hashCode() == b.hashCode()
```

it does **NOT** mean:

```java
a.equals(b) == true
```

Why?

Because different objects can have the same hash code.

This is called a:

# Hash Collision

Example:

```java
class Employee {

    int id;

    Employee(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
```

Every Employee gets:

```text
hashCode() = 1
```

So:

```java
Employee e1 = new Employee(101);
Employee e2 = new Employee(102);
```

can have:

```java
e1.hashCode() == e2.hashCode()
```

which is:

```text
true
```

But:

```java
e1.equals(e2)
```

can still be:

```text
false
```

Therefore:

```text
Same hash code
      ≠
Same object / equal object
```

---

# 7. The Core Hash-Based Collection Flow

This is one of the most useful diagrams to remember:

```text
                 Object
                    |
                    v
               hashCode()
                    |
                    v
              Find bucket
                    |
                    v
                 equals()
                    |
                    v
          Same logical object?
```

More precisely:

```text
Object
  |
  v
hashCode()
  |
  v
Determine candidate bucket
  |
  v
Check objects in that bucket
  |
  v
equals()
  |
  v
Determine logical equality
```

### Important

`hashCode()` does NOT itself determine equality.

It helps the collection find the **candidate location**.

`equals()` determines whether two objects are logically equal.

---

# 8. Why Override `hashCode()` When Overriding `equals()`?

Suppose:

```java
Employee e1 = new Employee(101);
Employee e2 = new Employee(101);
```

Our `equals()` says:

```java
e1.equals(e2) == true
```

But if we do not override `hashCode()`, the inherited implementation from `Object` may produce different hash codes for the two objects.

We could end up conceptually with:

```text
e1.equals(e2)
      ↓
    true

BUT

e1.hashCode()
      ↓
  123456

e2.hashCode()
      ↓
  789012
```

That violates the Java contract.

The two equal objects may therefore be directed to different buckets in a hash-based collection.

The collection may then fail to recognize them as the same logical object.

### Golden Rule

> **Whenever you override `equals()`, override `hashCode()` consistently.**

---

# 9. Correct `Employee` Example

```java
class Employee {

    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
```

Here:

```java
equals()
```

uses:

```java
id
```

and:

```java
hashCode()
```

also uses:

```java
id
```

Therefore the two methods are consistent.

---

# 10. Why Should `equals()` and `hashCode()` Use the Same Fields?

Suppose:

```java
equals()
```

uses:

```java
id
```

but:

```java
hashCode()
```

uses:

```java
name
```

Then two objects can be equal according to `equals()` but produce different hash codes.

Example:

```text
Employee 1
id = 101
name = Alice

Employee 2
id = 101
name = Bob
```

If:

```java
equals()
```

only compares `id`, then:

```java
e1.equals(e2)
```

is:

```text
true
```

But if:

```java
hashCode()
```

uses `name`, the hash codes may differ.

That violates the contract.

### Rule

The fields that determine logical equality should be reflected consistently in `hashCode()`.

---

# 11. HashSet Example

```java
Employee e1 = new Employee(101, "Alice");
Employee e2 = new Employee(101, "Bob");
Employee e3 = new Employee(102, "Charlie");

Set<Employee> employees = new HashSet<>();

employees.add(e1);
employees.add(e2);
employees.add(e3);

System.out.println(employees.size());
```

Because:

```java
e1.equals(e2) == true
```

and:

```java
e1.hashCode() == e2.hashCode()
```

`HashSet` treats `e2` as a duplicate.

Therefore:

```text
size = 2
```

Conceptually:

```text
add(e1)
   ↓
hashCode()
   ↓
bucket
   ↓
store e1


add(e2)
   ↓
hashCode()
   ↓
same bucket
   ↓
equals(e2, e1)
   ↓
true
   ↓
duplicate
   ↓
do not add
```

---

# 12. HashMap Example

`HashMap` uses the same basic idea for its **keys**.

```java
Employee e1 = new Employee(101, "Alice");
Employee e2 = new Employee(101, "Bob");

Map<Employee, String> map = new HashMap<>();

map.put(e1, "Developer");

System.out.println(map.get(e2));
```

Even though:

```java
e1 != e2
```

if:

```java
e1.equals(e2) == true
```

and:

```java
e1.hashCode() == e2.hashCode()
```

then `HashMap` can find the value associated with the logical key.

Output:

```text
Developer
```

### Important

The `equals()` / `hashCode()` contract is particularly important when an object is used as a:

```text
HashMap key
```

or:

```text
HashSet element
```

---

# 13. HashMap `put()` / `get()` Mental Model

For:

```java
map.put(key, value);
```

conceptually:

```text
key
 |
 v
hashCode()
 |
 v
find bucket
 |
 v
compare existing keys using equals()
 |
 v
insert / replace
```

For:

```java
map.get(key);
```

conceptually:

```text
key
 |
 v
hashCode()
 |
 v
find bucket
 |
 v
compare candidate keys using equals()
 |
 v
return matching value
```

This is why a broken `equals()` / `hashCode()` implementation can make a `HashMap` appear to "lose" values.

---

# 14. Important `HashMap` Trap

Consider:

```java
Employee e1 = new Employee(101, "Alice");

Map<Employee, String> map = new HashMap<>();

map.put(e1, "Developer");

e1.id = 999;
```

If `id` participates in:

```java
hashCode()
```

then the object's hash code has changed **after it was inserted**.

The object may now logically belong to a different bucket.

The map does not automatically move the object to the new bucket.

Therefore:

```java
map.get(e1)
```

may fail to find the entry.

### Very important rule

> **Do not mutate fields that participate in `equals()` / `hashCode()` while the object is being used as a key in a `HashMap` or as an element in a `HashSet`.**

This is a common deeper interview question.

---

# 15. Full `equals()` Contract

The `equals()` method has important properties.

## 1. Reflexive

For a non-null object:

```java
x.equals(x)
```

must be:

```text
true
```

An object must be equal to itself.

---

## 2. Symmetric

If:

```java
x.equals(y) == true
```

then:

```java
y.equals(x) == true
```

must also be true.

Example:

```text
x.equals(y)
    ↓
  true

y.equals(x)
    ↓
  true
```

---

## 3. Transitive

If:

```java
x.equals(y) == true
y.equals(z) == true
```

then:

```java
x.equals(z)
```

must also be true.

---

## 4. Consistent

If neither object changes, repeated calls should consistently return the same result.

```java
x.equals(y)
```

should not randomly alternate between:

```text
true
false
true
false
```

---

## 5. Non-null

For a non-null object:

```java
x.equals(null)
```

must return:

```text
false
```

It should not normally throw a `NullPointerException`.

---

# 16. Safe `equals()` Implementation

A robust implementation commonly starts with:

```java
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
```

### Why each check?

### `this == obj`

```java
if (this == obj)
```

Checks whether both references point to the exact same object.

If yes, they are obviously equal.

---

### `obj == null`

```java
if (obj == null)
```

An object cannot logically be equal to `null`.

---

### `getClass() != obj.getClass()`

Ensures we are comparing compatible class types.

Then we can safely cast:

```java
Employee other = (Employee) obj;
```

---

# 17. Null-Safe String Comparison

This can throw:

```java
String name = null;

name.equals("Alice");
```

because `name` is `null`.

This is safer:

```java
"Alice".equals(name);
```

because the string literal cannot be null.

Another common option:

```java
Objects.equals(name1, name2);
```

Example:

```java
Objects.equals(name1, name2);
```

This handles null values safely.

---

# 18. `Objects.hash()`

Instead of manually calculating a hash code, we can use:

```java
Objects.hash(id, name);
```

Example:

```java
@Override
public int hashCode() {
    return Objects.hash(id, name);
}
```

This is convenient when multiple fields participate in equality.

Example:

```java
@Override
public boolean equals(Object obj) {

    if (this == obj) {
        return true;
    }

    if (!(obj instanceof Employee)) {
        return false;
    }

    Employee other = (Employee) obj;

    return id == other.id &&
           Objects.equals(name, other.name);
}

@Override
public int hashCode() {
    return Objects.hash(id, name);
}
```

---

# 19. Hash Collision

A collision happens when:

```java
a.hashCode() == b.hashCode()
```

even though:

```java
a.equals(b) == false
```

Example:

```java
class CollisionExample {

    private int id;

    CollisionExample(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof CollisionExample)) {
            return false;
        }

        CollisionExample other =
                (CollisionExample) obj;

        return this.id == other.id;
    }
}
```

Then:

```java
CollisionExample a =
        new CollisionExample(101);

CollisionExample b =
        new CollisionExample(102);
```

can produce:

```text
a.hashCode() == b.hashCode()
        ↓
       true

a.equals(b)
        ↓
       false
```

This is valid.

### Remember:

```text
Same hash code
      ≠
Equal objects
```

---

# 20. Why Doesn't HashMap Use Only `hashCode()`?

Because hash collisions are possible.

Suppose:

```text
Object A → hash 100
Object B → hash 100
```

They have the same hash code but may not be equal.

Therefore the collection needs another check:

```java
equals()
```

So the general idea is:

```text
hashCode()
    ↓
quickly locate candidate bucket
    ↓
equals()
    ↓
confirm logical equality
```

This gives hash-based collections both **efficiency** and **correctness**.

---

# 21. `HashSet` Does Not Simply Compare Every Object With Every Other Object

A common beginner misconception is:

> "HashSet uses equals() to compare every new object with every existing object."

That's not the normal conceptual model.

Instead:

```text
new element
     ↓
hashCode()
     ↓
candidate bucket
     ↓
compare with relevant elements
     ↓
equals()
```

The hash code reduces the number of objects that need to be compared.

That is why a good hash function matters.

---

# 22. What Happens If `hashCode()` Is Poor?

A poor hash function can produce many collisions.

For example:

```java
@Override
public int hashCode() {
    return 1;
}
```

All objects go to the same hash bucket.

The collection can still be **correct** if `equals()` is correct, but performance can become worse because many objects have to be compared.

Therefore:

> A good `hashCode()` should distribute objects reasonably across buckets.

---

# 23. What Happens If `equals()` Is Wrong?

If `equals()` incorrectly says two different logical objects are equal:

```text
HashSet
```

may reject an object that should have been stored.

Similarly, `HashMap` may treat two different keys as the same logical key.

So both methods are important:

```text
hashCode()
+
equals()
```

---

# 24. `hashCode()` Does NOT Need to Be Unique

Another interview trap:

> Does every object need a unique hash code?

**No.**

Hash codes are `int` values, so there are far fewer possible hash codes than possible Java objects.

Therefore collisions are inevitable in principle.

The requirement is:

```text
Equal objects
     ↓
Same hash code
```

NOT:

```text
Different objects
     ↓
Different hash codes
```

---

# 25. `hashCode()` Can Be Cached

For immutable objects, a hash code can sometimes be cached.

This is useful because:

```java
hashCode()
```

may be called many times when an object is used in hash-based collections.

A classic example is `String`, whose implementation is designed around immutable content.

The important concept:

> If an object's equality-defining state does not change, its hash code can safely remain stable.

---

# 26. Immutability and Hash-Based Collections

Objects used as:

```text
HashMap keys
HashSet elements
```

are safest when their equality-defining fields are immutable.

Example:

```java
final class EmployeeKey {

    private final int id;

    public EmployeeKey(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof EmployeeKey)) {
            return false;
        }

        EmployeeKey other =
                (EmployeeKey) obj;

        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
```

Because `id` cannot change:

```java
private final int id;
```

the hash code remains stable.

---

# 27. Interview Question: Why Override Both?

### Question

**Why should we override `equals()` and `hashCode()` together?**

### Interview Answer

> `equals()` defines logical equality, while `hashCode()` is used by hash-based collections such as `HashMap` and `HashSet` to locate candidate buckets. Java's contract requires that if two objects are equal according to `equals()`, they must have the same hash code. Therefore, whenever we override `equals()`, we should also override `hashCode()` consistently using the same equality-defining fields.

---

# 28. Interview Question: Can Two Unequal Objects Have the Same Hash Code?

### Answer

Yes.

That is called a:

```text
Hash Collision
```

For example:

```text
a.hashCode() == b.hashCode()
```

can be true while:

```text
a.equals(b)
```

is false.

---

# 29. Interview Question: Can Two Equal Objects Have Different Hash Codes?

### Answer

**No.**

If:

```java
a.equals(b) == true
```

then:

```java
a.hashCode() == b.hashCode()
```

must be true.

If they have different hash codes, the implementation violates the Java contract.

---

# 30. Interview Question: Is `hashCode()` Unique?

### Answer

No.

Multiple unequal objects can have the same hash code because hash collisions are possible.

---

# 31. Interview Question: What Does `HashSet` Use?

### Short answer:

```text
hashCode() + equals()
```

Conceptually:

```text
add(object)
    ↓
hashCode()
    ↓
find bucket
    ↓
equals()
    ↓
duplicate or new element?
```

---

# 32. Interview Question: What Does `HashMap` Use?

For keys:

```text
hashCode() + equals()
```

Conceptually:

```text
put(key, value)
       ↓
   hashCode()
       ↓
   find bucket
       ↓
     equals()
       ↓
 insert / update
```

For:

```java
map.get(key)
```

conceptually:

```text
key
 ↓
hashCode()
 ↓
candidate bucket
 ↓
equals()
 ↓
matching key
 ↓
return value
```

---

# 33. Golden Rules

```text
============================================================
INTERVIEW QUICK REVISION
============================================================

==

    For objects:
    compares references.

------------------------------------------------------------

equals()

    Defines logical equality.

------------------------------------------------------------

hashCode()

    Returns an int hash value.

    Hash-based collections use it to locate
    candidate buckets efficiently.

------------------------------------------------------------

IMPORTANT CONTRACT

If:

    a.equals(b) == true

Then:

    a.hashCode() == b.hashCode()

MUST be true.

------------------------------------------------------------

BUT:

    a.hashCode() == b.hashCode()

does NOT mean:

    a.equals(b) == true

Why?

    Hash collisions are possible.

------------------------------------------------------------

HashSet

    hashCode() + equals()

------------------------------------------------------------

HashMap

    hashCode() + equals() for keys

------------------------------------------------------------

HASH COLLISION

    Different / unequal objects
    can have the same hash code.

------------------------------------------------------------

IMPORTANT

    Same hash code
        !=
    Equal objects

------------------------------------------------------------

ANOTHER IMPORTANT RULE

    Equal objects
        =>
    Same hash code

------------------------------------------------------------

WHEN OVERRIDING

    Override equals()
        +
    Override hashCode()

together.

------------------------------------------------------------

FIELDS

    The fields used to determine equality
    should be represented consistently in hashCode().

------------------------------------------------------------

MUTABILITY TRAP

    Do not change equality/hashCode-defining fields
    while an object is being used as a HashMap key
    or HashSet element.

------------------------------------------------------------

PERFORMANCE

    Good hashCode()
        →
    fewer collisions
        →
    fewer equality checks
        →
    better hash-based collection performance.

============================================================
```

# 34. One Diagram to Remember Forever

```text
                    OBJECT
                       |
                       v
                  hashCode()
                       |
                       v
                FIND BUCKET
                       |
                       v
              CANDIDATE OBJECTS
                       |
                       v
                   equals()
                       |
                       v
             LOGICALLY EQUAL?
                  /        \
                YES         NO
                 |           |
                 v           v
             same key/    different
             duplicate     object
```

### The mental model

```text
hashCode()
    =
"Where should I look?"

equals()
    =
"Is this actually the same logical object?"
```

This distinction is the key to understanding:

```text
HashSet
HashMap
ConcurrentHashMap
Caching
Entity identity
Collections Framework
```

and many Java interview questions.

# 35. Final One-Line Memory Trick

> **`hashCode()` finds the neighborhood; `equals()` identifies the house.**
