
public class Node {
        int x, y;                   // x- and y- coordinates
        Node left, right;           // left and right subtrees
        int N;                      // size of the subtree rooted at the Node
        LabelGeneral label;         // The label which belongs to this node

        Node(int x, int y) {
            this.x   = x;
            this.y   = y;
            this.N   = 1;
        }
    }