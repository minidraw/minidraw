package tools;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import tools.shapes.Shape;

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
		Shape selectedShape = canvas.objectAt(e.getPoint().x, e.getPoint().y);
		Graphics iBGraphics = canvas.getimageBufferGraphics();
		
		if ( selectedShape != null ){
			if ( !selectedShape.isSelected() )
				selectedShape.select(iBGraphics, true);
			else
				selectedShape.select(iBGraphics, false);
		} else {
			canvas.deselectAll();
		}
		canvas.repaint();
	}
}
