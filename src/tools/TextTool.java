package tools;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import tools.shapes.TextShape;
import app.DrawingCanvas;

/**
 * Draws text on the canvas.
 * 
 * The user selects the starting point with the mouse and then types in the
 * desired text.  Currently, the font is not selectable.  A new text box will
 * begin when the user clicks on a different canvas location.  Changing the
 * current tool, in effect, also stops current text entry.
 */
public class TextTool extends Tool {

	/* Class member variables */
	protected DrawingCanvas canvas;
	protected Point startingPosition;
	protected StringBuffer text;
	protected Font font = new Font("Serif", Font.BOLD, 24);
	protected TextShape shape;
	protected Class<?> k;

	/****< Constructor >*********************************************************/
	public TextTool(DrawingCanvas c, Class<?> k) {
		if( c != null && k != null){
			canvas = c;
			this.k = k;
		}
		else
			throw new IllegalArgumentException();
	}

	/****< Event Handler Methods >***********************************************/
	/* (non-Javadoc)
	 * 
	 * Returns focus to the drawing canvas and stores the starting location for
	 * the text display.
	 * @see tools.Tool#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e)  {
		try{
			shape = new TextShape(canvas.getpenColor());
			shape.setCanvas(canvas);
			canvas.requestFocus();
			startingPosition = e.getPoint();
			Graphics iBGraphics = canvas.getimageBufferGraphics();
			iBGraphics.setFont(font);
			text = new StringBuffer();
			shape.draw(iBGraphics, startingPosition.x-3, startingPosition.y-19, 0, 25);
			canvas.addShape(shape);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * 
	 * Adds a character to the string buffer
	 * 
	 * @see tools.Tool#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e)  {
		char nextChar = e.getKeyChar(); 
		Graphics iBGraphics = canvas.getimageBufferGraphics();
		text.append(nextChar); 

		if(e.isShiftDown())
			shape.updateBounds(iBGraphics, 18);
		else
			shape.updateBounds(iBGraphics, 12);
		
		iBGraphics.drawString(text.toString(), startingPosition.x, startingPosition.y);
		shape.updateText(text);
		canvas.repaint();
	}
}// end public class TextTool extends Tool
