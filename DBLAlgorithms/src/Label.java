
import java.util.ArrayList;
import java.util.List;


class Label {
    public PointData point;
    public List<PointData> possibleCollisions = new ArrayList<>();
    
    public Direction direction;
    
    public Label(PointData point, Direction direction) {
        this.point = point;
        this.direction = direction;
    }
}
