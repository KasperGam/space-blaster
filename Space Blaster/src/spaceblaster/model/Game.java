package spaceblaster.model;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import spaceblaster.gui.GameFrame;
import spaceblaster.gui.component.*;

import spaceblaster.model.entities.*;
import spaceblaster.model.entities.Player.Acceleration;

public class Game implements KeyListener, ActionListener, WindowListener {
	
	/**
	 * Describes the state of the game as either MainMenu, Credits, Playing, or Paused. This 
	 * information is then used to determine what threads to run and what graphics to display.
	 * @author kaspergammeltoft
	 *
	 */
	
	/**
	 * Constants defining the images stored in Game.images
	 */
	public static final String PLAY_BUTTON_N = "playButtonImageNormal";
	public static final String PLAY_BUTTON_H = "playButtonImageHighlighted";
	
	public static final String CREDITS_BUTTON_N = "credsButtonImageNormal";
	public static final String CREDITS_BUTTON_H = "credsButtonImageHighlighted";
	
	public static final String INFO_BUTTON_N = "infoButtonImageNormal";
	public static final String INFO_BUTTON_H = "infoButtonImageHighlighted";
	
	public static final String QUIT_BUTTON_N = "quitButtonImageNormal";
	public static final String QUIT_BUTTON_H = "quitButtonImageHighlighted";
	
	public static final String CONTINUE_BUTTON_N = "continueButtonImageNormal";
	public static final String CONTINUE_BUTTON_H = "continueButtonImageHighlighted";
	
	public static final String STORE_BUTTON_N = "storeButtonImageNormal";
	public static final String STORE_BUTTON_H = "storeButtonImageHighlighted";
	
	public static final String BACK_TO_MAIN_BUTTON_N = "backMainButtonImageNormal";
	public static final String BACK_TO_MAIN_BUTTON_H = "backMainButtonImageHighlighted";
	
	public static final String MAIN_BACKGROUND_IMAGE = "mainBackground";
	public static final String PAUSE_BACKGROUND_IMAGE = "pauseBackground";
	
	/**
	 * Entity Images, ships, player, etc.
	 */
	
	public static final String PLAYER_SHIP_IMAGE = "playerImage0";
	public static final String BASIC_ENEMY_IMAGE = "basicEnemy0";
	public static final String F250BULLET_IMAGE = "f250Image0";
	
	public static final String BASIC_LASER_IMAGE = "basicLaser";
	
	
	
	/**
	 * Constants for defigning sounds in the game.
	 */
	
	
	
	public static final String BASIC_LASTER_FIRING_SOUND = "basicLaserFire";

	
	/**
	 * Defines the state of the game. MainMenu is when the menu is displayed and avalible,
	 * Credits is the page displaying the credits for the game, Playing is the active playing
	 * state of the game involving the Playing Thread, Paused is a paused state and Store describes when
	 * the player is at the main game store during a period of paused play. 
	 * @author kaspergammeltoft
	 *
	 */
	public enum StateOfGame{
		MainMenu,
		Credits,
		Playing,
		Store,
		Paused;
		
	}
	
	/**
	 * All of the images for the game are loaded into this map, with strings describing them above. 
	 */
	Map<String, Image> images = new HashMap<String, Image>();
	
	
	/**
	 * All of the sounds for the game are loaded into this map, with strings describing them above.
	 */
	Map<String, URL> sounds = new HashMap<String, URL>();
	
	
	/**
	 * The state of the game.
	 */
	private StateOfGame state;
	
	/**
	 * The amount of points the player has.
	 */
	int points;
	
	/**
	 * A list of all of the entities currently in the game.
	 */
	List<Entity> shipEntities = new CopyOnWriteArrayList<Entity>();
	List<Entity> projectiles = new CopyOnWriteArrayList<Entity>();
	
	LevelGenorator levelGen;
	
	/**
	 * A thread to dispatch in game events and update the game wile playing.
	 */
	PlayingThread gameThread = new PlayingThread();
	
	/**
	 * The graphics or view of the game, specifically an extension of a JFrame
	 */
	GameFrame view;
	
	
	//testing
	Player player;
	
	
	/**
	 * Constructor, initializes and loads.
	 */
	public Game(GameFrame frame) {
		state = StateOfGame.MainMenu;
		view = frame;
		view.addKeyListener(this);
		view.addWindowListener(this);
		loadImages();
		loadSounds();
		loadMainScreen();
		loadPausedScreen();
		//new player object
		player = new Player(200, 200, images.get(PLAYER_SHIP_IMAGE));
		player.setLaser(new Laser(0, 0, 2.0, 0.0, 20, true, images.get(BASIC_LASER_IMAGE), sounds.get(BASIC_LASTER_FIRING_SOUND)));
		levelGen = new LevelGenorator(view.getWidth());
		
		BasicEnemy enemy1 = new BasicEnemy(0, 0, images.get(BASIC_ENEMY_IMAGE));
		enemy1.setLaser(new Laser(0, 0, -2.0, Math.PI, 5, false, images.get(BASIC_LASER_IMAGE), sounds.get(BASIC_LASTER_FIRING_SOUND)));
		
		F250Bullet enemy2 = new F250Bullet(0, 0, images.get(F250BULLET_IMAGE), 40, view.getWidth()-40);
		enemy2.setLaser(new Laser(0, 0, -3.0, Math.PI, 10, false, images.get(BASIC_LASER_IMAGE), sounds.get(BASIC_LASTER_FIRING_SOUND)));
		
		levelGen.addNewEnemy(enemy1);
		levelGen.addNewEnemy(enemy2);
		//testing
		view.addEntity(player);
		view.setPlayer(player);
	}
	
	/**
	 * Loads the images for the game, all of which must be contained in the spaceblaster.resources directory.
	 */
	private void loadImages(){
		String resourceDir = "/spaceblaster/resources/";
		Image player = new ImageIcon(this.getClass().getResource(resourceDir+"playerShip.png")).getImage();
		images.put(PLAYER_SHIP_IMAGE, player);
		
		Image basicEnemy = new ImageIcon(this.getClass().getResource(resourceDir+"basicEnemy.png")).getImage();
		images.put(BASIC_ENEMY_IMAGE, basicEnemy);
		
		Image F250BulletEnemy = new ImageIcon(this.getClass().getResource(resourceDir+"F250Bullet0.png")).getImage();
		images.put(F250BULLET_IMAGE, F250BulletEnemy);
		
		Image basicLaser = new ImageIcon(this.getClass().getResource(resourceDir+"baseLaser.png")).getImage();
		images.put(BASIC_LASER_IMAGE, basicLaser);
		
		// images for the main menu
		Image play0 = new ImageIcon(this.getClass().getResource(resourceDir+"playButton0.png")).getImage();
		Image play1 = new ImageIcon(this.getClass().getResource(resourceDir+"playButton1.png")).getImage();
		images.put(PLAY_BUTTON_N, play0);
		images.put(PLAY_BUTTON_H, play1);
		
		Image creds0 = new ImageIcon(this.getClass().getResource(resourceDir+"creditsButton0.png")).getImage();
		Image creds1 = new ImageIcon(this.getClass().getResource(resourceDir+"creditsButton1.png")).getImage();
		images.put(CREDITS_BUTTON_N, creds0);
		images.put(CREDITS_BUTTON_H, creds1);

		Image info0 = new ImageIcon(this.getClass().getResource(resourceDir+"controlsButton0.png")).getImage();
		Image info1 = new ImageIcon(this.getClass().getResource(resourceDir+"controlsButton1.png")).getImage();
		images.put(INFO_BUTTON_N, info0);
		images.put(INFO_BUTTON_H, info1);
		
		Image quit0 = new ImageIcon(this.getClass().getResource(resourceDir+"quitButton0.png")).getImage();
		Image quit1 = new ImageIcon(this.getClass().getResource(resourceDir+"quitButton1.png")).getImage();
		images.put(QUIT_BUTTON_N, quit0);
		images.put(QUIT_BUTTON_H, quit1);
		
		Image mainBackground = new ImageIcon(this.getClass().getResource(resourceDir+"spaceBlasterMainBackground.png")).getImage();
		images.put(MAIN_BACKGROUND_IMAGE, mainBackground);

		
		
		// images for the paused menu
		Image continue0 = new ImageIcon(this.getClass().getResource(resourceDir+"continueButton0.png")).getImage();
		Image continue1 = new ImageIcon(this.getClass().getResource(resourceDir+"continueButton1.png")).getImage();
		images.put(CONTINUE_BUTTON_N, continue0);
		images.put(CONTINUE_BUTTON_H, continue1);
		
		Image store0 = new ImageIcon(this.getClass().getResource(resourceDir+"storeButton0.png")).getImage();
		Image store1 = new ImageIcon(this.getClass().getResource(resourceDir+"storeButton1.png")).getImage();
		images.put(STORE_BUTTON_N, store0);
		images.put(STORE_BUTTON_H, store1);
		
		Image backMain0 = new ImageIcon(this.getClass().getResource(resourceDir+"backMainButton0.png")).getImage();
		Image backMain1 = new ImageIcon(this.getClass().getResource(resourceDir+"backMainButton1.png")).getImage();
		images.put(BACK_TO_MAIN_BUTTON_N, backMain0);
		images.put(BACK_TO_MAIN_BUTTON_H, backMain1);

		Image pauseBackground = new ImageIcon(this.getClass().getResource(resourceDir+"pausedBackground.png")).getImage();
		images.put(PAUSE_BACKGROUND_IMAGE, pauseBackground);

	}
	
	
	private void loadSounds(){
		String resourceDir = "/spaceblaster/resources/sounds/";

		
		URL basicLaserFire = this.getClass().getResource(resourceDir+"basicLaserFire.wav");
		sounds.put(BASIC_LASTER_FIRING_SOUND, basicLaserFire);
	}
	
	/**
	 * Loads the main screen buttons and background image, and gives this information to the view  to display.
	 */
	private void loadMainScreen(){
		FillButtonSpace spacer1 = new FillButtonSpace(images.get(PLAY_BUTTON_N).getWidth(null), images.get(PLAY_BUTTON_N).getHeight(null));
		view.addButtonToMain(spacer1);
		view.addButtonToMain(new FillButtonSpace(spacer1.getPreferredSize().width, spacer1.getPreferredSize().height));
		ImageButton play = new ImageButton(images.get(PLAY_BUTTON_N), images.get(PLAY_BUTTON_H), "Play", this);
		view.addButtonToMain(play);
		ImageButton credits = new ImageButton(images.get(CREDITS_BUTTON_N), images.get(CREDITS_BUTTON_H), "Credits", this);
		view.addButtonToMain(credits);
		ImageButton info = new ImageButton(images.get(INFO_BUTTON_N), images.get(INFO_BUTTON_H), "Info", this);
		view.addButtonToMain(info);
		ImageButton quit = new ImageButton(images.get(QUIT_BUTTON_N), images.get(QUIT_BUTTON_H), "Quit", this);
		view.addButtonToMain(quit);
		
		view.setMainBackgroundImage(images.get(MAIN_BACKGROUND_IMAGE));
	}
	
	/**
	 * Loads the buttons and background for the paused menu in game.
	 */
	private void loadPausedScreen(){
		FillButtonSpace spacer1 = new FillButtonSpace(images.get(PLAY_BUTTON_N).getWidth(null), images.get(PLAY_BUTTON_N).getHeight(null));
		view.addButtonToPaused(spacer1);
		view.addButtonToPaused(new FillButtonSpace(spacer1.getPreferredSize().width, spacer1.getPreferredSize().height));
		ImageButton continueButton = new ImageButton(images.get(CONTINUE_BUTTON_N), images.get(CONTINUE_BUTTON_H), "Continue", this);
		view.addButtonToPaused(continueButton);
		ImageButton store = new ImageButton(images.get(STORE_BUTTON_N), images.get(STORE_BUTTON_H), "Store", this);
		view.addButtonToPaused(store);
		ImageButton backMain = new ImageButton(images.get(BACK_TO_MAIN_BUTTON_N), images.get(BACK_TO_MAIN_BUTTON_H), "BackMain", this);
		view.addButtonToPaused(backMain);
		
		view.setPausedBackgroundImage(images.get(PAUSE_BACKGROUND_IMAGE));
		
	}
	
	
	/**
	 * Pauses the game.
	 */
	private void pauseGame(){
		gameThread.end();
		gameThread = new PlayingThread();
		state = StateOfGame.Paused;
		view.setStateOfGame(state);
	}
	
	/**
	 * Un-pauses the game.
	 */
	private void unpauseGame(){
		if(gameThread==null){
			gameThread = new PlayingThread();
		}
		gameThread.start();
		state = StateOfGame.Playing;
		view.setStateOfGame(state);
	}
	
	/**
	 * Returns the game to the main screen, erasing all progress made by the player.
	 */
	private void returnToMain(){
		view.clearAllEntities();
		view.repaint();
		gameThread.end();
		gameThread = new PlayingThread();
		state = StateOfGame.MainMenu;
		view.setStateOfGame(state);
		player = new Player(200, 200, images.get(PLAYER_SHIP_IMAGE));
		player.setLaser(new Laser(0, 0, 2.0, 0.0, 20, true, images.get(BASIC_LASER_IMAGE), sounds.get(BASIC_LASTER_FIRING_SOUND)));
	}
	
	/**
	 * Fires from the buttons in game, described in the ImageButton class. Handles these events and decides
	 * what to do when a certain button is pressed. It gathers which button this is by the unique name associated
	 * with it. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ImageButton source = (ImageButton)e.getSource();
		source.setHighlighed(false);
		if(state==StateOfGame.MainMenu){
			if(source.getName().equals("Play")){
				view.setStateOfGame(StateOfGame.Playing);
				view.addEntity(player);
				view.setPlayer(player);
				state = StateOfGame.Playing;
				gameThread.start();
			} else if (source.getName().equals("Credits")){
				
			} else if (source.getName().equals("Info")){
				
			} else if (source.getName().equals("Quit")){
				view.dispose();
				System.exit(0);
			}
		} else if (state==StateOfGame.Paused){
			if(source.getName().equals("Continue")){
				unpauseGame();
			} else if (source.getName().equals("Store")){
				
			} else if (source.getName().equals("BackMain")){
				returnToMain();
			}
		}
	}
	
	
	
	/**
	 * Listens to key events on the view and decides what to do with them (controlling the player 
	 * most often).
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		
		if(state==StateOfGame.Playing){
			//for upward movement
			if(c=='w' || c=='W'){
				player.setYAcceleration(Acceleration.Negative);
			//for down
			} else if (c=='s' || c=='S'){
				player.setYAcceleration(Acceleration.Positive);

			//for left
			} else if (c=='a' || c=='A'){
				player.setXAcceleration(Acceleration.Negative);

			//for right
			} else if (c=='d' || c=='D'){
				player.setXAcceleration(Acceleration.Positive);
				
			//space fires a new laser
			} else if (c==' '){
				Laser l = player.getNewLaser((int)player.getX(), (int)player.getY()-player.getHeight()/2, 2.0, 0.0);
				projectiles.add(l);
				view.addEntity(l);
			}
			//P pauses the game.
			if(c=='p' || c=='P'){
				state = StateOfGame.Paused;
				pauseGame();
				view.setStateOfGame(state);
			}
		
			// un-pauses the game.
		} else if (state==StateOfGame.Paused){
			if(c=='p' || c=='P'){
				state = StateOfGame.Playing;
				unpauseGame();
				view.setStateOfGame(state);
			}
		}
	}


	/**
	 * Listens to key events on the view and decides what to do with them (controlling the player 
	 * most often).
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		char c = e.getKeyChar();
		double yv = player.getYVelocity();
		double xv = player.getXVelocity();
		//stops upward motion
		if((c=='w' || c=='W') && yv<0){
			player.setYAcceleration(Acceleration.Zeroing);
		//stops downard 
		} else if ((c=='s' || c=='S') && yv>0){
			player.setYAcceleration(Acceleration.Zeroing);
		//stops left
		} else if ((c=='a' || c=='A') && xv<0){
			player.setXAcceleration(Acceleration.Zeroing);
		//stops right
		} else if ((c=='d' || c=='D') && xv>0){
			player.setXAcceleration(Acceleration.Zeroing);
		} 
	}



	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		pauseGame();
	}

	@Override
	/**
	 * Pauses the game when the window is minimized.
	 */
	public void windowIconified(WindowEvent arg0) {
		gameThread.end();
		gameThread = new PlayingThread();
		state = StateOfGame.Paused;

	}

	@Override
	/**
	 * Shows the pause screen as the window becomes un-minimized.
	 */
	public void windowOpened(WindowEvent arg0) {
		view.setStateOfGame(state);
	}
	
	/**
	 * Thread that executes the various things needed to play the game, fires new events, etc.
	 * @author kaspergammeltoft
	 *
	 */
	public class PlayingThread extends Thread{
		
		boolean end = false;
		public PlayingThread(){
			super();
		}
		
		public void end(){
			end = true;
		}
		
		public void run(){
			while(true && !end){
				
				Rectangle pBounds = new Rectangle((int)player.getX()-player.getWidth()/2, (int)player.getY()-player.getHeight()/2, player.getWidth(), player.getHeight());
				
				// updates the projectiles
				for(Entity e: projectiles){
					if(e.isMobile()){
						e.setY(e.getY()+e.getYVelocity());
						e.setX(e.getX()+e.getXVelocity());
					}
					Rectangle ebounds = new Rectangle((int)e.getX()-e.getWidth()/2, (int)e.getY()-e.getHeight()/2, e.getWidth(), e.getHeight());
					if(e.getX()+e.getWidth()/2>0 && e.getX()-e.getWidth()/2<view.getWidth() && e.getY()<view.getHeight()-50){
						boolean isPlayer = true;
						
						if(Laser.class.isInstance(e)){ 
							Laser l = (Laser)e;
							isPlayer = l.getIsFromPlayer();
							if(!isPlayer){
								
								if(ebounds.intersects(pBounds)){
									view.removeEntity(e);
									projectiles.remove(e);
									player.setHealth(player.getHealth()-(int)l.getDamage());
								}
							}
						}
						if(isPlayer){
							for(Entity ent: shipEntities){
								Rectangle bounds = new Rectangle((int)ent.getX()-ent.getWidth()/2, (int)ent.getY()-ent.getHeight()/2, ent.getWidth(), ent.getHeight());
								if(ebounds.intersects(bounds)){
									Ship ship = (Ship)ent;
									Laser l = (Laser)e;
									view.removeEntity(e);
									projectiles.remove(e);
									ship.setHealth(ship.getHealth()-(int)l.getDamage());
								}
							}
						}
					} else {
						projectiles.remove(e);
						view.removeEntity(e);
					}
				}
				
				// updates the ships/enemies
				for(Entity e: shipEntities){
					boolean alive = true;
					if( e instanceof Ship) {
						Ship ship = (Ship)e;
						if(ship.getHealth()<=0){
							view.removeEntity(e);
							shipEntities.remove(e);
							player.addToScore(10);
							alive = false;
						}
					}
					if(e instanceof BasicEnemy){
						BasicEnemy enemy = (BasicEnemy)e;
						if(enemy.shouldFire()){
							Laser l = enemy.getNewLaser((int)enemy.getX(), (int)enemy.getY()+enemy.getHeight()/2, 2.0, Math.PI);
							projectiles.add(l);
							view.addEntity(l);
							enemy.justFired();
						}
					}
					if(alive){
						if(e.isMobile()){
							e.setY(e.getY()+e.getYVelocity());
							e.setX(e.getX()+e.getXVelocity());
						}
						Rectangle ebounds = new Rectangle((int)e.getX()-e.getWidth()/2, (int)e.getY()-e.getHeight()/2, e.getWidth(), e.getHeight());
						if(e.getX()+e.getWidth()/2>0 && e.getX()-e.getWidth()/2<view.getWidth() && e.getY()<view.getHeight()-50){
							if(e.isAlive()){
								e.update();
							}
							if(ebounds.intersects(pBounds)){
								view.removeEntity(e);
								shipEntities.remove(e);
								player.setHealth(player.getHealth()-10);
							}
							
						} else {
							shipEntities.remove(e);
							view.removeEntity(e);
						}
					}
				}
				player.update();
				
				if(levelGen.isCurrentLevelDone()){
					levelGen.updateLevel(3.0);
					view.Level++;
				}
				
				ArrayList<Entity> newEntities = levelGen.getNewEntities();
				if(newEntities !=null && !newEntities.isEmpty()){
					for(Entity e: newEntities){
						view.addEntity(e);
						shipEntities.add(e);
					}
				}
				
				// keeps the player in bounds.
				if((player.getX()<player.getWidth()/2) && player.getXVelocity()<0){
					player.setXVelocity(0);
				} else if ((player.getX()>view.getWidth()-player.getWidth()/2) && player.getXVelocity()>0){
					player.setXVelocity(0);
				} else {
					player.setX(player.getX()+player.getXVelocity());
				} 
					
				if((player.getY()<player.getHeight()/2) && player.getYVelocity()<0){
					player.setYVelocity(0);
				} else if ((player.getY()>view.getHeight()-player.getHeight() - 50) && player.getYVelocity()>0){
					player.setYVelocity(0);
				} else {
					player.setY(player.getY()+player.getYVelocity());
				} 
				
				if(player.getHealth()<=0){
					pauseGame();
					returnToMain();
				}
				
				//refresh the screen.
				view.updateGraphics();
				
				
				//Sleeps (10 milliseconds)
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					
				}
			}
		}
	}
	
}
