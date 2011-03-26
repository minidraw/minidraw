package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;

import app.DrawingCanvas;
import tools.shapes.TwoEndShape;

/**
 * Draws any TwoEndShape on the DrawingCanvas.
 * 
 * This tool takes advantage of some generic behavior for drawing a shape.  Any
 * TwoEndShape can be drawn with this tool where a TwoEndShape is defined as
 * a shape with a starting and ending point.  The user clicks the mouse button
 * to define the starting location, drags the mouse, and releases the mouse
 * at the ending location.
 */
public class TwoEndShapeTool extends Tool {
   
  /* Class Member Variables */
  protected DrawingCanvas canvas;
  protected Point startingMousePosition;
  protected Point currentMousePosition;
  protected Color saveColor;
  protected Class<?> k;
  protected TwoEndShape shape;

  /****< Constructor >*********************************************************/
  public TwoEndShapeTool(DrawingCanvas c, Class<?> k) {
    if( c != null && k != null  ) {
      canvas = c;
      this.k = k;
    } else {
      throw new IllegalArgumentException();
    }
  }
  
  /****< Event Handler Methods >***********************************************/
  /* (non-Javadoc)
   * 
   * Establish starting point for next drawing
   * 
   * @see tools.Tool#mousePressed(java.awt.event.MouseEvent)
   */
public void mousePressed(MouseEvent e)  {
    try {
    	@SuppressWarnings("rawtypes")
		Constructor construct = k.getConstructor(Color.class);
    	shape = (TwoEndShape) construct.newInstance(canvas.getpenColor());
    	shape.setCanvas(canvas);
	    startingMousePosition = e.getPoint();
	    currentMousePosition = startingMousePosition;
	    Graphics iBGraphics = canvas.getimageBufferGraphics();
	    saveColor = iBGraphics.getColor( );
	    iBGraphics.setXORMode(Color.lightGray);
	    iBGraphics.setColor(Color.white);
	    shape.drawOutline(iBGraphics,
	                      startingMousePosition.x,
	                      startingMousePosition.y,
	                      startingMousePosition.x,
			                  startingMousePosition.y);
	
	    canvas.repaint();
    } catch ( Exception ex ){
    	ex.printStackTrace();
    }
  }

  /* (non-Javadoc)
   * 
   * Resizes the TwoEndShape and updates the shape outline
   * 
   * @see tools.Tool#mouseDragged(java.awt.event.MouseEvent)
   */
  public void mouseDragged(MouseEvent e)  {
    Point newMousePosition = e.getPoint();
    Graphics iBGraphics = canvas.getimageBufferGraphics(); 

    /* erase previous temporary figure by redrawing it */
    shape.drawOutline(iBGraphics,
                      startingMousePosition.x,
		                  startingMousePosition.y,
                      currentMousePosition.x, 
		                  currentMousePosition.y);

    /* draw new temporary figure */
    shape.drawOutline(iBGraphics,
                      startingMousePosition.x,
                      startingMousePosition.y,
                      newMousePosition.x,
                      newMousePosition.y);

    /* update current mouse coordinates */
    currentMousePosition = newMousePosition;
    canvas.repaint();
  }


  /* (non-Javadoc)
   * 
   * Draws the finalized TwoEndShape
   * 
   * @see tools.Tool#mouseReleased(java.awt.event.MouseEvent)
   */
  public void mouseReleased(MouseEvent e) { 
    Graphics iBGraphics = canvas.getimageBufferGraphics();

    /* Erase final temporary figure  */
    shape.drawOutline(iBGraphics,
                      startingMousePosition.x, 
                      startingMousePosition.y,
                      currentMousePosition.x, 
                      currentMousePosition.y);
    
    /* Return graphics context to normal drawing mode and color */
    iBGraphics.setPaintMode();
    iBGraphics.setColor(saveColor);

    /* Draw final"permanent" figure */
    shape.draw(iBGraphics,
               startingMousePosition.x, 
               startingMousePosition.y,
               e.getPoint().x, 
               e.getPoint().y);
    canvas.addShape(shape);
    canvas.repaint();
  }
}// end public class TwoEndShapeTool extends Tool
