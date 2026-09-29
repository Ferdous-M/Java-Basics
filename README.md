/*

           

⭐ One thing I especially want to keep

This diagram:

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

It's much better than memorizing:

"HashSet uses hashCode and equals."

Because when an interviewer asks “Why?”, you can actually explain the mechanism.
* ============================================================
* INTERVIEW QUICK REVISION
* ============================================================
*
* ==          -> compares references for objects
*
* equals()    -> compares logical equality
*
* hashCode()  -> produces hash value used by hash-based
*                collections to locate a bucket
*
*
* IMPORTANT CONTRACT:
*
* a.equals(b) == true
*        =>
* a.hashCode() == b.hashCode()
*
*
* BUT:
*
* a.hashCode() == b.hashCode()
*        =>
* DOES NOT mean
* a.equals(b) == true
*
*
* WHY?
*
* Because hash collisions are possible.
*
*
* HashSet:
*      hashCode() + equals()
*
* HashMap:
*      hashCode() + equals() for keys
*
*
* GOLDEN RULE:
*
* Override equals() and hashCode() together.
*
* ============================================================
  */
