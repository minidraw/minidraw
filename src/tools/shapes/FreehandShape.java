package tools.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import app.DrawingCanvas;


public class FreehandShape extends Shape {
	private int maxY, maxX;
	private Vector<Point> points;

	public FreehandShape(DrawingCanvas c, Color co){
		super(c, co);
		maxY = 0;
		maxX = 0;
		shapeX = Integer.MAX_VALUE;
		shapeY = Integer.MAX_VALUE;
		points = new Vector<Point>();
	}

	@Override
	public void draw(Graphics g, int px0, int py0, int px1, int py1) {
		g.drawLine(px0, py0, px1, py1);
		/* redraw only the small rectangle  */
		/* containing the new line segment  */
		int x0 = Math.min(px0, px1);
		int y0 = Math.min(py0, py1);
		int dx = Math.abs(px1 - px0)+ 1;
		int dy = Math.abs(px1 - py0) + 1;
		
		maxX = ( px1 > maxX ) ? px1 : maxX;
		shapeX = ( px1 < shapeX ) ? px1 : shapeX;	// We want a smaller x
		
		maxY = ( py1 > maxY ) ? py1 : maxY;
		shapeY = ( py1 < shapeY ) ? py1 : shapeY;	// And a larger y
		
		shapeHeight = maxY - shapeY;
		shapeWidth = maxX - shapeX;
		
		bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
		
		canvas.repaint(x0, y0, dx, dy);
	}
	
	public void addPoint(Point p){
		points.add(p);
	}

	@Override
	public void redraw(Graphics g, Color c) {
		if ( c != null ) outlineColor = c;
		g.setColor(outlineColor);
		for ( int i = 1; i < points.size(); i++ ){
			draw(g, points.get(i).x, points.get(i).y, points.get(i-1).x, points.get(i-1).y);
		}
		g.setColor(canvas.getpenColor());
	}
	
	  public void redraw(Graphics g, int x, int y){
		  shapeX = shapeX+x;
		  shapeY = shapeY+y;
		  erase(g);
		  System.out.println(bounds);
		  bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
		  points.set(0, new Point(points.get(0).x+x, points.get(0).y+y));
		  for ( int i = 1; i <  points.size(); i++ ){
			  Point currPoint = points.get(i);
			  points.set(i, new Point(currPoint.x+x, currPoint.y+y));
			  draw(g, currPoint.x, currPoint.y, points.get(i-1).x, points.get(i-1).y);
		  }
		  if ( selected ) drawBounds(g);
	  }

}
