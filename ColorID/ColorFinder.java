/**
 * @(#)main.java
 *
 *
 * @author 
 * @version 1.00 2009/1/31
 */
 
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
//import java.awt.TextComponent;

public class ColorFinder implements ActionListener, MouseListener
{
	//GUI STUFF
	private BufferedImage image;    // the rasterized image
    private JFrame frame;           // on-screen view
    JPanel jpMenu;
    JPanel jpTop;
    JPanel jpBot;
    JPanel jpMaster;
    JTextArea colorText;
    JScrollPane ctDisplay;
	
	//Private member variables.
	private Picture pic;			//Current picture
	private int mouse_x;			//X coordinate of most recent mouse click
	private int mouse_y;			//Y coordinate of most recent mouse click
	private String dbPath;			//location of the database
	private String filename;		//location of the current picture
	private String text;			//text to be displayed
	private ColorDB db;				//Database of ColorNames which we need to check
	private boolean pic_changed = false;		//Boolean for if we have loaded another picture

    public ColorFinder(String db_in) {
    	//Set the member variables
    	db = new ColorDB(db_in);
    	text = "";
    	pic = new Picture("banana_ripeningchart.jpg"); //Default picture
    }
    
    /* Uses the private members pic and the mouseclick coordinates *
     * Based on results of functions called alters the GUI to      *
     * display the color at the coordinates						   */
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
    	 
    	//colorText.setText(text);
    	frame.dispose();
    	pic_changed = true;
    	GUI();
    	
    	return;
    }
    
    /*take (x,y) location in pic and return the average color 	 *
     *by refering the pixels around it						     */
    private Color averagePixel(Picture pic, int x, int y)
    {
    	System.out.println ("averagePixel is called ");
    	/*Set the lower and upper limint for reference
    	 */

    	int range = 4;
    	/*Create local RGB variables to store the	*
    	 *values, using floats for future division	*/
    	int green = 0, blue = 0, red = 0;
    	
    	int total = 0;
    	
    	/*Scan all pixels around (x, y) and store		*
    	 *their RGB values, changing to floats			*/
    	for(int yCounter = y - range; yCounter <= y + range; yCounter++)
    	{
    		for(int xCounter = x - range; xCounter <= x + range; xCounter++)
    		{
/*
    			green += (float)(pic.get(x, y).getGreen());
    			System.out.println("ycounter: " + yCounter + " xcounter: " + xCounter + " green : " + green);
    			blue += (float)(pic.get(x, y).getBlue());
    			System.out.println(" blue : " + blue);
    			red += (float)(pic.get(x, y).getRed());
    			System.out.println(" red : " + red);
*/

    			// Test to make sure that we are still in bounds
    			if( yCounter > 0 && yCounter < pic.height() && xCounter > 0 && xCounter < pic.width() )
    			{
    				green += (float)(pic.get(xCounter, yCounter).getGreen());
    				blue += (float)(pic.get(xCounter, yCounter).getBlue());
    				red += (float)(pic.get(xCounter, yCounter).getRed());
    				total ++;
    			}
    		}
    	}
    	
    	System.out.println ("green: " + green);
		System.out.println ("blue: " + blue);
		System.out.println ("red: " + red);
    	/*Use the resulting RGB values to create* 
    	 *an average color						*/
    	Color average = new Color(red/total, green/total, blue/total);
    	
    	/*That is what we want*/
   		return average;
    }
    
    //set the text to be displayed
    public void setText(String ss){
    	text = ss;
    }
    
    /*display the graphic user interface
     */
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
    		JMenuBar menuBar = new JMenuBar();		//Add the top menu
    		JMenu menu = new JMenu("File");			//Create a new menu item
            menuBar.add(menu);						//add that menu item to the menu
            JMenuItem menuItem1 = new JMenuItem(" Load...   ");	//Create sub category to the File tab
            menuItem1.addActionListener(this);		//Add a listener to the menu for when the load button is clicked
            menu.add(menuItem1);					//add Load sub category to the menu
            jpMenu.add(menuBar);				//Add menu bar to the frame
            
            //Container content = new Container();
            //content.add(pic.getJLabel());			//display image
            //content.add(new JLabel(text));				//display text\
            //jpTop.add(menuBar);
            
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
            
            
            //frame.setContentPane(pic.getJLabel());
                        
            //frame.setLayeredPane(new JLabel(text));
            //frame.setGlassPane(textbox);
            
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	//default close operation
            frame.setTitle("Color Identifier");				//title
            
            //frame.addMouseListener(this);				
            
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
    	frame.repaint();				//draw
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        FileDialog chooser = new FileDialog(frame,
                             "Use a .png or .jpg extension", FileDialog.LOAD);
        chooser.setVisible(true);
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

	//mouse detection
	public void mouseClicked(MouseEvent e)
	{
		mouse_x = e.getX();
		mouse_y = e.getY();
		System.out.println("mouse x click Coordinate = " + mouse_x);
		System.out.println("mouse y click Coordinate = " + mouse_y);
		
		// Update to the current color
		getColor();
	}
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