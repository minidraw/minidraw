package tools.shapes;

import java.awt.Graphics;


/**
 * A basic line
 *
 * Defines a basic line class that runs from a starting point to and ending 
 * point. 
 */
public class LineShape extends TwoEndShape {

/**
   * 
   * Draws a simple line from the starting to ending point.
   * 
   * @see tools.shapes.TwoEndShape#draw(java.awt.Graphics, int, int, int, int)
   */
  public void draw(Graphics g, int x0, int y0, int x1, int y1) {
	shapeX = x0;
	shapeY = y0;
	shapeHeight = y1;
	shapeWidth = x1;
	
	bounds.update(shapeX, shapeY, x0+x1, y0+y1);
    g.drawLine(shapeX, shapeY, shapeWidth, shapeHeight);
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
public void redraw(Graphics g) {
	
	  g.drawLine(shapeX, shapeY, shapeWidth, shapeHeight);
}
}// end public class LineShape extends TwoEndShape
