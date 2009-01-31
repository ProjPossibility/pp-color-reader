/**
 * @(#)ColorName.java
 *
 *
 * @author 
 * @version 1.00 2009/1/31
 */


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
    	System.out.println(dbString.substring(c, nextC - 1));
    	name = dbString.substring(c, nextC - 1);
    	
    	// Remove any underscores from the name, convert to spaces
    	name.replace('_', ' ');
    	
    	// Increase our iterator to the current location
    	c = nextC;
    	
    	// Now, find the Hue value
    	c = getNextNonWhitespace(dbString, c);
    	nextC = getNextWhitespace(dbString, c);
    	System.out.println(dbString.substring(c, nextC - 1));
    	hue = Float.parseFloat(dbString.substring(c, nextC - 1));
    	
    	// Again, increase our iterator past the hue
    	c = nextC;
    	
    	// Now, find the Saturation value
    	c = getNextNonWhitespace(dbString, c);
    	nextC = getNextWhitespace(dbString, c);
    	System.out.println(dbString.substring(c, nextC - 1));
    	sat = Float.parseFloat(dbString.substring(c, nextC - 1));
    	
    	// Again, increase our iterator past the hue
    	c = nextC;
    	
    	// Obtain the "Value" from the database
    	c = getNextNonWhitespace(dbString, c);
    	nextC = getNextWhitespace(dbString, c);
    	System.out.println(dbString.substring(c, nextC - 1));
    	val = Float.parseFloat(dbString.substring(c, nextC - 1));
		
		// Ignore everything else in the string  	
    }
    
    
    // starting at index i, return the location of the next
    // non-whitespace character
    private int	getNextNonWhitespace(String s, int i)
    {
    	while( s.charAt(i) == ' ' && i < s.length() )
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
    	while( s.charAt(i) != ' ' && i < s.length() )
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