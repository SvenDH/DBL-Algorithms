
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
            
            point.LabelNW = new Label(point, Direction.N, Direction.W, point.x - width, point.y);
            point.LabelNE = new Label(point, Direction.N, Direction.E, point.x, point.y);
            point.LabelSW = new Label(point, Direction.S, Direction.W, point.x - width, point.y - height);
            point.LabelSE = new Label(point, Direction.S, Direction.E, point.x, point.y - height);
            for (PointData otherPoint : points) {
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
            Label label = queue.poll();
            PointData point = label.point;
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
            for (PointData otherPoint : label.realCollisions) {
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
            for (PointData otherPoint : label.NCollisions) {
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
            for (PointData otherPoint : label.SCollisions) {
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
            for (PointData otherPoint : label.WCollisions) {
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
            for (PointData otherPoint : label.ECollisions) {
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
            for (PointData otherPoint : label.NWCollisions) {
                if (otherPoint.SE) {
                    otherPoint.SE = false;
                    queue.remove(otherPoint.LabelSE);
                }
            }
            //Delete SW labels of NE points
            for (PointData otherPoint : label.NECollisions) {
                if (otherPoint.SW) {
                    otherPoint.SW = false;
                    queue.remove(otherPoint.LabelSW);
                }
            }
            //Delete NE labels of SW points
            for (PointData otherPoint : label.SWCollisions) {
                if (otherPoint.NE) {
                    otherPoint.NE = false;
                    queue.remove(otherPoint.LabelNE);
                }
            }
            //Delete NW labels of SE points
            for (PointData otherPoint : label.SECollisions) {
                if (otherPoint.NW) {
                    otherPoint.NW = false;
                    queue.remove(otherPoint.LabelNW);
                }
            }
        }
    }
    
    private void checkCollisions (Label label,PointData point) {
        if (    point.x > label.x - width &&
                point.x < label.x + 2*width &&
                point.y > label.y - height &&
                point.y < label.y + 2*height){//Is point in "danger-zone"?
            
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
