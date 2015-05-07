public class PointData2Pos extends PointData {
    
    boolean NW = true;
    boolean NE = true;
    
    Label2Pos LabelNW = null;
    Label2Pos LabelNE = null;
    
    PointData2Pos (int x, int y) {
        super(x, y);
    }

    @Override
    String getLabelInfo() {
        if (NW) {
            return "NW";
        } else if (NE) {
            return "NE";
        } else {
            return "NILL";
        }
    }
    
}
