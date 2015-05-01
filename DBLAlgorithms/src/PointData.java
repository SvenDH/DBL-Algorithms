
public class PointData {
    int x;
    int y;
    
    boolean NW = false;
    boolean NE = false;
    boolean SW = false;
    boolean SE = false;
    
    Label LabelNW = null;
    Label LabelNE = null;
    Label LabelSW = null;
    Label LabelSE = null;
    
    PointData (int x, int y) {
        this.x = x;
        this.y = y;
    }

}
