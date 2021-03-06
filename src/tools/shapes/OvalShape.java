package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Defines the drawing behavior for drawing an oval.
 */
public class OvalShape extends TwoEndShape {
	

	public OvalShape(Color c) {
		super(c);
	}


/* (non-Javadoc)
   * 
   * Defines how to draw the oval bounded by the starting and ending points.
   * The smallest coordinates of the starting and ending positions are used to 
   * locate the origin of the oval boundary.  The absolute value of the 
   * differences in the x and y coordinates are used for the width and
   * height respectively.
   * 
   * @see tools.shapes.TwoEndShape#draw(java.awt.Graphics, int, int, int, int)
   */
  public void draw(Graphics g, int x0, int y0, int x1, int y1) {
    
    // find smallest x coordinate and calculate width
    if (x0 <= x1) {
      shapeX = x0;
      shapeWidth = (x1-x0)+1;
    }
    else {
      shapeX = x1;
      shapeWidth = (x0-x1)+1;
    }
    
    // find smallest y coordinate and calculate height
    if (y0 <= y1) {
      shapeY = y0;
      shapeHeight = (y1-y0)+1;
    }
    else {
      shapeY = y1;
      shapeHeight = (y0-y1)+1;
    }
    bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
    if (canvas.getFilled() == true){
		g.fillOval(shapeX, shapeY, shapeWidth, shapeHeight);
		setFilled(true);
	}
	else{
		g.drawOval(shapeX, shapeY, shapeWidth, shapeHeight);
	}    if ( selected ) drawBounds(g);
}
  
  public void drawShape(Graphics g, int x, int y, int width, int height){
	  g.setColor(outlineColor);
	  shapeX = x;
	  shapeY = y;
	  shapeWidth = width;
	  shapeHeight = height;
	  bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
	  if (getFilled() == true){
			g.fillOval(shapeX, shapeY, shapeWidth, shapeHeight);
		}
		else{
			g.drawOval(shapeX, shapeY, shapeWidth, shapeHeight);
		}	  if ( selected ) drawBounds(g);
  }
  
  /* (non-Javadoc)
   * 
   * Same algorithm as draw
   * 
   * @see tools.shapes.TwoEndShape#drawOutline(java.awt.Graphics, int, int, int, int)
   */
  public void drawOutline(Graphics g, int x0, int y0,
                                        int x1, int y1) {
    if (x0 <= x1) {
    shapeX = x0;
    shapeWidth = (x1-x0)+1;
    }
    else {
      shapeX = x1;
      shapeWidth = (x0 -x1)+1;
    }
    if (y0 <= y1) {
      shapeY = y0;
      shapeHeight = (y1-y0)+1;
    }
    else {
      shapeY = y1;
      shapeHeight = (y0-y1)+1;
    }
    if (getFilled() == true){
		g.fillOval(shapeX, shapeY, shapeWidth, shapeHeight);
	}
	else{
		g.drawOval(shapeX, shapeY, shapeWidth, shapeHeight);
	}
   }

}// end public class OvalShape extends Tool
