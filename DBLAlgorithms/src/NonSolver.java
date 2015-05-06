
import java.util.Comparator;
import java.util.List;

class NonSolver extends LabelSolver {
    
    @Override
    public void getLabeledPoints2pos(List<PointData> points) {
        points.sort(new Comparator<PointData>(){
            @Override
            public int compare(PointData o1, PointData o2) {
                return o1.x - o2.x;
            }
        });
    }
    
    @Override
    public void getLabeledPoints4pos(List<PointData> points) {
        points.sort(new Comparator<PointData>(){
            @Override
            public int compare(PointData o1, PointData o2) {
                return o1.x - o2.x;
            }
        });
    }

    @Override
    public void getLabeledPoints1slider(List<PointData> points) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
