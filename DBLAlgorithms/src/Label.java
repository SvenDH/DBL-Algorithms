
import java.util.ArrayList;
import java.util.List;


class Label {
    
    private final int N = 0;
    private final int S = 1;
    private final int W = 2;
    private final int E = 3;
    
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
    public int x;
    public int y;
    
    public Label(PointData point, int vertical, int horizontal, int width, int height) {
        
        verticalDirection = vertical;
        horizontalDirection = horizontal;
        
        if (verticalDirection == N) {
            if (horizontalDirection == W) {
                this.x = point.x - width;
                this.y = point.y;
            }
            else if (horizontalDirection == E) {
                this.x = point.x;
                this.y = point.y;
            }
            else {
                throw new IllegalArgumentException("Something went wrong with the direction");
            }
        }
        else if (verticalDirection == S) {
            if (horizontalDirection == W) {
                this.x = point.x - width;
                this.y = point.y - height;
            }
            else if (horizontalDirection == E) {
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
