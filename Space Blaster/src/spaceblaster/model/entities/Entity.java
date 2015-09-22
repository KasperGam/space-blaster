package spaceblaster.model.entities;

import java.awt.Image;

public class Entity {

	
	protected boolean canMove;
	protected boolean animate;
	protected boolean solid;
	protected boolean visible;
	protected double xCoord;
	protected double yCoord;
	protected double xVelocity;
	protected double yVelocity;
	protected Image image;
	
	
	/**
	 * 
	 * @param alive If the entity is alive(and can move)
	 * @param x The x coordinate for the center of the entity
	 * @param y The y coordinate for the center of the entity
	 * @param img The image representing the entity
	 */
	public Entity(boolean alive, int x, int y, Image img) {
		if(alive)
			canMove = true;
		else 
			canMove = false;
		animate = canMove;
		xCoord = x;
		yCoord = y;
		image = img;
		
	}
	
	
	/**
	 * 
	 * @param alive If the entity is alive(and can move)
	 * @param x The x coordinate for the center of the entity
	 * @param y The y coordinate for the center of the entity
	 * @param img The image representing the entity
	 */
	public Entity(boolean alive, int x, int y) {
		if(alive)
			canMove = true;
		else 
			canMove = false;
		animate = canMove;
		xCoord = x;
		yCoord = y;		
	}
	
	/**
	 * 
	 * @param mobile If the entity can move.
	 * @param alive If the entity is alive and has health.
	 * @param x The x coordinate for the center of the entity
	 * @param y The y coordinate for the center of the entity
	 * @param img The image representing the entity
	 */
	public Entity(boolean mobile, boolean alive, int x, int y, Image img){
		canMove = mobile;
		animate = canMove;
		xCoord = x;
		yCoord = y;
		image = img;
	}
	
	/**
	 * This method is called on all entities that are alive every 10 milliseconds, or every tick.
	 */
	public void update(){
		
	}
	
	/**
	 * The x coordinate in the game.
	 * @return
	 */
	public double getX(){
		return xCoord;
	}
	
	/**
	 * The y coordinate in the game.
	 * @return
	 */
	public double getY(){
		return yCoord;
	}
	
	/**
	 * Gets the width of this entity, as it is represented on screen.
	 * @return
	 */
	public int getWidth(){
		if(image==null)
			return 10;
		return image.getWidth(null);
	}
	
	/**
	 * Gets the height of this entity, as it is represented on screen.
	 * @return
	 */
	public int getHeight(){
		if(image==null)
			return 10;
		return image.getHeight(null);
	}
	
	public void setXVelocity(double xv){
		xVelocity = xv;
	}
	
	public void setYVelocity(double yv){
		yVelocity = yv;
	}
	
	public double getXVelocity(){
		return xVelocity;
	}
	
	public double getYVelocity(){
		return yVelocity;
	}
	
	/**
	 * The image used to represent the entity in game.
	 * @return
	 */
	public Image getImage(){
		return image;
	}
	
	/**
	 * If this entity has health or not, whether it can be killed.
	 * @return
	 */
	public boolean isAlive(){
		return animate;
	}
	
	/**
	 * Whether this entity can move, or be moved.
	 * @return
	 */
	public boolean isMobile(){
		return canMove;
	}
	
	public void setIsMobile(boolean mobile){
		canMove = mobile;
	}
	
	public void setIsAlive(boolean isAlive){
		animate = isAlive;
	}
	
	public void setX(double x){
		xCoord = x;
	}
	
	public void setY(double y){
		yCoord = y;
	}
	
	public void setImage(Image img){
		image = img;
	}

}
