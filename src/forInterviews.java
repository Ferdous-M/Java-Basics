public class forInterviews {
    /*
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
}
