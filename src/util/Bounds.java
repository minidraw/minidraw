package util;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

import tools.shapes.Shape;

public class Bounds{
	private Rectangle bounds;
	public Rectangle topLeft, topRight, botLeft, botRight;
	
	public boolean contains(int x, int y){
		if ( bounds.contains(x, y) ) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gives the corner of where the point is contained in
	 * @param x int for x coord
	 * @param y int for y coord
	 * @return Direction corner which is being clicked
	 */
	public Direction containsInOuterBounds(int x, int y){
		Point p = new Point(x, y);
		if ( topLeft.contains(p) )
			return Direction.TOP_LEFT;
		else if ( topRight.contains(p) )
			return Direction.TOP_RIGHT;
		else if ( botLeft.contains(p) )
			return Direction.BOTTOM_LEFT;
		else if ( botRight.contains(p) )
			return Direction.BOTTOM_RIGHT;
		return null;
	}

	public int getX(){
		return bounds.x;
	}
	
	public int getY(){
		return bounds.y;
	}
	
	public int getHeight(){
		return bounds.height;
	}
	
	public int getWidth(){
		return bounds.width;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
	
	public String toString(){
		return "X: " + bounds.getX() + " Y: " + bounds.getY() + " Width: " + bounds.getWidth() + " Height: " + 
		bounds.getHeight();
	}

	/**
	 * Updates the bounds of the Shape
	 * @param x int x coordinate of top left
	 * @param y int y coordinate of top left
	 * @param width int width of the shape
	 * @param height int height of the shape
	 */
	public void update(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
		topLeft = new Rectangle(bounds.x-1, bounds.y-1, 4, 4);
		topRight = new Rectangle(bounds.x+bounds.width-1, bounds.y-1, 4, 4);
		botLeft = new Rectangle(bounds.x-1, bounds.y+bounds.height-1, 4, 4);
		botRight = new Rectangle(bounds.x+bounds.width-1, bounds.y+bounds.height-1, 4, 4);
	}
	
	/**
	 * In order to have nested objects on the screen, we must be able to resolve
	 * a conflict in which object has been selected. In order to do this we must
	 * first determine if the objects are indeed intersecting, if so then we take the object
	 * with the smaller area, since that is the most probable object desired to be selected.
	 * @param shapes Vector of shapes to choose from.
	 * @return Shape the most fitting guess
	 */
	public static Shape resolveMultipleSelect(Vector<Shape> shapes){
		Shape bestFit = shapes.firstElement();
		for ( Shape shape : shapes ){
			if ( shape.getBounds().getBounds().intersects(bestFit.getBounds().getBounds()) 
					&& shape.getBounds().area() < bestFit.getBounds().area() ){
				bestFit = shape;
			}
		}
		return bestFit;
	}
	
	public Vector<Shape> findIntersectingShapes(Vector<Shape> shapes){
		Vector<Shape> intersectingShapes = new Vector<Shape>();
		
		// Then find our intersections
		for ( Shape shape : shapes ){
			if ( bounds.intersects(shape.getBounds().getBounds()) ){
				intersectingShapes.add(shape);
			}
		}
		return intersectingShapes;
	}
	
	public int area(){
		return bounds.height * bounds.width;
	}
}
