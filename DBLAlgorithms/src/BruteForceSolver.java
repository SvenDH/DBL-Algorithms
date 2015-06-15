
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class BruteForceSolver extends LabelSolver {
    
    int width;
    int height;
    int bestLength;
    int greedyNils;
    int nilLabels;
    List<PointData> pointList;
    QuadTree QT;
    Stack<LabelGeneral> disabledLabels;
    Stack<PointGeneral> labeledPoints;
    List<PointGeneral> bestSolution;
    
    public BruteForceSolver(int width, int height) {
        this.width = width;
        this.height = height;
        this.bestLength = 0;
        this.nilLabels = 0;
        this.pointList = new ArrayList();
        this.QT = new QuadTree(width, height);
        this.disabledLabels = new Stack();
        this.labeledPoints = new Stack();
        this.bestSolution = new ArrayList();
    }
    
    public void bruteForce(List<PointGeneral> currentSolution, final List<PointGeneral> labelPoints) {
        if (bestSolution.size() == Globals.numberOfPoints) {
            return;
        }
        
        for (PointGeneral point : labelPoints) {
            if (!labeledPoints.contains(point)) {
                for (LabelGeneral label : point.labels) {
                    PointGeneral labelPoint = new PointGeneral(point.x, point.y);
                    int removedLAmount = 0;
                    boolean labelCheck = false;
                    if (!disabledLabels.contains(label)) {
                        labelCheck = true;
                        if (label.overlappingLabels != null) {
                            for (LabelGeneral overlap : label.overlappingLabels) {
                                    disabledLabels.push(overlap);
                                    removedLAmount++;
                            }
                        }
                        disabledLabels.push(label);
                        labelPoint.labels.add(label);
                    } else {
                        nilLabels++;
                    }
                    if (nilLabels >= greedyNils) {
                        nilLabels--;
                        return;
                    }
                    labeledPoints.push(labelPoint);
                    currentSolution.add(labelPoint);
                    if (currentSolution.size() > bestLength) {
                        bestSolution = new ArrayList(currentSolution);
                        bestLength = bestSolution.size();
                    }
                    bruteForce(currentSolution, labelPoints);
                    currentSolution.remove(labelPoint);
                    labeledPoints.pop();
                    if (labelCheck) {
                        disabledLabels.pop();
                        while (removedLAmount > 0) {
                            disabledLabels.pop();
                            removedLAmount--;
                        }
                    } else {
                        nilLabels--;
                    }
                }
            }
        }
    }

    @Override
    List<PointData> getLabeledPoints2pos(List<Point> points) {
        
        List<PointGeneral> labelPoints = new ArrayList();
        
        for(Point point : points) {
            PointGeneral pointData = new PointGeneral(point.x, point.y);
            pointList.add(pointData);
            //Create new labels for this point
            Set<PointGeneral> labelPositions = new HashSet<>(2);
            labelPositions.add(new PointGeneral(point.x, point.y));
            labelPositions.add(new PointGeneral(point.x - width, point.y));
            //Put new labels in datastructures
            for (PointGeneral pos : labelPositions) {
                LabelGeneral label;
                if (pos.labels.isEmpty()) {
                    label = new LabelGeneral(pointData, pos.x, pos.y);
                    pointData.labels.add(label);
                    QT.insert(label);
                } else {
                    label = pos.labels.get(0);
                    label.points.add(pointData);
                }
            }
            labelPoints.add(pointData);
        }
        
        //Find overlaps
        findOverlaps(labelPoints);
        
        //Put the greedy choice in bestSolution first
        LabelSolver greedy = new GreedyGeneral(this.width, this.height);
        List<PointData> greedySolution = greedy.getLabeledPoints2pos(points);
        for (PointData point : greedySolution) {
            if (!point.getLabelInfo().equals("NIL")){
                this.bestLength++;
            }
        }
        greedyNils = greedySolution.size() - bestLength;
        
        //Put the labels into the pointList
        if (this.bestLength != Globals.numberOfPoints) {
            bruteForce(new ArrayList(), labelPoints);
                        
            if (bestSolution.size() <= bestLength) { //Bruteforce did not find a better than greedy solution
                return greedySolution;
            }
            
            //Use the bestSolution to get the output
            for (PointGeneral pointData : bestSolution) {
                PointData checkPoint = null;
                for (PointData point : this.pointList) {
                    if (pointData.x == point.x && pointData.y == point.y) {
                        checkPoint = point;
                    }
                }
                this.pointList.remove(checkPoint);
                this.pointList.add(pointData);
            }
            
            return this.pointList;
        } else { //The greedy solution is optimal
            return greedySolution;
        }
    }

    @Override
    List<PointData> getLabeledPoints4pos(List<Point> points) {
        
        List<PointGeneral> labelPoints = new ArrayList();
        
        for(Point point : points) {
            PointGeneral pointData = new PointGeneral(point.x, point.y);
            pointList.add(pointData);
            //Create new labels for this point
            Set<PointGeneral> labelPositions = new HashSet<>(4);
            labelPositions.add(new PointGeneral(point.x, point.y));
            labelPositions.add(new PointGeneral(point.x - width, point.y));
            labelPositions.add(new PointGeneral(point.x, point.y - height));
            labelPositions.add(new PointGeneral(point.x - width, point.y - height));
            //Put new labels in datastructures
            for (PointGeneral pos : labelPositions) {
                LabelGeneral label;
                if (pos.labels.isEmpty()) {
                    label = new LabelGeneral(pointData, pos.x, pos.y);
                    pointData.labels.add(label);
                    QT.insert(label);
                } else {
                    label = pos.labels.get(0);
                    label.points.add(pointData);
                }
            }
            labelPoints.add(pointData);
        }
        
        //Find overlaps
        findOverlaps(labelPoints);
        
        //Put the greedy choice in bestSolution first
        LabelSolver greedy = new GreedyGeneral(this.width, this.height);
        List<PointData> greedySolution = greedy.getLabeledPoints4pos(points);
        for (PointData point : greedySolution) {
            if (!point.getLabelInfo().equals("NIL")){
                this.bestLength++;
            }
        }
        
        //Put the labels into the pointList
        if (this.bestLength != Globals.numberOfPoints) {
            bruteForce(new ArrayList(), labelPoints);
            
            if (bestSolution.size() <= bestLength) { //Bruteforce did not find a better than greedy solution
                return greedySolution;
            }
            
            //Use the bestSolution to get the output
            for (PointGeneral pointData : bestSolution) {
                PointData checkPoint = null;
                for (PointData point : this.pointList) {
                    if(pointData.x == point.x && pointData.y == point.y) {
                        checkPoint = point;
                    }
                }
                this.pointList.remove(checkPoint);
                this.pointList.add(pointData);
            }
            
            return this.pointList;
        } else { //The greedy solution is optimal
            return greedySolution;
        }
    }

    @Override
    List<PointData> getLabeledPoints1slider(List<Point> points) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    /**
     * Returns the labels contained in labelMap which collide with the specified label
     * @param Label the specified label
     * @param labelMap a set of labels 
     * @return 
     */
    void findOverlaps(List<PointGeneral> labelPoints) {
        for (PointGeneral pointData : labelPoints) {
            for(LabelGeneral label : pointData.labels) {
                //Create interval strictly smaller than this label
                Interval2D<Integer> rect = new Interval2D<Integer>(
                        new Interval<Integer>(label.x, label.x + width),
                        new Interval<Integer>(label.y, label.y + height));
                label.overlappingLabels = QT.query2D(rect);
                label.overlappingLabels.remove(label);
            }
        }
    }
}
