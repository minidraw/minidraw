package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Vector;

import tools.shapes.Shape;
import util.Bounds;
import app.DrawingCanvas;

/**
 * Selects a shape on the current canvas
 * 
 * In order to select a shape on the canvas to move or resize it one must
 * activate the select tool and left click on the desired object.  From there,
 * multiple selection is enabled, but if a click happens with no area, then all objects are 
 * deselected.
 */
public class SelectionTool extends Tool {
	protected DrawingCanvas canvas;

	protected Point clickValue;

	protected boolean selectCorner(Shape shape, Point point){
		Bounds bounds = shape.getBounds();
		Bounds upperLeftCorner = new Bounds();
		upperLeftCorner.update(bounds.getX()-1, bounds.getY()-1,4,4);
		Bounds upperRightCorner = new Bounds();
		upperRightCorner.update(bounds.getX()+bounds.getWidth()-1, bounds.getY()-1,4,4);
		Bounds lowerLeftCorner = new Bounds();
		lowerLeftCorner.update(bounds.getX()-1, bounds.getY()+bounds.getHeight()-1,4,4);
		Bounds lowerRightCorner = new Bounds();
		lowerRightCorner.update(bounds.getX()+bounds.getWidth()-1, bounds.getY()+bounds.getHeight()-1,4,4);

		if(upperLeftCorner.contains(point.x, point.y) || upperRightCorner.contains(point.x, point.y) ||
				lowerLeftCorner.contains(point.x, point.y) || lowerRightCorner.contains(point.x, point.y)){

			return true;
		}
		else{
			return false;
		}
	}

	/****< Constructor >*********************************************************/
	public SelectionTool(DrawingCanvas c){
		if( c != null )
			canvas = c;
		else
			throw new IllegalArgumentException();
	}

	/* (non-Javadoc)
	 * 

	 * Press to enter selection state
	 */
	public void mousePressed(MouseEvent e){
		Shape selectedShape = canvas.objectAt(e.getPoint().x, e.getPoint().y);
		Graphics iBGraphics = canvas.getimageBufferGraphics();
		clickValue = e.getPoint();
		if ( selectedShape != null ){
			if ( !selectedShape.isSelected() ){
				selectedShape.select(iBGraphics, true);
			}
			else{
				if(selectCorner(selectedShape, clickValue) == true){
					selectedShape.erase(iBGraphics);
					selectedShape.redraw(iBGraphics, clickValue);
				}
				else{
					selectedShape.select(iBGraphics, false);
					iBGraphics.setXORMode(Color.lightGray);
					iBGraphics.setColor(Color.white);
					selectedShape.redraw(iBGraphics,0,0);
				}
			}
		}
		else {

			canvas.setcurrentTool(this);
		}

		canvas.repaint();
	}


	/* (non-Javadoc)
	 * 
	 * Drags the selected shapes
	 */
	public void mouseDragged(MouseEvent e){
		Graphics iBGraphics = canvas.getimageBufferGraphics();
		Vector<Shape> shapes = canvas.getDrawnShapes().allSelected();
		Shape resizeShape = canvas.objectAt(e.getX(), e.getY());

		if(selectCorner(resizeShape, clickValue) == true){
			resizeShape.erase(iBGraphics);
			resizeShape.redraw(iBGraphics, clickValue);
		}
		else{
			for ( Shape shape : shapes ){
				shape.erase(iBGraphics);
				int shape_x = shape.getBounds().getX();
				int shape_y = shape.getBounds().getY();

				// {x,y}_offset: the offset from the (x,y) of the shape and the source of the click
				int x_offset = Math.abs(clickValue.x - shape_x);
				int y_offset = Math.abs(clickValue.y - shape_y);
				// d{x,y}: the distance the shape moves from the offset
				int dx = e.getPoint().x - (shape_x+x_offset);
				int dy = e.getPoint().y - (shape_y+y_offset);

				shape.redraw(iBGraphics, dx, dy);

			}
		}
		// update offset
		clickValue = e.getPoint();

		canvas.repaint();
	}

	public void mouseReleased(MouseEvent e){
		Graphics iBGraphics = canvas.getimageBufferGraphics();
		iBGraphics.setPaintMode();
		iBGraphics.setColor(canvas.getpenColor());
		Vector<Shape> shapes = canvas.getDrawnShapes().allSelected();

		for( Shape shape : shapes ){
			shape.redraw(iBGraphics, 0, 0);
			shape.drawBounds(iBGraphics);

		}
		iBGraphics.setPaintMode();
		for( Shape shape : shapes ){
			shape.redraw(iBGraphics, shape.outlineColor);
			shape.drawBounds(iBGraphics);
		}

		canvas.repaint();
	}
}
