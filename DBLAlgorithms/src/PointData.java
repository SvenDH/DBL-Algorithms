
public class PointData {
    int x;
    int y;
    
    boolean NW = true;
    boolean NE = true;
    boolean SW = true;
    boolean SE = true;
    
    Label LabelNW = null;
    Label LabelNE = null;
    Label LabelSW = null;
    Label LabelSE = null;
    
    PointData (int x, int y) {
        this.x = x;
        this.y = y;
    }

}
