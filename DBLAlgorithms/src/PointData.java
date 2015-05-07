
abstract class PointData {
    int x;
    int y;
    
    PointData (int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    abstract String getLabelInfo();
}
