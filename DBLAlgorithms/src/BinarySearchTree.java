
import java.util.List;
import java.util.NoSuchElementException;

public class BinarySearchTree {
    
    private List<PointData> points;
    private Node root;   // root of the primary BST
    
    public BinarySearchTree (List<PointData> points) {
        this.points = points;
    }

    // BST helper node data type
    private class Node {
        int key;           // sorted by key
        PointData point;                   // x- and y- coordinates
        Node left, right;           // left and right subtrees
        int N;             // number of nodes in subtree

        Node(int key, PointData p, int N) {
            this.key = key;
            this.point = p;
            this.N = N;
        }
    }
    
     // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return number of key-value pairs in BST
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

   /***********************************************************************
    *  Search BST for given key, and return associated value if found,
    *  return null if not found
    ***********************************************************************/
    // does there exist a key-value pair with given key?
    public boolean contains(int key) {
        return get(key) != null;
    }

    // return value associated with the given key, or null if no such key exists
    public PointData get(int key) {
        return get(root, key);
    }

    private PointData get(Node node, int key) {
        if (node == null) return null;
        if      (points.get(key).x < node.point.x) return get(node.left, key);
        else if (points.get(key).x > node.point.x) return get(node.right, key);
        else              return node.point;
    }
    
    public void put(int key, PointData val) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
    }

    private Node put(Node node, int key, PointData point) {
        if (node == null) return new Node(key, point, 1);
        if      (points.get(key).x < node.point.x) node.left  = put(node.left,  key, point);
        else if (points.get(key).x > node.point.x) node.right = put(node.right, key, point);
        else              node.point   = point;
        node.N = 1 + size(node.left) + size(node.right);
        return node;
    }
    
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    
    public void delete(int key) {
        root = delete(root, key);
    }

    private Node delete(Node node, int key) {
        if (node == null) return null;
        if      (points.get(key).x < node.point.x) node.left  = delete(node.left,  key);
        else if (points.get(key).x > node.point.x) node.right = delete(node.right, key);
        else { 
            if (node.right == null) return node.left;
            if (node.left  == null) return node.right;
            Node t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        } 
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }
    
     public int min() {
        if (isEmpty()) return -1;
        return min(root).key;
    } 

    private Node min(Node node) { 
        if (node.left == null) return node; 
        else                return min(node.left); 
    } 

    public int max() {
        if (isEmpty()) return -1;
        return max(root).key;
    } 

    private Node max(Node node) { 
        if (node.right == null) return node; 
        else                 return max(node.right); 
    } 
    
}