import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class LabelPlacer {
    
    private static RangeTree<Integer> rs;
    private static List<PointData> inputList = new ArrayList<>();
    private static int numberOfPoints;
   
    static void parseInput(Scanner sc) {
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
    }
    
    static void buildRangeTree() {
        rs = new RangeTree<Integer>();
        
        for (PointData point : inputList) {
            rs.insert(point.x, point.y);
        }
    }
    
    static void output() {
        int numberOfLabels = 0;
        System.out.println("number of labels: " + numberOfLabels);
        
        for (int i = 0; i < numberOfPoints; i++) {
            System.out.println(inputList.get(i).x + " " + inputList.get(i).y + " NIL");
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);        
        parseInput(sc);
        
        /*
         * Todo: find optimal labels.
         */
        buildRangeTree();
        output();
    }
}
