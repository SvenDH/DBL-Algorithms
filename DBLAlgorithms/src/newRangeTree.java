
import java.util.ArrayList;
import sun.misc.Queue;


public class newRangeTree {
    
    private Node root; //Root node which contains all nodes in the tree
    
    private class Node {
        int x, y;                   // x- and y- coordinates
        Node left, right;           // left and right subtrees
        int N;                      // size of the subtree rooted at the Node

        Node(int x, int y) {
            this.x   = x;
            this.y   = y;
            this.N   = 1;
        }
    }
    
    public void insert(int x, int y) {
        if (root == null) {
            root = new Node(x, y);
        }
        else {
            insert(new Node(x, y), root);
        }
    }
    /**
     * Inserts a given node, sorted on x primarily and y secondarily
     * 
     * @param x The given node, to be inserted
     * @param y The node which is used to check where the given node should be
     * inserted, varying per iteration
     */
    private void insert(Node x, Node y) {
        if (x.x == y.x) {
            if (x.y == y.y) {
                System.out.println(
                    "Invalid input for RangeTree: '"
                            + x.x + ", " + x.y + "' already exists");
            } else if (x.y < y.y) {
                if (y.left == null) {
                    y.left = x;
                } else {
                    insert(x, y.left);
                }
            } else {
                if (y.right == null) {
                    y.right = x;
                } else {
                    insert(x, y.right);
                }
            }
        } else if (x.x < y.x) {
            if (y.left == null) {
                y.left = x;
            } else {
                insert(x, y.left);
            }
        } else {
            if (y.right == null) {
                y.right = x;
            } else {
                insert(x, y.right);
            }
        }
    }
    
    public void remove(int x, int y) {
        remove(new Node(x, y), root);
    }
    
    /**
     * Removes a specified node, given that it exists
     * @param x the specified node
     * @param y the node which is being checked
     * @pre Node x != null
     */
    private void remove(Node x, Node y) {
        if (y == null) {
            return;
        }
        if (x.x == y.x) {
            if (x.y == y.y) {
                y = joinLR(y.left, y.right);
            } else if (x.y < y.y) {
                remove(x, y.left);
            } else {
                remove(x, y.right);
            }
        } else if (x.x < y.x) {
            remove(x, y.left);
        } else {
            remove(x, y.right);
        }
        fix(y);
    }
    
    private Node joinLR(Node a, Node b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        if (size(a) > size(b)) {
            a.right = joinLR(a.right, b);
            fix(a);
            return a;
        } else {
            b.left = joinLR(a, b.left);
            fix(b);
            return b;
        }
    }
    
    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.N;
    }
    
    private void fix(Node x) {
        if (x == null) {  // check needed for remove
            return;
        }
        x.N = 1 + size(x.left) + size(x.right);
    }
    
    public void query2D(Interval2D<Integer> rect) {
        Interval<Integer> intervalX = rect.intervalX;
        // find splitting node h where h.x is in the x-interval
        Node h = root;
        while (h != null && !intervalX.contains(h.x)) {
            if (intervalX.high <= h.x) {
                h = h.left;
            } else if (h.x <= intervalX.low) {
                h = h.right;
            }
        }
        if (h == null) {
            return;
        }
        if (rect.contains(h.x, h.y)) {
            System.out.println("A: " + h.x + ", " + h.y);
        }
        queryL(h.left,  rect);
        queryR(h.right, rect);
    }
    
    // find all keys >= xmin in subtree rooted at h
    private void queryL(Node h, Interval2D<Integer> rect) {
        if (h == null) {
            return;
        }
        if (rect.contains(h.x, h.y)) {
            System.out.println("B: " + h.x + ", " + h.y);
        }
        if (h.x > rect.intervalX.low) {
            enumerate(h.right, rect);
            queryL(h.left, rect);
        } else {
            queryL(h.right, rect);
        }
    }

    // find all keys <= xmax in subtree rooted at h
    private void queryR(Node h, Interval2D<Integer> rect) {
        if (h == null) {
            return;
        }
        if (rect.contains(h.x, h.y)) {
            System.out.println("C: " + h.x + ", " + h.y);
        }
        if (rect.intervalX.high > h.x) {
            enumerate(h.left, rect);
            queryR(h.right, rect);
        } else {
            queryR(h.left, rect);
        }
    }
    
    // precondition: subtree rooted at h has keys in rect
    private void enumerate(Node h, Interval2D<Integer> rect) {
        if (h == null) {
            return;
        }
        ArrayList<Integer> list = range(h, rect.intervalY);
        for (int x : list) {
            int y = h.y;
            System.out.println("D: " + x + ", " + y);
        }
    }
    
    public ArrayList<Integer> range(Node x, Interval<Integer> interval) { 
        ArrayList<Integer> list = new ArrayList<Integer>();
        range(x, interval, list);
        return list;
    }
    private void range(Node x, Interval<Integer> interval, ArrayList<Integer> list) {
        if (x == null) {
            return;
        }
        if (x.x < interval.low) {
            range(x.left, interval, list);
        }
        if (interval.contains(x.x)) {
            list.add(x.x);
        }
        if (interval.high >= x.x) {
            range(x.right, interval, list);
        }
    }
}
