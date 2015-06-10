
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class SliderQuadTree {
    private Node root;

    
    public SliderQuadTree() {
    }

    // helper node data type
    private class Node {
        int x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;   // four subtrees
        ForceLabel label;           //labels having this point

        Node(int x, int y, ForceLabel label) {
            this.x = x;
            this.y = y;
            this.label = label;
        }
    }


  /***********************************************************************
    *  Insert (x, y) into appropriate quadrant
    ***********************************************************************/
    public void insert(ForceLabel label) {
        root = insert(root, label.point.x, label.point.y, label);
    }

    private Node insert(Node h, int x, int y, ForceLabel label) {
        if (h == null) return new Node(x, y, label);
        if (x == h.x && y == h.y) h.label = label;
        else if ( x < h.x &&  y < h.y) h.SW = insert(h.SW, x, y, label);
        else if ( x < h.x && y >= h.y) h.NW = insert(h.NW, x, y, label);
        else if (x >= h.x && y < h.y) h.SE = insert(h.SE, x, y, label);
        else if (x >= h.x && y >= h.y) h.NE = insert(h.NE, x, y, label);
        return h;
    }


  /***********************************************************************
    *  Range search.
    ***********************************************************************/

    public List<ForceLabel> findNeighbours(ForceLabel label) {
        Interval<Integer> intX = new Interval<Integer>(label.point.x - 2 * Globals.width, label.point.x + 2 * Globals.width);
        Interval<Integer> intY = new Interval<Integer>(label.point.y - Globals.height, label.point.y + Globals.height);
        Interval2D<Integer> rect = new Interval2D<Integer>(intX, intY);
        HashSet<ForceLabel> result = new HashSet<>();
        query2D(root, rect, result);
        return new ArrayList<>(result);
    }

    private void query2D(Node h, Interval2D<Integer> rect, HashSet<ForceLabel> result) {
        if (h == null) return;
        int xmin = rect.intervalX.low;
        int ymin = rect.intervalY.low;
        int xmax = rect.intervalX.high;
        int ymax = rect.intervalY.high;
        if (rect.contains(h.x, h.y)){
            if (h.label.x != xmin && h.label.y != ymin
                    && h.label.x != xmax && h.label.y != ymax ){
                    //&& !(label.x == xmin && label.y == ymin)){
                //System.out.println(label.x+", "+xmax);
                result.add(h.label);
            }
        }
        if ( xmin < h.x &&  ymin < h.y) query2D(h.SW, rect, result);
        if ( xmin < h.x && ymax >= h.y) query2D(h.NW, rect, result);
        if (xmax >= h.x &&  ymin < h.y) query2D(h.SE, rect, result);
        if (xmax >= h.x && ymax >= h.y) query2D(h.NE, rect, result);
    }
    //public static void main(String[] args) {
    //    LabelPlacer.main(args);
    //}
    
}
