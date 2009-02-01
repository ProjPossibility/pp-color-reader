
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;


/* ******************************************************************************
 * ColorDB
 *
 * Stores the database of colors from the file.  This can be searched by
 * hue 
 *
 *
 * *****************************************************************************/
import java.io.*;
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
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
class ColorDB {
	
	
	///////////////////////////////////////////////////////////////////////////
	//                         MEMBER VARIABLES                              //
	///////////////////////////////////////////////////////////////////////////
	
	private static final int MAX_N_COLORS = 15;
	private ColorName[] nameDB = new ColorName[MAX_N_COLORS];
	
	
	// The actual number of elements in the nameDB array
	private int size;
			
		
	
	///////////////////////////////////////////////////////////////////////////
	//                            CONSTRUCTOR                                //
	///////////////////////////////////////////////////////////////////////////
	
	
	/* *************************************************************
	 * CONSTRUCTOR
	 *
	 * Sorts the database by hue values, making it faster to search
	 * for the name of a specific hue.
	 *
	 * ARGUMENTS:
	 *	filename - The name of the database file.
	 * ************************************************************/
	public ColorDB( String fileName )
	{
		// The file stream of the database
		BufferedReader fin = null;
    	
    	// Attempt to open the database file.  If not, fail!!
    	try
    	{
    		fin = new BufferedReader(new FileReader(fileName));
    		
    		// Loop through all of the lines of the database and 
	    	// add them to the actual database in memory
	    	
	    	// The current index in the nameDB
	    	int i = 0;
	    	
	    	// The string for the current line in the DB file
			String line;
			
			// Get each of the lines from the DB file and add them to
			// the database
			while ( ((line = fin.readLine()) != null) && i < MAX_N_COLORS )
			{
				// Create the ColorName value at the correct index in the array
				nameDB[i] = new ColorName(line);
				
				// Increment the location in the database
				i++;
			}
			
			// Update the number of colors in the database
			size = i;
    	}
    	catch (IOException e) 
    	{
             // e.printStackTrace();
             throw new RuntimeException("Could not open file");
    	}
    	//finally
    	//{
    		// Close up the file if it exists.  if it does not,
    		// make sure that the size is 0
    	//	if( fin != null)
		//		fin.close();
		//	else
		//		size = 0;
    	//}

	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	//                           MUTATORS                                    //
	///////////////////////////////////////////////////////////////////////////
	
	/* *************************************************************
	 * SortByHue
	 *
	 * Sorts the database by hue values, making it faster to search
	 * for the name of a specific hue.
	 * ************************************************************/
	public void SortByHue()
	{
		
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	//                            ACCESSORS                                  //
	///////////////////////////////////////////////////////////////////////////
	
	/* *************************************************************
	 * ColorToName
	 *
	 * Convert the RGB parameters of the theColor to HSB parameters
	 * Then finds the color with the closest matching hue to the
	 * hue of the color
	 *
	 * ARGUMENTS
	 *	theColor - The color whose name we are trying to find
	 *
	 * RETURNS
	 *	A string representing the hue's name
	 * ************************************************************/	
	public String ColorToName(Color theColor)
	{
		//Create an array of three floats
		float[] HSB = new float[3];
		
		//Use the library function RGBtoHSB to convert from RGB parameters to HSB parameters
		HSB = theColor.RGBtoHSB(theColor.getRed(), theColor.getGreen(), theColor.getBlue(), HSB);
		
		System.out.println("Testing color RGB(" + theColor.getRed() + ", " + theColor.getGreen() + ", " + theColor.getBlue() + ")" );
		
		//Create string to store the name of the color
		String name = "";
		
		// Debug output
		System.out.println("Testing hue: " + HSB[0]);
		
		//Loop through the array of database of colors
		for(int i = 0; i < size; i++)
		{
			//Compare the hue of the color to see if it is within a .05 range from the hue of the database color.
			//And if it is within range, then change the string to the color of the name.
			if(HSB[0] <= nameDB[i].hue+.05 && HSB[0] >= nameDB[i].hue-.05)
			{
				name = nameDB[i].name;
				break;
			}
		}
		
		//If the hue is not within the range of any database colors, then return Color not found.
		if(name == "")
		{
			return "Color not found.";
		}
		
		//If a color is found, then return the name of the color.
		System.out.println("Color found: " + name);
		return name;
	}

}
