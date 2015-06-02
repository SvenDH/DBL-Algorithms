
import java.util.HashSet;
import java.util.Scanner;

public class RangeTreeTester {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        newRangeTree rangeTree = new newRangeTree();
        String line;
        while (true) {
            line = sc.nextLine();
            if ("quit".equals(line)) {
                return;
            }
            if (line == null || line.equals("stop") || line.length() < 3) {
                System.out.println("Stopping input");
                break;
            }
            int space = line.indexOf(" ");
            int x = Integer.parseInt(line.substring(0, space));
            int y = Integer.parseInt(line.substring(space + 1));
            rangeTree.insert(x, y);
        }
//        for (int i = 0; i < 11; i++) {
//            for (int j = 0; j < 11; j++) {
//                rangeTree.insert(i, j);
//            }
//        }
        System.out.println("Starting query after input");
        HashSet set = new HashSet();
        rangeTree.query2D(new Interval2D<Integer>(new Interval<Integer>(0, 10), new Interval<Integer>(0, 10)));
        while (true) {
            line = sc.nextLine();
            if ("quit".equals(line)) {
                return;
            }
            if (line == null || line.equals("stop") || line.length() < 3) {
                System.out.println("Stopping input");
                break;
            }
            int space = line.indexOf(" ");
            int x = Integer.parseInt(line.substring(0, space));
            int y = Integer.parseInt(line.substring(space + 1));
            rangeTree.remove(x, y);
        }
        System.out.println("Starting query after deletion");
        rangeTree.query2D(new Interval2D<Integer>(new Interval<Integer>(0, 10), new Interval<Integer>(0, 10)));
    }
    
}
