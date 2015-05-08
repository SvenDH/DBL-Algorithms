
import java.util.ArrayList;
import java.util.List;

public class Label1Slider extends Label {
    List<PointData1Slider> points; //Points owning this label
    List<PointData1Slider> realCollisions = new ArrayList<>();
    List<PointData1Slider> WCollisions = new ArrayList<>();
    List<PointData1Slider> ECollisions = new ArrayList<>();
    
    float sliderPosition;
    
    Label1Slider (PointData1Slider point, float sliderPosition, int width, int height) {
        if (sliderPosition <= 1 && sliderPosition >= 0) //Remove this shit for less overhead
            this.sliderPosition = sliderPosition;
        else 
            throw new IllegalArgumentException("sliderPosition must be between 0 and 1");
        this.points.add(point);
    }
}