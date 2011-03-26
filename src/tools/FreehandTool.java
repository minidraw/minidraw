package tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import tools.shapes.FreehandShape;


import app.DrawingCanvas;

/**
 * Draws a Freehand shape on the DrawingCanvas.
 * 
 * A Freehand shape can consist of many different points and simply follows the
 * user's mouse.  As the user moves the mouse, line segments are drawn to
 * screen.
 */
public class FreehandTool extends Tool {
   
  /* Class member variables */
  protected DrawingCanvas canvas;
  protected FreehandShape shape;
  protected Point startingMousePosition;

  /****< Constructor >*********************************************************/
  public FreehandTool(DrawingCanvas c) {
    if( c != null )
      canvas = c;
    else
      throw new IllegalArgumentException();
  }
  
  /****< Draw Method >*********************************************************/
  protected void drawLineSegment(Point p1, Point p2) {
	  shape.draw(canvas.getimageBufferGraphics(), p1.x, p1.y, p2.x, p2.y);
  }

  /****< Event Handlers >******************************************************/
  /* (non-Javadoc)
   * 
   * Establish starting point for next drawing.
   * 
   * @see tools.Tool#mousePressed(java.awt.event.MouseEvent)
   */
  public void mousePressed(MouseEvent e)  {    
    startingMousePosition = e.getPoint();
    shape = new FreehandShape(canvas, canvas.getpenColor());
    shape.addPoint(startingMousePosition);
  }

  /* (non-Javadoc)
   * 
   * Draws the next line segment on the canvas.
   * 
   * @see tools.Tool#mouseDragged(java.awt.event.MouseEvent)
   */
  public void mouseDragged(MouseEvent e)  {
    Point newMousePosition = e.getPoint();
    drawLineSegment(startingMousePosition,
				newMousePosition);
    /* update current mouse coordinates */
    startingMousePosition = newMousePosition;
    shape.addPoint(newMousePosition);
  }
  
  public void mouseReleased(MouseEvent e){
	  canvas.addShape(shape);
  }
}// end public class FreehandTool extends Tool
