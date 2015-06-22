import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomRunner {

    static List<Point> list = new ArrayList<>();
    
    static List<Point> run() {
        int i = 0;
        
            while (i < Globals.numberOfPoints) {
                    int randX = StdRandom.uniform(10000);
                    int randY = StdRandom.uniform(10000);   
                    if (!list.contains(new Point(randX, randY))) {
                        list.add(new Point(randX, randY));
                        i++;
                    }
            }                
        return list;
    }
}