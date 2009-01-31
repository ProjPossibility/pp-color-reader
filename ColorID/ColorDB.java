
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
	
	private ColorName[] nameDB;
	private static final int MAX_N_COLORS = 15;
	
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
    		fin = new BufferedReader(new FileReader("colors.txt"));
    		
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
			}
			
			// Update the number of colors in the database
			size = i;
    	}
    	catch (Exception e)
    	{
    		System.err.println("Error: " + e.getMessage());
    		
    		// Failed, no size
    		size = 0;
    	}
    	finally
    	{
    		// Close up the file
    		if( fin != null)
				fin.close();
    	}

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
	 * HueToName
	 *
	 * Finds the color with the closest matching hue to the
	 * supplied value
	 *
	 * ARGUMENTS
	 *	hue - The hue whose name we are trying to find
	 *
	 * RETURNS
	 *	A string representing the hue's name
	 * ************************************************************/	
	public String ColorToName(Color theColor)
	{
		// TODO:  Binary search through the array (or something)
		// for the correct hue
		float[] HSB = new float[3];
		theColor.RGBtoHSB(theColor.getRed(), theColor.getGreen(), theColor.getBlue(), HSB);
		String name = "";
		for(int i = 0; i < size; i++)
		{
			if(HSB[0] <= nameDB[i].hue || HSB[0] >= nameDB[i].hue)
				name = nameDB[i].name;
		}
		if(name == "")
		{
			return "Color not found.";
		}
		return name;
	}

}
