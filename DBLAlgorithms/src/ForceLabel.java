
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class ForceLabel extends Label {
    
    HashMap<ForceLabel, Double> neighbours;
    double totalForce = 0.0;
    
    boolean unplacable = false;
    
    ForcePointData point = null;
    
    public ForceLabel(int x, int y){
        super.x = x;
        super.y = y;
        this.neighbours = new HashMap<>();
    }
    
    boolean isOverlapping(){
        for (ForceLabel otherLabel : neighbours.keySet()) {
            return (this.x + Globals.width) > otherLabel.x && (otherLabel.x + Globals.width) > this.x;
        }
        return false;
    }
}
