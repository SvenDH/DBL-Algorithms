import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class LabelGeneral extends Label {
    
    List<PointGeneral> points; //Points that own this label
    List<LabelGeneral> overlappingLabels; //Labels this are within 
    
    public LabelGeneral(PointGeneral point, int x, int y) {
        points = new ArrayList<>();
        points.add(point);
        super.x = x; //left-bottom position
        super.y = y;
    }
}
