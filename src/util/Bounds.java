package util;

import java.awt.Rectangle;

public class Bounds {
	private Rectangle bounds;
	
	public Bounds(int x, int y, int width, int height){
		bounds = new Rectangle(x, y, width, height);
	}
	
	public boolean contains(int x, int y){
		if ( bounds.contains(x, y) ) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getX(){
		return bounds.x;
	}
	
	public int getY(){
		return bounds.y;
	}
	
	public int getHeight(){
		return bounds.height;
	}
	
	public int getWidth(){
		return bounds.width;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}

	public void update(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
		
	}
}
