package spaceblaster.model.entities;

import java.awt.Image;

/**
 * Similar to a BasicEnemy, but is faster, does slightly more damage, and can be moving
 * to the left or right. Rebounds in other direction if it is too close to the edge of the screen.
 * @author kaspergammeltoft
 *
 */
public class F250Bullet extends BasicEnemy {
	
	
	protected int xMaxVal;
	protected int xMinVal;

	/**
	 * Creats a new ship
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param img The image used to represent this ship.
	 */
	public F250Bullet(int x, int y, Image img, int xMin, int xMax) {
		super(x, y, img);
		this.health = 20;
		this.setSpeed(1.0);
		boolean neg = Math.random()<.5;
		this.setXVelocity(Math.random());
		if(neg){
			this.setXVelocity(this.getXVelocity()*-1);
		}
		this.setShootTime(200);
		this.setMinLevel(1);
		this.setFrequency(300);
		xMaxVal = xMax;
		xMinVal = xMin;
	}

	/**
	 * Creates a new ship with the specified inputs.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param img The iamge used for this ship
	 * @param hp The health of this ship 
	 * @param spd The speed of this ship
	 * @param hv The horizontal velocity of this ship.
	 */
	public F250Bullet(int x, int y, Image img, int hp, double spd, double hv, int xMax, int xMin) {
		super(x, y, img, hp, spd, hv);
		this.setMinLevel(3);
		this.setFrequency(300);
		xMaxVal = xMax;
		xMinVal = xMin;
		
	}
	
	
	@Override
	/**
	 * Gets a copy of this enemy, using the new x and y positions.
	 */
	public F250Bullet getCopy(int x, int y){
		F250Bullet copy = new F250Bullet(x, y, this.getImage(), xMinVal, xMaxVal);
		copy.setLaser(this.laser);
		copy.setMinLevel(this.getMinLevel());
		copy.setFrequency(this.getFrequency());
		return copy;
	}
	
	/**
	 * Sets the minimum x position where this enemy would turn around and head to the right.
	 * @param xmin
	 */
	public void setXMin(int xmin){
		xMinVal = xmin;
	}
	
	/**
	 * Sets the maximum x position where this enemy would turn and head to the left.
	 * @param xmax
	 */
	public void setXMax(int xmax){
		xMaxVal = xmax;
	}
	
	@Override
	/**
	 * Updates the enemy, checks the x maximum and minimum values with the current x coordinate.
	 */
	public void update(){
		super.update();
		if(this.getX()>xMaxVal){
			this.setXVelocity(-Math.abs(this.getXVelocity()));
		} else if (this.getX()<xMinVal){
			this.setXVelocity(Math.abs(this.getXVelocity()));
		}
	}

}
