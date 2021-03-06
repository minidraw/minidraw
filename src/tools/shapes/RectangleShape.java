package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;


/**
 * Defines how to draw a Rectangle on the DrawingCanvas
 */
public class RectangleShape extends TwoEndShape {

	public RectangleShape(Color c) {
		super(c);
	}


	/* (non-Javadoc)
	 * 
	 * Defines how to draw the rectangle with corners at the starting and ending
	 * point. The smallest coordinates of the starting and ending positions are
	 * used to locate the origin of the rectangular shape.  The absolute value
	 * of the differences in the x and y coordinates are used for the width and
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
			g.fillRect(shapeX, shapeY, shapeWidth, shapeHeight);
			setFilled(true);
		}
		else{
			g.drawRect(shapeX, shapeY, shapeWidth, shapeHeight);
		}		if ( selected ) drawBounds(g);
	}


	/* (non-Javadoc)
	 * 
	 * Implemented like draw().  See above.
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
		bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
		g.drawRect(shapeX, shapeY, shapeWidth, shapeHeight);
	}

	public void drawShape(Graphics g, int x, int y, int width, int height){
		g.setColor(outlineColor);
		shapeX = x;
		shapeY = y;
		shapeWidth = width;
		shapeHeight = height;
		bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
		if (canvas.getFilled() == true){
			g.fillRect(shapeX, shapeY, shapeWidth, shapeHeight);
			setFilled(true);
		}
		else{
			g.drawRect(shapeX, shapeY, shapeWidth, shapeHeight);
		}		if ( selected ) drawBounds(g);
	}

	public void drawBounds(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(bounds.topLeft.x, bounds.topLeft.y, bounds.topLeft.width, bounds.topLeft.height);
		g.fillRect(bounds.topRight.x, bounds.topRight.y, bounds.topRight.width, bounds.topRight.height);
		g.fillRect(bounds.botLeft.x, bounds.botLeft.y, bounds.botLeft.width, bounds.botLeft.height);
		g.fillRect(bounds.botRight.x, bounds.botRight.y, bounds.botRight.width, bounds.botLeft.height);
		g.setColor(canvas.getpenColor());
	}
}// end public class RectangleShape extends TwoEndShape
