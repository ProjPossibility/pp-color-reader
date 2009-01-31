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
    
    private Color averagePixel(Picture pic, int m_x, int m_y)
    {
    	return new Color(000);
    }
    
    private String colorToName(Color theColor)
    {
    	return "";
    }
    
    
}