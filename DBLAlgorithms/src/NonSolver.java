
import java.util.Comparator;
import java.util.List;

class NonSolver implements LabelSolver {

    @Override
    public void getLabeledPoints(List<PointData> points) {
        points.sort(new Comparator<PointData>(){
            @Override
            public int compare(PointData o1, PointData o2) {
                return o1.x - o2.x;
            }
        });
    }
    
}
