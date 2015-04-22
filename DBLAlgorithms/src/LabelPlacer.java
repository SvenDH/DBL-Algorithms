import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author s132303
 */
public class LabelPlacer {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> points = new ArrayList<>();
        
        String model = sc.nextLine().substring(16);
        int width = Integer.parseInt(sc.nextLine().substring(7));
        int height = Integer.parseInt(sc.nextLine().substring(8));
        int numberOfPoints = Integer.parseInt(sc.nextLine().substring(18));
        
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            points.add(line);
            System.out.println(line);
        }
        
    }
    
}
