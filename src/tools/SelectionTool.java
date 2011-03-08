package tools;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Vector;

import tools.shapes.Shape;

import app.DrawingCanvas;

public class SelectionTool extends Tool {
	protected DrawingCanvas canvas;

	public SelectionTool(DrawingCanvas c){
		if( c != null )
			canvas = c;
		else
			throw new IllegalArgumentException();
	}

	public void mouseClicked(MouseEvent e){
		Vector<Shape> selectedShapes = canvas.objectAt(e.getPoint().x, e.getPoint().y);
		Graphics iBGraphics = canvas.getimageBufferGraphics();
		
		if ( selectedShapes.size() > 0 ){
			for ( Shape shape : selectedShapes ){
				shape.select(iBGraphics);
			}
		}
		else{
			
		}
		canvas.repaint();
	}
}
