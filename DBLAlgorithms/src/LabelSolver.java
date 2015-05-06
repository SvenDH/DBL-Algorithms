
import java.util.List;


abstract class LabelSolver {
    
    int width;
    int height;
    
    public abstract void getLabeledPoints2pos (List<PointData> points);
    public abstract void getLabeledPoints4pos (List<PointData> points);
    public abstract void getLabeledPoints1slider (List<PointData> points);
    
    public void checkCollisions2pos (Label label, PointData point) {
        if (    point.x > label.x - width &&
                point.x < label.x + 2 * width &&
                point.y > label.y - height &&
                point.y < label.y + height){//Is point in "danger-zone"?
            
            if (point.y > label.y) {     //Middle part
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
    
    public void checkCollisions4pos (Label label, PointData point) {
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
