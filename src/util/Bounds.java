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
	
	public static Vector<Shape> resolveIntersectingRedraw(Shape s, Vector<Shape> shapes){
		Vector<Shape> intersecting = null;
		for ( Shape shape : shapes ){
			if ( s.getBounds().getBounds().intersects(shape.getBounds().getBounds())){
				intersecting.add(shape);
			}
		}
		return intersecting;
	}
	
	public int area(){
		return bounds.height * bounds.width;
	}
}
