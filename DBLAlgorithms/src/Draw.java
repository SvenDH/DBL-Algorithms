/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Scanner;
import java.util.Random;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import javax.swing.SwingUtilities;

/**
 *
 * @author DBL Algorithms 2015 - Group 7
 */

class Draw extends JPanel {

    public static void createAndShowGUI() {
        JFrame f = new JFrame("Draw Labels");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new Draw());
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(660, 660);
    }
    
    public Draw() { }
        /*
        int sum = 0;
        for (int i = 0; i < boxList.size(); i++) {
            for (int j = i + 1; j < boxList.size(); j++) {

                // 
                if (doOverlap(boxList.get(i), boxList.get(j))) {
                    sum = sum + 1;
                }
            }
        }
        System.out.println("\n" + "The amount of overlaps is: " + sum);
    }

    private static boolean doOverlap(Box box1, Box box2) {

        // If label 2 is placed further right than label 1, or if
        // label 2 is placed further right than label 2: no overlap.
        if (box1.p1.x >= box2.p2.x || box2.p1.x >= box1.p2.x) {
            return false;
        }
        
        // If label 2 is placed further up than label 1, or if
        // label 1 is placed further up than label 2: no overlap.
        else if (box1.p1.y >= box2.p2.y || box2.p1.y >= box1.p2.y) {
            return false;
        }
        
        // Else, there is overlap
        else { return true; }
    }
    */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int point1X = 0;
        int point1Y = 0;
        
        // Scaling factor: (0.06 for optimal view of entire 10.000x10.000 matrix
        // Translation: offset from origin
        AffineTransform t = AffineTransform.getTranslateInstance(0, getHeight());
        t.scale(1, -1);
        g2d.setTransform(t);
        
        g2d.scale(0.06, 0.06);
        g2d.translate(500, 500);
        
        // Draw the matrix
        g2d.drawLine(10000, 0, 10000, 10000);
        g2d.drawLine(0, 10000, 10000, 10000);
        g2d.drawLine(0, 0, 0, 10000);
        g2d.drawLine(0, 0, 10000, 0);
        
        // For each point in arrayList
        for (Label label : LabelPlacer.labelList) {
            // Color to red and transparancy to 0.6f.
            g2d.setColor(Color.RED);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            
            // Draw red rectangle for all possible labels
            if (label.direction == Direction.SE) {
                point1X = label.point.x;
                point1Y = label.point.y - LabelPlacer.height;
            } else
            if (label.direction == Direction.SW){
                point1X = label.point.x - LabelPlacer.width;
                point1Y = label.point.y - LabelPlacer.height;
            } else
            if (label.direction == Direction.NE){
                point1X = label.point.x;
                point1Y = label.point.y;
            } else
            if (label.direction == Direction.NW){
                point1X = label.point.x - LabelPlacer.width;
                point1Y = label.point.y;
            } else {
                label = null;
            }
             
            if (label != null) { 
                g2d.draw(new Rectangle2D.Double(point1X, point1Y, LabelPlacer.width, LabelPlacer.height));
            }
            
            // Color to black and transparancy to 1f.
            g2d.setColor(Color.BLACK);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            
            // Central point
            g2d.fillRect(label.point.x, label.point.y, 51, 51);
        }
    }
}
