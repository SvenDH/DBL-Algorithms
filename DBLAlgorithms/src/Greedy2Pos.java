
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Greedy2Pos implements ModelSpecificSolver {
    
    int width;
    int height;
    PriorityQueue<Label2Pos> queue;
    
    public Greedy2Pos (int width, int height) {
        this.width = width;
        this.height = height;
        queue = new PriorityQueue<>(new Comparator<Label2Pos>(){//Heuristics
            @Override
            public int compare(Label2Pos o1, Label2Pos o2) {
                int o1Size = 2*o1.realCollisions.size()
                        + o1.WCollisions.size()
                        + o1.ECollisions.size();
                
                int o2Size = 2*o2.realCollisions.size()
                        + o2.WCollisions.size()
                        + o2.ECollisions.size();
                        
                return o1Size - o2Size;
            }
        });
    }
    
    @Override
    public void getLabeledPoints(List<? extends PointData> points) {
        
        for (Iterator<? extends PointData> it1 = points.iterator(); it1.hasNext();) {
            PointData2Pos point = (PointData2Pos) it1.next();
            point.LabelNW = new Label2Pos(point, Direction.W, width, height);
            point.LabelNE = new Label2Pos(point, Direction.E, width, height);
            for (Iterator<? extends PointData> it2 = points.iterator(); it2.hasNext();) {
            PointData2Pos otherPoint = (PointData2Pos) it2.next();
                if (!otherPoint.equals(point)) {//Is it a different point?
                    if (    otherPoint.x < point.x + 2*width && 
                            otherPoint.x > point.x - 2*width &&
                            otherPoint.y < point.y + height &&
                            otherPoint.y > point.y - 2*height) {//Is the point inside the "danger-zone"?
                        checkCollisions(point.LabelNW, otherPoint);
                        checkCollisions(point.LabelNE, otherPoint);
                    }
                }
            }
            queue.add(point.LabelNE);
            queue.add(point.LabelNW);
        }
        
        while (!queue.isEmpty()) {
            Label2Pos label = queue.poll();
            PointData2Pos point = label.point;
            //Delete all other labels for point
            if (!point.LabelNE.equals(label) && point.NE){
                point.NE = false;
                queue.remove(point.LabelNE);
            }
            if (!point.LabelNW.equals(label) && point.NW){
                point.NW = false;
                queue.remove(point.LabelNW);
            }
            
            //Delete all labels of points that are in the new label
            for (PointData2Pos otherPoint : label.realCollisions) {
                if (otherPoint.NW) {
                    otherPoint.NW = false;
                    queue.remove(otherPoint.LabelNW);
                }
                if (otherPoint.NE) {
                    otherPoint.NE = false;
                    queue.remove(otherPoint.LabelNE);
                }
            }
            //Delete all Eastern labels of Western points
            for (PointData2Pos otherPoint : label.WCollisions) {
                if (otherPoint.NE) {
                    otherPoint.NE = false;
                    queue.remove(otherPoint.LabelNE);
                }
            }
            //Delete all Western labels of Eastern points
            for (PointData2Pos otherPoint : label.ECollisions) {
                if (otherPoint.NW) {
                    otherPoint.NW = false;
                    queue.remove(otherPoint.LabelNW);
                }
            }
        }
    }
    
    public void checkCollisions (Label2Pos label, PointData2Pos point) {
        if (    point.x > label.x - width &&
                point.x < label.x + 2 * width &&
                point.y > label.y - height &&
                point.y < label.y + height){//Is point in "danger-zone"?
            
            if (point.x >= label.x + width) {   //E
                label.ECollisions.add(point);
            } else if (point.x > label.x) {     //Label rectangle
                label.realCollisions.add(point);
            } else {                            //W
                label.WCollisions.add(point);
            }
        }
    }
}
