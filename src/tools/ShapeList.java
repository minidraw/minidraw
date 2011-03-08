package tools;

import java.util.Vector;

import tools.shapes.Shape;

@SuppressWarnings("serial")
public class ShapeList extends Vector<Shape>{
	private Vector<Shape> list;
	
	public ShapeList(){
		list = new Vector<Shape>();
	}
	
	public boolean add(Shape shape){
		if ( shape != null ){
			list.add(shape);
			return true;
		}
		return false;
	}
	
	public Shape search(int x, int y){
		Vector<Shape> shapes = new Vector<Shape>();
		Shape foundShape = null;
		
		for ( Shape shape : list ){
			if ( shape.getBounds().contains(x, y) ){
				shapes.add(shape);
			}
		}
		
		// If there are more than one shapes being selected, then we want to only chose the 
		// shape that has smaller area, since it is chanced that one is inside the other/
		if ( shapes.size() > 1 ){
			
		}
		return foundShape;
	}
}
