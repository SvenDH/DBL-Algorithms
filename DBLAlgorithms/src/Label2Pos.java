
import java.util.ArrayList;
import java.util.List;

public class Label2Pos extends Label {
   
    public PointData2Pos point;
    public List<PointData2Pos> realCollisions = new ArrayList<>();
    public List<PointData2Pos> SCollisions = new ArrayList<>();
    public List<PointData2Pos> WCollisions = new ArrayList<>();
    public List<PointData2Pos> ECollisions = new ArrayList<>();
    public List<PointData2Pos> SWCollisions = new ArrayList<>();
    public List<PointData2Pos> SECollisions = new ArrayList<>();
    
    public int horizontalDirection;
    
    public Label2Pos(PointData2Pos point, int horizontal, int width, int height) {
        horizontalDirection = horizontal;
        
        if (horizontalDirection == Direction.W) {
            this.x = point.x - width;
            this.y = point.y;
        } else if (horizontalDirection == Direction.E) {
            this.x = point.x;
            this.y = point.y;
        } else {
            throw new IllegalArgumentException("Something went wrong with the direction");
        }
        this.point = point;
    }
}
