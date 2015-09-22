package spaceblaster.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import spaceblaster.model.Game.StateOfGame;
import spaceblaster.model.entities.Entity;
import spaceblaster.model.entities.Player;

public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	
	
	/**
	 * List of the entities to display on screen.
	 */
	private List<Entity> entitiesToDisplay = new CopyOnWriteArrayList<Entity>();
	
	// the player
	private Player player = null;
	//private ArrayList<GameComponents> otherComponents = new ArrayList<GameComponents>();
	
	/**
	 * JPanels for the main menu and during play, handles graphics.
	 */
	PlayingPanel playView;
	MainPanel main;
	MainPanel paused;
	JPanel mainButtonPanel = new JPanel();
	JPanel pausedButtonPanel = new JPanel();
	
	/**
	 * The state of the game, used to determin what to draw on screen.
	 */
	StateOfGame state = StateOfGame.MainMenu;
	
	public int Level = 1;
			
	/**
	 * Constructor, creates new frame with specific width and height in the center of the screen,
	 * defaults the game mode to MainMenu and initializes all of the graphics and containers.
	 * @param w The width of the frame.
	 * @param h The height of the frame.
	 */
	public GameFrame(int w, int h){
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setResizable(false);
		
		playView = new PlayingPanel(w, h);
		main = new MainPanel(w, h);
		main.setBackground(Color.BLACK);
		main.setLayout(new GridLayout(0, 3));
		JPanel panel1 = new JPanel();
		panel1.setVisible(false);
		main.add(panel1);
		mainButtonPanel.setLayout(new FlowLayout());
		mainButtonPanel.setOpaque(false);
		main.add(mainButtonPanel);
		JPanel panel2 = new JPanel();
		panel2.setVisible(false);
		main.add(panel2);
		main.validate();
		this.add(main);
		this.pack();
		this.setLocation(screenWidth/2-w/2, screenHeight/2-h/2);
		this.setVisible(true);
		setUpPausedPanel(w, h);
		
	}
	
	/**
	 * Creates and initializes the pause panel, or the screen seen when the game is paused.
	 * @param w
	 * @param h
	 */
	private void setUpPausedPanel(int w, int h){
		paused = new MainPanel(w, h);
		paused.setBackground(Color.BLACK);
		paused.setLayout(new GridLayout(0, 3));
		JPanel panel1 = new JPanel();
		panel1.setVisible(false);
		paused.add(panel1);
		pausedButtonPanel.setLayout(new FlowLayout());
		pausedButtonPanel.setOpaque(false);
		paused.add(pausedButtonPanel);
		JPanel panel2 = new JPanel();
		panel2.setVisible(false);
		paused.add(panel2);
		paused.validate();
	}
	
	/**
	 * Removes all entities from the view.
	 */
	public void clearAllEntities(){
		entitiesToDisplay.clear();
	}
	
	
	/**
	 * Sets which entity is the player, and is controlled by the user's input. Must be a Player object
	 * or inherited from Player.
	 * @param p The player.
	 */
	public void setPlayer(Player p){
		player = p;
	}
	
	/**
	 * Adds a new button (should be an ImageButton or a FillButtonSpace) to the main menu
	 * @param c The component, either an ImageButton or a FillButtonSpace
	 */
	public void addButtonToMain(Component c){
		mainButtonPanel.add(c);
		mainButtonPanel.validate();
	}
	
	/**
	 * Adds a new button (should be an ImageButton or a FillButtonSpace) to the paused menu
	 * @param c The component, either an ImageButton or a FillButtonSpace
	 */
	public void addButtonToPaused(Component c){
		pausedButtonPanel.add(c);
		pausedButtonPanel.validate();
	}
	
	/**
	 * Sets the main menu background image.
	 * @param img The image to be displayed on the main menu (center justified).
	 */
	public void setMainBackgroundImage(Image img){
		main.setBackgroundImage(img);
		main.repaint();
	}
	
	/**
	 * Sets the paused menu background image.
	 * @param img The image to be displayed on the paused menu (center justified).
	 */
	public void setPausedBackgroundImage(Image img){
		paused.setBackgroundImage(img);
		paused.repaint();
	}
	
	/**
	 * Sets the state of the game, and makes the necessary changes to the top most container,
	 * often removing one and replacing it with another that handles a different aspect of the game.
	 * @param gameState
	 */
	public void setStateOfGame(StateOfGame gameState){
		
		if(state==StateOfGame.MainMenu){
			if(gameState==StateOfGame.Playing){
				this.remove(main);
				this.add(playView);
				this.validate();
			}
		} else if (state==StateOfGame.Playing){
			if(gameState==StateOfGame.Paused){
				this.add(paused);
				paused.validate();
				paused.repaint();
				this.validate();
			} else if (gameState==StateOfGame.MainMenu){
				this.remove(playView);
				this.add(main);
				main.repaint();
				main.validate();
				this.validate();
			}
		} else if (state==StateOfGame.Paused){
			if(gameState==StateOfGame.Playing){
				this.remove(paused);
				this.validate();
			} else if (gameState==StateOfGame.MainMenu){
				this.remove(paused);
				playView.repaint();
				this.remove(playView);
				this.add(main);
				main.repaint();
				main.validate();
				this.validate();
			}
		}
			
		state = gameState;

	}
	
	
	/**
	 * Adds an entity to be displayed on screen during gameplay.
	 * @param e The entity to be added
	 */
	public void addEntity(Entity e){
		entitiesToDisplay.add(e);
	}
	
	/**
	 * Removes the specific entity so that it will no longer be drawn on screen.
	 * @param e The entity to be removed
	 */
	public void removeEntity(Entity e){
		entitiesToDisplay.remove(e);
	}
	
	/**
	 * Updates the graphics during gameplay (as it should not be necessary to update during
	 * main menu or other areas of static graphics during the game). 
	 */
	public void updateGraphics(){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		playView.repaint();
            }
        });
	}
	
	
	/**
	 * A JPanel to handle the graphics and drawing during the game.
	 * @author kaspergammeltoft
	 *
	 */
	public class PlayingPanel extends JPanel{
		
			/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
			int width;
			int height;
		
			public PlayingPanel(int w, int h){
				super();
				this.setBackground(Color.BLACK);
				width = w;
				height = h;
				
			}
		
		   @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        
		        Graphics2D g2 = (Graphics2D)g;
		        
		        //clears area and paints the background
				g2.clearRect(0, 0, getWidth(), getHeight());
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, getWidth(), getHeight());
				
				//draws entities
				g2.setColor(Color.WHITE);
				for(Entity e: entitiesToDisplay){
					if(e.getImage()!=null){
						Image i = e.getImage();
						g2.drawImage(i, (int)e.getX()-i.getWidth(null)/2, (int)e.getY()-i.getHeight(null)/2, null);
					} else {
						g2.drawRect((int)e.getX()-e.getWidth()/2, (int)e.getY()-e.getHeight()/2, e.getWidth(), e.getHeight());
					}
				}
				
				//draws the bottom bar of the game, with the information pertaining to the player (health, money, etc)
				g2.setColor(Color.LIGHT_GRAY);
				g2.fillRect(0, this.getHeight()-50, this.getWidth(), 50);
				
				//draws health bar
				g2.setColor(Color.BLACK);
				g2.drawRect(getWidth()/2-100, getHeight()-35, 200, 20);
				double hp = player.getHealth();
				double max = player.getMaxHealth();
				double ratio = (double)hp/max;
				Color c = new Color((int)((1-ratio)*255), (int)(ratio*255), 0);
				g2.setColor(c);
				g2.fillRect(getWidth()/2-100+1, getHeight()-34, (int)(199*ratio), 19);
				
				//draws the score in the right corner of the gui
				g2.setColor(Color.BLACK);
				int score = player.getScore();
				g2.drawString("Score: "+Integer.toString(score), getWidth()-60-8*((int)Math.log10(score)), getHeight()-20);
				
				double money = player.getMoney();
				g2.drawString("$"+Double.toString(money), 10, getHeight()-20);
				
				g2.setColor(Color.WHITE);
				g2.drawString("Level "+Integer.toString(Level), getWidth()-60-8*((int)Math.log10(Level)), 20);
		    }

		    //so our panel is the corerct size when pack() is called on Jframe
		    @Override
		    public Dimension getPreferredSize() {
		        return new Dimension(width, height);
		    }
	}
	
	/**
	 * The MainMenu panel
	 * Handles the display for buttons and a background image, used for the Pause menu and Main menu.
	 * @author kaspergammeltoft
	 *
	 */
	public class MainPanel extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int width;
		int height;
		Image backgroundImage;
	
		public MainPanel(int w, int h){
			super();
			width = w;
			height = h;
			
		}
		
		public void setBackgroundImage(Image img){
			backgroundImage = img;
		}
		
		@Override
		public void paintComponent(Graphics g){
			Graphics2D g2 = (Graphics2D)g;
			g2.drawImage(backgroundImage, (this.getWidth()-backgroundImage.getWidth(null))/2, 0, null);
		}

	    @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(width, height);
	    }
}
	

}
