import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.SwingUtilities;
import java.util.Random;

public class LabelPlacer {   
    final static int NB = 50;
    static List<Point> inputList = new ArrayList<>();
    static List<? extends PointData> outputList;
    static int width;
    static int height;
    static String model;
    static int numberOfPoints;

    static void output() {
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String st;
        Scanner sc = new Scanner(System.in);        
        String line;
        line = sc.nextLine();
        System.out.println(line);
        model = line.substring(17);
        line = sc.nextLine();
        System.out.println(line);
        width = Integer.parseInt(line.substring(7));
        line = sc.nextLine();
        System.out.println(line);
        height = Integer.parseInt(line.substring(8));
        line = sc.nextLine();
        System.out.println(line);
        numberOfPoints = Integer.parseInt(line.substring(18));
        line = sc.nextLine();
        if ("random".equals(line)) {
            for (int i = 0; i < numberOfPoints; i++) {
                Random randomGenerator = new Random();
                int x = randomGenerator.nextInt(10000);
                int y = randomGenerator.nextInt(10000);
                inputList.add(new Point(x,y));
            }
        } else {
        //Process points
        for (int i = 0; i < numberOfPoints; i++) {
            line = sc.nextLine();
            // Splitting the input by spaces, with the space regex
            String[] split = line.split("\\s+");
            inputList.add( new Point( Integer.parseInt(split[0]), 
                                        Integer.parseInt(split[1])));
        }
        }
        LabelSolver labelSolver;
        /*
         * Todo: chose best algorithm for numberOfPoints and model
         */
        if (numberOfPoints <= NB) {
            labelSolver = BruteForceSolver.getInstance(width, height);
        } else {
            labelSolver = new GreedySolver(width, height);
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
        
        int numberOfLabels = 0;
        for (int i = 0; i < numberOfPoints; i++) {
            PointData point = outputList.get(i);
            if (!point.getLabelInfo().equals("NILL")){
                numberOfLabels++;
            }
        }
        
        for (int i = 0; i < numberOfPoints; i++) {
            System.out.println
                (outputList.get(i).x + " " + outputList.get(i).y + " " + outputList.get(i).getLabelInfo());
        }
        System.out.println("number of labels: " + numberOfLabels);
        Draw.createAndShowGUI();     
    }
}
