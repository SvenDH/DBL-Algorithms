
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class ForceLabel extends Label {
    
    HashMap<ForceLabel, Double> neighbours;
    double totalForce = 0.0;
    
    ForcePointData point = null;
    
    public ForceLabel(int x, int y){
        super.x = x;
        super.y = y;
        this.neighbours = new HashMap<>();
    }
}
