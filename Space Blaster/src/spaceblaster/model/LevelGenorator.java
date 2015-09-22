package spaceblaster.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import spaceblaster.model.entities.*;

public class LevelGenorator {
	
	private int level = 1;
	private Random randgen = new Random();
	private int timeOutCount = 0;
	ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
	private Map<BasicEnemy, Integer> numEnemiesSpawned = new HashMap<BasicEnemy, Integer>();
	private int width;
	
	
	/**
	 * Creates a new LevelGenorator, used for keeping track of enemy spawning and level completion.
	 * @param w The width of the map or playing area, helps to spawn enemies.
	 */ 
	public LevelGenorator(int w) {
		width = w;
	}
	
	/**
	 * Adds a new type of enemy to the game, as long as it is a type of BasicEnemy (must be inherited
	 * from that class) and must have a functioning getCopy(int x, int y) method to reproduce this enemy.
	 * @param e
	 */
	public void addNewEnemy(BasicEnemy e){
		enemies.add(e);
		numEnemiesSpawned.put(e, 0);
	}
	
	/**
	 * Gets a list of all of the new entities generated each tick, or 1/100 seconds.
	 * @return ArrayList<Entity> An ArrayList containing all of the new entities created.
	 */
	public ArrayList<Entity> getNewEntities(){
		if(timeOutCount>0){
			timeOutCount--;
			return null;
		}
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		for(BasicEnemy e: enemies){
			if(level>=e.getMinLevel() && numEnemiesSpawned.get(e)<e.getAmountForLevel()){
				if(0 == randgen.nextInt()%(int)e.getFrequency()){
					entities.add(e.getCopy((int)(Math.random()*width), 0));
					numEnemiesSpawned.put(e, numEnemiesSpawned.get(e)+1);
				}
			}
		
		}
		
		return entities;
	}
	
	
	/**
	 * A utility method for checking if the current level completion parameters are all met. Should
	 * be called from the model to update to the next level.
	 * @return boolean Returns true if the level is complete, false if otherwise.
	 */
	public boolean isCurrentLevelDone(){
		if(enemies.isEmpty()){
			return false;
		}
		for(BasicEnemy e: numEnemiesSpawned.keySet()){
			if(numEnemiesSpawned.get(e).intValue()<e.getAmountForLevel()){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Updates the level, giving a certain timeout until the next level starts generating enemies.
	 * @param timeOut The amount of time in seconds to wait before resuming enemy generation.
	 */
	public void updateLevel(double timeOut){
		timeOutCount = (int)(timeOut*100);
		level++;
		for(BasicEnemy e: numEnemiesSpawned.keySet()){
			numEnemiesSpawned.put(e, 0);
			if(level<15){
				e.setAmountForLevel(e.getAmountForLevel()+(int)(Math.random()*15)+5);
			}
		}
	}
	
	/**
	 * Gets the current level
	 * @return int The current level, usually starts at 1 and is incrimented.
	 */
	public int getLevel(){
		return level;
	}

}
