import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class LabelPlacer {   
    private final static int NB = 10000;
    
    static void output() {
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<PointData> inputList = new ArrayList<>();
        int numberOfPoints;
        
        Scanner sc = new Scanner(System.in);        
        String line;
        line = sc.nextLine();
        System.out.println(line);
        String model = line.substring(16);
        line = sc.nextLine();
        System.out.println(line);
        int width = Integer.parseInt(line.substring(7));
        line = sc.nextLine();
        System.out.println(line);
        int height = Integer.parseInt(line.substring(8));
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
            labelSolver = new BruteForceSolver(width, height);
        } else {
            labelSolver = new NonSolver();
        /*
         * Todo: find optimal labels efficiently.
         */
        }
        
        labelSolver.getLabeledPoints(inputList);
        
        int numberOfLabels = 0;
        System.out.println("number of labels: " + numberOfLabels);
        
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
    }
}
