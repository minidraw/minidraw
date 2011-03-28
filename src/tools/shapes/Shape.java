package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import app.DrawingCanvas;

import util.Bounds;
import util.Direction;

public abstract class Shape {
	protected Bounds bounds;
	protected boolean selected;
	protected DrawingCanvas canvas;
	public Color outlineColor;
	protected int shapeX, shapeY, shapeHeight, shapeWidth;

	public Shape(Color c){
		selected = false;
		bounds = new Bounds();
		outlineColor = c;
	}
	
	public Shape(DrawingCanvas c, Color co){
		this(co);
		canvas = c;
	}
	
	public void setCanvas(DrawingCanvas c){
		canvas = c;
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
	
	protected void drawBounds(Graphics g){
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
		g.clearRect(bounds.getX()-5, bounds.getY()-5, bounds.getWidth()+10, bounds.getHeight()+10);
		
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
		redraw(g, 0, 0);
		switch ( direction ){
		// If were modifying the top {left, right} we want to update the (x,y) along with the (h,w)
		case TOP_LEFT:
			draw(g, shapeX-dx, shapeY-dy, shapeWidth+shapeX, shapeHeight+shapeY);
			break;
		case TOP_RIGHT:
			draw(g, shapeX+dx, shapeY+dy, shapeWidth+shapeX, shapeHeight+shapeY);
			break;
		// If were modifying the bottom {left, right} we want to update the (h,w)
		case BOTTOM_LEFT:
			break;
		case BOTTOM_RIGHT:
			break;
		}
	}

	abstract public void draw(Graphics g, int x0, int y0, int x1, int y1);
	abstract public void redraw(Graphics g, Color c);
	/**
	 * UPDATES WITH THE DX AND DY !!
	 * @param g
	 * @param x
	 * @param y
	 */
	abstract public void redraw(Graphics g, int x, int y);
}
