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
    public ColorName(double p_hue, double p_sat, double p_val, String p_name)
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
    public float	saturation;		// The SATURATION value in the HSV color space
    public float	value;			// The VALUE value in the HSV color space
    public String	name;			// The name that corresponds to the HSV color
  
}