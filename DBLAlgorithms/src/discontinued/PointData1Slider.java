
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class PointData1Slider extends PointData {

    boolean hasLabel = true;
    float min = 0;
    float max = 1;
    
    PriorityQueue labelRanking;

    PointData1Slider(int x, int y) {
        super(x, y);
    }
    
    @Override
    String getLabelInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
