package spaceblaster.model.entities;

import java.awt.Image;
import java.net.URL;


public class Laser extends Entity {
	
	
	protected double speed;
	protected double directionAngle;
	protected double damage;
	protected boolean fromPlayer = false;
	protected URL fireSound;

	public Laser(int x, int y, double spd, double ang, double dmg, boolean playerFired, Image img, URL sound) {
		super(true, false, x, y, img);
		speed = spd;
		damage = dmg;
		directionAngle = ang + Math.PI/2;
		fromPlayer = playerFired;
		fireSound = sound;
	}
	
	public double getDamage(){
		return damage;
	}
	
	public URL getFireSound(){
		return fireSound;
	}
	
	public void setFireSound(URL sound){
		fireSound = sound;
	}
	
	/**
	 * Sets whether this laser was fired from the player or not.
	 * @param playerFired true for if this laser originated from the player, false if otherwise.
	 */
	public void setIsFromPlayer(boolean playerFired){
		fromPlayer = playerFired;
	}
	
	/**
	 * Whether this laser is from the player.
	 * @return true if from the player, false if otherwise.
	 */
	public boolean getIsFromPlayer(){
		return fromPlayer;
	}
	
	/**
	 * Sets the speed of the laser, along with its angle of direction.
	 * @param spd The speed, in pixels per 1/100 seconds.
	 * @param angle The angle, with 0 being directly upward, -¹/2 being right,
	 * ¹/2 being left, and ¹ or -¹ being downward. This is offset from the unit circle
	 * due to the fact that many lasers will be either up or down, rather than left or right.
	 */
	public void setSpeed(double spd, double angle){
		speed = spd;
		directionAngle = angle+Math.PI/2;
	}
	
	@Override
	public double getXVelocity(){
		return speed*Math.cos(directionAngle);
	}
	
	@Override
	public double getYVelocity(){
		return -speed*Math.sin(directionAngle);
	}
	
	/**
	 * Returns a new laser object that is a copy of this one, with a new x, y, and velocity.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param speed The speed of this laser
	 * @param ang The direction of the laser, see Laser.setSpeed(double spd, double angle)
	 * @return Laser a new laser object originating from this one.
	 */
	public Laser getCopy(int x, int y, double speed, double ang){
		Laser l = new Laser(x, y, speed, ang, this.getDamage(), this.getIsFromPlayer(), this.getImage(), this.fireSound);
		return l;
	}

}
