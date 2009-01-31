/**
 * @(#)AveragePixels.java
 *
 *
 * @author 
 * @version 1.00 2009/1/31
 */


public class AveragePixels 
{
	/*Find the average color at (x, y) in pic 	*
	 *by refering to pixels nearby				*/
	public Color AveragePixels(Picture pic, int x, int y) 
    {
    	int lower = y - 1;
    	int upper = y + 1;
    	int left = x - 1;
    	int right = x + 1;
    	/*Creat local RGB variables to store the	*
    	 *values, using floats for future division	*/
    	float green = 0.0, blue = 0.0, red = 0.0;
    	
    	/*Scan all 9 pixels around (x, y) and	*
    	 *store their RGB values				*/
    	for(int i = lower; i <= upper;i++)
    	{
    		for(int j = left; j <= right; j++)
    		{
    			green += (float)(pic.get(x, y).getGreen);
    			blue += (float)(pic.get(x, y).getBlue);
    			red += (float)(pic.get(x, y).getRed);
    		}
    	}
    	
    	/*Use the resulting RGB values to create* 
    	 *an average color						*/
    	Color average(red/9, green/9, blue/9);
    	
    	/*That is what we want*/
   		return average;
    }
}