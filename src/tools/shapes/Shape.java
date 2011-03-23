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
	protected int shapeX, shapeY, shapeHeight, shapeWidth;

	public Shape(){
		selected = false;
		bounds = new Bounds();
	}
	
	public Shape(DrawingCanvas c){
		this();
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
		g.setColor(Color.BLACK);
		
		if ( selected ){
			g.drawRect(bounds.getX(), bounds.getY(),bounds.getWidth(), bounds.getHeight());
			g.fillRect(bounds.getX()-1, bounds.getY()-1,4,4);
			g.fillRect(bounds.getX()+bounds.getWidth()-1, bounds.getY()-1,4,4);
			g.fillRect(bounds.getX()-1, bounds.getY()+bounds.getHeight()-1,4,4);
			g.fillRect(bounds.getX()+bounds.getWidth()-1, bounds.getY()+bounds.getHeight()-1,4,4);
		} else {
			erase(g);
			redraw(g);
		}
		g.setColor(canvas.getpenColor());
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
		collidingShapes.remove(this); // Take out us
		
		// Erase everyone!
		g.clearRect(bounds.getX()-5, bounds.getY()-5, bounds.getWidth()+10, bounds.getHeight()+10);
		
		// Draw everyone, keeping their selection state
		for ( Shape shape : collidingShapes ){
			shape.redraw(g);
			if ( shape.isSelected() ){
				shape.select(g, true);
			}
		}
	}

	abstract public void draw(Graphics g, int x0, int y0, int x1, int y1);
	abstract public void redraw(Graphics g);
}
