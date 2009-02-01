
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

import java.util.Array; // For array sort


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
			
			// Make sure that the array of names is sorted
			// do this using the basic java array sort
			Array.sort(nameDB, 0, size, new ColorNameCompare() );
			
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
	 * for the name of a specific hue.  Uses the hue comparitor
	 * of colorname
	 *
	 * PARAMETERS
	 *		
	 * ************************************************************/
	public void SortByHue( int min, int max, int pivot )
	{
		
		// Utilizing a comparison function of ColorName, let's
		// use the basic java sorting algorithm
		
	
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
		theColor.RGBtoHSB(theColor.getRed(), theColor.getGreen(), theColor.getBlue(), HSB);
		
		System.out.println("Testing color RGB(" + theColor.getRed() + ", " + theColor.getGreen() + ", " + theColor.getBlue() + ")" );
		
		//Create string to store the name of the color
		String name = "";
		
		// Debug output
		System.out.println("Testing hue: " + HSB[0]);
		
		//If the saturation or brightness are too low and hard to 
		if(HSB[1] < 0.25 || HSB[2] < 0.25)
		{
			return "Brightness or saturation too low.";
		}
		
/*		//Creates the range of the first color in the database
		float lower = 0;
		float upper = nameDB[0].hue+()(nameDB[1]-nameDB[0])/2.0);
		
		//Loop through the array of database of colors
		for(int i = 0; i < size; i++)
		{
			//Compare the hue of the color to see if it is within the range of the hue of the database color
			//And if it is within range, then change the string to the color of the name
			if(HSB[0] <= upper && HSB[0] >= lower)
			{
				if(nameDB[i].hue == nameDB[i+1].hue)
				name = nameDB[i].name;
				break;  //Break out of loop if within range
			}
			
			//If not within range change the lower limit of the range to the previous upper limit range.
			lower = upper;
			
			//Check if the loop is about to 
			if(i == size-1)
			{
				upper = 1;
			}
			else
			{
				upper = nameDB[i+1]+(nameDB[i+2]-nameDB[i+1])/2);
			}
		}*/
		
		// Find hue index in the data base using binary search.
		int index;
		int min = 0;
		int max = size;
		int mid;
		while (true)
		{
			mid = (max + min)/2;
			if (nameDB[mid].hue <= HSB[0] && HSB[0] <= nameDB[mid+1].hue)
			{
				if (HSB[0]-nameDB[mid].hue <= nameDB[mid+1].hue-HSB[0])
				{
					index = mid;
				}
				else
				{
					index = mid+1;
				}
				break;
			}
			else if (HSB[0] < nameDB[mid].hue)
			{
				max = mid;
				continue;
			}
			else if (nameDB[mid+1].hue < HSB[0])
			{
				min = mid;
				continue;
			}
		}
		
		if(nameDB[index].sat > HSB[1]){
			name = compareSatL(index, HSB[1]);	
		}else if(nameDB[index].sat < HSB[1]){
			name = compareSatR(index, HSB[1]);
		}else if(nameDB[index].sat == HSB[1]){
			name = nameDB[index].name;
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

	private String compareSatL(int index, float s)
	{
		while(nameDB[index].hue == nameDB[index-1].hue)
		{
			if(Math.abs(s - nameDB[index].sat) < Math.abs(s - nameDB[index-1].sat)){
				return nameDB[index].name;
			}else{
				index--;
			}
		}
		return nameDB[index].name;
	}
	
	private String compareSatR(int index, float s)
	{
		while(nameDB[index].hue == nameDB[index-1].hue)
		{
			if(Math.abs(s - nameDB[index].sat) < Math.abs(s - nameDB[index+1].sat)){
				return nameDB[index].name;
			}else{
				index++;
			}
		}
		return nameDB[index].name;
	}
}
