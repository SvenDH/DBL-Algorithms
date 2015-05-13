/*************************************************************************
 *  Compilation:  javac Interval.java
 *  Execution:    java Interval
 *
 *  Implementation of an interval.
 *
 *************************************************************************/

public class Interval<Key extends Comparable<Key>> { 
    public final Key low;      // left endpoint
    public final Key high;     // right endpoint
   
    public Interval(Key low, Key high) {
        if (less(high, low)) throw new RuntimeException("Illegal argument");
        this.low  = low;
        this.high = high;
    }

    // is x between low and high
    public boolean contains(Key x) {
        return !less(x, low) && !less(high, x);
    }
    
    // is x between low and high
    public boolean strictlyContains(Key x) {
        return less(low, x) && less(x, high);
    }

    // does this interval intersect that interval?
    public boolean intersects(Interval<Key> that) {
        if (less(this.high, that.low)) return false;
        if (less(that.high, this.low)) return false;
        return true;
    }

    // does this interval equal that interval?
    public boolean equals(Interval<Key> that) {
        return this.low.equals(that.low) && this.high.equals(that.high);
    }


    // comparison helper functions
    private boolean less(Key x, Key y) {
        return x.compareTo(y) < 0;
    }

    // return string representation
    public String toString() {
        return "[" + low + ", " + high + "]";
    }
}


