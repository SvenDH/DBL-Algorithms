
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

class FDL1Slider extends SliderSolver{
   
    int width;
    int height;
    PriorityQueue<LabelGeneral> queue;
    HashMap<Point,LabelGeneral> labelMap;
    List<PointData> pointList;
    newRangeTree rangeTree;   
    
    public FDL1Slider(int width, int height) {
        this.width = width;
        this.height = height;
        queue = new PriorityQueue<>(new Comparator<LabelGeneral>(){
            @Override
                    public int compare(LabelGeneral o1, LabelGeneral o2){
                    if(o1.neighbourlaps.size() > o2.neighbourlaps.size())
                        return 1;
                    else if(o1.neighbourlaps.size() < o2.neighbourlaps.size())
                        return -1;
                    else {
                        int o1min = Integer.MAX_VALUE;
                        int o2min = Integer.MAX_VALUE;
                        for (PointGeneral point : o1.points){
                            if (point.labels.size() < o1min){
                                o1min = point.labels.size();
                            }
                        }
                        for (PointGeneral point : o2.points){
                            if (point.labels.size() < o2min){
                                o2min = point.labels.size();
                            }
                        }
                        return o1min - o2min;
                    }
                }
        });
        labelMap = new HashMap<>();
        pointList = new ArrayList<>();
        rangeTree = new newRangeTree();
    } 
    
    @Override
    List<PointData> getLabeledPoints1slider(List<Point> points) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i <= width; i++){
            positions.add(i);
        }
        for(Point point : points){
            PointGeneral pointData = new PointGeneral(point.x, point.y);
            pointList.add(pointData);
            //Create new labels for this point
            Set<Point> labelPositions = new HashSet<>(4);
            for (int i : positions){
                labelPositions.add(new Point(point.x - width + i, point.y));
            }            
            //Put new labels in datastructures
            for (Point pos : labelPositions){
                LabelGeneral label = labelMap.get(pos);
                if (label == null) {
                    label = new LabelGeneral(pointData, pos.x, pos.y);
                    labelMap.put(pos, label);
                    rangeTree.insert(label.x,label.y);
                } else {
                    label.points.add(pointData);
                }
                pointData.labels.add(label);
            }            
        }
        
        //Find all overlapping labels
        findOverlaps();
        
        //Place labels
        placeLabels();
        
        return pointList;
    }
    
    void findOverlaps(){
        for (Map.Entry<Point, LabelGeneral> entry : labelMap.entrySet()){
            LabelGeneral label = entry.getValue();
            //Create interval strictly smaller than this label
//            Interval2D<Integer> rect = new Interval2D<Integer>(
//                    new Interval<Integer>(label.x, label.x + width), 
//                    new Interval<Integer>(label.y, label.y + height));
            HashSet<Node> pts = rangeTree.query2D(label);
            for(Node node : pts) {
                if (node.label != null) {
                    label.neighbourlaps.add(node.label);
                }
            }
            label.neighbourlaps.remove(label);
            queue.add(label);
        }
    }
    
    void placeLabels () {
        while (!queue.isEmpty()) {
            LabelGeneral label = queue.poll();
            
            PointGeneral pointData = label.points.get(0);
            
            //Which point should the label be assigned to
            int max = 0;
            for (PointGeneral point : label.points) {
                //All overlaps the labels of this point can make
                Set<Label> allOverlaps = new HashSet<>();
                for (LabelGeneral otherLabel : point.labels) {
                    allOverlaps.addAll(otherLabel.neighbourlaps);
                }
                allOverlaps.removeAll(point.labels);
                int totalOverlaps = allOverlaps.size();
                if (totalOverlaps == max) {
                    if( point.x < pointData.x) {
                        if( point.y > pointData.y) {
                            pointData = point;
                            max = totalOverlaps;
                        }
                    }
                    if( point.y > pointData.y) {
                        pointData = point;
                        max = totalOverlaps; 
                    }
                }
                if (totalOverlaps > max) {
                    pointData = point;
                    max = totalOverlaps;
                }
            }
            
            //Delete this label from all other points owning that label
            for (PointGeneral otherPoint : label.points) {
                if (!otherPoint.equals(pointData)){
                    otherPoint.labels.remove(label);
                    label.points.remove(otherPoint);
                }
            }
            
            //Keep track of all labels that need to be updated and deleted
            Set<LabelGeneral> updateList = new HashSet<>();
            Set<LabelGeneral> deleteList = new HashSet<>();
            
            //Delete all other labels for this point
            for (LabelGeneral otherLabel : pointData.labels) {
                if (!label.equals(otherLabel)) { //If it is another label for this point
                    //Remove pointData from the list of points owning this label
                    otherLabel.points.remove(pointData);
                    
                    //Check if the label is still owned
                    if (otherLabel.points.isEmpty()){
                        //If not label can not be placed anymore so all overlapping labels need to be updated
                        deleteList.add(otherLabel);
                        for (LabelGeneral updateLabel : otherLabel.neighbourlaps) {
                            updateList.add(updateLabel);
                        }
                    }
                }
            }
            pointData.labels = new ArrayList<>();
            pointData.labels.add(label);
            
            //Remove all labels that overlap this label
            for (LabelGeneral otherLabel : label.neighbourlaps) {
                deleteList.add(otherLabel);
                //Label can not be placed anymore so all overlapping labels need to be updated
                for (LabelGeneral updateLabel : otherLabel.neighbourlaps) {
                    updateList.add(updateLabel);
                }
            }
            
            //Remove labels that are being deleted from update list
            updateList.removeAll(deleteList);
            
            //Delete all labels from delete list
            for (LabelGeneral otherLabel : deleteList) {
                for (LabelGeneral updateLabel : otherLabel.neighbourlaps) {
                    updateLabel.neighbourlaps.remove(otherLabel);
                }
                for (PointGeneral updatePoint : otherLabel.points) {
                    updatePoint.labels.remove(otherLabel);
                }
                queue.remove(otherLabel);
            }
            
            //Update all labels from update list
            for (LabelGeneral otherLabel : updateList) {
                if (queue.remove(otherLabel)) {
                    queue.add(otherLabel);
                }
            }
            //System.out.println("Labels left: " + pointData.labels.size());
            //System.out.println("Placing label: " + pointData.getLabelInfo());
        }
    }

}