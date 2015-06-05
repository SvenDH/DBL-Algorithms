
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class NewBruteForceSolver extends LabelSolver {
    
    int width;
    int height;
    List<PointData> pointList;
    QuadTree QT;
    Stack<Map.Entry<Point, LabelGeneral>> disabledLabels = new Stack();
    HashMap<Point, LabelGeneral> bestSolution;
    
    public NewBruteForceSolver(int width, int height) {
        this.width = width;
        this.height = height;
        this.pointList = new ArrayList();
        this.QT = new QuadTree(width, height);
        this.bestSolution = new HashMap();
    }
    
    public void bruteForce(HashMap<Point, LabelGeneral> possibleLabels, HashMap<Point, LabelGeneral> currentSolution) {
        if (possibleLabels.isEmpty() || bestSolution.size() == Globals.numberOfPoints) {
            return;
        }
        
        for (Map.Entry<Point, LabelGeneral> label : possibleLabels.entrySet()) {
            // TODO: find the overlaps with this label, query quadtree
            HashMap<Point, LabelGeneral> overlaps = findOverlaps();
            possibleLabels.remove(label.getKey(), label.getValue());
            int removedLAmount = 0;
            for (Map.Entry<Point, LabelGeneral> overlap : overlaps.entrySet()) {
                possibleLabels.remove(overlap.getKey(), overlap.getValue());
                disabledLabels.push(overlap);
                removedLAmount++;
            }
            currentSolution.put(label.getKey(), label.getValue());
            if (currentSolution.size() > bestSolution.size()) {
                bestSolution = currentSolution;
            }
            bruteForce(possibleLabels, currentSolution);
            currentSolution.remove(label.getKey(), label.getValue());
            possibleLabels.put(label.getKey(), label.getValue());
            while (removedLAmount > 0) {
                Map.Entry<Point, LabelGeneral> entry = disabledLabels.pop();
                possibleLabels.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    List<PointData> getLabeledPoints2pos(List<Point> points) {
        HashMap<Point, LabelGeneral> possibleLabels = new HashMap();
        
        for(Point point : points) {
            PointGeneral pointData = new PointGeneral(point.x, point.y);
            pointList.add(pointData);
            //Create new labels for this point
            Set<Point> labelPositions = new HashSet<>(2);
            labelPositions.add(new Point(point.x, point.y));
            labelPositions.add(new Point(point.x - width, point.y));
            //Put new labels in datastructures
            for (Point pos : labelPositions) {
                LabelGeneral label = possibleLabels.get(pos);
                if (label == null) {
                    label = new LabelGeneral(pointData, pos.x, pos.y);
                    possibleLabels.put(pos, label);
                    QT.insert(label);
                } else {
                    label.points.add(pointData);
                }
                pointData.labels.add(label);
            }            
        }

        //Place labels
        bruteForce(possibleLabels, new HashMap());
        
        //TODO: use the bestSolution to alter pointList
        
        return pointList;
    }

    @Override
    List<PointData> getLabeledPoints4pos(List<Point> points) {
        return new ArrayList();
    }

    @Override
    List<PointData> getLabeledPoints1slider(List<Point> points) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    HashMap<Point, LabelGeneral> findOverlaps() {
        return new HashMap();
    }
    
}
