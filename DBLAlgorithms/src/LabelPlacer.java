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
        List<String> input = new ArrayList<>();
        
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            input.add(line);
            System.out.println(line);
        }
        
    }
    
}
