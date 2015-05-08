
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class GreedySolver extends LabelSolver {
    
    ModelSpecificSolver solver;
    List<PointData> pointData;
    
    GreedySolver (int w, int h) {
        width = w;
        height = h;
        pointData = new ArrayList<>();
    }

    @Override
    public List<PointData> getLabeledPoints1slider(List<Point> points) {
        for (Point point : points)
            pointData.add(new PointData1Slider(point.x, point.y));
        solver = new Greedy1Slider(width, height);
        solver.getLabeledPoints(pointData);
        return pointData;
    }

    @Override
    public List<PointData> getLabeledPoints2pos(List<Point> points) {
        for (Point point : points)
            pointData.add(new PointData2Pos(point.x, point.y));
        solver = new Greedy2Pos(width, height);
        solver.getLabeledPoints(pointData);
        return pointData;
    }

    @Override
    public List<PointData> getLabeledPoints4pos(List<Point> points) {
        for (Point point : points)
            pointData.add(new PointData4Pos(point.x, point.y));
        solver = new Greedy4Pos(width, height);
        solver.getLabeledPoints(pointData);
        return pointData;
    }
}
