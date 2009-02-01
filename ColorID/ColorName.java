/**
 * @(#)ColorName.java
 *
 *
 * @author 
 * @version 1.00 2009/1/31
 */


import java.util.Comparator; // For comparitor

public class ColorName  
{
	

	///////////////////////////////////////////////////////////////////////
	//                         Constructors                              //
	///////////////////////////////////////////////////////////////////////


	/* ********************************************************************
	 * 
	 * ColorName Constructor #1
	 * Use this when you need the constructor to parse out the values
	 * from the "data base" because they are not pre defined.
	 *
	 * Parameters:
	 *		dbString - a line from the color database
	 *
	 * ********************************************************************/ 
    public ColorName(String dbString)
    {
    	// integers for iterating through the input string
    	int c = 0;
    	int nextC = 0;
    	
    	// Find the name
    	c = getNextNonWhitespace(dbString, c);
    	nextC = getNextWhitespace(dbString, c);
    	System.out.println(dbString.substring(c, nextC));
    	name = dbString.substring(c, nextC);
    	
    	// Remove any underscores from the name, convert to spaces
    	name.replace('_', ' ');
    	
    	// Increase our iterator to the current location
    	c = nextC;
    	
    	// Now, find the Hue value
    	c = getNextNonWhitespace(dbString, c);
    	nextC = getNextWhitespace(dbString, c);
    	hue = Float.parseFloat(dbString.substring(c, nextC));
    	
    	// Again, increase our iterator past the hue
    	c = nextC;
    	
    	// Now, find the Saturation value
    	c = getNextNonWhitespace(dbString, c);
    	nextC = getNextWhitespace(dbString, c);
    	sat = Float.parseFloat(dbString.substring(c, nextC));
    	
    	// Again, increase our iterator past the hue
    	c = nextC;
    	
    	// Obtain the "Value" from the database
    	c = getNextNonWhitespace(dbString, c);
    	nextC = getNextWhitespace(dbString, c);
    	val = Float.parseFloat(dbString.substring(c, nextC));
		
		// Ignore everything else in the string  	
    }
    
    
    // starting at index i, return the location of the next
    // non-whitespace character
    private int	getNextNonWhitespace(String s, int i)
    {
    	while( i < s.length() && (s.charAt(i) == ' ' || s.charAt(i) == '\t') )
    	{
    		// Continue searching until not a whitespace char
    		i++;
    	}
    	
    	return i;    	
    }
    
    
    // Starting at index i, return the next location in string s that 
    // is a space character
    private int getNextWhitespace(String s, int i)
    {
    	while( i < s.length() && ! (s.charAt(i) == ' ' || s.charAt(i) == '\t'))
    	{
    		// Continue searching until not a whitespace char
    		i++;
    	}
    	
    	return i;
    }
   
    
    
    /* ********************************************************************
	 * 
	 * ColorName Constructor #2
	 * Use this constructor when you already know the hue/name pair
	 * and don't need it parsed out from a database line.
	 *
	 * Parameters:
	 *		dbString - a line from the color database
	 *
	 * ********************************************************************/ 
    public ColorName(float p_hue, float p_sat, float p_val, String p_name)
    {
    	
    	hue = p_hue;
    	sat = p_sat;
    	val = p_val;
    	name = p_name;    	
    }

    
    
    ///////////////////////////////////////////////////////////////////////
	//                       Member Variables                            //
	///////////////////////////////////////////////////////////////////////
    
    /* ********************************************************************
     * Structure variables.
     * I'm leaving these as public because there is no need for the
     * processing overhead of using accessors and the like.  This is
     * more just a struct anyway.
     * ********************************************************************/
    public float	hue;			// The HUE value in the HSV color space
    public float	sat;			// The SATURATION value in the HSV color space
    public float	val;			// The VALUE value in the HSV color space
    public String	name;			// The name that corresponds to the HSV color
  
}




class ColorNameCompare implements Comparator 
{
	
	/* ********************************************************************
	 * 
	 * Compare
	 *
	 * Compares two color names.  This is used for sorting color names
	 * by hue followed by saturation
	 *
	 * PARAMETERS:
	 *		o1 - The first color name
	 *		o2 - The second color name
	 *
	 * RETURNS:
	 *		1 if o1 should come after o2
	 *		-1 if o1 should come before o2
	 *		0 if they are equivilent
	 *
	 * ********************************************************************/
   
    public int compare( ColorName o1, ColorName o2 )
    {
    	
    	// First try to sort by the hue values
    	if( o1.hue > o2.hue )
    		return 1;
    		
    	else if( o1.hue < o2.hue )
    		return -1;
    		
    	else
    	{
    		// If they have the same hues, sort them by
    		// saturation values
    		if( o1.sat > o2.sat )
    			return 1;
    			
    		if( o1.sat < o2.sat )
    			return -1;
    			
    		else
    			return 0;
    	}  	
    }
    
    
    /* ********************************************************************
	 * 
	 * Equal
	 *
	 * Compares two color names.  This is used for sorting color names
	 * by hue.
	 *
	 * PARAMETERS:
	 *		o1 - The first color name
	 *		o2 - The second color name
	 *
	 * RETURNS:
	 *		true if the hues are equal, false otherwise.
	 *
	 * ********************************************************************/
   
//    public boolean equal( ColorName o1, ColorName o2 )
//    {
//    	if( o1.hue == o2.hue )
//    		return true;
//    		
//    	return false;    	
//    }

}