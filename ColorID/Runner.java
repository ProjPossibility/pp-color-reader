/**
 * @(#)Runner.java
 *
 *
 * @author 
 * @version 1.00 2009/1/31
 */
import java.awt.Color;

public class Runner 
{

    public Runner() 
    {
    	
    }
    
    public static void main(String[] args) 
    {
        /*Picture pic = new Picture("banana_ripeningchart.jpg");
        pic.show();
        Color x = pic.get(4,5);
        System.out.println(x);*/
        
        ColorFinder test = new ColorFinder("colors.txt");
        test.GUI();
    }
    
    
}