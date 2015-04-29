
import java.util.ArrayList;
import java.util.List;


class Label {
    public PointData point;
    
    public List<PointData> possibleCollisions = new ArrayList<>();
    
    public Label(PointData point) {
        this.point = point;
    }
}
