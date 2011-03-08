package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import app.DrawingCanvas;

import tools.ShapeList;
import util.Bounds;

public abstract class Shape {
	protected Bounds bounds;
	protected boolean selected;

	public Shape(){
		selected = false;
		bounds = new Bounds();
	}

	public Bounds getBounds(){
		return bounds;
	}

	public boolean isSelected(){
		return selected;
	}

	public void deselect(DrawingCanvas canvas){
		select(canvas.getimageBufferGraphics(), false);
	}

	private void select(Graphics g, boolean s){
		selected = !s;
		select(g);
	}

	public void select(Graphics g){
		selected = !selected;

		if ( selected ){
			g.drawRect(bounds.getX(), bounds.getY(),bounds.getWidth()+1, bounds.getHeight()+1);
			g.fillRect(bounds.getX()-1, bounds.getY()-1,4,4);
			g.fillRect(bounds.getX()+bounds.getWidth()-1, bounds.getY()-1,4,4);
			g.fillRect(bounds.getX()-1, bounds.getY()+bounds.getHeight()-1,4,4);
			g.fillRect(bounds.getX()+bounds.getWidth()-1, bounds.getY()+bounds.getHeight()-1,4,4);
		} else {
			Vector<Shape> temp = new Vector<Shape>();
			ShapeList list = new ShapeList();
			temp = list.intersect(this);

			g.clearRect(bounds.getX()-1, bounds.getY()-1, bounds.getWidth()+4, bounds.getHeight()+4);
			draw(g,bounds.getX(), bounds.getY(),bounds.getX()+bounds.getWidth()-1, bounds.getY()+bounds.getHeight()-1);
			
			if (temp != null){
				for(int i = 0; i < temp.size(); i++){
					
				}
			}
		}
	}



	abstract public void draw(Graphics g, int x0, int y0, int x1, int y1);



}
