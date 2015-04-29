
import java.util.List;

class BruteForceSolver implements LabelSolver {
     
    private RangeTree<Integer> rs;
    
    @Override
    public void getLabeledPoints(List<PointData> points) {
        rs = new RangeTree<Integer>();
        
        for (PointData point : points) {
            rs.insert(point.x, point.y);
        }
        
        
    }
    
}
