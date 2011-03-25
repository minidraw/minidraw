package app;

import tools.SelectionTool;
import tools.ShapeList;
import tools.Tool;
import tools.shapes.Shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JComponent;

/**
 * Main view of the MiniDraw program which serves a both a view and a model.
 * The model component is specified below, and the view component displays the
 * image contents of the model.
 * 
 * To prevent screen flicker when drawing or resizing, an ImageBuffer is used.
 * All drawing from the associated tools is executed on imageBuffer, which is
 * drawn to screen on updates which are specified by the tools.
 */
@SuppressWarnings("serial")
public class DrawingCanvas extends JComponent {

	/* Class member variables */
	public final Color BACKGROUND = Color.white;
	protected DrawingCanvasController DCcontroller;
	protected Image imageBuffer;
	protected Graphics imageBufferGraphics;
	protected int canvasWidth;
	protected int canvasHeight;
	protected Color penColor = Color.black;
	protected Tool currentTool;
	protected ShapeList drawnObjects;

	/****< Constructor >*********************************************************/
	/**
	 * Creates a default DrawingCanvas with a white background
	 */
	public DrawingCanvas() {
		setBackground( BACKGROUND );
		DCcontroller = createDrawingCanvasController();
		addDrawingCanvasListener(DCcontroller);
		drawnObjects = new ShapeList();
	}

	/****< Factory Methods >*****************************************************/

	protected DrawingCanvasController createDrawingCanvasController() {
		return new DrawingCanvasController(this);
	}

	/****< Listener Register Methods >*******************************************/
	protected void addDrawingCanvasListener(DrawingCanvasController listener) {
		if( listener != null ) {
			addMouseListener((MouseListener) listener);
			addMouseMotionListener((MouseMotionListener) listener);
			addKeyListener((KeyListener) listener );
		}
	}

	/****< Drawing Methods >*****************************************************/
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#update(java.awt.Graphics)
	 */
	public void update(Graphics g){
		paint(g);
	}

	/* (non-Javadoc)
	 * 
	 * Painting the DrawingCanvas is simply displaying the contents of the
	 * imageBuffer.
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		g.drawImage(imageBuffer,0, 0, this);
	}


	/**
	 * Paints over the drawing canvas in the background color
	 */
	public void clearCanvas() {
		imageBufferGraphics.setColor(BACKGROUND);
		imageBufferGraphics.fillRect(0, 0, canvasWidth, canvasHeight);
		imageBufferGraphics.setColor(penColor);
		repaint();
	}

	/****< Accessor and Update Methods >*****************************************/

	/**
	 * Updates the current drawing color
	 * 
	 * @param c new drawing color
	 */
	public void setpenColor(Color c) {
		if( c != null ) {	
			penColor = c;
			imageBufferGraphics.setColor(c);
			for ( Shape shape : drawnObjects ){
				if ( shape.isSelected() ){
					shape.redraw(imageBufferGraphics, c);
				}
			}
			repaint();
		}
	}

	/**
	 * Accessor method for current drawing color
	 * 
	 * @return current drawing color
	 */
	public Color getpenColor() {
		return penColor;
	}

	/**
	 * Updates current drawing tool
	 * 
	 * @param t new drawing tool
	 */
	public void setcurrentTool(Tool t)  {
		if( t != null ){
			// Check for selection twice to deselect
			if ( currentTool instanceof SelectionTool && currentTool == t){
				deselectAll(false);
				currentTool = null;
			} else
			currentTool = t;
		}
	}

	/**
	 * Accessor method for current drawing tool
	 * 
	 * @return current drawing tool
	 */
	public Tool getcurrentTool() {
		return currentTool;
	}

	/**
	 * Accessor method for imageBuffer
	 * 
	 * @return current image buffer graphics context
	 */
	public Graphics getimageBufferGraphics() {
		return imageBufferGraphics;
	}


	/* (non-Javadoc)
	 * 
	 * Adjusts the size of the DrawingCanvas as well as the imageBuffer to match
	 * the new DrawingCanvas size.
	 * 
	 * @see java.awt.Component#setBounds(int, int, int, int)
	 */
	public void setBounds(int x, int y, int width, int height) {
		Image newimageBuffer = createImage(width, height);
		imageBufferGraphics = newimageBuffer.getGraphics();
		if (imageBuffer != null) {
			imageBufferGraphics.drawImage(imageBuffer, 0, 0 ,this);
		}
		imageBuffer = newimageBuffer;
		setpenColor(penColor);
		super.setBounds(x, y, width, height);
		repaint();
		canvasWidth = width;
		canvasHeight = height;
	}

	/** 
	 * Accessor method for an object at a specific location on the canvas
	 * 
	 * @param x int x coordinate
	 * @param y int y coordinate
	 * @return Shape containing the given (x,y)
	 */
	public Shape objectAt(int x, int y){
		return drawnObjects.search(x, y);
	}
	
	public ShapeList getDrawnShapes(){
		return drawnObjects;
	}

	/**
	 * Setter method for adding a shape to the canvas
	 * 
	 * @param shape Shape to be added
	 */
	public void addShape(Shape shape){
		drawnObjects.add(shape);
	}

	/**
	 * Removes all objects from the drawing canvas
	 */
	public void removeAllShapes() {
		drawnObjects.removeAllElements();
	}

	/**
	 * Deselects all the selected shapes on the canvas
	 * 
	 * @param erase boolean If true => erase the shapes as well
	 */
	public void deselectAll(boolean erase){
		Vector<Shape> shapesToBeRemoved = new Vector<Shape>();
		for ( Shape shape : drawnObjects ) {
			if ( shape.isSelected() ){
				shape.deselect(imageBufferGraphics);
				if ( erase ){
					shapesToBeRemoved.add(shape);
				}
			}
		}
		for ( Shape shape : shapesToBeRemoved ){
			drawnObjects.remove(shape);
			shape.erase(imageBufferGraphics);
		}
		repaint();
	}
	
}// end public class DrawingCanvas extends JComponent
