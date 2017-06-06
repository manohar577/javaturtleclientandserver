/*
* 
* File Name:  LinesComponent.java
* Author1: Harika Hari (NAU ID: hh453@nau.edu)
* Author2: Caitlin Barrett (NAU ID: cb2693@nau.edu)
*  
* Description: THis is a GUI based java code that draws the line structure and switches the user 
* input criteria to allow the frame to be repainted to left/right/up or down in the specified length
* by the GUI client 
* 
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class LinesComponent extends JComponent{

    int x1 = 200;
    int y1 = 200;
    int x2 = 200;
    int y2 = 200;

//class for line and structures    
private static class Line {
    final int x1; 
    final int y1;
    final int x2;
    final int y2;   
    final Color color;

    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }               
}

private final LinkedList<Line> lines = new LinkedList<Line>();

public void addLine(int x1, int x2, int x3, int x4) {
    addLine(x1, x2, x3, x4, Color.black);
}

public void addLine(int x1, int x2, int x3, int x4, Color color) {
    lines.add(new Line(x1,x2,x3,x4, color));        
    repaint();
}

//function to repaint the line as per user direction
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (Line line : lines) {
        g.setColor(line.color);
        g.drawLine(line.x1, line.y1, line.x2, line.y2);
    }
}

//public method to initialize the frame component
public void initialize(LinesComponent lineComponent) {
    JFrame testFrame = new JFrame();
    testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    final LinesComponent comp = lineComponent;
    comp.setPreferredSize(new Dimension(600, 600));
    testFrame.getContentPane().add(comp, BorderLayout.CENTER);
    testFrame.setTitle("TURTLE SERVER");
    testFrame.pack();
    testFrame.setVisible(true);
}

//function to draw line as per user direcion and length
public void drawLine(String direction, String length) {

    switch(direction.trim()) {
	case "up":
		this.x1 = x2;
		this.y1 = y2;
		this.y2 = y2 - Integer.parseInt(length);
		break;
	case "down": 
		this.x1 = x2;
		this.y1 = y2;
		this.y2 = y2 + Integer.parseInt(length);
		break;
	case "left": 
		this.x1 = x2;
		this.y1 = y2;
		this.x2 = x2 - Integer.parseInt(length);
		break;
	case "right": 
		this.x1 = x2;
		this.y1 = y2;
		this.x2 = x2 + Integer.parseInt(length);
		break;
    }
    addLine(x1, y1, x2, y2, Color.black);
}

}