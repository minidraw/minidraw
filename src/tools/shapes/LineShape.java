package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;


/**
 * A basic line
 *
 * Defines a basic line class that runs from a starting point to and ending 
 * point. 
 */
public class LineShape extends TwoEndShape {
	public LineShape(Color c) {
		super(c);
	}

	private int lineStartX;
	private int lineStartY;
	private int lineEndX;
	private int lineEndY;
	

/**
   * 
   * Draws a simple line from the starting to ending point.
   * 
   * @see tools.shapes.TwoEndShape#draw(java.awt.Graphics, int, int, int, int)
   */
  public void draw(Graphics g, int x0, int y0, int x1, int y1) {
	lineStartX = x0;
	lineStartY = y0;
	lineEndX = x1;
	lineEndY = y1;
	  
	if( y1 < y0 && x1 < x0){
		shapeX = x1;
		shapeY = y1;
		shapeHeight = y0 - y1;
		shapeWidth = x0 - x1;
	}
	  
	else if( x1 < x0 ){
		shapeX = x1;
		shapeY = y0;
		shapeHeight = y1 - y0;
		shapeWidth = x0 - x1;
	}
	  
	else if( y1 < y0 ){
		shapeX = x0;
		shapeY = y1;
		shapeHeight = y0 - y1;
		shapeWidth = x1 - x0;
	}
	  
	
	else{
		shapeX = x0;
		shapeY = y0;
		shapeHeight = y1-y0;
		shapeWidth = x1-x0;
	}
	
	bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
    g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);

  }

  /**
   * 
   * Draws the outline of a line from the starting to ending point.
   * 
   * @see tools.shapes.TwoEndShape#drawOutline(java.awt.Graphics, int, int, int, int)
   */
  public void drawOutline(Graphics g, int x0, int y0, int x1, int y1) {
	shapeX = x0;
	shapeY = y0;
	shapeHeight = y1;
	shapeWidth = x1;
	  g.drawLine(shapeX, shapeY, shapeWidth, shapeHeight);
  }

@Override
public void redraw(Graphics g, Color c) {
	if ( c != null ) outlineColor = c;
	g.setColor(outlineColor);
    g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);
    g.setColor(canvas.getpenColor());
}

public void redraw(Graphics g, int x, int y){
	  g.drawLine(x, y, shapeX, shapeY);
}
}// end public class LineShape extends TwoEndShape
