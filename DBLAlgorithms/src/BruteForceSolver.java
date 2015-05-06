
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
        Stack<PlaceLabelCommand> commands = getMaxLabels(new Stack<PlaceLabelCommand>(), 0, 0, points);
        while (!commands.empty()) {
            PlaceLabelCommand command = commands.pop();
            command.execute();
        }
    }
  
    private Stack<PlaceLabelCommand> getMaxLabels(Stack<PlaceLabelCommand> commands, int labels, int maxLabels, List<PointData> points) {
        if (maxLabels == points.size()) {
            return commands;
        }
        List<PossibleLabel> possibleLabels = getPossibleLabels(points, commands);
        for (PossibleLabel possibleLabel : possibleLabels) {
            maxLabels++;
            if (labels > maxLabels) {
                commands.add(new PlaceLabelCommand(
                        possibleLabel.getPoint(), 
                        possibleLabel.getVertical(), 
                        possibleLabel.getHorizontal(), width, height));
                return getMaxLabels(commands, labels, labels, points);
            } else {
                maxLabels--;
            }
        }
        return null;
    }
    
    private List<PossibleLabel> getPossibleLabels(List<PointData> points, Stack<PlaceLabelCommand> commands) {
        List<PossibleLabel> possibleLabels = new ArrayList<PossibleLabel>();
        for (PointData point : points) {
            //no points within NW range
            if (!getCollision(point, 0, 2, commands)) {
                possibleLabels.add(new PossibleLabel(point, 0, 2)); //NorthWest
            }
            //no points within NE range
            if (!getCollision(point, 0, 3, commands)) {
                possibleLabels.add(new PossibleLabel(point, 0, 3)); //NorthEast
            }
            //no points within SW range
            if (!getCollision(point, 1, 2, commands)) {
                possibleLabels.add(new PossibleLabel(point, 1, 2)); //SouthWest
            }
            //no points within SE range
            if (!getCollision(point, 1, 3, commands)) {
                possibleLabels.add(new PossibleLabel(point, 1, 3)); //SouthEast
            }
        }
        return possibleLabels;
    }
    
    private boolean getCollision(PointData point, int vertical, int horizontal, Stack<PlaceLabelCommand> commands) {
        PlaceLabelCommand[] commandsArray = null;
        commands.copyInto(commandsArray);
        boolean result = false;
        int x = point.x;
        int y = point.y;
        //Making sure the x and y we use are at the top left corner of the label
        if (horizontal == 2) { 
            x -= width;
        }
        if (vertical == 0) {
            y -= height;
        }
        
        for (int i = 0; i < commandsArray.length; i++) {
            if (commandsArray[i].getVertical() == 0) {
                if (commandsArray[i].getHorizontal() == 2) { //NW case
                    if (x < commandsArray[i].getPoint().x 
                            && commandsArray[i].getPoint().x < x + 2 * width
                            && commandsArray[i].getPoint().y < y
                            && y - 2 * height < commandsArray[i].getPoint().y) {
                        result = true;
                    }
                } else if (commandsArray[i].getHorizontal() == 3) { //NE case
                    if (x - width < commandsArray[i].getPoint().x
                            && commandsArray[i].getPoint().x < x + width
                            && commandsArray[i].getPoint().y < y
                            && y - 2 * height < commandsArray[i].getPoint().y) {
                        result = true;
                    }
                }
            } else if (commandsArray[i].getVertical() == 1) {
                if (commandsArray[i].getHorizontal() == 2) { //SW case
                    if (x < commandsArray[i].getPoint().x 
                            && commandsArray[i].getPoint().x < x + 2 * width
                            && commandsArray[i].getPoint().y < y + height
                            && y - height < commandsArray[i].getPoint().y) {
                        result = true;
                    }
                } else if (commandsArray[i].getHorizontal() == 3) { //SE case
                    if (x - width < commandsArray[i].getPoint().x 
                            && commandsArray[i].getPoint().x < x + width
                            && commandsArray[i].getPoint().y < y + height
                            && y - height < commandsArray[i].getPoint().y) {
                        result = true;
                    }
                }
            }
        }
        return result;
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
