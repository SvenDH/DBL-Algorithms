
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

class FDL1Slider extends SliderSolver{

    HashMap<Point,LabelGeneral> labelMap;
    List<PointData> pointList;
    List<LabelGeneral> lapped;
    newRangeTree rangeTree;   
    
    public FDL1Slider(int width, int height) {
        this.width = width;
        this.height = height;
        labelMap = new HashMap<>();
        pointList = new ArrayList<>();
        rangeTree = new newRangeTree();
        lapped = new ArrayList<>();
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
                    rangeTree.insert(label.x, label.y);
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
            rangeTree.remove(label.x, label.y);
            label.neighbourlaps = rangeTree.query2D(label);
            lapped.add(label);
        }
    }
    
    void placeLabels () {
    
    }

}