/**
 * @(#)main.java
 *
 *
 * @author 
 * @version 1.00 2009/1/31
 */
 
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class findColor implements ActionListener{
	//GUI STUFF
	private BufferedImage image;    // the rasterized image
    private JFrame frame;           // on-screen view
	
	//Private member variables.
	private Picture pic;			//Current picture
	private int mouse_x;			//X coordinate of most recent mouse click
	private int mouse_y;			//Y coordinate of most recent mouse click
	private ColorName[] colors;		//Database of colors
	private String dbPath;			//location of the database
	private String filename;		//location of the current picture

    public findColor(String db) {
    	//Set the member variables
		colors = importDB(db);
    	/****************************************
    	 * Initialize GUI						*
    	 *		Create Form						*
    	 *		Mouse Listener					*
    	 *		Loading Picture					*
    	 *		Loop							*
    	 *			-On click call getColor		* 
    	 *			-Load new picture(getImage)	*
    	 ****************************************/
    }
    
    /* Uses the private members pic and the mouseclick coordinates *
     * Based on results of functions called alters the GUI to      *
     * display the color at the coordinates						   */
    private void getColor()
    {
    	//Local variables
    	Color pic_color;
    	String color_name;
    	
    	/*Call average pixel on pic and the x,y coordinates to find
    	 *the average color around the passed pixel*/
    	 pic_color = averagePixel(pic, mouse_x, mouse_y);
    	 
    	/*Convert the color found into the appropriate english name*/
    	color_name = colorToName(pic_color);
    	
    	/*Now that we have found the color we want to output alter
    	 *the GUI to display the color to the user*/
    	
    	return;
    }
    
    private Color averagePixel(Picture pic, int x, int y)
    {
    	int lower = y + 1;
    	if (lower >= pic.height()) lower = pic.height()-1;
    	int upper = y - 1;
    	if (upper < 0) upper = 0;
    	int left = x - 1;
    	if (left < 0) left = 0;
    	int right = x + 1;
    	if (right < pic.width()) right = pic.width()-1;
    	/*Create local RGB variables to store the	*
    	 *values, using floats for future division	*/
    	float green = 0, blue = 0, red = 0;
    	
    	/*Scan all 9 pixels around (x, y) and	*
    	 *store their RGB values				*/
    	for(int yCounter = lower; yCounter <= upper; yCounter++)
    	{
    		for(int xCounter = left; xCounter <= right; xCounter++)
    		{
    			green += (float)(pic.get(x, y).getGreen());
    			blue += (float)(pic.get(x, y).getBlue());
    			red += (float)(pic.get(x, y).getRed());
    		}
    	}
    	
    	/*Use the resulting RGB values to create* 
    	 *an average color						*/
    	Color average = new Color(red/((right-left)*(upper-lower)),
    								 green/((right-left)*(upper-lower)),
    								 blue/((right-left)*(upper-lower)));
    	
    	/*That is what we want*/
   		return average;
    }
    
    private String colorToName(Color theColor)
    {
    	float[] HSB = new float[3];
    	String name = "";
    	theColor.RGBtoHSB(theColor.getRed(), theColor.getGreen(), theColor.getBlue(), HSB);
    	for(int i = 0; i < colors.length; i++)
    	{
    		if(HSB[1] <= colors[i].hue+0.05 || HSB[i] >= colors[i].hue-0.05)
    		{
    			name = colors[i].name;
    		}
    	}
    	if(name == "")
    		return "Color not found.";
    	return name;
    }
    
    public void GUI()
    {
    	if (frame == null)
    	{
    		frame = new JFrame();
    		
    		JMenuBar menuBar = new JMenuBar();
    		 JMenu menu = new JMenu("File");
            menuBar.add(menu);
            JMenuItem menuItem1 = new JMenuItem(" Load...   ");
            menuItem1.addActionListener(this);
            menu.add(menuItem1);
            frame.setJMenuBar(menuBar);
            
            frame.setContentPane(pic.getJLabel());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setTitle("Color Identifier");
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
    	}
    	
    	frame.repaint();
    }

    ColorName[] importDB(String DB)
    {
<<<<<<< .mine
    	ColorName colorArray[NUMBER_OF_COLORS];
    	FileInputStream fin;
    	
    	try
    	{
    		FileInputStream fin;
    		fin = new FileInputStream (colors.txt);
    		int i = 0;
    		String currentLine;
    		while ( (currentLine = fin.readLine())!=NULL && i < NUMBER_OF_COLORS )
    		{
    			colorArray[i].hue =
    			colorArray[i].sat =
    			colorArray[i].val = 
    			colorArray[i].name = 
    		}
    	}
    	catch (Exception e)
    	{
    		System.err.println("Error: " + e.getMessage());
    	}
    	ColorName[] names = new ColorName[1];
    	return names;
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        FileDialog chooser = new FileDialog(frame,
                             "Use a .png or .jpg extension", FileDialog.LOAD);
        chooser.setVisible(true);
        if (chooser.getFile() != null) 
        {
            pic = new Picture(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

}