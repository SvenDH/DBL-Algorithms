
import java.util.List;
import java.util.Stack;


public class BruteForceSolver implements LabelSolver {
    
    private final int width;
    private final int height;
    private Stack<Command> commands = new Stack<Command>();
    private int maxLabels = 0;
    
    BruteForceSolver (int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void getLabeledPoints(List<PointData> points) {
        
    }
    
    private void getMaxLabels(Stack<Command> commandStack) {
        
    }

}
