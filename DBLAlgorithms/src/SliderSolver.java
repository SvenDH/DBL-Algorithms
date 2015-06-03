
import java.util.List;


abstract class SliderSolver {
    
    int width;
    int height;
    
    abstract List<PointData> getLabeledPoints1slider (List<Point> points);
    
}
