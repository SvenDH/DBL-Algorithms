public class PointData4Pos extends PointData {
    
    boolean NW = true;
    boolean NE = true;
    boolean SW = true;
    boolean SE = true;
    
    Label4Pos LabelNW = null;
    Label4Pos LabelNE = null;
    Label4Pos LabelSW = null;
    Label4Pos LabelSE = null;
    
    PointData4Pos (int x, int y) {
        super(x, y);
    }
    
    @Override
    String getLabelInfo() {
        if (NW) {
            return "NW";
        } else if (NE) {
            return "NE";
        } else if (SW) {
            return "SW";
        } else if (SE) {
            return "SE";
        } else {
            return "NIL";
        }
    }
}
