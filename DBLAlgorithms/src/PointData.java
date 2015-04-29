
public class PointData {
    public int x;
    public int y;
    
    boolean NW;
    boolean NE;
    boolean SW;
    boolean SE;
    
    public PointData (int x, int y) {
        this.x = x;
        this.y = y;
        NW = true;
        NE = true;
        SW = true;
        SE = true;
    }
}
