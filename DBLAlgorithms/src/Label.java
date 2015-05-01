
import java.util.ArrayList;
import java.util.List;


class Label {
    public PointData point;
    public List<PointData> realCollisions = new ArrayList<>();
    public List<PointData> NCollisions = new ArrayList<>();
    public List<PointData> SCollisions = new ArrayList<>();
    public List<PointData> WCollisions = new ArrayList<>();
    public List<PointData> ECollisions = new ArrayList<>();
    public List<PointData> NWCollisions = new ArrayList<>();
    public List<PointData> NECollisions = new ArrayList<>();
    public List<PointData> SWCollisions = new ArrayList<>();
    public List<PointData> SECollisions = new ArrayList<>();
    
    public int verticalDirection;
    public int horizontalDirection;
    
    public Label(PointData point, int vertical, int horizontal) {
        this.point = point;
        verticalDirection = vertical;
        horizontalDirection = horizontal;
    }
}
