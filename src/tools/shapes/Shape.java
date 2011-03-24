package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import app.DrawingCanvas;

import util.Bounds;

public abstract class Shape {
	protected Bounds bounds;
	protected boolean selected;
	protected DrawingCanvas canvas;
	public Color outlineColor;
	protected int shapeX, shapeY, shapeHeight, shapeWidth;

	public Shape(Color c){
		selected = false;
		bounds = new Bounds();
		outlineColor = c;
	}
	
	public Shape(DrawingCanvas c, Color co){
		this(co);
		canvas = c;
	}
	
	public void setCanvas(DrawingCanvas c){
		canvas = c;
	}

	public Bounds getBounds(){
		return bounds;
	}

	public boolean isSelected(){
		return selected;
	}

	public void deselect(Graphics g){
		select(g, false);
	}

	public void select(Graphics g, boolean s){
		selected = s;
		
		if ( selected ){
			g.setColor(Color.BLACK);
			g.drawRect(bounds.getX(), bounds.getY(),bounds.getWidth(), bounds.getHeight());
			g.fillRect(bounds.getX()-1, bounds.getY()-1,4,4);
			g.fillRect(bounds.getX()+bounds.getWidth()-1, bounds.getY()-1,4,4);
			g.fillRect(bounds.getX()-1, bounds.getY()+bounds.getHeight()-1,4,4);
			g.fillRect(bounds.getX()+bounds.getWidth()-1, bounds.getY()+bounds.getHeight()-1,4,4);
			g.setColor(canvas.getpenColor());
		} else {
			erase(g);
			redraw(g, outlineColor);
		}
	}
	
	public boolean equals(Object o){
		return hashCode() == o.hashCode();
	}
	
	public int hashCode(){
		return bounds.hashCode();
	}
	
	public void erase(Graphics g){
		// Time to grab all intersecting and children shapes 
		Vector<Shape> collidingShapes = canvas.getDrawnShapes().intersecting(this);
		collidingShapes.remove(this); // Take out me
		
		// Erase everyone!
		g.clearRect(bounds.getX()-5, bounds.getY()-5, bounds.getWidth()+10, bounds.getHeight()+10);
		
		// Draw everyone, keeping their selection state
		for ( Shape shape : collidingShapes ){
			shape.redraw(g, shape.outlineColor);
			if ( shape.isSelected() ){
				shape.select(g, true);
			}
		}
	}

	abstract public void draw(Graphics g, int x0, int y0, int x1, int y1);
	abstract public void redraw(Graphics g, int x, int y);
	abstract public void redraw(Graphics g, Color c);
}
