
/* ******************************************************************************
 * ColorDB
 *
 * Stores the database of colors from the file.  This can be searched by
 * hue 
 *
 *
 * *****************************************************************************/
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
		FileInputStream fin;
    	
    	// Attempt to open the database file.  If not, fail!!
    	try
    	{
    		FileInputStream fin;
    		fin = new FileInputStream (colors.txt);
    	}
    	catch (Exception e)
    	{
    		System.err.println("Error: " + e.getMessage());
    	}
    	
    	// Loop through all of the lines of the database and 
    	// add them to the actual database in memory
    	
    	// The current index in the nameDB
    	int i = 0;
    	
    	// The string for the current line in the DB file
		String currentLine;
		
		// Get each of the lines from the DB file and add them to
		// the database
		while ( (currentLine = fin.readLine())!=NULL && i < NUMBER_OF_COLORS )
		{
			// Create the ColorName value at the correct index in the array
			colorArray[i] = new ColorName(currentLine);
		}
		
		// Update the number of colors in the database
		size = i;

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
	public String HueToName( float hue )
	{
		// TODO:  Binary search through the array (or something)
		// for the correct hue
		String name = "";
		for(int i = 0; i < size; i++)
		{
			if(hue <= nameDB[i].hue || hue >= nameDB[i].hue)
				name = nameDB[i].name;
		}
		if(name == "")
		{
			return "Color not found.";
		}
		return name;
	}

}
