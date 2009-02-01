/**
 * @(#)Runner.java
 *
 *
 * @Color Reader - Team 1
 * @version 1.00 2009/1/31
 *
 * The basic main class that just creates an instance of the
 * ColorFinder class and calls the GUI function which then
 * handles the rest of the program
 */
import java.awt.Color;

public class Runner 
{

    public Runner() 
    {
    	
    }
    
    //Creates a ColorFinder object then calls its GUI function
    public static void main(String[] args) 
    {
        ColorFinder cf = new ColorFinder();
        cf.GUI();
    }
    
    
}