/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Amazing_label_placement_program;

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
