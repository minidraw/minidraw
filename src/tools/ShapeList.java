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
	
	public Vector<Shape> search(int x, int y){
		Vector<Shape> shapes = new Vector<Shape>();
		for ( Shape shape : list ){
			if ( shape.getBounds().contains(x, y) ){
				if ( !shape.isSelected() ){
					shapes.add(shape);
				}
			}
		}
		return shapes;
	}
}
