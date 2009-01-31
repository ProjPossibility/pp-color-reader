/**
 * @(#)main.java
 *
 *
 * @author 
 * @version 1.00 2009/1/31
 */
 
import java.awt.Color;

public class FindColor {
	
	//Private member variables.
	private Picture pic;
	private int mouse_x;
	private int mouse_y;
	private ColorName[] colors;

    public FindColor(Picture pic_in, int m_x, int m_y) {
    	//Set the member variables
    	pic = pic_in;
    	mouse_x = m_x;
    	mouse_y = m_y;
    	/****************************************
    	 * Initialize GUI						*
    	 *		Create Form						*
    	 *		Mouse Listener					*
    	 *		Loading Picture					*
    	 *		Loop							*
    	 *			-On click call getColor		* 
    	 *			-Load new picture(getImage)	*
    	 ****************************************/
    }
    
    /* Uses the private members pic and the mouseclick coordinates *
     * Based on results of functions called alters the GUI to      *
     * display the color at the coordinates						   */
    private void getColor()
    {
    	//Local variables
    	Color pic_color;
    	String color_name;
    	
    	/*Call average pixel on pic and the x,y coordinates to find
    	 *the average color around the passed pixel*/
    	 pic_color = averagePixel(pic, mouse_x, mouse_y);
    	 
    	/*Convert the color found into the appropriate english name*/
    	color_name = colorToName(pic_color);
    	
    	/*Now that we have found the color we want to output alter
    	 *the GUI to display the color to the user*/
    	
    	return;
    }
    
    private Color averagePixel(Picture pic, int x, int y)
    {
    	int lower = y - 1;
    	int upper = y + 1;
    	int left = x - 1;
    	int right = x + 1;
    	/*Creat local RGB variables to store the	*
    	 *values, using floats for future division	*/
    	float green = 0, blue = 0, red = 0;
    	
    	/*Scan all 9 pixels around (x, y) and	*
    	 *store their RGB values				*/
    	for(int i = lower; i <= upper;i++)
    	{
    		for(int j = left; j <= right; j++)
    		{
    			green += (float)(pic.get(x, y).getGreen());
    			blue += (float)(pic.get(x, y).getBlue());
    			red += (float)(pic.get(x, y).getRed());
    		}
    	}
    	
    	/*Use the resulting RGB values to create* 
    	 *an average color						*/
    	Color average = new Color(red/((right-left)*(upper-lower)),
    								 green/((right-left)*(upper-lower)),
    								 blue/((right-left)*(upper-lower)));
    	
    	/*That is what we want*/
   		return average;
    }
    
    private String colorToName(Color theColor)
    {
    	float HSB[3];
    	String name;
    	RGBtoHSB(theColor.getRed, theColor.getGreen, theColor.getBlue, HSB);
    	for(int i = 0; i < colors.length(); i++)
    	{
    		if(HSB[1] == colors[i].hue)
    		{
    			name = colors[i].name;
    		}
    	}
    	return name;
    }
    

}