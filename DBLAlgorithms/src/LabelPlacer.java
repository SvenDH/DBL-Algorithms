import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class LabelPlacer {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        int numberOfPoints = Integer.parseInt(line.substring(18));
        //Process points
        List<PointData> inputList = new ArrayList<>();
        for (int i = 0; i < numberOfPoints; i++) {
            line = sc.nextLine();
            // Splitting the input by spaces, with the space regex
            String[] split = line.split("\\s+");
            inputList.add( new PointData( Integer.parseInt(split[0]), 
                                        Integer.parseInt(split[1])));
        }
        
        /*
         * Todo: find optimal labels.
         */
        
        int numberOfLabels = 0;
        System.out.println("number of labels: " + numberOfLabels);
        
        for (int i = 0; i < numberOfPoints; i++) {
            System.out.println(inputList.get(i).x + " " + inputList.get(i).y + " NIL");
        }

    }
}
