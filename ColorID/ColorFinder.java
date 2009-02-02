/**
 * @(#)ColorFinder.java
 *
 *
 * @Color Reader - Team Number 1 
 * @version 1.00 2009/1/31
 *
 * This class is contains the main functionality for our program
 * it contains the GUI information and utilizes all the associated
 * classes. Main function is to allow user to click on a loaded picture
 * and it will return the color of the area where they clicked
 */
 
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class ColorFinder implements ActionListener, MouseListener
{
	//Constant Basic Database names
	private static final String APPLE_DB = "apple.txt";
	private static final String COLORS_DB = "colors.txt";
	private static final String SIMPLIFIED_DB = "simplified.txt";
	private static final String BANANA_DB = "banana.txt";
	
	//GUI STUFF
	private BufferedImage image;    // the rasterized image
    private JFrame frame;           // on-screen view
    JPanel jpMenu;					// Panel for the menu
    JPanel jpTop;					// Panel for the top
    JPanel jpBot;					// Panel for the bottom
    JPanel jpMaster;				// Master Panel
    JTextArea colorText;			// Text area for the bottom 
    JScrollPane ctDisplay;			// scroll pane in which the text box would be put in
	
	//Private member variables.
	private Picture pic;			//Current picture
	private int mouse_x;			//X coordinate of most recent mouse click
	private int mouse_y;			//Y coordinate of most recent mouse click
	private String dbPath;			//location of the database
	private String filename;		//location of the current picture
	private String text;			//text to be displayed
	private ColorDB db;				//Database of ColorNames which we need to check
	private boolean pic_changed = false;		//Boolean for if we have loaded another picture
	
	private ColorRead ttsAgent;		// The object that reads the color name

	/*******************************************************************
	 *Default Constructor                                              *
	 *Sets the database to the simplified database imports the default *
	 *picture, should have it have no picture but we were unable to    *
	 *figure that out, also sets the default text and text to speech   *
	 *voice.                                                           *
	 *******************************************************************/

    public ColorFinder() {
    	//Set the member variables
    	db = new ColorDB(SIMPLIFIED_DB);
    	text = "";
    	pic = new Picture("banana_ripeningchart.jpg"); //Default picture
    	
    	// Create the TTS agent
    	ttsAgent = new ColorRead("Kevin16");
    	
    }
    
    /**************************************************************
     *ChangeDB                                                    *
     *Parameters-                                                 *
     *    String dbnew - the string that is the path to the       *
     *					 database that we are trying to change to *
     *Returns-                                                    *
     *	  does not return anything, but alters the member database*			
     *it take a string and switch the data base by creating a new *
     *database and setting the db member to that newly created DB *
     **************************************************************/
     public void changeDB(String dbnew)
     {
     	System.out.println("Changing the Database");
     	db = new ColorDB(dbnew);
     }
     
    /***************************************************************
     * getColor													   *
     * Parameters-												   *
     *		No parameters, uses member variables pic_color and     *
     *		color_name to find the string name of the color        *
     * Returns-													   *
     *		No return value, changes the color name and outputs the*
     *		text to speech										   *
     * Uses the private members pic and the mouseclick coordinates *
     * Based on results of functions called alters the GUI to      *
     * display the color at the coordinates						   *
     ***************************************************************/
    private void getColor()
    {
    	//Local variables
    	Color pic_color;			//Average color around clicked pixel
    	String color_name;			//
    	
    	/*Call average pixel on pic and the x,y coordinates to find
    	 *the average color around the passed pixel*/
    	// pic_color = averagePixel(pic, pic.width(), pic.height());
    	 pic_color = averagePixel(pic, mouse_x, mouse_y);
    	 
    	 
    	/*Convert the color found into the appropriate english name*/
    	color_name = db.ColorToName(pic_color);
    	
    	setText(color_name);
    	/*Now that we have found the color we want to output alter
    	 *the GUI to display the color to the user*/
    	 
    	colorText.setText(text);
    	GUI();
    	
    	// Gets the Text to Speech agent to repeat the color name
    	ttsAgent.speak(color_name);
    	
    	return;
    }
    
    /*************************************************************
     *averagePixel												 *
     *Parameters-												 *
     *		int x,y - the x and y coordinates of the center pixel*
     * 				  that we want the average color around      *
     *		Picture pic - the picture we are using				 *
     *Returns-													 *
     *		Returns a color object that contains the averaged    *
     *		data												 *
     *Take (x,y) location in pic and return the average color 	 *
     *By refering the pixels around it						     *
     *************************************************************/
    private Color averagePixel(Picture pic, int x, int y)
    {
    	/*Set the lower and upper limint for reference
    	 */

    	int range = 4;    	/*Create local RGB variables to store the	*
    	 *values, using floats for future division	*/
    	int green = 0, blue = 0, red = 0;
    	
    	int total = 0;
    	
    	/*Scan all pixels around (x, y) and store		*
    	 *their RGB values, changing to floats			*/
    	for(int yCounter = y - range; yCounter <= y + range; yCounter++)
    	{
    		for(int xCounter = x - range; xCounter <= x + range; xCounter++)
    		{

    			// Test to make sure that we are still in bounds
    			if( yCounter > 0 && yCounter < pic.height() && xCounter > 0 && xCounter < pic.width() )
    			{
    				// The pixel that we are checking is within bounds.  We should
    				// add its values to our running sum and assume a greater total
    				green += (float)(pic.get(xCounter, yCounter).getGreen());
    				blue += (float)(pic.get(xCounter, yCounter).getBlue());
    				red += (float)(pic.get(xCounter, yCounter).getRed());
    				total ++;
    			}
    		}
    	}
    	
    	// Check to make sure that we were in bounds (ie there are more than
    	// 0 total colors in the total
    	Color average;
    	if( total > 0 )
    	{
    		// Divide the colors by the total number of tests, get an average
	    	// color
	    	average = new Color(red/total, green/total, blue/total);
    	}
    	else
    	{
    		// Since we didn't have any pixels to average, we should
    		// just return black and an error message
    		System.out.println("WARNING:  Clicked on an area outside of the picture, returning black");
    		average = new Color(0, 0, 0);
    	}
    	
    	// The color is averaged and ready, gogogo!
   		return average;
    }
    
    //set the text to be displayed
    public void setText(String ss){
    	text = ss;
    }
    
    /**************************************************************************
     * GUI                                                                    *
     * No Parameters or Returns												  *
     * This is the function that handles all the gui interface, it creates the*
     * panels, menus, and pictures.                                           *
     **************************************************************************/
    public void GUI()
    {  		
    	if (frame == null)			//If frame has not been initialized yet
    	{
    		frame = new JFrame();	//Create a new Jframe
    		
    		//Instantiate Panels
    		jpMenu = new JPanel();
    		jpTop = new JPanel();
    		jpBot = new JPanel();
    		jpMaster = new JPanel();
    		
    		//Instantiate Text box
    		colorText = new JTextArea();
    		ctDisplay = new JScrollPane(colorText);
    		colorText.setText(text);
    		colorText.setEditable(false);
    		colorText.setFont(new Font("Times New Roman",1,16));
    		
    		//Add text to bottom
    		jpBot.add(ctDisplay);
    		
    		//Configure menuBar
    		JMenuBar menuBar = new JMenuBar();					//Add the top menu
    		JMenu menuFile = new JMenu("File");					//Create a new menu item
            menuBar.add(menuFile);								//add that menu item to the menu
            JMenuItem menuItem1 = new JMenuItem(" Load...   ");	//Create sub category to the File tab
            menuItem1.addActionListener(this);					//Add a listener to the menu for when the load button is clicked
            menuItem1.setActionCommand("loadpic");				//Set the ActionCommand to load
            menuFile.add(menuItem1);							//add Load sub category to the menu
            
            JMenu menuDB = new JMenu("Database");				//Create a new menu item for the database
            menuBar.add(menuDB);
            
            JMenuItem menuItem2 = new JMenuItem("Apple");		//Create a new submenu item for the apple db
            menuItem2.addActionListener(this);
            menuItem2.setActionCommand("apple");				//Give the event the name apple
            menuDB.add(menuItem2);
            
            JMenuItem menuItem3 = new JMenuItem("Banana");		//Create a new submenu item for the banana db
            menuItem3.addActionListener(this);
            menuItem3.setActionCommand("banana");				//Give the event the name banana
            menuDB.add(menuItem3);
            
            JMenuItem menuItem4 = new JMenuItem("Simplified Colors");	//Create a new submenu item for the simplified colors
            menuItem4.addActionListener(this);
            menuItem4.setActionCommand("simplified");					//Give the event the name simplified
            menuDB.add(menuItem4);
            
            JMenuItem menuItem5 = new JMenuItem("Complex Colors");	//Create a new submenu item for the complex color
            menuItem5.addActionListener(this);
            menuItem5.setActionCommand("complex");					//Give the event the name complex
            menuDB.add(menuItem5);
            
            JMenuItem menuItem6 = new JMenuItem("Custom Database");	//Create a new submenu item for the Custum database
            menuItem6.addActionListener(this);
            menuItem6.setActionCommand("loadDB");					//Give the event the name loadDB
            menuDB.add(menuItem6);
            
            jpMenu.add(menuBar);				//Add menu bar to the frame
            
            //Set top panel to the picture and add a mouselistener
            jpTop.add(pic.getJLabel());
            jpTop.addMouseListener(this);
            
            //Configure and setup master panel
            jpMaster.setLayout(new BoxLayout(jpMaster, BoxLayout.Y_AXIS));
            jpMaster.add(jpMenu);
            jpMaster.add(jpTop);
            jpMaster.add(jpBot);
            
            //add master panel to the frame
            frame.add(jpMaster);
            
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	//default close operation
            frame.setTitle("Color Identifier");							//title			
            
            //Set frame constants
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
    	}
    	if (pic_changed)
    	{
    		frame = new JFrame();	//Create a new Jframe
    		//Instantiate Panels
    		jpTop = new JPanel();
    		jpBot = new JPanel();
    		jpMaster = new JPanel();
    		
    		//Instantiate Text box
    		colorText = new JTextArea();
    		ctDisplay = new JScrollPane(colorText);
    		colorText.setText(text);
    		colorText.setEditable(false);
    		colorText.setFont(new Font("Times New Roman",1,16));
    		
    		//Add text to the bottom panel
    		jpBot.add(ctDisplay);
    		
    		//Set top panel to the picture and add a mouselistener
    		jpTop.add(pic.getJLabel());
            jpTop.addMouseListener(this);
            
            //Configure and setup master panel
            jpMaster.setLayout(new BoxLayout(jpMaster, BoxLayout.Y_AXIS));
            jpMaster.add(jpMenu);
            jpMaster.add(jpTop);
            jpMaster.add(jpBot);
            frame.add(jpMaster);
            
            //Set frame constants
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	//default close operation
            frame.setTitle("Color Identifier");				//title
    		frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
            
            
            pic_changed = false;
            
    	}
    	frame.validate();
    	frame.repaint();				//draw
    }
    
    /**********************************************************************
    *actionPerformed													  *
    *Parameters-														  *
    *		ActionEvent e - the action that caused the event			  *
    *Returns-															  *
    *		No return													  *
    *Action event handler that takes an action event that has occurred on *
    *the Menu of the program, does action based on which item was clicked *
    ***********************************************************************/
    public void actionPerformed(ActionEvent e) 
    {
    	//If the Load item was clicked in the File menu
    	//We wish to load a new picture so open a file dialog
    	if("loadpic".equals(e.getActionCommand()))
    	{
    		//Create a new file dialog 
	    	FileDialog chooser = new FileDialog(frame,
	                             "Use a .png or .jpg extension", FileDialog.LOAD);
	        chooser.setVisible(true);	//Make dialog visible
	        
	        //If a file was chosen set the pic to the new picture, and set pic changed boolean
	        //to true. Dispose the previous frame (Probably could be optimized so this is unneccesary
	        //but we are inexperienced with Java GUI so we went for simplicity), erase the text,
	        //then call GUI to create a new frame.
	        if (chooser.getFile() != null) 
	        {
	            pic = new Picture(chooser.getDirectory() + File.separator + chooser.getFile());
	            System.out.println("got to new picture");
	            pic_changed = true;
	            frame.dispose();
	            text = "";
	            GUI();
	        }
    	}
    	//If the apple menu was pressed change database to the
    	//apple database
    	else if("apple".equals(e.getActionCommand()))
    	{
    		changeDB(APPLE_DB);
    	}
    	//If the banana menu was pressed change database to the
    	//banana database
    	else if("banana".equals(e.getActionCommand()))
    	{
    		changeDB(BANANA_DB);
    	}
    	//If the simplified menu was pressed change database to the
    	//simplified database
    	else if("simplified".equals(e.getActionCommand()))
    	{
    		changeDB(SIMPLIFIED_DB);
    	}
    	//If the complex menu was pressed change database to the
    	//colors database
    	else if("complex".equals(e.getActionCommand()))
    	{
    		changeDB(COLORS_DB);
    	}
    	//If the custum menu was pressed open a file dialog
    	//so the user can choose their desired database in a .txt format
    	//in the correct format, if no file is chosen do nothing
    	else if("loadDB".equals(e.getActionCommand()))
    	{
    		FileDialog DBchooser = new FileDialog(frame,
	                             "Use a .txt extension", FileDialog.LOAD);
	        DBchooser.setVisible(true);
	        if (DBchooser.getFile() != null) 
	        {
	            changeDB(DBchooser.getDirectory() + File.separator + DBchooser.getFile());
	        }
    	}
        
    }

	/**************************************************************
	 *mouseClicked                                                *
	 *Parameters-												  *
     *		ActionEvent e - the action that caused the event	  *
     *Returns-													  *
     *		No return											  *
	 *Mouse action event handler, for when the mouse is clicked.  *
	 *If the mouse is clicked set the mouse_x and mouse_y member  *
	 *variables to the location clicked. The minus five is used to*
	 *offset the boundaries of the panel.                         *
	 **************************************************************/
	public void mouseClicked(MouseEvent e)
	{
		mouse_x = e.getX()-5;	//picture is shifted left 5 pixels with gui
		mouse_y = e.getY()-5;	//picture is shifted up 5 pixels with gui
		
		// Update to the current color
		getColor();
	}
	
	//These are empty mouse event handlers, since we do not need these actions
	//to be handled, but we need to override them since we use the mouse listners and
	//actions.
	 public void mousePressed(MouseEvent e) {
        return;
    }
    
    public void mouseReleased(MouseEvent e) {
        return;
    }
    
    public void mouseEntered(MouseEvent e) {
        return;
    }
    
    public void mouseExited(MouseEvent e) {
        return;
    }
   
}