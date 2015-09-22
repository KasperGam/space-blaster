package spaceblaster.model.entities;

import java.awt.Image;

public class Player extends Ship {
	
	/**
	 * Defines a series of acceleration possibilities for the player to be actively performing.
	 * The player can be accelerating (positive), decelerating (negative), or be slowed to a 
	 * stop as the player stops pressing the movement keys in a certain direction (zeroing).
	 * @author kaspergammeltoft
	 *
	 */
	public enum Acceleration{
		Positive,
		Negative,
		Zeroing;
	}

	protected Acceleration xAcceleration;
	protected Acceleration yAcceleration;
	protected double maxAcceleration = .3;
	protected double maxSpeed = 3;
	
	protected Laser laser;
	
	
	protected int score;
	protected double money;
	
	/**
	 * Creates a new player with the given parameters. Starts its health at 200 with no velocity.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param img The image used to represent this player on screen.
	 */
	public Player(int x, int y, Image img){
		super(x, y, img, 200, 0.0, 0.0);
		
		
	}
	
	/**
	 * Sets a new laser object for this ship to use.
	 * @param l
	 */
	public void setLaser(Laser l){
		l.setIsFromPlayer(true);
		laser = l;
	}
	
	/**
	 * Gets a new laser depending on what laser is assigned to be the current one for this player.
	 * @param x The x coordinate of the laser
	 * @param y The y coordinate of the laser
	 * @param speed The speed of the laser
	 * @param angle The angle of direction for this laser, see Laser.setSpeed(double spd, double angle) for more information.
	 * @return Laser l Returns the new laser object which is the same as the laser given to this player initially, with
	 * the new specified differences in position and velocity.
	 */
	public Laser getNewLaser(int x, int y, double speed, double angle){
		Laser l = laser.getCopy(x, y, speed, angle);
		
		return l;
	}
	
	/**
	 * Gets the score for the player, or a representation of how well the player
	 * is doing.
	 * @return int The score for the player.
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * Adds the specified amount to the score for the player.
	 * @param scr The amount to be added to the player's score.
	 */
	public void addToScore(int scr){
		score += scr;
	}
	
	/**
	 * Gets the amount of money the player has to spend.
	 * @return double The amount of money.
	 */
	public double getMoney(){
		return money;
	}
	
	
	/**
	 * Adds the specified amount of money to the player's money count.
	 * @param mny The amount of money to add (negative values will be subtracted from the money count).
	 */
	public void addMoney(double mny){
		money +=mny;
	}

	/**
	 * Sets a new x acceleration mode.
	 * @param a An Acceleration, either positive, negative, or zeroing.
	 */
	public void setXAcceleration(Acceleration a){
		xAcceleration = a;
	}
	
	/**
	 * Sets a new y acceleration mode.
	 * @param a An acceleration mode, either positive, negative, or zeroing.
	 */
	public void setYAcceleration(Acceleration a){
		yAcceleration = a;
	}
	
	/**
	 * Returns the acceleration mode of this player in the x direction.
	 * @return
	 */
	public Acceleration getXAcceleration(){
		return xAcceleration;
	}
	
	/**
	 * Returns the acceleration mode of this player in the y direction.
	 * @return
	 */
	public Acceleration getYAcceleration(){
		return yAcceleration;
	}
	
	/**
	 * Sets the max speed (a cap for both vertical and horizontal motion).
	 * @param speed The new max speed of the player, in pixels per 1/100 seconds.
	 */
	public void setMaxSpeed(double speed){
		maxSpeed = speed;
	}
	
	/**
	 * Sets the maximum acceleration (cap for bot vertical and horizontal motion).
	 * @param a The new max acceleration, or change in virtical or horizontal velocity. Set
	 * in pixels per 1/100 seconds.
	 */
	public void setMaxAcceleration(double a){
		maxAcceleration = a;
	}
	
	@Override
	public void update(){
		if(xAcceleration == Acceleration.Positive && xVelocity<maxSpeed){
			xVelocity+=maxAcceleration;
			if(xVelocity>maxSpeed){
				xVelocity = maxSpeed;
			}
		} else if (xAcceleration == Acceleration.Negative && xVelocity>-maxSpeed){
			xVelocity-=maxAcceleration;
			if(xVelocity<-maxSpeed){
				xVelocity = -maxSpeed;
			}
		} else if (xAcceleration== Acceleration.Zeroing) {
			if(xVelocity<0){
				xVelocity+=maxAcceleration;
			} else if (xVelocity>0){
				xVelocity-=maxAcceleration;
			}
			if(Math.abs(xVelocity)<.15){
				xVelocity = 0;
			}
		}
		
		if(yAcceleration == Acceleration.Positive && yVelocity<maxSpeed){
			yVelocity+=maxAcceleration;
			if(yVelocity>maxSpeed){
				yVelocity = maxSpeed;
			}
		} else if (yAcceleration == Acceleration.Negative && yVelocity>-maxSpeed){
			yVelocity-=maxAcceleration;
			if(yVelocity<-maxSpeed){
				yVelocity = -maxSpeed;
			}
		} else if (yAcceleration== Acceleration.Zeroing) {
			if(yVelocity<0){
				yVelocity+=maxAcceleration;
			} else if (yVelocity>0){
				yVelocity-=maxAcceleration;
			}
			
			if(Math.abs(yVelocity)<.15){
				yVelocity = 0;
			}
		}
		
	}

}
