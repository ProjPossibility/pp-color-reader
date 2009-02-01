
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;


/* ******************************************************************************
 * ColorDB Version 1.0															*
 *																				*
 * Stores the database of colors from the file.									*
 *																				*
 * Also use for converting a color's RGB values to HSB values					*
 * Then using the HSB values and comparing to the database						*
 * Find the name of a color that have the closest values to 					*
 * the color in question.														*
 *																				*
 *																				*
 * ******************************************************************************/
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
	
	//An array use to hold the database of colors
	private static final int MAX_N_COLORS = 100;
	private ColorName[] nameDB = new ColorName[MAX_N_COLORS];
	
	
	// The actual number of elements in the nameDB array
	private int size;
			
		
	
	///////////////////////////////////////////////////////////////////////////
	//                            CONSTRUCTOR                                //
	///////////////////////////////////////////////////////////////////////////
	
	
	/* **************************************************************
	 * CONSTRUCTOR                                                  *
	 *                                                              *
	 * Sorts the database by hue values, making it faster to search *
	 * for the name of a specific hue.                              *
	 *                                                              *
	 * ARGUMENTS:                                                   *
	 *	filename - The name of the database file.					*
	 * **************************************************************/
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
				// Make sure that the line is good by checking the first
				// character.
				// If the first character is a # or a newline, the line
				// is completely ignored
				if( line.length() > 0 && line.charAt(0) != '#' )
				{
					// Create the ColorName value at the correct index in the array
					nameDB[i] = new ColorName(line);
					
					// Increment the location in the database
					i++;
				}
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
	
	/* *****************************************************************
	 * ColorToName                                                     *
	 *                                                                 *
	 * Convert the RGB parameters of the theColor to HSB parameters    *
	 * Then calls the searchHue function along with the compareSatL    *
	 * and the compareSatR functions to find the name of the color     *
	 *                                                                 *
	 * ARGUMENTS                                                       *
	 *	theColor - The color whose name we are trying to find          *
	 *                                                                 *
	 * RETURNS                                                         *
	 *	A string representing the hue's name                           *
	 * *****************************************************************/	
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
		
		//If the saturation or brightness are too low and make it difficult to find the color
		if(HSB[1] < 0.1 || HSB[2] < 0.1)
		{
			return "Brightness or saturation too low.";
		}
		
		//Find the index on the array of color with the cloest hue to the color
		int index = searchHue(HSB);
		
		System.out.println(index);
		System.out.println(HSB[1]);
		
		//Using the index with the cloest hue, check its saturation against the saturation of the color
		//If the index have a higher saturation, use the function compareSatL to find the name
		//If the index have a lower saturation, use the function compareSatR to find the name
		//If the index have the same satauration, name of the color is the name of the index.
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

	/************************************************************************
 	* searchHue																*
 	*																		*
 	* Using binary search to find the index in the array of a color with 	*
 	* the cloest hue to the unkown color									*
 	*																		*
 	* ARGUMENTS																*
 	*  HSB - the HSB values of the unknown color							*
 	*																		*
 	* RETURNS																*
 	*  index - the index with the closest hue								*
 	*																		*
 	*************************************************************************/
	private int searchHue(float[] HSB)
	{
		//Set intial index and range of array
		int index = 0;
		int min = 0;
		int max = size;
		
		//Create variables for future use
		int mid;
		float lower;
		float upper;
		
		//While there are more than one elements left, keep searching
		while (min != max)
		{
			//Choose the middle of the array to check which side to check for the cloest value
			mid = (max + min)/2;
			
			//Special cases:
			//If middle is the first index, then set the lower hue range limit to 0 and compute the upper range limit
			//If middle is the last index, then set the upper hue range limit to 1 and compute the lower limit
			//Else just compute the lower limit by finding the midpoint between the hue of the middle and the hue of the index before middle,
			//and compute the upper limit by finding the midpoint between the hue of the middle and the hue of the index after middle.
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
			
			//Check if the hue of the color is within the range of the hue of the middle index.
			//If it is, then set index to middle and break out of the loop
			//If it is not, check the hue of the color against the hue of the middle index
			//If the hue of the color is lower, then cut the array into the first half by setting max to middle
			//If the hue of the color is higher, then cut the array into the second half by setting min to middle
			if(lower <= HSB[0] && HSB[0] <= upper)
			{
				index = mid;
				break;
			}else if(HSB[0] < nameDB[mid].hue){
				max = mid;
			}else if(HSB[0] > nameDB[mid].hue){
				min = mid;
			}
			
			//Check if the min and max are at the same place in the array indicating that is the last element left
			//If they are then change index to that place
			if( min == max )
			{
				index = min;
			}
		}
		
		//Returns the index of the color that have the closest hue to the hue of the color in question
		return index;
	}

	/****************************************************************************************
	 * compareSatL																			*
	 *																						*
	 * Check if the indicies to the left of the indicated index have the same hue			*
	 * If any do, check which one have the cloest hue to the hue of the color				*
	 *																						*
	 * ARGUMENTS																			*
	 *  index - the index with the closest hue to the value s								*
	 *  s - the saturation of the unknown function											*
	 *																						*
	 * RETURNS																				*
	 *  a string of the name of a color that have the closeset								*
	 * hue and saturation to the unknown color												*
	 *																						*
	 ****************************************************************************************/
	private String compareSatL(int index, float s)
	{
		//While the indicated index is not the first index and the index to the left of the indicated index have the same hue
		while(index != 0 && nameDB[index].hue == nameDB[index-1].hue)
		{
			//If the saturation of the indicated index is closer or equal distance to the saturation of the color than the saturation of the index to the left of the indicated index
			//then the name of the color is the name of the indicated index.
			//Else move the index one to the left and repeat the loop.
			if(Math.abs(s - nameDB[index].sat) <= Math.abs(s - nameDB[index-1].sat)){
				return nameDB[index].name;
			}else{
				index--;
			}
		}
		
		//If the indicated index is the first index, the name of the color is the color name in the indicated index
		return nameDB[index].name;
	}
	
	/****************************************************************************************
	 * compareSatR																			*
	 *																						*
	 * Check if the indicies to the right of the indicated index have the same hue			*
	 * If any do, check which one have the cloest saturation to the saturation of the color *
	 *																						*
	 * ARGUMENTS																			*
	 *  index - the index with the closest hue to the value s								*
	 *  s - the saturation of the unknown function											*
 	 *																						*
	 * RETURNS																				*
	 *  a string of the name of a color that have the closeset								*
	 * hue and saturation to the unknown color												*
	 *																						*
	 ****************************************************************************************/
	private String compareSatR(int index, float s)
	{
		//While the indicated index is not the last index and the index to the right of the indicated index have the same hue
		while(index != size-1 && nameDB[index].hue == nameDB[index+1].hue)
		{
			//If the saturation of the indicated index is closer or equal distance to the saturation of the color than the saturation of the index to the right of the indicated index
			//then the name of the color is the name of the indicated index.
			//Else move the index one to the right and repeat the loop.
			if(Math.abs(s - nameDB[index].sat) <= Math.abs(s - nameDB[index+1].sat)){
				return nameDB[index].name;
			}else{
				index++;
			}
		}
		//If the indicated index is the last index, the name of the color is the color name in the indicated index
		return nameDB[index].name;
	}
}
