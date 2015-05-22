
import java.util.Scanner;

public class RangeTreeTester {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RangeTree rangeTree = new RangeTree();
        String line;
        while (true) {
            line = sc.nextLine();
            if (line == null || line.equals("stop") || line.length() < 3) {
                System.out.println("Stopping input");
                break;
            }
            int space = line.indexOf(" ");
            int x = Integer.parseInt(line.substring(0, space));
            int y = Integer.parseInt(line.substring(space + 1));
            rangeTree.insert(x, y);
        }
        System.out.println("Starting query");
        rangeTree.query2D(new Interval2D<Integer>(new Interval<Integer>(0, 10), new Interval<Integer>(0, 10)));
        while (true) {
            line = sc.nextLine();
            if (line == null || line.equals("stop") || line.length() < 3) {
                System.out.println("Stopping input");
                break;
            }
            
        }
    }
    
}