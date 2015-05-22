
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s121924
 */
public class RangeTreeTester {
    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RangeTree rangeTree = new RangeTree();
        String line;
        while (true) {
            line = sc.nextLine();
            int space = line.indexOf(" ");
            int x = Integer.parseInt(line.substring(space));
            int y = Integer.parseInt(line.substring(space + 1));
            System.out.println(x + " " + y);
        }
    }
    
}
