
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
        queue = new PriorityQueue<>(new Comparator<Label>(){
            @Override
            public int compare(Label o1, Label o2) {
                return o1.possibleCollisions.size() - o2.possibleCollisions.size();
            }
        });
    }
    
    @Override
    public void getLabeledPoints(List<PointData> points) {
        
        for (PointData point : points ) {
            
            point.LabelNW = new Label(point, Direction.NW);
            point.LabelNE = new Label(point, Direction.NE);
            point.LabelSW = new Label(point, Direction.SW);
            point.LabelSE = new Label(point, Direction.SE);
            for (PointData otherPoint : points) {
                if (!otherPoint.equals(point)) {//Is it a different point?
                    if (    otherPoint.x < point.x + width && 
                            otherPoint.x > point.x - width &&
                            otherPoint.y < point.y + height &&
                            otherPoint.y > point.y - height) {//Is the point inside one of the labels?
                        if (otherPoint.y >= point.y) {//Is the point in a North label?
                            if (otherPoint.x >= point.x) { //NE
                                point.LabelNE.possibleCollisions.add(otherPoint);
                            }
                            if (otherPoint.x <= point.x) { //NW
                                point.LabelNW.possibleCollisions.add(otherPoint);
                            }
                        }
                        if (otherPoint.y <= point.y) {//Is the point in a South label?
                            if (otherPoint.x >= point.x) { //SE
                                point.LabelSE.possibleCollisions.add(otherPoint);
                            }
                            if (otherPoint.x <= point.x) { //SW
                                point.LabelSW.possibleCollisions.add(otherPoint);
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
            
            //Remove labels that overlap point
            for (PointData otherPoint : point.LabelNE.possibleCollisions) {
                if (otherPoint.SW && !(point.x == otherPoint.x || point.y == otherPoint.y)) {
                    otherPoint.SW = false;
                    queue.remove(otherPoint.LabelSW);
                }
            }
            for (PointData otherPoint : point.LabelNW.possibleCollisions) {
                if (otherPoint.SE && !(point.x == otherPoint.x || point.y == otherPoint.y)) {
                    otherPoint.SE = false;
                    queue.remove(otherPoint.LabelSE);
                }
            }
            for (PointData otherPoint : point.LabelSE.possibleCollisions) {
                if (otherPoint.NW && !(point.x == otherPoint.x || point.y == otherPoint.y)) {
                    otherPoint.NW = false;
                    queue.remove(otherPoint.LabelNW);
                }
            }
            for (PointData otherPoint : point.LabelSW.possibleCollisions) {
                if (otherPoint.NE && !(point.x == otherPoint.x || point.y == otherPoint.y)) {
                    otherPoint.NE = false;
                    queue.remove(otherPoint.LabelNE);
                }
            }
            //Remove other labels that can't be placed anymore
            if (point.LabelNE.equals(label)){
                for (PointData otherPoint : point.LabelNE.possibleCollisions) {
                    if (otherPoint.SE && !(point.y == otherPoint.y)) {
                        otherPoint.SE = false;
                        queue.remove(otherPoint.LabelSE);
                    }
                    if (otherPoint.NW && !(point.x == otherPoint.x)) {
                        otherPoint.NW = false;
                        queue.remove(otherPoint.LabelNW);
                    }
                    if (otherPoint.NE) {
                        otherPoint.NE = false;
                        queue.remove(otherPoint.LabelNE);
                    }
                }
                for (PointData otherPoint : point.LabelSE.possibleCollisions) {
                    if (otherPoint.NE) {
                        otherPoint.NE = false;
                        queue.remove(otherPoint.LabelNE);
                    }
                }
                for (PointData otherPoint : point.LabelNW.possibleCollisions) {
                    if (otherPoint.NE) {
                        otherPoint.NE = false;
                        queue.remove(otherPoint.LabelNE);
                    }
                }
            } else if (point.LabelNW.equals(label)){
                for (PointData otherPoint : point.LabelNW.possibleCollisions) {
                    if (otherPoint.SW && !(point.y == otherPoint.y)) {
                        otherPoint.SW = false;
                        queue.remove(otherPoint.LabelSW);
                    }
                    if (otherPoint.NW) {
                        otherPoint.NW = false;
                        queue.remove(otherPoint.LabelNW);
                    }
                    if (otherPoint.NE && !(point.x == otherPoint.x)) {
                        otherPoint.NE = false;
                        queue.remove(otherPoint.LabelNE);
                    }
                }
                for (PointData otherPoint : point.LabelSW.possibleCollisions) {
                    if (otherPoint.NW) {
                        otherPoint.NW = false;
                        queue.remove(otherPoint.LabelNW);
                    }
                }
                for (PointData otherPoint : point.LabelNE.possibleCollisions) {
                    if (otherPoint.NW) {
                        otherPoint.NW = false;
                        queue.remove(otherPoint.LabelNW);
                    }
                }
            } else if (point.LabelSE.equals(label)){
                for (PointData otherPoint : point.LabelSE.possibleCollisions) {
                    if (otherPoint.SE) {
                        otherPoint.SE = false;
                        queue.remove(otherPoint.LabelSE);
                    }
                    if (otherPoint.SW && !(point.x == otherPoint.x)) {
                        otherPoint.SW = false;
                        queue.remove(otherPoint.LabelSW);
                    }
                    if (otherPoint.NE && !(point.y == otherPoint.y)) {
                        otherPoint.NE = false;
                        queue.remove(otherPoint.LabelNE);
                    }
                }
                for (PointData otherPoint : point.LabelSW.possibleCollisions) {
                    if (otherPoint.SE) {
                        otherPoint.SE = false;
                        queue.remove(otherPoint.LabelSE);
                    }
                }
                for (PointData otherPoint : point.LabelNE.possibleCollisions) {
                    if (otherPoint.SE) {
                        otherPoint.SE = false;
                        queue.remove(otherPoint.LabelSE);
                    }
                }
            } else if (point.LabelSW.equals(label)){
                for (PointData otherPoint : point.LabelSW.possibleCollisions) {
                    if (otherPoint.SE && !(point.x == otherPoint.x)) {
                        otherPoint.SE = false;
                        queue.remove(otherPoint.LabelSE);
                    }
                    if (otherPoint.SW) {
                        otherPoint.SW = false;
                        queue.remove(otherPoint.LabelSW);
                    }
                    if (otherPoint.NW && !(point.y == otherPoint.y)) {
                        otherPoint.NW = false;
                        queue.remove(otherPoint.LabelNW);
                    }
                }
                for (PointData otherPoint : point.LabelSE.possibleCollisions) {
                    if (otherPoint.SW) {
                        otherPoint.SW = false;
                        queue.remove(otherPoint.LabelSW);
                    }
                }
                for (PointData otherPoint : point.LabelNW.possibleCollisions) {
                    if (otherPoint.SW) {
                        otherPoint.SW = false;
                        queue.remove(otherPoint.LabelSW);
                    }
                }
            }
            
            
        }
    }
    
}
