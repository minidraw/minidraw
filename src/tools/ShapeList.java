package tools;

import java.util.Vector;

import tools.shapes.Shape;
import util.Bounds;

/**
 * A basic vector list that is intended to contain shapes.
 * 
 */
@SuppressWarnings("serial")
public class ShapeList extends Vector<Shape>{	
	
	/**
	 * Searches the list of drawn objects for an object containing points (x,y).
	 * It will add all the objects that contain the points and guess at the best fit for the selection.
	 * @param x int x coordinate
	 * @param y int y coordinate
	 * @return Shape desired object on the canvas
	 */
	public Shape search(int x, int y){
		Vector<Shape> shapes = new Vector<Shape>();
		Shape foundShape = null;
		
		for ( Shape shape : this ){
			if ( shape.getBounds().contains(x, y) ){
				shapes.add(shape);
			}
		}
		
		// If there is more than one candidate, eliminate down to one.
		if ( shapes.size() > 0 ){
			foundShape = Bounds.resolveMultipleSelect(shapes);
		}
		return foundShape;
	}
	
}
