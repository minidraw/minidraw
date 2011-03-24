package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import tools.shapes.Shape;
import tools.shapes.TwoEndShape;
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
	protected Point startingMousePosition;
	protected Point currentMousePosition;
	protected Graphics iBGraphics;
	protected Shape selectedShape;

	/****< Constructor >*********************************************************/
	public SelectionTool(DrawingCanvas c){
		if( c != null )
			canvas = c;
		else
			throw new IllegalArgumentException();
	}

	/* (non-Javadoc)
	 * 
	 * Select/Deselect an object
	 * 
	 * @see tools.Tool#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e){
		Shape selectShape = canvas.objectAt(e.getPoint().x, e.getPoint().y);
		iBGraphics = canvas.getimageBufferGraphics();

		if ( selectShape != null ){
			if ( !selectShape.isSelected() )
				selectShape.select(iBGraphics, true);
			else
				selectShape.select(iBGraphics, false);
		} else {
			canvas.deselectAll(false);
		}
		canvas.repaint();
	}

	public void mousePressed(MouseEvent e)  {
		try {
			iBGraphics = canvas.getimageBufferGraphics();
			selectedShape = canvas.objectAt(e.getPoint().x, e.getPoint().y);
			if(selectedShape.isSelected()){
				startingMousePosition = e.getPoint();
				currentMousePosition = startingMousePosition;				
			}
		} catch ( Exception ex ){
			ex.printStackTrace();
		}
	}
	public void mouseDragged(MouseEvent e){
		try {
			selectedShape = canvas.objectAt(startingMousePosition.x, startingMousePosition.y);
			iBGraphics = canvas.getimageBufferGraphics();
			if(selectedShape.isSelected()){
				startingMousePosition = currentMousePosition;
				currentMousePosition = e.getPoint();
				
				int xDifference = startingMousePosition.x - currentMousePosition.x;
				int yDifference = startingMousePosition.y - currentMousePosition.y;
		
				selectedShape.redraw(iBGraphics,xDifference , yDifference);
				canvas.repaint();
				
			}
		}
		catch ( Exception ex ){
			ex.printStackTrace();
		}

	}
}
