package app;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;

import tools.EraserTool;
import tools.FreehandTool;
import tools.SelectionTool;
import tools.TextTool;
import tools.ToolList;
import tools.TwoEndShapeTool;

/**
 * Represents the MiniDraw program and can be initialized as either an
 * application or an applet.  This contains references to the main
 * components of MiniDraw and is responsible for initializing those references
 * and any associations.
 */
@SuppressWarnings("serial")
public class ObjectDraw1 extends JApplet  {

  /* Main Components */
  protected DrawingCanvas canvas;
  protected ControlPanelView controlPanel;
  protected ToolBarView toolBar;
  protected MenuBarView menuBar;
  protected ToolList toolList;
  protected boolean isApplet = false;
    
  /****< Constructors and Initializers >***************************************/
  public ObjectDraw1(boolean isApplet) { 
    this.isApplet = isApplet;
    if (!isApplet) {
      init();
    }
  }

  public ObjectDraw1() {
    /* invoked as Applet */
    this(true);
  }

  /**
   * Invoked implicitly if MiniDraw is executed as an applet.  Otherwise, the
   * constructor will explicitly call this method to initialize and display all 
   * of the MiniDraw components. 
   */
  public void init() {
    getContentPane().setLayout(new BorderLayout());
    canvas = createDrawingCanvas();
    getContentPane().add(canvas, BorderLayout.CENTER);
    controlPanel = createControlPanelView();
    getContentPane().add(controlPanel, BorderLayout.SOUTH);
    toolList = createToolList();
    toolBar = createToolBarView(toolList);
    getContentPane().add(toolBar, BorderLayout.WEST);
    menuBar = createMenuBarView(toolList);
    getContentPane().add (menuBar, BorderLayout.NORTH);
    setSize(600, 600);
  }

  /****< Factory methods >*****************************************************/
  
  /**
   *  Initialize a new DrawingCanvas
   *  
   *  Simple factory method.  See DrawingCanvas constructor for more details.
   */
  protected DrawingCanvas createDrawingCanvas() {
    return new DrawingCanvas();
  }
  
  /**
   * Initialize a new ControlPanelView
   * 
   * Simple factory method.  Requires the canvas to be initialized. 
   * See ControlPanelView for more details. 
  **/
  protected ControlPanelView createControlPanelView() 
    throws NullPointerException
  {
	  if( canvas != null ) {
		  return new ControlPanelView(canvas);
	  } else {
		throw new NullPointerException( "Canvas not initialized." );
	  }
  }// end protected ControlPanelView createControlPanelView()
  
 
  /**
   * Initialize a new ToolBarView
   * 
   * Simple factory method.  Uses the passed ToolList to populate the 
   * ToolBarView.  See the ToolBarView constructor for more details.
   * 
   * @param toolList list of tools to display on the view; null list is accepted
   * @return newly initialized ToolBarView with indicated tools included
  **/
  protected ToolBarView createToolBarView(ToolList toolList) {
    return new ToolBarView(toolList);
  }
  
  /**
   * 
   * @param toolList list of tools to display on the view; null list is accepted
   * @return newly initialized ToolBarView with indicated tools included
  **/
  protected MenuBarView createMenuBarView(ToolList toolList) {
    return new MenuBarView(toolList);
  }
 
  
  /**
   *  Configure tool list used for ToolBar and MenuBar construction.
   *  
   *  ToolList is initialized by adding several ToolControllers.  
   *  ToolControllers extend the abstract action class allowing easy display of
   *  the tool via a menu and a tool bar button.  More details are provided in
   *  the ToolController documentation.
  **/
  protected ToolList createToolList() {
    ToolList actions = new ToolList();

    actions.add(
      new ToolController("Freehand",
  	                     getImageIcon("freehand.jpg"),
  	                     "freehand drawing tool",
  	                     canvas,
  	                     new FreehandTool(canvas)));
try{
    actions.add(
  		new ToolController("Line",
  		getImageIcon("line.jpg"),
  		"Line drawing tool",
  		canvas,
  		new TwoEndShapeTool(canvas, Class.forName("tools.shapes.LineShape"))));
  
    actions.add(
  		new ToolController("Rectangle",
  		getImageIcon("rectangle.jpg"),
  		"Rectangle drawing tool",
  		canvas,
  		new TwoEndShapeTool(canvas, Class.forName("tools.shapes.RectangleShape"))));
  		
    actions.add(
  	    new ToolController("Oval",
  	    getImageIcon("oval.jpg"),
  		"Oval drawing tool",
  		canvas,
  		new TwoEndShapeTool(canvas, Class.forName("tools.shapes.OvalShape"))));

    actions.add(
  		new ToolController("Text",
  		getImageIcon("text.jpg"),
  		"text drawing tool",
        canvas,
  		new TextTool(canvas, Class.forName("tools.shapes.TextShape"))));
} catch ( ClassNotFoundException e){
	e.printStackTrace();
}
    actions.add(
  		new ToolController("Eraser",
  		getImageIcon("eraser.jpg"),
  		"Eraser drawing tool",
  		canvas,
  		new EraserTool(canvas)));
    actions.add(
		new ToolController("Select",
		getImageIcon("select.gif"),
		"Select shapes tool",
		canvas,
		new SelectionTool(canvas)));
  
    return actions;
  }// end protected ToolList createToolList()
  
  /****< CLASS METHODS >*******************************************************/
  
  /**
   * Attempts to load an Image from the local disk.
   * 
   * Retrieving the current working directory varies whether the program was
   * executed as an Applet or an Application.  Assumes that the images will be
   * located at the top directory of the binary files.
   * 
   * @param fileName file name of the image relative to the current working
   *                 directory
   * @return new ImageIcon
   */
  protected ImageIcon getImageIcon(String fileName) {
    URL url;
	if(isApplet) {
      try {
        url = new URL(getCodeBase(), fileName);    
      } catch(MalformedURLException e) {
        return null;
      }
      return new ImageIcon( url );
    }// end executed as applet
    else {
      return new ImageIcon( fileName );
    }// end executed as application    
  }// end protected ImageIcon getImageIcon( String )
  
  /* Main method  */
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setTitle("MiniDraw Fourth Iteration");
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(new ObjectDraw1(false),
			      BorderLayout.CENTER);
    frame.addWindowListener(new AppCloser());
    frame.pack();
    frame.setSize(600, 600);
    frame.setVisible(true);
  }
  
  /****< AppCloser >***********************************************************/

  /**
   * Inner class for terminating the application.
   *
   * When executed as an application, closing the window does not necessarily
   * trigger application termination. This class catches the window closing
   * event and terminates the application.
   */
  static class AppCloser extends WindowAdapter  {
    public void windowClosing(WindowEvent e) {
       System.exit(0);
    }
  }// end static class AppCloser extends WindowAdapter
}// end public class MiniDraw4 extends JApplet
