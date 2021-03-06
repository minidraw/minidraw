package app;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Controller which is associated with a ControlPanelView.  Functions as an
 * ActionListener for the Clear Canvas Button and an ItemListener for the 
 * Color Selection Combo Box.
 * 
 * Needs a reference to the view's DrawingCanvas in order to clear the canvas
 * and update the drawing color.
 */
public class ControlPanelController	implements ActionListener, ItemListener {

	/* Class members */
	protected DrawingCanvas canvas;

	/****< Constructors >********************************************************/
	/**
	 * Creates a ControlPanelController associated with the specified 
	 * DrawingCanvas.
	 * 
	 * @param c associated DrawingCanvas
	 */
	ControlPanelController(DrawingCanvas c) {
		if( c != null )
			canvas = c;
		else
			throw new IllegalArgumentException();
	}

	/****< Event Handlers >******************************************************/
	/* (non-Javadoc)
	 * 
	 * Fields actions performed by the Clear Canvas button on a ControlPanelView
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if ( e.getActionCommand() == "clear" ){
			canvas.removeAllShapes();
			canvas.clearCanvas();
		} else if(e.getActionCommand() == "filled"){
			canvas.setFilled();
		}else {
			canvas.deselectAll(true);
		}
	}

	/* (non-Javadoc)
	 * 
	 * Fields actions performed by the color combo box on a ControlPanelView
	 * 
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e)  {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			canvas.setpenColor(itemToColor(e.getItem()));
		}
	}

	/**
	 * Used to determine which color to select. Maps a string constant to a new
	 * color. If the supplied argument is null or unrecognized, the pen color
	 * will remain the same. 
	 * 
	 * @param item Selected item from a ControlPanelView's combo box
	 * @return Associated color
	 */
	protected Color itemToColor(Object item) {
		if( item == null ) {
			return canvas.getpenColor();
		}

		if("black".equals(item)) {
			return Color.black;
		}
		else if("blue".equals(item)) {
			return Color.blue;
		}
		else if("green".equals(item)) {
			return Color.green;
		}
		else if("red".equals(item)) {
			return Color.red;
		}
		else {
			return canvas.getpenColor();
		}
	}// end protected Color itemToColor( Object )
}// end public class ControlPanelController implements ActionListener...
