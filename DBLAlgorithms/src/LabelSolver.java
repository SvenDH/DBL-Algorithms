
import java.util.List;


abstract class LabelSolver {
    
    int width;
    int height;
    
    abstract List<PointData> getLabeledPoints2pos (List<Point> points);
    abstract List<PointData> getLabeledPoints4pos (List<Point> points);
    abstract List<PointData> getLabeledPoints1slider (List<Point> points);
    
}
