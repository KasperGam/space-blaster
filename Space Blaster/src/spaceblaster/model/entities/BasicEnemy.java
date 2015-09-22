package spaceblaster.model.entities;

import java.awt.Image;


public class BasicEnemy extends Ship {

	protected double range;
	protected Laser laser;
	protected double frequency = 250; // 1 every 2.5 seconds approximately
	protected int minLevel = 1; // the minimum level this enemy can appear in.
	protected int amountForLevel = 20;
	protected boolean fireFlag = false;
	protected int shootTime = 500;
	private int count = 0;
	protected int pointsPerShip = 10;
	

	/**
	 * Creates a new BasicEnemy, with the given parameters and 60 health, .5 y Velocity, and 0 x Velocity and, as by default,
	 * 250 frequency, 20 per level, 500 shoot time, and 10 points per ship.
	 * @param x The x coordinate of the new enemy.
	 * @param y The y coordinate of the new enemy.
	 * @param img The image used to represent this enemy on screen.
	 */
	public BasicEnemy(int x, int y, Image img) {
		super(x, y, img);
		this.setHealth(60);
		this.setSpeed(.5);
		this.setXVelocity(0.0);
		this.range = 0.0;
	}

	/**
	 * Creates a new basic enemy with the given parameters.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param img The image used to represent this enemy on screen.
	 * @param hp The health of the enemy
	 * @param spd The initial vertical speed or velocity of the enemy.
	 * @param hv The initial horizontal speed or velocity of the enemy.
	 */
	public BasicEnemy(int x, int y, Image img, int hp, double spd, double hv) {
		super(x, y, img, hp, spd, hv);
	}
	
	/**
	 * Sets the laser used for this ship.
	 * @param l The Laser used for this ship.
	 */
	public void setLaser(Laser l){
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
	 * Gets how many of this type of enemy to spawn in a level.
	 * @return
	 */
	public int getAmountForLevel(){
		return amountForLevel;
	}
	
	/**
	 * Sets how many ships of this type should be spawned per level. This should also be set
	 * before each level if the value is to change.
	 * @param amount
	 */
	public void setAmountForLevel(int amount){
		amountForLevel = amount;
	}
	
	/**
	 * Gets how many points the player should earn for destroying this type of enemy.
	 * @return
	 */
	public int getPointsPerShip(){
		return pointsPerShip;
	}
	
	/**
	 * Sets how many points the player should earn for destroying this type of enemy.
	 * @param points
	 */
	public void setPointsPerShip(int points){
		pointsPerShip = points;
	}
	
	@Override
	/**
	 * Updates the recharging period for when this enemy can fire.
	 */
	public void update(){
		if(fireFlag==false){
			count++;
			if(count==shootTime){
				fireFlag = true;
			}
		}
	}
	
	/**
	 * Call whenever the method getNewLaser() is called, to signify the initiation of the
	 * recharging period for this enemy.
	 */
	public void justFired(){
		fireFlag = false;
		count = 0;
		
	}
	
	/**
	 * Returns whether this enemy should create a new laser to fire at the player or not.
	 * @return
	 */
	public boolean shouldFire(){
		return fireFlag;
	}
	
	/**
	 * Sets the time between shots or lasers for this enemy. Divide by 100 to get the number
	 * of seconds between firing.
	 * @param t
	 */
	public void setShootTime(int t){
		shootTime = t;
	}
	
	
	/**
	 * Gets a copy of this enemy at a new position.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return BasicEnemy a copy of this enemy, with all of its settings.
	 */
	public BasicEnemy getCopy(int x, int y){
		BasicEnemy copy = new BasicEnemy(x, y, this.getImage(), this.getHealth(), this.getSpeed(), this.getXVelocity());
		copy.setFrequency(this.getFrequency());
		copy.setLaser(laser);
		copy.setMinLevel(this.getMinLevel());
		return copy;
	}
	
	/**
	 * Gets the frequency of this enemy, or how often it spawns. Divide this number by 100 to get
	 * how much time between ships on average.
	 * @return
	 */
	public double getFrequency(){
		return frequency;
	}
	
	/**
	 * Sets the frequency, or how frequent this type of enemy spawns. The spawn rate is given as 1 ship per every frequency/100 seconds.
	 * @param f
	 */
	public void setFrequency(double f){
		frequency = f;
	}
	
	/**
	 * Gets the minimum level that this enemy can spawn at.
	 * @return
	 */
	public int getMinLevel(){
		return minLevel;
	}
	
	/**
	 * Sets the minimum level that this enemy can spawn at.
	 * @param minL
	 */
	public void setMinLevel(int minL){
		minLevel = minL;
	}

}
