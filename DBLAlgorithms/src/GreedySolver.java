
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class GreedySolver implements LabelSolver {
    
    private int width;
    private int height;
    PriorityQueue<Label> queue;
    
    GreedySolver (int w, int h) {
        width = w;
        height = h;
        queue = new PriorityQueue<>(new Comparator<Label>(){//Heuristics
            @Override
            public int compare(Label o1, Label o2) {
                int o1Size = 4*o1.realCollisions.size()
                        + o1.NCollisions.size()
                        + o1.SCollisions.size()
                        + o1.WCollisions.size()
                        + o1.ECollisions.size()
                        + 2*o1.NWCollisions.size()
                        + 2*o1.NECollisions.size()
                        + 2*o1.SWCollisions.size()
                        + 2*o1.SECollisions.size();
                
                int o2Size = 4*o2.realCollisions.size()
                        + o2.NCollisions.size()
                        + o2.SCollisions.size()
                        + o2.WCollisions.size()
                        + o2.ECollisions.size()
                        + 2*o2.NWCollisions.size()
                        + 2*o2.NECollisions.size()
                        + 2*o2.SWCollisions.size()
                        + 2*o2.SECollisions.size();
                        
                return o1Size - o2Size;
            }
        });
    }
    
    @Override
    public void getLabeledPoints(List<PointData> points) {
        
        for (PointData point : points ) {
            
            point.LabelNW = new Label(point, Direction.N, Direction.W);
            point.LabelNE = new Label(point, Direction.N, Direction.E);
            point.LabelSW = new Label(point, Direction.S, Direction.W);
            point.LabelSE = new Label(point, Direction.S, Direction.E);
            for (PointData otherPoint : points) {
                if (!otherPoint.equals(point)) {//Is it a different point?
                    if (    otherPoint.x < point.x + width && 
                            otherPoint.x > point.x - width &&
                            otherPoint.y < point.y + height &&
                            otherPoint.y > point.y - height) {//Is the point inside one of the labels?
                        if (otherPoint.y >= point.y) {//Is the point in a North label?
                            if (otherPoint.x >= point.x) { //NE
                                point.LabelNE.realCollisions.add(otherPoint);
                            }
                            if (otherPoint.x <= point.x) { //NW
                                point.LabelNW.realCollisions.add(otherPoint);
                            }
                        }
                        if (otherPoint.y <= point.y) {//Is the point in a South label?
                            if (otherPoint.x >= point.x) { //SE
                                point.LabelSE.realCollisions.add(otherPoint);
                            }
                            if (otherPoint.x <= point.x) { //SW
                                point.LabelSW.realCollisions.add(otherPoint);
                            }
                        }
                    }
                }
            }
            queue.add(point.LabelNE);
            queue.add(point.LabelNW);
            queue.add(point.LabelSE);
            queue.add(point.LabelSW);
        }
        
        while (!queue.isEmpty()) {
            Label label = queue.poll();
            PointData point = label.point;
            
            
            
            
        }
    }
    
    private void generateCollisions (Label label) {
        
    }
}
