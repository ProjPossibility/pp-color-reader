//Kevin Simon
/*The GUI that displays all information about the game and acquires all input*/
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI implements ActionListener
{
	public static final int IDNUM = 2;
	
	//All these variables are Swing junk; thus, I don't feel like commenting them.
	final int WIDTH = 50;
	final int HEIGHT = 50;
	final int COLS = 11;
    final int ROWS = 11;
    final int TEXTHEIGHT = 150;
    final int MENUHEIGHT = 20;
    final int VERT_COMP = 30;
    final int SAVE_WIDTH = 150;
	final int SAVE_HEIGHT = 100;
    
    private World wor;
    
    private JFrame jf;
    private JButton[][] b;
    
    private JMenu playerMenu;
	private JMenu defaultMenu;
	private JMenu createCharacterMenu;
	private JMenuBar menuBar;
	private JMenuItem createPlayer;
	private JMenuItem saveness;
	private JMenuItem loadness;
	
	private String characterToCreate;
	
	//text box
	private JTextArea playerText;
	private JScrollPane playerTextDisplay;
	
	private JTextArea text;
	private JScrollPane textDisplay;
	
	private JPanel jpTop; 		//top jpanel
    private JPanel jpBottom; 	//bottom jpanel
    private JPanel jpMaster; 	//master jpanel
    
    private JPopupMenu popup;
    
    //save window
    JTextField nameGetter;
    JFrame saveWindow;
    JButton saveEnter;
    JPanel savePanel;
    
    //load window
    JFrame loadWindow;
    JButton loadEnter;
    JPanel loadPanel;

	//GUIs and Worlds both talk to each other... a GUI contains a World, and a World contains a GUI 
	public GUI(World w)
	{
		jf = new JFrame("Dungeons and Dragons");
		jf.setIconImage(new ImageIcon("Images/sword.gif").getImage());
    	this.assembleFrame();
    	//shows the Jframe
    	jf.setVisible(true);
    	wor = w;
	}
	
	
	//build all the GUI stuff
	public void assembleFrame()
	{
		makeJFrame();
		makeJPanels();
		makeMenu();
		makeGrid();
		makeTextArea();
		jpBottom.add(playerTextDisplay);
		jpBottom.add(textDisplay);
		jpBottom.add(menuBar);
		
		//adds all the components together to make the final JFrame
		jpMaster.add(jpTop);
		jpMaster.add(jpBottom);
    	jf.add(jpMaster);
	}
	
    //changes the first menu on the menubar
	public void setMenu(JMenu menu)
	{
		menuBar.remove(playerMenu);
		playerMenu = menu;
		menuBar.add(playerMenu, 0);
		menuBar.updateUI();
	}
	
    //adds some output to the bottom text window
    public void append(String output)
    {
    	text.append(output + '\n');
    }
    
    //sets the text in the window with all the player's stats
    public void setPlayerText(String pText)
    {
    	playerText.setText(pText);
    }
    
    //updates the grid
    public void refreshGrid()
    {
    	for(int r = 0; r < b.length; r++)
    	{
    		for(int c = 0; c <  b[0].length; c++)
    		{
    			Location loc = new Location(c + wor.getXRef(), r + wor.getYRef());
    			if(wor.getOccupants(loc) != null)
    			{
    				b[r][c].setIcon(wor.getTopOccupant(loc).getIcon());
    			}
    			else
    			{
    				b[r][c].setIcon(null);
    			}
    		}
    	}
    }
    
    //Remove all entries from the popup, so that a new location can be selected
    public void clearPopup()
	{
		popup.removeAll();
	}	
    
    //Create a player, save a game, load a game, or do pretty much anything else possible
	public void actionPerformed(ActionEvent e)
	{
    	if("createPlayer".equals(e.getActionCommand()))
    	{
    		new CharacterCreator(wor);
    	}
    	else if("savegame".equals(e.getActionCommand()))
    	{
    		makeSaveWindow();
    	}
    	else if("loadgame".equals(e.getActionCommand()))
    	{
    		makeLoadWindow();
    	}
    	else if("saveEnter".equals(e.getActionCommand()))
    	{
    		Saver saver = new Saver(nameGetter.getText() + ".txt", wor);
    		saver.saveWorld();
    		saveWindow.dispose();
    	}
    	else if("loadEnter".equals(e.getActionCommand()))
    	{
    		Loader loader = new Loader(nameGetter.getText() + ".txt", wor);
    		loader.load();
    		System.out.println("Loaded!");
    		wor.update();
    		loadWindow.dispose();
    	}
    	//The action codes for either a grid button or a popup entry.  Check the 
    	//grid button, and then the popup.
    	else if(!actionPerformedOnGrid(e))	
    	{
			actionPerformedOnPopup(e);
    	}			
	}
	
	//Look at the popup and cause the active player to take an action based on what the human has selected
	public void actionPerformedOnPopup(ActionEvent e)
	{
		//popup.getInvoker() refers to the button that produced the popup
		if(popup.getInvoker() != null)
		{
			//the action command of a button is its location
			Scanner in = new Scanner(((JButton)popup.getInvoker()).getActionCommand());
			int y = in.nextInt();
			int x = in.nextInt();
			Location temp = new Location(x + wor.getXRef(), y + wor.getYRef());	//location of the popup
			int i = 0;
			while(wor.getOccupants(temp) != null && i < wor.getOccupants(temp).size())
			{
				Occupant o = wor.getOccupants(temp).get(i);
				//the action command of a member of a popup is its name
				if(o.getName().equals(e.getActionCommand()))	
				{
					Player p = (Player)wor.getActiveOccupant();
					p.takeAction(o, temp);
					wor.update();
					popup.setVisible(false);
				}
				i ++;
			}
		}
	}
	
	//Check if the ActionEvent codes for a button and, if so, act accordingly
	//If the ActionEvent does not code for a button, return false so the popup will be checked
	public boolean actionPerformedOnGrid(ActionEvent e)
	{
		boolean successful = true;
		String location = e.getActionCommand();
		Scanner buttonMaker = new Scanner(location);
		try
		{
			int r = buttonMaker.nextInt();
			int c = buttonMaker.nextInt();
			checkButton(r, c);
		}
		catch(Exception ofAnnoyingness)
		{
			successful = false;
			//it's not giving a location
			//therefore do nothing
		}
		return successful;
	}
	
	//Given a button, move the active Player to it or display information about it
	public void checkButton(int r, int c)
	{
		Location temp = new Location(c + wor.getXRef(), r + wor.getYRef());

		Player p = (Player)wor.getActiveOccupant();
		if("move".equals(p.getActionToTake()))	//the player is moving
		{
			p.takeAction(null, temp);			//target no Occupant; move to the location
			wor.update();
		}
		else									//the player is doing something else
		{
			//make a popupmenu to show all the occupants at a given location
			//Only show it if the player is not moving... it gets annoying otherwise
			clearPopup();
			populatePopup(temp);									
			popup.show(b[r][c], 0, 0);			//show the popup menu so they can select their target
		}
	}
	
	//Turn a button a color... good for showing ranges of attack/movement
	public void setButtonBackground(Location loc, Color c)
	{
		if(wor.isValid(loc))
		{
			b[loc.getY() - wor.getYRef()][loc.getX() - wor.getXRef()].setBackground(c);
		}	
	}
	
	//makes the window that everything goes in
	public void makeJFrame()
	{
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes program when window is closed
    	jf.setResizable(false); //sets the size of the window
    	jf.setSize(new Dimension(COLS*WIDTH, ROWS*HEIGHT + TEXTHEIGHT + MENUHEIGHT + VERT_COMP));
       	jf.setLocation(0, 0); //sets the position of the window
	}
	
	//makes the top and bottom panels to hold everything
	//makes the master panel to hold the two panels
	public void makeJPanels()
	{
		//panels
    	jpTop = new JPanel(); //top jpanel
    	jpBottom = new JPanel(); //bottom jpanel
    	jpMaster = new JPanel(); //master jpanel
    	//end constructors
		
		jpTop.setLayout(new GridLayout(COLS,ROWS)); //makes is so the JPanels are filled in a grid pattern
    	jpTop.setMinimumSize(new Dimension(COLS*WIDTH, ROWS*HEIGHT));
    	jpTop.setMaximumSize(new Dimension(COLS*WIDTH, ROWS*HEIGHT));
    	jpTop.setPreferredSize(new Dimension(COLS*WIDTH, ROWS*HEIGHT)); //sets the ideal dimensions fo jpTop
    	
    	jpBottom.setLayout(new GridLayout(1,1));
    	jpBottom.setPreferredSize(new Dimension(COLS*WIDTH, TEXTHEIGHT + MENUHEIGHT));
    	jpBottom.setLayout(new BoxLayout(jpBottom, BoxLayout.Y_AXIS));
    	
    	//sets the layout to be top down
    	jpMaster.setLayout(new BoxLayout(jpMaster, BoxLayout.Y_AXIS));
	}
	
	//makes the menu that manages the world
	public void makeMenu()
	{
		//the menu
    	playerMenu = new JMenu();
    	defaultMenu = new JMenu("Generic controls for the game go here");
    	menuBar = new JMenuBar(); //the menu on the bottom
    	createCharacterMenu = new JMenu("Create a Character");
    	createPlayer = new JMenuItem("Player");
    	saveness = new JMenuItem("Save the game");
    	loadness = new JMenuItem("Load a game");
    	
    	/* make the default menu so the user can add players to the grid and load/save games*/
    	createCharacterMenu.add(createPlayer);
    	createPlayer.addActionListener(this);
    	createPlayer.setActionCommand("createPlayer");
    	saveness.addActionListener(this);
    	saveness.setActionCommand("savegame");
    	loadness.addActionListener(this);
    	loadness.setActionCommand("loadgame");
		defaultMenu.add(createCharacterMenu);
		defaultMenu.add(saveness);
		defaultMenu.add(loadness);
		
		//add everything to the menubar on the bottom
		menuBar.add(playerMenu);
		menuBar.add(defaultMenu);
		menuBar.setPreferredSize(new Dimension(COLS*WIDTH, MENUHEIGHT));
		menuBar.setMaximumSize(new Dimension(COLS*WIDTH, MENUHEIGHT));
	}
	
	//Fill the popup menu with the occupants at the location
	public void populatePopup(Location loc)
	{
		ArrayList<Occupant> list = wor.getOccupants(loc);
		if(list != null)
		{
			for(Occupant o : list)
			{
				JMenuItem occupant = new JMenuItem(o.getName());
				occupant.setIcon(o.getIcon());
				occupant.setActionCommand(o.getName());
				occupant.addActionListener(this);
				popup.add(occupant);
			}
		}
	}
	
	//makes the grid that the graphics and occupants interact within
	public void makeGrid()
	{
		//the grid
    	b = new JButton[COLS][ROWS]; //the grid of buttons on top
    	popup = new JPopupMenu();
    	
    	//manages the button grid
    	for(int r = 0; r < ROWS; r++)
    	{
    		for(int c = 0; c <  COLS; c++)
    		{
    			b[r][c] = new JButton();
    			b[r][c].setBackground(Color.white);
    			b[r][c].setActionCommand(String.valueOf(String.valueOf(r) + " "+ String.valueOf(c)));
    			b[r][c].addActionListener(this);
    			jpTop.add(b[r][c]);
    		}
    	}
	}
	
	//makes the area to display what's going in via text
	public void makeTextArea()
	{
		//the text display box
		text = new JTextArea();
    	textDisplay = new JScrollPane(text);
    	
    	
    	playerText = new JTextArea();
    	playerTextDisplay = new JScrollPane(playerText);
    	
    	//manages the text display box
    	textDisplay.setMinimumSize(new Dimension(COLS*WIDTH, TEXTHEIGHT*2/3));
    	textDisplay.setPreferredSize(new Dimension(COLS*WIDTH, TEXTHEIGHT*2/3));
    	textDisplay.setMaximumSize(new Dimension(COLS*WIDTH, TEXTHEIGHT*2/3));
    	text.setEditable(false);
    	text.setLineWrap(true);
    	
    		
    	playerTextDisplay.setMinimumSize(new Dimension(COLS*WIDTH, TEXTHEIGHT/3));
    	playerTextDisplay.setPreferredSize(new Dimension(COLS*WIDTH, TEXTHEIGHT/3));
    	playerTextDisplay.setMaximumSize(new Dimension(COLS*WIDTH, TEXTHEIGHT/3));
    	playerText.setEditable(false);
	}
	
	//make the save window to ask for a save game name
	public void makeSaveWindow()
	{
		saveWindow = new JFrame("Save Game");
		saveWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	saveWindow.setResizable(false);
    	saveWindow.setSize(new Dimension(SAVE_WIDTH, SAVE_HEIGHT));
    	saveWindow.setVisible(true);
    	
    	savePanel = new JPanel();
    	
    	nameGetter = new JTextField("Game Name");
    	
    	saveEnter = new JButton("Enter");
    	saveEnter.addActionListener(this);
    	saveEnter.setActionCommand("saveEnter");
    	
    	saveWindow.add(savePanel);
    	savePanel.add(nameGetter);
    	savePanel.add(saveEnter);
	}
	
	//make the load window to ask for a load game name
	public void makeLoadWindow()
	{
		loadWindow = new JFrame("Load Game");
		loadWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	loadWindow.setResizable(false);
    	loadWindow.setSize(new Dimension(SAVE_WIDTH, SAVE_HEIGHT));
    	loadWindow.setVisible(true);
    	
    	loadPanel = new JPanel();
    	
    	nameGetter = new JTextField("Game Name");
    	
    	loadEnter = new JButton("Enter");
    	loadEnter.addActionListener(this);
    	loadEnter.setActionCommand("loadEnter");
    	
    	loadWindow.add(loadPanel);
    	loadPanel.add(nameGetter);
    	loadPanel.add(loadEnter);
	}
}
