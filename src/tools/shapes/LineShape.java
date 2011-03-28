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

	private int lineEndX;
	private int lineEndY;
	private float slope;


	/**
	 * 
	 * Draws a simple line from the starting to ending point.
	 * 
	 * @see tools.shapes.TwoEndShape#draw(java.awt.Graphics, int, int, int, int)
	 */
	public void draw(Graphics g, int x0, int y0, int x1, int y1) {
		shapeX = x0;
		shapeY = y0;
		lineEndX = x1;
		lineEndY = y1;
		
		slope = (float)(shapeY - lineEndY) / (float)(shapeX - lineEndX);
		shapeHeight = Math.abs(y0 - y1);
		shapeWidth = Math.abs(x0 - x1);
		if ( slope >= 0 ){
			bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
		} else {
			bounds.update(shapeX-(x0-x1), shapeY, shapeWidth, shapeHeight);
		}
		g.drawLine(shapeX, shapeY, lineEndX, lineEndY);
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
		lineEndY = y1;
		lineEndX = x1;
		g.drawLine(shapeX, shapeY, lineEndX, lineEndY);
	}

	public void drawShape(Graphics g, int x0, int y0, int x1, int y1){
		shapeX = x0;
		shapeY = y0;
		lineEndX = lineEndX;
		lineEndY = lineEndY;
		shapeHeight = y1;
		shapeWidth = x1;
		g.setColor(outlineColor);
		slope = (float)(shapeY - lineEndY) / (float)(shapeX - lineEndX);
		if ( slope >= 0 ){
			bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
		} else {
			bounds.update(shapeX-(shapeX-lineEndX), shapeY, shapeWidth, shapeHeight);
		}
		g.drawLine(shapeX, shapeY, lineEndX, lineEndY);
		g.setColor(canvas.getpenColor());
		if ( selected ) drawBounds(g);
	}

	@Override
	public void redraw(Graphics g, Color c) {
		if ( c != null ) outlineColor = c;
		g.setColor(outlineColor);
		g.drawLine(shapeX, shapeY, lineEndX, lineEndY);
		g.setColor(canvas.getpenColor());
		if ( selected ) drawBounds(g);
	}

	public void redraw(Graphics g, int x, int y){
		g.setColor(outlineColor);
		shapeX += x;
		shapeY += y;
		lineEndX += x;
		lineEndY += y;
		erase(g);
		if ( slope >= 0 ){
			bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
		} else {
			bounds.update(shapeX-(shapeX-lineEndX), shapeY, shapeWidth, shapeHeight);
		}
		g.drawLine(shapeX, shapeY, lineEndX, lineEndY);
		g.setColor(canvas.getpenColor());
		if ( selected ) drawBounds(g);
	}

	public void drawBounds(Graphics g){
		g.setColor(Color.BLACK);
		// Check slope for which way facing to add only 2 bounds.
		float slope = (float)(shapeY - lineEndY) / (float)(shapeX - lineEndX);
		if ( slope >= 0 ){
			g.fillRect(bounds.topLeft.x, bounds.topLeft.y, bounds.topLeft.width, bounds.topLeft.height);
			g.fillRect(bounds.botRight.x, bounds.botRight.y, bounds.botRight.width, bounds.botLeft.height);
		} else {
			g.fillRect(bounds.topRight.x, bounds.topRight.y, bounds.topRight.width, bounds.topRight.height);
			g.fillRect(bounds.botLeft.x, bounds.botLeft.y, bounds.botLeft.width, bounds.botLeft.height);
		}
		g.setColor(canvas.getpenColor());
	}
}// end public class LineShape extends TwoEndShape
