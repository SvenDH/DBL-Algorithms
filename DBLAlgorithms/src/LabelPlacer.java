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
        line = sc.nextLine();
        System.out.println(line);
        numberOfPoints = Integer.parseInt(line.substring(18));
        Globals.numberOfPoints = numberOfPoints;
        /*line = sc.nextLine();
        
        String[] split = line.split("\\s+");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            inputList.add(new Point(x,y));
            Globals.maxX = x;
            Globals.minX = x;
            Globals.maxY = y;
            Globals.minY = y; 
            
            //Process points
            for (int i = 0; i < numberOfPoints - 1; i++) {
                line = sc.nextLine();
                // Splitting the input by spaces, with the space regex
                split = line.split("\\s+");
                x = Integer.parseInt(split[0]);
                y = Integer.parseInt(split[1]);
                inputList.add(new Point(x,y));
                if (x > Globals.maxX) {
                    Globals.maxX = x;
                } else
                if (x < Globals.minX) {
                    Globals.minX = x;
                }
                
                if (y > Globals.maxY) {
                    Globals.maxY = y;
                } else
                if (y < Globals.minY) {
                    Globals.minY = y;
                }
            }*/
      
        LabelSolver labelSolver;
        /*
         * Todo: chose best algorithm for numberOfPoints and model
         */
        for (int k = 0; k < 3; k++) {
            inputList = RandomRunner.run();
            switch (k) {
                case 0: model = "2pos";  
                        if (numberOfPoints <= 100){
                            labelSolver = new BruteForceSolver(width, height);
                        } else {
                            labelSolver = new GreedyGeneral(width, height);
                        }
                        outputList = labelSolver.getLabeledPoints2pos(inputList);
                        break;
                case 1: model = "4pos";
                        if (numberOfPoints <= 100){
                            labelSolver = new BruteForceSolver(width, height);
                        } else {
                            labelSolver = new GreedyGeneral(width, height);
                        }
                        outputList = labelSolver.getLabeledPoints4pos(inputList);
                        break;
                case 2: model = "1slider";
                        labelSolver = new ForceDirectedSimulatedAnnealing(width, height);
                        outputList = labelSolver.getLabeledPoints1slider(inputList);
                        break;
                default: break;
            }
            /*
        if (model.equals("1slider")) {
            labelSolver = new ForceDirectedSimulatedAnnealing(width, height);
        } else if (numberOfPoints <= 100){
            labelSolver = new BruteForceSolver(width, height);
        } else {
            labelSolver = new GreedyGeneral(width, height);
        }

        if (model.equals("2pos")) {
            outputList = labelSolver.getLabeledPoints2pos(inputList);
        } else if (model.equals("4pos")) {
            outputList = labelSolver.getLabeledPoints4pos(inputList);
        } else if (model.equals("1slider")) {
            outputList = labelSolver.getLabeledPoints1slider(inputList);
        } else {
            throw new IllegalArgumentException("Invalid model");
        }
 */
        int numberOfLabels = 0;
        for (int i = 0; i < numberOfPoints; i++) {
            PointData point = outputList.get(i);
            if (!point.getLabelInfo().equals("NIL")){
                numberOfLabels++;
            }
        }
        Globals.numberOfLabels = numberOfLabels;
        
        System.out.println("number of labels: " + numberOfLabels);
        
        for (int i = 0; i < numberOfPoints; i++) {
            System.out.println
                (outputList.get(i).x + " " + outputList.get(i).y + " " + outputList.get(i).getLabelInfo());
        }
        Draw Drawit = new Draw();
        Drawit.createAndShowGUI();     
        }
    }
}
