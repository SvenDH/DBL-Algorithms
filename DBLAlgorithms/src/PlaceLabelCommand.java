
public class PlaceLabelCommand extends Command<PointData> {
    
    private Direction direction;

    public PlaceLabelCommand(PointData point, Direction direction) throws NullPointerException {
        super(point);
        
    }
    
    @Override
    public void execute() {
        super.execute();
        switch(direction) {
        case NW:
            receiver.LabelNW = new Label(receiver, direction);
            receiver.NW = true;
            break;
        case NE:
            receiver.LabelNE = new Label(receiver, direction);
            receiver.NE = true;
            break;
        case SE:
            receiver.LabelSE = new Label(receiver, direction);
            receiver.SE = true;
            break;
        case SW:
            receiver.LabelSW = new Label(receiver, direction);
            receiver.SW = true;
            break;
        default:
            break;
        }
    }
    
    @Override
    public void undo() {
        super.undo();
        switch(direction) {
        case NW:
            receiver.LabelNW = null;
            receiver.NW = false;
            break;
        case NE:
            receiver.LabelNE = null;
            receiver.NE = false;
            break;
        case SE:
            receiver.LabelSE = null;
            receiver.SE = false;
            break;
        case SW:
            receiver.LabelSW = null;
            receiver.SW = false;
            break;
        default:
            break;
        }
    }
}
