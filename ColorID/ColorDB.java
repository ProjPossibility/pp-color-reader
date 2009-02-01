
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

import java.util.Arrays; // For array sort


class ColorDB {
	
	
	///////////////////////////////////////////////////////////////////////////
	//                         MEMBER VARIABLES                              //
	///////////////////////////////////////////////////////////////////////////
	
	private static final int MAX_N_COLORS = 100;
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
			Arrays.sort(nameDB, 0, size, new ColorNameCompare() );
			
			// Debug output to make sure that the colors sorted
			System.out.println("Sorted DB:" );
			for( int counter = 0; counter < size; counter++ )
			{
				System.out.println("   " + nameDB[counter].name + ", Hue: " + nameDB[counter].hue + ", Sat: " + nameDB[counter].sat );
			}
			
			// Close up the file
			fin.close();
			
    	}
    	catch (IOException e) 
    	{
    		// Make the database size 0 since it does not exist
        	size = 0;
    		
             // e.printStackTrace();
             throw new RuntimeException("Could not open file");
             
             
    	}
    	
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
		System.out.println("Testing saturation: " + HSB[1]);
		System.out.println("Testing BRIGHTNESS: " + HSB[2]);
		
		//If the saturation or brightness are too low and hard to 
		if(HSB[1] < 0.25 || HSB[2] < 0.25)
		{
			return "Brightness or saturation too low.";
		}
		
		int index = searchHue(HSB);
		
		System.out.println(index);
		System.out.println(HSB[1]);
		
		if(nameDB[index].sat > HSB[1])
			name = compareSatL(index, HSB[1]);	
		else if(nameDB[index].sat < HSB[1])
			name = compareSatR(index, HSB[1]);
		else if(nameDB[index].sat == HSB[1])
			name = nameDB[index].name;

		//If the hue is not within the range of any database colors, then return Color not found.
		if(name == "")
		{
			return "Color not found.";
		}
		
		//If a color is found, then return the name of the color.
		System.out.println("Color found: " + name);
		return name;
	}

/*	private int searchHue(float[] HSB, float lower, float upper, int min, int max) 
	{
		
		if (max < min)
           return -1; // not found
		int mid = min + (max - min) / 2;  // Note: not (low + high) / 2 !!
		
		if(mid == 0)
		{
			lower = 0;
			upper = nameDB[mid].hue+(nameDB[mid+1].hue+nameDB[mid].hue)/2;
		}else if(mid == size-1){
			lower = nameDB[mid].hue-(nameDB[mid-1].hue+nameDB[mid-1].hue)/2;
			upper = 1;
		}else{
			lower = nameDB[mid].hue-(nameDB[mid-1].hue-nameDB[mid-1].hue)/2;
			upper = nameDB[mid].hue+(nameDB[mid+1].hue-nameDB[mid].hue)/2;
		}
		
		if (nameDB[mid].hue > upper)
           return searchHue(HSB, lower, upper, min, mid-1);
		else if (nameDB[mid].hue < lower)
			return searchHue(HSB, lower, upper, mid+1, max);
		else
			return mid; // found
   }*/


	// Find hue index in the data base using binary search.
	private int searchHue(float[] HSB)
	{
		int index = 0;
		int min = 0;
		int max = size;
		int mid;
		float lower;
		float upper;
		//While there are more than one elements left, keep searching
		while (min != max)
		{
			//Choose the middle of the array to check which side to check for the cloest value
			mid = (max + min)/2;
			
			//Special case, if the middle is
			if(mid == 0){
				lower = 0;
				upper = nameDB[mid].hue+(nameDB[mid+1].hue-nameDB[mid].hue)/2;
			}else if(mid == size-1){
				lower = nameDB[mid].hue-(nameDB[mid].hue-nameDB[mid-1].hue)/2;
				upper = 1;
			}else{
				lower = nameDB[mid].hue-(nameDB[mid].hue-nameDB[mid-1].hue)/2;
				upper = nameDB[mid].hue+(nameDB[mid+1].hue-nameDB[mid].hue)/2;
			}
			
			if(lower < HSB[0] && HSB[0] < upper)
			{
				index = mid;
				break;
			}else if(HSB[0] < nameDB[mid].hue){
				max = mid;
			}else if(HSB[0] > nameDB[mid].hue){
				min = mid;
			}
			
			if( min == max )
			{
				index = min;
			}
		}
		return index;
	}
	
	private String compareSatL(int index, float s)
	{
		System.out.println("Index is : " + index);
		while(index != 0 && nameDB[index].hue == nameDB[index-1].hue)
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
		while(index != size-1 && nameDB[index].hue == nameDB[index+1].hue)
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
