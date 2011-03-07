package tools;

import java.util.Vector;

import tools.shapes.Shape;

public class ShapeList {
	private Vector<Shape> list;
	
	public ShapeList(){
		list = new Vector<Shape>();
	}
	
	public void add(Shape shape){
		if ( shape != null ){
			list.add(shape);
		}
	}
	
	public Shape search(int x, int y){
		for ( Shape shape : list ){
			if ( shape.getBounds().contains(x, y) ){
				return shape;
			}
		}
		return null;
	}
}
