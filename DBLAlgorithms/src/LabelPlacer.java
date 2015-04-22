import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class LabelPlacer {
    
    private static int width;
    private static int height;
    private static List<Vector> inputList;
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String model = sc.nextLine().substring(16);
        width = Integer.parseInt(sc.nextLine().substring(7));
        height = Integer.parseInt(sc.nextLine().substring(8));
        int numberOfPoints = Integer.parseInt(sc.nextLine().substring(18));
        
        parseInput(sc);
    }
    
    public static void parseInput(Scanner sc) {
        
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            // Splitting the input by spaces, with the space regex
            String[] split = line.split("\\s+");
            inputList.add(  new Vector( Integer.parseInt(split[0]), 
                                        Integer.parseInt(split[1])));
        }
        
    }

}
