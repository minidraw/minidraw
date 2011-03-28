package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class TextShape extends Shape{
	
	public TextShape(Color c) {
		super(c);
		canResize = false;
	}

	private String text;

	public void updateBounds(Graphics g, int margin){
		bounds.update(bounds.getX(), bounds.getY(), bounds.getWidth()+margin, bounds.getHeight());
	}
	
	@Override
	public void draw(Graphics g, int x0, int y0, int x1, int y1) {
		shapeX = x0;
		shapeY = y0;
		shapeHeight = y1;
		shapeWidth = x1;
		
		bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
	}

	public void updateText(StringBuffer txt) {
		if(txt != null){
			text = txt.toString();
		}
	}
	 
	public void drawShape(Graphics g, int x, int y, int width, int height){
		g.setColor(outlineColor);
		shapeX = x;
		shapeY = y;
		shapeWidth = width;
		shapeHeight = height;
		bounds.update(shapeX, shapeY, bounds.getWidth(), bounds.getHeight());
		g.drawString(text, shapeX+3, shapeY+19);
		if ( selected ) drawBounds(g);
	  }

	public String getText(){return text;}
}
