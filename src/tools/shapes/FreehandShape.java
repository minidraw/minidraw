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
		canResize = false;
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
		
		// If were moving backwards record it
		if ( shapeX == px1 ){
			maxX = maxX - dx;
		}
		
		maxY = ( py1 > maxY ) ? py1 : maxY;
		shapeY = ( py1 < shapeY ) ? py1 : shapeY;	// And a smaller y
		
		// If were moving backwards record it
		if ( shapeY == py1 ){
			maxY = maxY - dy;
		}
		
		shapeHeight = Math.abs(maxY - shapeY);
		shapeWidth = Math.abs(maxX - shapeX);
		
		bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);

		canvas.repaint(x0, y0, dx, dy);
	}

	public void addPoint(Point p){
		points.add(p);
	}
	
	public void drawShape(Graphics g, int x, int y, int width, int height){
		g.setColor(outlineColor);
		shapeX = x;
		shapeY = y;
		shapeWidth = width;
		shapeHeight = height;
		bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
		for ( int i = 1; i < points.size(); i++ ){
			draw(g, points.get(i).x, points.get(i).y, points.get(i-1).x, points.get(i-1).y);
		}

		if ( selected ) drawBounds(g);
	  }
	
	  public void redraw(Graphics g, int dx, int dy){
		  g.setColor(outlineColor);
		  shapeX = shapeX+dx;
		  shapeY = shapeY+dy;
		  erase(g);
		  bounds.update(shapeX, shapeY, shapeWidth, shapeHeight);
		  points.set(0, new Point(points.get(0).x+dx, points.get(0).y+dy));
		  for ( int i = 1; i <  points.size(); i++ ){
			  Point currPoint = points.get(i);
			  points.set(i, new Point(currPoint.x+dx, currPoint.y+dy));
			  currPoint = points.get(i);
			  draw(g, currPoint.x, currPoint.y, points.get(i-1).x, points.get(i-1).y);
		  }
		  g.setColor(canvas.getpenColor());
		  if ( selected ) drawBounds(g);
	  }


}
