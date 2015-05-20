import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.SwingUtilities;
import java.util.Random;

public class LabelPlacer {   
    final static int NB = 0;
    static List<Point> inputList = new ArrayList<>();
    static List<? extends PointData> outputList;
    static int width;
    static int height;
    static String model;
    static int numberOfPoints;

    static boolean doOverlap(int i, int j)
    {
    // Point l1, Point r1, Point l2, Point r2
    Point l1 = new Point(0,0);
    Point l2 = new Point(0,0);
    Point r1 = new Point(0,0); 
    Point r2 = new Point(0,0);

    // NW
    if (outputList.get(i).getLabelInfo().equals("NW")) {
        r1 = new Point(outputList.get(i).x, outputList.get(i).y);
        l1 = new Point(outputList.get(i).x-width, outputList.get(i).y+height);
    }    
    if (outputList.get(j).getLabelInfo().equals("NW")) {
        r2 = new Point(outputList.get(j).x, outputList.get(j).y);
        l2 = new Point(outputList.get(j).x-width, outputList.get(j).y+height);
    }    
    // NE
    if (outputList.get(i).getLabelInfo().equals("NE")) {
        r1 = new Point(outputList.get(i).x+width, outputList.get(i).y);
        l1 = new Point(outputList.get(i).x, outputList.get(i).y+height);        
    }   
    if (outputList.get(j).getLabelInfo().equals("NE")) {
        r2 = new Point(outputList.get(j).x+width, outputList.get(j).y);
        l2 = new Point(outputList.get(j).x, outputList.get(j).y+height);
    }   
    // SE
    if (outputList.get(i).getLabelInfo().equals("SE")) {
        l1 = new Point(outputList.get(i).x, outputList.get(i).y);
        r1 = new Point(outputList.get(i).x+width, outputList.get(i).y-height);
    }
    if (outputList.get(j).getLabelInfo().equals("SE")) {
        l2 = new Point(outputList.get(j).x, outputList.get(j).y);
        r2 = new Point(outputList.get(j).x+width, outputList.get(j).y-height);
    }
    // SW
    if (outputList.get(i).getLabelInfo().equals("SW")) {
        r1 = new Point(outputList.get(i).x, outputList.get(i).y - height);
        l1 = new Point(outputList.get(i).x - width, outputList.get(i).y);
    }
    if (outputList.get(j).getLabelInfo().equals("SW")) {
        r2 = new Point(outputList.get(j).x, outputList.get(j).y - height);
        l2 = new Point(outputList.get(j).x - width, outputList.get(j).y);
    }
    
    // If one rectangle is on left side of other
    if (l1.x > r2.x || l2.x > r1.x)
        return false;
 
    // If one rectangle is above other
    if (l1.y < r2.y || l2.y < r1.y)
        return false;
 
    return true;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        Scanner sc = new Scanner(System.in);        
        String line;
        line = sc.nextLine();
        System.out.println(line);
        model = line.substring(17);
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
        line = sc.nextLine();
        if ("random".equals(line)) {
            Random randomGenerator = new Random();
            int x = randomGenerator.nextInt(10000);
            int y = randomGenerator.nextInt(10000);
            inputList.add(new Point(x,y));
            Globals.maxX = x;
            Globals.minX = x;
            Globals.maxY = y;
            Globals.minY = y;
            for (int i = 0; i < numberOfPoints - 1; i++) {
                x = randomGenerator.nextInt(10000);
                y = randomGenerator.nextInt(10000);
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
            }
        } else {
            line = sc.nextLine();
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
            }
        }
        
        LabelSolver labelSolver;
        /*
         * Todo: chose best algorithm for numberOfPoints and model
         */
        //if (numberOfPoints <= NB) {
        //    labelSolver = BruteForceSolver.getInstance(width, height);
        //} else {
            labelSolver = new GreedyGeneral(width, height);
        //}

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
            if (!point.getLabelInfo().equals("NIL")){
                numberOfLabels++;
            }
        }
        
        System.out.println("number of labels: " + numberOfLabels);
        
        for (int i = 0; i < numberOfPoints; i++) {
            System.out.println
                (outputList.get(i).x + " " + outputList.get(i).y + " " + outputList.get(i).getLabelInfo());
        }

        int sum = 0;
        
        for (int i = 0; i < numberOfPoints - 1; i++) {
            for (int j = i + 1; j < numberOfPoints; j++) {
                if ((outputList.get(i).getLabelInfo() != "NIL") && 
                    (outputList.get(j).getLabelInfo() != "NIL") &&
                    (doOverlap(i,j))) 
                        {sum = sum + 1;}
            }
        }
        System.out.println("There are " + sum + " overlaps");
        Draw.createAndShowGUI();     
    }
}
