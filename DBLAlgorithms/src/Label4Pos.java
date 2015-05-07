
import java.util.ArrayList;
import java.util.List;

public class Label4Pos extends Label {
  
    public PointData4Pos point;
    public List<PointData4Pos> realCollisions = new ArrayList<>();
    public List<PointData4Pos> NCollisions = new ArrayList<>();
    public List<PointData4Pos> SCollisions = new ArrayList<>();
    public List<PointData4Pos> WCollisions = new ArrayList<>();
    public List<PointData4Pos> ECollisions = new ArrayList<>();
    public List<PointData4Pos> NWCollisions = new ArrayList<>();
    public List<PointData4Pos> NECollisions = new ArrayList<>();
    public List<PointData4Pos> SWCollisions = new ArrayList<>();
    public List<PointData4Pos> SECollisions = new ArrayList<>();
    
    public int verticalDirection;
    public int horizontalDirection;
    
    public Label4Pos(PointData4Pos point, int vertical, int horizontal, int width, int height) {
        verticalDirection = vertical;
        horizontalDirection = horizontal;
        
        if (verticalDirection == Direction.N) {
            if (horizontalDirection == Direction.W) {
                this.x = point.x - width;
                this.y = point.y;
            }
            else if (horizontalDirection == Direction.E) {
                this.x = point.x;
                this.y = point.y;
            }
            else {
                throw new IllegalArgumentException("Something went wrong with the direction");
            }
        }
        else if (verticalDirection == Direction.S) {
            if (horizontalDirection == Direction.W) {
                this.x = point.x - width;
                this.y = point.y - height;
            }
            else if (horizontalDirection == Direction.E) {
                this.x = point.x;
                this.y = point.y - height;
            }
            else {
                throw new IllegalArgumentException("Something went wrong with the direction");
            }
        }
        this.point = point;
    }
}
