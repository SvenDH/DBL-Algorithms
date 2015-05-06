
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class BruteForceSolver extends LabelSolver {
    
    private BruteForceSolver instance;
    
    private BruteForceSolver(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public BruteForceSolver getInstance() {
        return this.instance;
    }
    
    public BruteForceSolver getInstance(int width, int height) {
        return new BruteForceSolver(width, height);
    }

    /**
     * First getMaxLabels is called to fill the stack.
     * All that's left after is to use the stack stored 
     * in the instance of BruteForceSolver to execute the commands which lead
     * to the ideal placement of labels.
     * @param points 
     */
    @Override
    public void getLabeledPoints4pos(List<PointData> points) {
        Stack<Command> commands = getMaxLabels(new Stack<Command>(), 0, 0, points);
        while (!commands.empty()) {
            Command command = commands.pop();
            command.execute();
        }
    }
  
    private Stack<Command> getMaxLabels(Stack<Command> commandStack, int labels, int maxLabels, List<PointData> points) {
        if (maxLabels == points.size()) {
            return commandStack;
        }
        List<PossibleLabel> possibleLabels = getPossibleLabels(points);
        for (PossibleLabel possibleLabel : possibleLabels) {
            maxLabels++;
            if (labels > maxLabels) {
                commandStack.add(new PlaceLabelCommand(
                        possibleLabel.getPoint(), 
                        possibleLabel.getVertical(), 
                        possibleLabel.getHorizontal(), width, height));
                return getMaxLabels(commandStack, labels, labels, points);
            } else {
                maxLabels--;
            }
        }
        return null;
    }
    
    private List<PossibleLabel> getPossibleLabels(List<PointData> points) {
        List<PossibleLabel> possibleLabels = new ArrayList<PossibleLabel>();
        for (PointData point : points) {
            //no points within NW range
            if (!getCollision(point, 0, 2)) {
                possibleLabels.add(new PossibleLabel(point, 0, 2)); //NorthWest
            }
            //no points within NE range
            if (!getCollision(point, 0, 3)) {
                possibleLabels.add(new PossibleLabel(point, 0, 3)); //NorthEast
            }
            //no points within SW range
            if (!getCollision(point, 1, 2)) {
                possibleLabels.add(new PossibleLabel(point, 1, 2)); //SouthWest
            }
            //no points within SE range
            if (!getCollision(point, 1, 3)) {
                possibleLabels.add(new PossibleLabel(point, 1, 3)); //SouthEast
            }
        }
        return possibleLabels;
    }
    
    private boolean getCollision(PointData point, int vertical, int horizontal) {
        
        return false;
    }

    @Override
    public void getLabeledPoints2pos(List<PointData> points) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getLabeledPoints1slider(List<PointData> points) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class PossibleLabel {
    private final PointData point;
    private final int verticalDirection;
    private final int horizontalDirection;
    
    public PossibleLabel(PointData point, int verticalDirection, int horizontalDirection) {
        this.point = point;
        this.verticalDirection = verticalDirection;
        this.horizontalDirection = horizontalDirection;
    }
     
    public PointData getPoint() {
        return point;
    }
    
    public int getVertical() {
        return verticalDirection;
    }
    
    public int getHorizontal() {
        return horizontalDirection;
    }
}
