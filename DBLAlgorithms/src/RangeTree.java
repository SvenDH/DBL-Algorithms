import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * A range tree, given a set of 2D points, uses O(nlgn) memory, and can answer in O(lgn+k) time which points lies in
 * a certain rectangle.
 */
public class RangeTree {
    ForceLabel[] points; // TODO: using two arrays for xs and ys is more effecient

    static final Comparator<ForceLabel> compareXCoord = new Comparator<ForceLabel>() {
        @Override
        public int compare(ForceLabel o1, ForceLabel o2) {
            if (o1.x < o2.x) {
                return -1;
            }
            else if (o1.x > o2.x) {
                return 1;
            }
            else {
                if (o1.y < o2.y) {
                    return -1;
                }
                else if (o1.y > o2.y) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        }
    };

    static final Comparator<ForceLabel> compareYCoord = new Comparator<ForceLabel>() {
        @Override
        public int compare(ForceLabel o1, ForceLabel o2) {
            if (o1.y < o2.y) {
                return -1;
            }
            else if (o1.y > o2.y) {
                return 1;
            }
            else {
                if (o1.x < o2.x) {
                    return -1;
                }
                else if (o1.x > o2.x) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        }
    };
    
    public RangeTree(ForceLabel[] points) {
        this.points = points;
        Arrays.sort(points, compareXCoord);
        fractions = new Fraction[points.length];
        buildFractions();
    }

    private void buildFractions() {
        Node root = root();
        fractions[root.index()] = new Fraction(Arrays.copyOf(points, points.length));
        buildFractions(root);
    }

    private void buildFractions(Node node) {
        // invariant: node already has his fraction filled
        int index = node.index(), from = node.from, to = node.to;
        if (node.lc()!=null) {
            fractions[node.index()] = new Fraction(Arrays.copyOfRange(points,
                    node.from, node.to));
            linkFraction(fractions[index],fractions[index].left, fractions[node.index()]);
            buildFractions(node);
        } else Arrays.fill(fractions[index].left, -1);
        node.become(from, to);
        if (node.rc()!=null) {
            fractions[node.index()] = new Fraction(Arrays.copyOfRange(points,
                    node.from, node.to));
            linkFraction(fractions[index],fractions[index].right, fractions[node.index()]);
            buildFractions(node);
        }  else Arrays.fill(fractions[index].right, -1);
    }

    private void linkFraction(Fraction parent, int[] parentLinks, Fraction child) {
        int p = 0, c = 0;
        while (p<parentLinks.length && c<child.points.length) {
            if (child.points[c].y >= parent.points[p].y) {
                parentLinks[p] = c;
                p++;
            }
            else c++;
        }
        for (; p<parentLinks.length; p++) parentLinks[p] = -1;
    }

    Fraction[] fractions;

    static class Fraction {
        ForceLabel[] points;
        int[] right;
        int[] left;

        Fraction(ForceLabel[] points) {
            this.points = points;
            Arrays.sort(points, compareYCoord);
            right = new int[points.length];
            left  = new int[points.length];
        }
    }

    class Node {
        int from, to;
        Node(Node node) {
            from = node.from;
            to   = node.to;
        }
        Node(int from, int to) {
            this.from = from;
            this.to   = to;
        }
        Node rc() {
            if (to-from==1) return null;
            return become(index()+1, to);
        }
        Node lc() {
            if (to-from==1) return null;
            return become(from, index());
        }
        int index() {
            return from+(to-from+1)/2-1;
        }
        ForceLabel get() {
            return points[index()];
        }
        Node become(int from, int to) {
            if (from==to) return null;
            this.from = from;
            this.to = to;
            return this;
        }
        @Override
        public String toString() {
            return "["+from+","+to+")["+index()+"]="+get();
        }
    }

    private Node root() {
        return make(0, points.length);
    }

    private Node make(int from, int to) {
        if (from==to) return null;
        return new Node(from, to);
    }

    private Node splitNode(double start, double end) {
        return splitNode(root(), start, end);
    }

    private Node splitNode(Node node, double start, double end) {
        if (node == null) return node;
        ForceLabel point = node.get();
        if (point.x>=end) return splitNode(node.lc(), start, end);
        else if (point.x<start) return splitNode(node.rc(), start, end);
        return node;
    }

    public ForceLabel splitPoint(double start, double end) {
        Node node = splitNode(start, end);
        return node!=null ? node.get() : null;
    }

    private void getAll(List<ForceLabel> out, Node node, int ymin_ix, double ysup) {
//        logger.info("all " + node);
        if (node==null || ymin_ix==-1) return;
        ForceLabel[] toadd = fractions[node.index()].points;
        for (int i=ymin_ix; i<toadd.length && toadd[i].y<ysup; i++) {
            out.add(toadd[i]);
        }
    }

    private void getSmaller(List<ForceLabel> out, Node node, double val,
                            int ymin_ix,
                            double ymin, double ysup) {
//        logger.info("Smaller " + node);
        if (node==null || ymin_ix==-1) return;
        Fraction frac = fractions[node.index()];
        ForceLabel point = node.get();
        if (point.x<val) {
            int from = node.from, to = node.to;
            if (point.y<ysup && point.y>=ymin) out.add(point);
            getSmaller(out, node.rc(), val, frac.right[ymin_ix], ymin, ysup);
            node.become(from, to);
            getAll(out, node.lc(), frac.left[ymin_ix], ysup);
        }
        else {
            getSmaller(out, node.lc(), val, frac.left[ymin_ix], ymin, ysup);
        }
    }

    private void getLargerEqual(List<ForceLabel> out, Node node, double val,
                                int ymin_ix,
                                double ymin, double ysup) {
//        logger.info("Greater or equal " + node);
        if (node==null || ymin_ix==-1) return;
        Fraction frac = fractions[node.index()];
        ForceLabel point = node.get();
        if (point.x>=val) {
            int from = node.from, to = node.to;
            if (point.y<ysup && point.y>=ymin) out.add(point);
            getLargerEqual(out, node.lc(), val, frac.left[ymin_ix], ymin, ysup);
            node.become(from, to);
            getAll(out, node.rc(), frac.right[ymin_ix], ysup);
        }
        else {
            getLargerEqual(out, node.rc(), val, frac.right[ymin_ix],
                    ymin, ysup);
        }
    }

    public void pointsInRange(List<ForceLabel> out, int xmin, int xsup,
                              int ymin, int ysup) {
//        logger.info("Find " + xmin + ":" + xsup + " " + ymin + ":" + ysup);
        Node node = splitNode(xmin, xsup);
        if (node==null) return;
        Fraction frac = fractions[node.index()];
        ForceLabel[] ypoints = fractions[node.index()].points;
        int ymin_ix = Arrays.binarySearch(ypoints, new ForceLabel(Integer.MIN_VALUE, ymin),
                compareYCoord);
        if (ymin_ix<0) ymin_ix = -1*ymin_ix-1;
//        logger.info("Split node: " + node + " ix "+ymin_ix);
        if (ymin_ix==ypoints.length) return;
        ForceLabel point = node.get();
        if (point.y<ysup && point.y>=ymin) out.add(point);
        Node lc = new Node(node).lc();
        Node rc = new Node(node).rc();
        getSmaller(out, rc, xsup, frac.right[ymin_ix], ymin, ysup);
        getLargerEqual(out, lc, xmin, frac.left[ymin_ix], ymin, ysup);
    }

    public List<ForceLabel> pointsInRange(int xmin, int xsup, int ymin, int ysup) {
        List<ForceLabel> out = new ArrayList();
        pointsInRange(out, xmin, xsup, ymin, ysup);
        return out;
    }
    
    public List<ForceLabel> getOverlappingLabels(ForceLabel label) {
        
        int xmin = label.x - Globals.width;
        int xsup = label.x + Globals.width;
        int ymin = label.y - Globals.height;
        int ysup = label.y + Globals.height;
        List<ForceLabel> out = new ArrayList();
        pointsInRange(out, xmin, xsup, ymin, ysup);
        List<ForceLabel> removes = new ArrayList();
        for (ForceLabel outLabel : out) {
            if (outLabel.x == xmin || outLabel.y == ymin
                        || outLabel.x == xsup || outLabel.y == ysup ){
                removes.add(outLabel);
            }
        }
        out.removeAll(removes);
        out.remove(label);
        return out;
    }

    /**
     * Debug only procedure to make sure a certain path in the tree is correct
     * @param dirs a string of directions, left or right
     * @return a list of points in the path
     */
    public List<ForceLabel> path(String dirs) {
        Node node = root();
        List<ForceLabel> result = new ArrayList();
        result.add(points[node.index()]);
        for (char d : dirs.toCharArray()) {
            if (node==null) continue;
            if (d=='l') node = node.lc();
            else node = node.rc();
            result.add(node!=null ? points[node.index()] : null);
        }
        return result;
    }
}