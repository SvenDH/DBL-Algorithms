import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.SwingUtilities;

public class LabelPlacer {   
    private final static int NB = 10000;
    public static List<PointData> inputList = new ArrayList<>();
    public static int width;
    public static int height;
    public static int numberOfPoints;

    static void output() {
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);        
        String line;
        line = sc.nextLine();
        System.out.println(line);
        String model = line.substring(17);
        line = sc.nextLine();
        System.out.println(line);
        width = Integer.parseInt(line.substring(7));
        line = sc.nextLine();
        System.out.println(line);
        height = Integer.parseInt(line.substring(8));
        line = sc.nextLine();
        System.out.println(line);
        numberOfPoints = Integer.parseInt(line.substring(18));
        
        //Process points
        for (int i = 0; i < numberOfPoints; i++) {
            line = sc.nextLine();
            // Splitting the input by spaces, with the space regex
            String[] split = line.split("\\s+");
            inputList.add( new PointData( Integer.parseInt(split[0]), 
                                        Integer.parseInt(split[1])));
        }
        
        LabelSolver labelSolver;
        /*
         * Todo: chose best algorithm for numberOfPoints and model
         */
        if (numberOfPoints <= NB) {
            labelSolver = new GreedySolver(width, height);
        } else {
            labelSolver = new NonSolver();
        /*
         * Todo: find optimal labels efficiently.
         */
        }

        if (model.equals("2pos")) {
            labelSolver.getLabeledPoints2pos(inputList);
        } else if (model.equals("4pos")) {
            labelSolver.getLabeledPoints4pos(inputList);
        } else {
            labelSolver.getLabeledPoints1slider(inputList);
        }
        
        int numberOfLabels = 0;
        for (int i = 0; i < numberOfPoints; i++) {
            PointData point = inputList.get(i);
            if (point.NW || point.NE || point.SW || point.SE){
                numberOfLabels++;
            }
        }
        
        for (int i = 0; i < numberOfPoints; i++) {
            String dir = "NIL";
            if (inputList.get(i).NW) 
                dir = "NW";
            else if (inputList.get(i).NE)
                dir = "NE";
            else if (inputList.get(i).SW)
                dir = "SW";
            else if (inputList.get(i).SE)
                dir = "SE";
            System.out.println
                (inputList.get(i).x + " " + inputList.get(i).y + " " + dir);
        }
        System.out.println("number of labels: " + numberOfLabels);
        Draw.createAndShowGUI();     
    }
}
