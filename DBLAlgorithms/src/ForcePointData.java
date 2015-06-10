public class ForcePointData extends PointData {
    
    ForceLabel label;

    public ForcePointData(int x, int y, ForceLabel label) {
        super(x, y);
        this.label = label;
    }
    
    double getShift(){
        return 1.0 - ((double)(x - label.x) / (double)Globals.width);
    }
    
    @Override
    String getLabelInfo() {
        if (label != null) {
            double shift = getShift();
            return String.valueOf(shift);
        } else {
            return "NIL";
        }
    }
    
}
