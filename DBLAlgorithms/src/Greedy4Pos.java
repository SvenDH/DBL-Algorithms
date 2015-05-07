
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Greedy4Pos implements ModelSpecificSolver {

    int width;
    int height;
    PriorityQueue<Label4Pos> queue;
    
    public Greedy4Pos (int width, int height) {
        this.width = width;
        this.height = height;
        queue = new PriorityQueue<>(new Comparator<Label4Pos>(){//Heuristics
            @Override
            public int compare(Label4Pos o1, Label4Pos o2) {
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
    public void getLabeledPoints(List<? extends PointData> points) {
        
        for (Iterator<? extends PointData> it1 = points.iterator(); it1.hasNext();) {
            PointData4Pos point = (PointData4Pos) it1.next();
            point.LabelNW = new Label4Pos(point, Direction.N, Direction.W, width, height);
            point.LabelNE = new Label4Pos(point, Direction.N, Direction.E, width, height);
            point.LabelSW = new Label4Pos(point, Direction.S, Direction.W, width, height);
            point.LabelSE = new Label4Pos(point, Direction.S, Direction.E, width, height);
            for (Iterator<? extends PointData> it2 = points.iterator(); it2.hasNext();) {
                PointData4Pos otherPoint = (PointData4Pos) it2.next();
                if (!otherPoint.equals(point)) {//Is it a different point?
                    if (    otherPoint.x < point.x + 2*width && 
                            otherPoint.x > point.x - 2*width &&
                            otherPoint.y < point.y + 2*height &&
                            otherPoint.y > point.y - 2*height) {//Is the point inside the "danger-zone"?
                        checkCollisions(point.LabelNW, otherPoint);
                        checkCollisions(point.LabelNE, otherPoint);
                        checkCollisions(point.LabelSW, otherPoint);
                        checkCollisions(point.LabelSE, otherPoint);
                    }
                }
            }
            queue.add(point.LabelNE);
            queue.add(point.LabelNW);
            queue.add(point.LabelSE);
            queue.add(point.LabelSW);
        }
        
        while (!queue.isEmpty()) {
            Label4Pos label = queue.poll();
            PointData4Pos point = label.point;
            //Delete all other labels for point
            if (!point.LabelNE.equals(label) && point.NE){
                point.NE = false;
                queue.remove(point.LabelNE);
            }
            if (!point.LabelNW.equals(label) && point.NW){
                point.NW = false;
                queue.remove(point.LabelNW);
            }
            if (!point.LabelSE.equals(label) && point.SE){
                point.SE = false;
                queue.remove(point.LabelSE);
            }
            if (!point.LabelSW.equals(label) && point.SW){
                point.SW = false;
                queue.remove(point.LabelSW);
            }
            
            //Delete all labels of points that are in the new label
            for (PointData4Pos otherPoint : label.realCollisions) {
                if (otherPoint.NW) {
                    otherPoint.NW = false;
                    queue.remove(otherPoint.LabelNW);
                }
                if (otherPoint.NE) {
                    otherPoint.NE = false;
                    queue.remove(otherPoint.LabelNE);
                }
                if (otherPoint.SW) {
                    otherPoint.SW = false;
                    queue.remove(otherPoint.LabelSW);
                }
                if (otherPoint.SE) {
                    otherPoint.SE = false;
                    queue.remove(otherPoint.LabelSE);
                }
            }
            //Delete all Southern labels of Northern points
            for (PointData4Pos otherPoint : label.NCollisions) {
                if (otherPoint.SW) {
                    otherPoint.SW = false;
                    queue.remove(otherPoint.LabelSW);
                }
                if (otherPoint.SE) {
                    otherPoint.SE = false;
                    queue.remove(otherPoint.LabelSE);
                }
            }
            //Delete all Northern labels of Southern points
            for (PointData4Pos otherPoint : label.SCollisions) {
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
            for (PointData4Pos otherPoint : label.WCollisions) {
                if (otherPoint.SE) {
                    otherPoint.SE = false;
                    queue.remove(otherPoint.LabelSE);
                }
                if (otherPoint.NE) {
                    otherPoint.NE = false;
                    queue.remove(otherPoint.LabelNE);
                }
            }
            //Delete all Western labels of Eastern points
            for (PointData4Pos otherPoint : label.ECollisions) {
                if (otherPoint.SW) {
                    otherPoint.SW = false;
                    queue.remove(otherPoint.LabelSW);
                }
                if (otherPoint.NW) {
                    otherPoint.NW = false;
                    queue.remove(otherPoint.LabelNW);
                }
            }
            //Delete SE labels of NW points
            for (PointData4Pos otherPoint : label.NWCollisions) {
                if (otherPoint.SE) {
                    otherPoint.SE = false;
                    queue.remove(otherPoint.LabelSE);
                }
            }
            //Delete SW labels of NE points
            for (PointData4Pos otherPoint : label.NECollisions) {
                if (otherPoint.SW) {
                    otherPoint.SW = false;
                    queue.remove(otherPoint.LabelSW);
                }
            }
            //Delete NE labels of SW points
            for (PointData4Pos otherPoint : label.SWCollisions) {
                if (otherPoint.NE) {
                    otherPoint.NE = false;
                    queue.remove(otherPoint.LabelNE);
                }
            }
            //Delete NW labels of SE points
            for (PointData4Pos otherPoint : label.SECollisions) {
                if (otherPoint.NW) {
                    otherPoint.NW = false;
                    queue.remove(otherPoint.LabelNW);
                }
            }
        }
    }
    
    public void checkCollisions(Label4Pos label, PointData4Pos point) {
        if (    point.x > label.x - width &&
                point.x < label.x + 2 * width &&
                point.y > label.y - height &&
                point.y < label.y + 2 * height) { //Is point in "danger-zone"?
            
            if (point.y >= label.y + height) {  //Northern part
                if (point.x >= label.x + width) {   //NE
                    label.NECollisions.add(point);
                } else if (point.x > label.x) {     //N
                    label.NCollisions.add(point);
                } else {                            //NW
                    label.NWCollisions.add(point);
                }
            } else if (point.y > label.y) {     //Middle part
                if (point.x >= label.x + width) {   //E
                    label.ECollisions.add(point);
                } else if (point.x > label.x) {     //Label rectangle
                    label.realCollisions.add(point);
                } else {                            //W
                    label.WCollisions.add(point);
                }
            } else {                            //Southern part
                if (point.x >= label.x + width) {   //SE
                    label.SECollisions.add(point);
                } else if (point.x > label.x) {     //S
                    label.SCollisions.add(point);
                } else {                            //SW
                    label.SWCollisions.add(point);
                }
            }
        }
    }
}
