package tools;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

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
		Shape selectedShape = canvas.objectAt(e.getPoint().x, e.getPoint().y);
		Graphics iBGraphics = canvas.getimageBufferGraphics();
		
		if ( selectedShape != null ){
			selectedShape.select(iBGraphics);
		}
		else{
			
		}
		canvas.repaint();
	}
}
