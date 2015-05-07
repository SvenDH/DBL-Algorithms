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
import java.awt.geom.Ellipse2D;
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
    
    public Draw() {}
        /*
        int sum = 0;
        for (int i = 0; i < LabelPlacer.numberOfPoints; i++) {
            for (int j = i + 1; j < LabelPlacer.numberOfPoints; j++) {

                // 
                if (doOverlap(LabelPlacer.inputList.get(i), LabelPlacer.inputList.get(j))) {
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
    }*/
    
    public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x-(r/2);
        y = y-(r/2);
        g.fillOval(x,y,r,r);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
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
        for (PointData point : LabelPlacer.outputList) {
            // Color to black and transparancy to 1f.
            g2d.setColor(Color.BLACK);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            
            // Central point
            drawCenteredCircle(g2d, point.x, point.y, 50);
            
            // Color to red and transparancy to 0.6f.
            g2d.setColor(Color.RED);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            
            // Draw red rectangle for all possible labels
            if (point.getLabelInfo().equals("SE")) {
                g2d.fillRect(point.x, point.y - LabelPlacer.height, LabelPlacer.width, LabelPlacer.height);
            } else
            if (point.getLabelInfo().equals("SW")){
                g2d.fillRect(point.x - LabelPlacer.width, point.y - LabelPlacer.height, LabelPlacer.width, LabelPlacer.height);
            } else
            if (point.getLabelInfo().equals("NE")){
                g2d.fillRect(point.x, point.y, LabelPlacer.width, LabelPlacer.height);
            } else
            if (point.getLabelInfo().equals("NW")){
                g2d.fillRect(point.x - LabelPlacer.width, point.y, LabelPlacer.width, LabelPlacer.height);
            } else
            if (point.getLabelInfo().equals("NILL")){
                //Do nothing
            } else {
                //Draw Label for slider model
            }
        }
    }
}
