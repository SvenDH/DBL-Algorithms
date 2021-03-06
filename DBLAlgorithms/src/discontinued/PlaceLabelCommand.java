//
//public class PlaceLabelCommand<T extends Label> extends Command<PointData> {
//    
//    private final int N = 0;
//    private final int S = 1;
//    private final int W = 2;
//    private final int E = 3;
//    private int verticalDirection;
//    private int horizontalDirection;
//    private final int width;
//    private final int height;
//
//    public PlaceLabelCommand(PointData point, int verticalDirection, int horizontalDirection, int width, int height) throws NullPointerException {
//        super(point);
//        this.verticalDirection = verticalDirection;
//        this.horizontalDirection = horizontalDirection;
//        this.width = width;
//        this.height = height;
//    }
//    
//    PointData getPoint() {
//        return this.receiver;
//    }
//    
//    public int getVertical() {
//        return this.verticalDirection;
//    }
//    
//    public int getHorizontal() {
//        return this.horizontalDirection;
//    }
//    
//    @Override
//    public void execute() {
//        super.execute();
//        if (verticalDirection == N) {
//            if (horizontalDirection == W) {
//                receiver.LabelNW = new T(receiver, horizontalDirection, verticalDirection, width, height);
//                receiver.NW = true;
//            }
//            else if (horizontalDirection == E) {
//                receiver.LabelNE = new Label(receiver, horizontalDirection, verticalDirection, width, height);
//                receiver.NE = true;
//            }
//            else {
//                throw new IllegalArgumentException("Something went wrong with the direction");
//            }
//        }
//        else if (verticalDirection == S) {
//            if (horizontalDirection == W) {
//                receiver.LabelSW = new Label(receiver, horizontalDirection, verticalDirection, width, height);
//                receiver.SW = true;
//            }
//            else if (horizontalDirection == E) {
//                receiver.LabelSE = new Label(receiver, horizontalDirection, verticalDirection, width, height);
//                receiver.SE = true;
//            }
//            else {
//                throw new IllegalArgumentException("Something went wrong with the direction");
//            }
//        }
//    }
//    
//    @Override
//    public void undo() {
//        super.undo();
//                if (verticalDirection == N) {
//            if (horizontalDirection == W) {
//                receiver.LabelNW = null;
//                receiver.NW = false;
//            }
//            else if (horizontalDirection == E) {
//                receiver.LabelNE = null;
//                receiver.NE = false;
//            }
//            else {
//                System.out.println("Something went wrong with the direction");
//            }
//        }
//        else if (verticalDirection == S) {
//            if (horizontalDirection == W) {
//                receiver.LabelSW = null;
//                receiver.SW = false;
//            }
//            else if (horizontalDirection == E) {
//                receiver.LabelSE = null;
//                receiver.SE = false;
//            }
//            else {
//                System.out.println("Something went wrong with the direction");
//            }
//        }
//    }
//}
