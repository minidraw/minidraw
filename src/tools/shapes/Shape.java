package tools.shapes;

import java.awt.Graphics;

import util.Bounds;

public abstract class Shape {
	protected Bounds bounds;
	protected boolean selected;
	
	public Shape(){
		selected = false;
	}
	
	public Bounds getBounds(){
		return bounds;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	 public void select(Graphics g){
		 selected = !selected;
		 
		 if ( selected ){
			 g.drawRect(bounds.getX(), bounds.getY(),bounds.getWidth(), bounds.getHeight());
		 } else {
			 g.clearRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		 }
	 }
}
