
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
        if (unplacable)
            return false;
        
        for (ForceLabel otherLabel : neighbours.keySet()) {
            if (otherLabel.unplacable)
		continue;
            if ((this.x + Globals.width) > otherLabel.x && (otherLabel.x + Globals.width) > this.x)
                return true;
        }
        return false;
    }
}
