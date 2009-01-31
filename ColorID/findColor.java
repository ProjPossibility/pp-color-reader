/**
 * @(#)main.java
 *
 *
 * @author 
 * @version 1.00 2009/1/31
 */


public class findColor {
	
	//Private member variables.
	private Picture pic;
	private int mouse_x;
	private int mouse_y;

    public findColor() {
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
    
    
}