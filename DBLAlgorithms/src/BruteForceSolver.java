
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;


public class BruteForceSolver extends LabelSolver {
    
    int width;
    int height;
    List<PointData> pointList;
    QuadTree QT;
    Stack<LabelGeneral> disabledLabels;
    Stack<PointGeneral> labeledPoints;
    List<PointGeneral> bestSolution;
    
    public BruteForceSolver(int width, int height) {
        this.width = width;
        this.height = height;
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
            for (LabelGeneral label : point.labels) {
                if (!disabledLabels.contains(label) && !labeledPoints.contains(point)) {
                    int removedLAmount = 0;
                    if (label.overlappingLabels != null) {
                        for (LabelGeneral overlap : label.overlappingLabels) {
                                disabledLabels.push(overlap);
                                removedLAmount++;
                        }
                    }
                    disabledLabels.push(label);
                    labeledPoints.push(point);
                    PointGeneral labelPoint = new PointGeneral(point.x, point.y);
                    labelPoint.labels.add(label);
                    currentSolution.add(labelPoint);
                    if (currentSolution.size() > bestSolution.size()) {
                        bestSolution = new ArrayList(currentSolution);
                    }
                    bruteForce(currentSolution, labelPoints);
                    currentSolution.remove(labelPoint);
                    disabledLabels.pop();
                    labeledPoints.pop();
                    while (removedLAmount > 0) {
                        disabledLabels.pop();
                        removedLAmount--;
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
        

        //Place labels
        bruteForce(new ArrayList(), labelPoints);
        
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

        //Place labels
        bruteForce(new ArrayList(), labelPoints);
        
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
