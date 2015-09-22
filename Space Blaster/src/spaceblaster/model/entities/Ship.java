package spaceblaster.model.entities;

import java.awt.Image;

public class Ship extends Entity {

	protected int health;
	protected int maxHealth;
	protected double speed;
	
	public Ship(int x, int y, int hp, double spd, double hv){
		super(true, x, y);
		maxHealth = hp;
		health = hp;
		speed = spd;
		this.setXVelocity(hv);
		this.setYVelocity(Math.sqrt(speed*speed - hv*hv));
	}
	
	public Ship(int x, int y, Image img) {
		super(true, x, y, img);
		maxHealth = 40;
		health = 40;
		speed = .3;
		this.setXVelocity(0);
		this.setYVelocity(.3);
	}
	
	/**
	 * Creates a new ship with the given parameters.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param img The image to represent this ship.
	 * @param hp The max health of the ship.
	 * @param spd The speed of the ship.
	 * @param hv The x velocity of the ship. The y will be calculated using the speed value and this
	 * horizontal component of velocity.
	 */
	public Ship(int x, int y, Image img, int hp, double spd, double hv){
		super(true, x, y, img);
		health = hp;
		maxHealth = hp;
		speed = spd;
		this.setXVelocity(hv);
		this.setYVelocity(Math.sqrt(speed*speed - hv*hv));
	}
	
	/**
	 * The absolute speed of the ship
	 * @return
	 */
	public double getSpeed(){
		return speed;
	}
	
	/**
	 * The health of the ship. If this reaches zero, the ship must be destroyed. 
	 * @return
	 */
	public int getHealth(){
		return health;
	}
	
	/**
	 * The speed of the ship. Will not change the xVelocity, just the y.
	 * @param spd
	 */
	public void setSpeed(double spd){
		speed = spd;
		this.setYVelocity(Math.sqrt(speed*speed - xVelocity*xVelocity));
	}
	
	/**
	 * Sets a new speed for the ship with a given x velocity. The y value is calculated.
	 * @param spd The new absolute speed.
	 * @param xv The horizontal velocity of the ship.
	 */
	public void setSpeed(double spd, double xv){
		speed = spd;
		this.setXVelocity(xv);
		this.setYVelocity(Math.sqrt(speed*speed - xVelocity*xVelocity));
	}
	
	/**
	 * Sets the health of the ship to the specified value. Will not set a higher value than
	 * the max health of the ship.
	 * @param hp
	 */
	public void setHealth(int hp){
		if(hp>maxHealth){
			health = maxHealth;
		} else {
			health = hp;
		}
	}
	
	/**
	 * Sets the limit for the health of this ship. Does not change how much health it currently has.
	 * @param maxHp The new maximum health for this ship.
	 */
	public void setMaxHealth(int maxHp){
		maxHealth = maxHp;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	

}
