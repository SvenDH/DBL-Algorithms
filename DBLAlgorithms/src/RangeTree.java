/*************************************************************************
 *  Compilation:  javac RangeTree.java
 *  Execution:    java RangeTree
 *  
 *  Range tree implemented using binary search trees.
 *
 *  Assumes no two points have the same x or y coordinate!!!!
 *
 *  Could be made more efficient by assuming points are given
 *  all at once. Then could sort by x and by y to ensure tree
 *  are perfectly balanced.
 * 
 *************************************************************************/

public class RangeTree<Key extends Comparable<Key>>  {

    private Node root;   // root of the primary BST

    // BST helper node data type
    private class Node {
        Key x, y;                   // x- and y- coordinates
        Node left, right;           // left and right subtrees
        RangeSearch<Key, Key> bst;  // secondary BST

        Node(Key x, Key y) {
            this.x   = x;
            this.y   = y;
            this.bst = new RangeSearch<Key, Key>();
            this.bst.put(y, x);
        }
    }


  /***********************************************************************
    *  Insert (x, y) into range tree
    *  Sort by x in primary BST; sort by y in secondary BST
    ***********************************************************************/
    public void insert(Key x, Key y) {
        root = insert(root, x, y);
    }

    private Node insert(Node h, Key x, Key y) {
        if (h == null) 
            return new Node(x, y);
        h.bst.put(y, x);
        if (lessEqual(x, h.x)) 
            h.left  = insert(h.left,  x, y);
        else              
            h.right = insert(h.right, x, y);
        return h;
    }


   /*************************************************************************
    *  Range searching
     * @param rect
    *************************************************************************/


    // print all points in range
    public void query2D(Interval2D<Key> rect) {
        Interval<Key> intervalX = rect.intervalX;
        // find splitting node h where h.x is in the x-interval
        Node h = root;
        while (h != null && !intervalX.contains(h.x)) {
            if (lessEqual(intervalX.high, h.x))
                h = h.left;
            else if (lessEqual(h.x, intervalX.low))
                h = h.right;
        }
        if (h == null)
            return;

        if (rect.contains(h.x, h.y))
            System.out.println("A: " + h.x + ", " + h.y);

        queryL(h.left,  rect);
        queryR(h.right, rect);
    }

    // find all keys >= xmin in subtree rooted at h
    private void queryL(Node h, Interval2D<Key> rect) {
        if (h == null) 
            return;
        if (rect.contains(h.x, h.y)) 
            System.out.println("B: " + h.x + ", " + h.y);
        if (!lessEqual(h.x, rect.intervalX.low)) {
            enumerate(h.right, rect);
            queryL(h.left, rect);
        }
        else {
            queryL(h.right, rect);
        }
    }

    // find all keys <= xmax in subtree rooted at h
    private void queryR(Node h, Interval2D<Key> rect) {
        if (h == null) 
            return;
        if (rect.contains(h.x, h.y)) 
            System.out.println("C: " + h.x + ", " + h.y);
        if (!lessEqual(rect.intervalX.high, h.x)) {
            enumerate(h.left, rect);
            queryR(h.right, rect);
        }
        else {
            queryR(h.left, rect);
        }
    }

    // precondition: subtree rooted at h has keys between xmin and xmax
    private void enumerate(Node h, Interval2D<Key> rect) {
        if (h == null) 
            return;
        System.out.println("integrity: " + h.bst.check());
        Iterable<Key> list = h.bst.range(rect.intervalY);
        for (Key y : list) {
            Key x = h.bst.get(y);
            System.out.println("D: " + x + ", " + y);
        }
        System.out.println("-");
    }


   /*************************************************************************
    *  helper comparison functions
    *************************************************************************/

    private boolean lessEqual(Key k1, Key k2) {
        return k1.compareTo(k2) <= 0;
    }

}


