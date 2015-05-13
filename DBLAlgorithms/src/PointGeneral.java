
import java.util.ArrayList;
import java.util.List;

public class PointGeneral extends PointData {

    List<LabelGeneral> labels;
    
    PointGeneral(int x, int y) {
        super(x, y);
        labels = new ArrayList<>();
    }
    
    @Override
    String getLabelInfo() {
        if (labels.size() == 0){
            return "NIL";
        } else if (labels.size() == 1){
            if (x == labels.get(0).x) {
                if (y == labels.get(0).y){
                    return "NE";
                } else {
                    return "SE";
                }
            } else {
                if (y == labels.get(0).y){
                    return "NW";
                } else {
                    return "SW";
                }
            }
        }
        throw new RuntimeException("There are multiple possible labels");
    }
    
}
