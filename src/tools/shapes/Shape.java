package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import util.Bounds;
import util.Direction;
import app.DrawingCanvas;

public abstract class Shape {
	protected Bounds bounds;
	protected boolean selected, canResize;
	protected DrawingCanvas canvas;
	public Color outlineColor;
	protected int shapeX, shapeY, shapeHeight, shapeWidth;
	protected boolean filled;

	public Shape(Color c){
		selected = false;
		bounds = new Bounds();
		outlineColor = c;
		canResize = true;
	}
	
	public Shape(DrawingCanvas c, Color co){
		this(co);
		canvas = c;
	}
	
	public void setCanvas(DrawingCanvas c){
		canvas = c;
	}
	
	public boolean getFilled(){
		return filled;
	}
	
	public void setFilled(boolean f){
		filled = f;
	}

	public Bounds getBounds(){
		return bounds;
	}

	public boolean isSelected(){
		return selected;
	}

	public void deselect(Graphics g){
		select(g, false);
	}

	public void select(Graphics g, boolean s){
		selected = s;
		
		if ( selected ){
			drawBounds(g);
		} else {
			erase(g);
			redraw(g, outlineColor);
		}
	}
	
	public void drawBounds(Graphics g){
		g.setColor(Color.BLACK);
		g.drawRect(bounds.getX(), bounds.getY(),bounds.getWidth(), bounds.getHeight());
		g.fillRect(bounds.topLeft.x, bounds.topLeft.y, bounds.topLeft.width, bounds.topLeft.height);
		g.fillRect(bounds.topRight.x, bounds.topRight.y, bounds.topRight.width, bounds.topRight.height);
		g.fillRect(bounds.botLeft.x, bounds.botLeft.y, bounds.botLeft.width, bounds.botLeft.height);
		g.fillRect(bounds.botRight.x, bounds.botRight.y, bounds.botRight.width, bounds.botLeft.height);
		g.setColor(canvas.getpenColor());
	}
	
	public boolean equals(Object o){
		return hashCode() == o.hashCode();
	}
	
	public int hashCode(){
		return bounds.hashCode();
	}
	
	/**
	 * Erases the shape and redraws the intersecting ones.
	 * @param g Graphics current graphics object
	 */
	public void erase(Graphics g){
		// Time to grab all intersecting and children shapes 
		Vector<Shape> collidingShapes = canvas.getDrawnShapes().intersecting(this);
		collidingShapes.remove(this); // Take out me
		
		// Erase everyone!
		g.clearRect(bounds.getX()-5, bounds.getY()-5, bounds.getWidth()+11, bounds.getHeight()+11);
		
		// Draw everyone, keeping their selection state
		for ( Shape shape : collidingShapes ){
			shape.redraw(g, shape.outlineColor);
			if ( shape.isSelected() ){
				shape.select(g, true);
			}
		}
	}

	/**
	 * Expands the shape by the given direction, width, and height differences
	 * @param g Graphics current graphics context
	 * @param direction Direction direction of expansion
	 * @param dx int difference in the width
	 * @param dy int difference in the height
	 */

	public void expand(Graphics g, Direction direction, int dx, int dy){
		if ( canResize ){
			erase(g);
			switch ( direction ){
			case TOP_LEFT:
				drawShape(g, shapeX-dx, shapeY-dy, shapeWidth+dx, shapeHeight+dy);
				break;
			case TOP_RIGHT:
				drawShape(g, shapeX, shapeY-dy, shapeWidth-dx, shapeHeight+dy);
				break;
			case BOTTOM_LEFT:
				drawShape(g, shapeX-dx, shapeY, shapeWidth+dx, shapeHeight-dy);
				break;
			case BOTTOM_RIGHT:
				drawShape(g, shapeX, shapeY, shapeWidth-dx, shapeHeight-dy);
				break;
			}
		}
	}
	
	/**
	 * Updates the outline color of the shape
	 * @param g
	 * @param c Color new outline color
	 */
	public void redraw(Graphics g, Color c){
		if ( c != null ) outlineColor = c;
		drawShape(g, shapeX, shapeY, shapeWidth, shapeHeight);
	}

	/**
	 * Updates the shapes x and y coorindate
	 * @param g
	 * @param x int dx
	 * @param y int dy
	 */
	public void redraw(Graphics g, int dx, int dy){
		erase(g);
		drawShape(g, shapeX+dx, shapeY+dy, shapeWidth, shapeHeight);
	}
	 
	abstract public void draw(Graphics g, int x0, int y0, int x1, int y1);
	abstract public void drawShape(Graphics g, int x, int y, int width, int height);
}
