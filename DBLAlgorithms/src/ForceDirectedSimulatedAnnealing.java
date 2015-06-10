
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;


public class ForceDirectedSimulatedAnnealing extends LabelSolver {
    final double MIN_FORCE = 0.5;
    
    final double DEFAULT_FORCE_FAKT_OVERLAPPING = 10.0;
    final double DEFAULT_FORCE_FAKT_EPS = 0.5;
    final double DEFAULT_OVERLAPPING_PENALTY = 3 * (1 / (DEFAULT_FORCE_FAKT_EPS * DEFAULT_FORCE_FAKT_EPS));

    double temperature = 0;
    double cooling_rate = 0;
    int    moves_per_stage = 0;
    int    size = 0;

    int   nRejected = 0;
    int   nTaken = 0;
    int   nUnsignificant = 0;
    int   nStages = 0;
    int   nIterations = 0;

    HashSet obstructed = null;

    float overallForce = 0.0f;
    
    int width;
    int height;
    
    List<PointData> pointList;
    
    SliderQuadTree QT;
     
    public ForceDirectedSimulatedAnnealing(int width, int height) {
        this.width = width;
        this.height = height;
        pointList = new ArrayList<>();
        QT = new SliderQuadTree();
    }

    @Override
    List<PointData> getLabeledPoints1slider(List<Point> points) {
        
        ArrayList<ForceLabel> labelList = new ArrayList<ForceLabel>();
        obstructed = new HashSet<ForceLabel>();
        
        //Place labels on random position
        for (Point point : points) {
            double shift = Math.random();
            //System.out.println(shift);
            int xPos = (int)(shift * (double)width);
            ForceLabel label = new ForceLabel(point.x - width + xPos, point.y);
            ForcePointData pointData = new ForcePointData(point.x, point.y, label);
            label.point = pointData;
            pointList.add(pointData);
            labelList.add(label);
            QT.insert(label);
        }
        
        //Find neighbour
        for (ForceLabel label : labelList){
            List<ForceLabel> neighbours = QT.findNeighbours(label);
            for(ForceLabel otherLabel : neighbours){
                double force = 0.0;
                if((label.x + Globals.width) > otherLabel.x && (otherLabel.x + Globals.width) > label.x){ //Are the labels overlapping?
                    if(label.x > otherLabel.x){ //other label is left from this label
                        force = DEFAULT_FORCE_FAKT_OVERLAPPING * ((otherLabel.x + Globals.width) - label.x) + DEFAULT_OVERLAPPING_PENALTY;
                    } else if(label.x < otherLabel.x){ //other label is right from this label
                        force = -DEFAULT_FORCE_FAKT_OVERLAPPING * ((label.x + Globals.width) - otherLabel.x) - DEFAULT_OVERLAPPING_PENALTY;
                    } else { //Labels are on the same position
                        force = -otherLabel.neighbours.getOrDefault(label, DEFAULT_FORCE_FAKT_OVERLAPPING * ((otherLabel.x + Globals.width) - label.x) + DEFAULT_OVERLAPPING_PENALTY);
                    }
                }
                label.totalForce += force;
                label.neighbours.put(otherLabel, force);
            }
            overallForce += Math.abs(label.totalForce);
        }
        
        for (ForceLabel otherLabel : labelList.get(0).neighbours.keySet()) {
            System.out.println("Force: " + labelList.get(0).neighbours.get(otherLabel));
        }
        System.out.println("Total force: " + labelList.get(0).totalForce);
        System.out.println("Overal force: " + overallForce);
        
        updateForces(labelList.get(0));
        
        for (ForceLabel otherLabel : labelList.get(0).neighbours.keySet()) {
            System.out.println("Force: " + labelList.get(0).neighbours.get(otherLabel));
        }
        System.out.println("Total force: " + labelList.get(0).totalForce);
        
        while (!obstructed.isEmpty()) {
            nIterations ++;
            ForceLabel current = chooseNextCandidate();
            
            double old_force = overallForce;
            double old_position = current.x;
        }
        
        return pointList;
    }
    
    ForceLabel chooseNextCandidate(){
        if(!obstructed.isEmpty()) {
            Random random = new Random();
            
            int i = random.nextInt(obstructed.size());
            Iterator it = obstructed.iterator();
            ForceLabel l = null;
            for(int j=0; j<= i && it.hasNext(); j++)
                l = (ForceLabel)it.next();

            return l;
        }
	return null;
    }
    
    void findEquilibrium (ForceLabel label) {
        if (Math.abs(label.totalForce) < MIN_FORCE) {
            System.out.println("Label already in equilibrium");
            return;
        }
        
        double total = 0; //Amount we can move
        double amount = 0; //Amount we move
        int old_direction = 0;
        int iteration = 0;
        
        //Label wants to move right
        if (label.totalForce > 0){
            total = label.point.x - label.x;
            old_direction = 1;
        } else //Label wants to move left
        if (label.totalForce < 0) {
            total = (label.x + Globals.width) - label.point.x;
            old_direction = -1;
        }
        
        amount = total * 0.2;
        
        while (iteration < 20 && Math.abs(label.totalForce) >= MIN_FORCE){
            label.x += old_direction * amount;
            if(label.x < label.point.x - Globals.width){
                label.x = label.point.x - Globals.width;
            }
            
            updateForces(label);
            
            iteration ++;
        }
        
    }
    
    void updateForces(ForceLabel label) {
        overallForce -= Math.abs(label.totalForce);
        label.totalForce = 0.0;
        
        for(ForceLabel otherLabel : label.neighbours.keySet()) {
            overallForce -= Math.abs(otherLabel.totalForce);
            otherLabel.totalForce -= otherLabel.neighbours.get(label);
            
            double force = 0.0;
            if((label.x + Globals.width) > otherLabel.x && (otherLabel.x + Globals.width) > label.x){ //Are the labels overlapping?
                if(label.x > otherLabel.x){ //other label is left from this label
                    force = DEFAULT_FORCE_FAKT_OVERLAPPING * ((otherLabel.x + Globals.width) - label.x) + DEFAULT_OVERLAPPING_PENALTY;
                } else if(label.x < otherLabel.x){ //other label is right from this label
                    force = -DEFAULT_FORCE_FAKT_OVERLAPPING * ((label.x + Globals.width) - otherLabel.x) - DEFAULT_OVERLAPPING_PENALTY;
                } else { //Labels are on the same position
                    force = -otherLabel.neighbours.getOrDefault(label, DEFAULT_FORCE_FAKT_OVERLAPPING * ((otherLabel.x + Globals.width) - label.x) + DEFAULT_OVERLAPPING_PENALTY);
                }
            }
            
            label.totalForce += force;
            otherLabel.totalForce += -force;
            overallForce += Math.abs(otherLabel.totalForce);
        }
        
        overallForce += Math.abs(label.totalForce);
    }
    
    @Override
    List<PointData> getLabeledPoints2pos(List<Point> points) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    List<PointData> getLabeledPoints4pos(List<Point> points) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
