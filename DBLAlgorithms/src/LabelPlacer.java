import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class LabelPlacer {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);        
        
        String model = sc.nextLine().substring(16);
        int width = Integer.parseInt(sc.nextLine().substring(7));
        int height = Integer.parseInt(sc.nextLine().substring(8));
        int numberOfPoints = Integer.parseInt(sc.nextLine().substring(18));
        List<Vector> inputList = new ArrayList<>();
        
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            // Splitting the input by spaces, with the space regex
            String[] split = line.split("\\s+");
            inputList.add(  new Vector( Integer.parseInt(split[0]), 
                                        Integer.parseInt(split[1])));
        }
    }
}
