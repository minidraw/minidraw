package util;

import java.awt.Rectangle;
import java.util.Vector;

import tools.shapes.Shape;

public class Bounds{
	private Rectangle bounds;
	
	public boolean contains(int x, int y){
		if ( bounds.contains(x, y) ) {
			return true;
		} else {
			return false;
		}
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

	public void update(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
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
	
	public int area(){
		return bounds.height * bounds.width;
	}
}
