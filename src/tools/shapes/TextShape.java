package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class TextShape extends Shape{
	
	public TextShape(Color c) {
		super(c);
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

	@Override
	public void redraw(Graphics g, Color c) {
		if ( c != null ) outlineColor = c;
		g.setColor(outlineColor);
		g.drawString(text, shapeX+3, shapeY+19);	
		g.setColor(canvas.getpenColor());
	}
	
	  public void redraw(Graphics g, int x, int y){
		  shapeX = shapeX+x;
		  shapeY = shapeY+y;
		  erase(g);
		  bounds.update(shapeX, shapeY, bounds.getWidth(), bounds.getHeight());
		  g.drawString(text, shapeX+3, shapeY+19);
		  if ( selected ) drawBounds(g);
	  }

	public void updateText(StringBuffer txt) {
		if(txt != null){
			text = txt.toString();
		}
	}

	public String getText(){return text;}
}
