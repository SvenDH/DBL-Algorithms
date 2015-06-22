import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.SwingUtilities;
import java.util.Random;

public class LabelPlacer {   
    static List<Point> inputList = new ArrayList<>();
    static List<? extends PointData> outputList;
    static int width;
    static int height;
    static String model;
    static int numberOfPoints;
    static int[] arr = { 10, 25, 100, 10000};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        Scanner sc = new Scanner(System.in);        
        String line;
        //line = sc.nextLine();
        //System.out.println(line);
        //model = line.substring(17);
        line = sc.nextLine();
        System.out.println(line);
        width = Integer.parseInt(line.substring(7));
        Globals.width = width;
        line = sc.nextLine();
        System.out.println(line);
        height = Integer.parseInt(line.substring(8));
        Globals.height = height;
        /*line = sc.nextLine();
        System.out.println(line);
        numberOfPoints = Integer.parseInt(line.substring(18));
        Globals.numberOfPoints = numberOfPoints;
        */
        LabelSolver labelSolver;
        int n = arr.length;
        /*
         * Todo: chose best algorithm for numberOfPoints and model
         */
        for (int j = 0; j < n; j++) {
            numberOfPoints = arr[j];
            Globals.numberOfPoints = numberOfPoints;
            inputList = RandomRunner.run();
            for (int k = 0; k < 3; k++) {
                long time1 = System.currentTimeMillis();
                switch (k) {
                    case 0: model = "2pos";  
                            labelSolver = new GreedyGeneral(width, height);
                            outputList = labelSolver.getLabeledPoints2pos(inputList);
                            break;
                    case 1: model = "4pos";
                            labelSolver = new GreedyGeneral(width, height);
                            outputList = labelSolver.getLabeledPoints4pos(inputList);
                            break;
                    case 2: model = "1slider";
                            if (Globals.numberOfPoints != 10000) {
                                labelSolver = new ForceDirectedSimulatedAnnealing(width, height);
                                outputList = labelSolver.getLabeledPoints1slider(inputList);
                            } 
                            break;
                        default: break;
                }
                if (model == "1slider" && Globals.numberOfPoints == 10000) {
                    System.out.println("Time limit exceeded!");
                }else {
                    long time2 = System.currentTimeMillis();
                    int numberOfLabels = 0;
                    for (int i = 0; i < numberOfPoints; i++) {
                        PointData point = outputList.get(i);
                        if (!point.getLabelInfo().equals("NIL")){
                            numberOfLabels++;
                        }
                    }   
                    Globals.numberOfLabels = numberOfLabels;
                    System.out.println("For " + arr[j] + " points in " + model + " model " + numberOfLabels + " labels were placed in " + (time2 - time1) + " milliseconds");
                }
            }
            System.out.println();
        }
    }
}
