
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
	private static final int N_COLORS = 15;
	private ColorName[] colors;		//Database of colors
			
		
	
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
		for(int i = 0; i < nameDB.getlength(); i++)
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
