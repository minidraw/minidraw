package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Defines the basic notion of a TwoEndShape.
 * 
 * This class is not meant to be instantiated directly, but rather, should be
 * used as a parent class for all TwoEndShapes.  The core identity of a 
 * TwoEndedShape is defined by both the draw and drawOutline methods which are
 * explained below.
 */
public abstract class TwoEndShape extends Shape{

public TwoEndShape(Color c) {
		super(c);
	}



/**
   * Facilitates drawing an outline as the user drags the mouse.
   * 
   * @param g Graphics context to use
   * @param x0 starting x coordinate
   * @param y0 starting y coordinate
   * @param x1 ending x coordinate
   * @param y1 ending y coordinate
   */
  abstract public void drawOutline(Graphics g, int x0, int y0, int x1, int y1);
}
