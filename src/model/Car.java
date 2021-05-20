/*
 * TCSS 305 - Easy Street
 */

package model;

import java.util.Map;

/**
 * Subclass for creating a car of type Vehicle.
 * 
 * @author Jessica Medrzycki
 * @version October 25, 2017
 */
public class Car extends AbstractVehicle implements Vehicle {
  
    /** The given time a car stays dead. */
    private static final int DEATH_TIME = 10;
    
    /** The Vehicle's name. */
    private static final String NAME = "Car";

    
    /**
     * Creates a Car of type vehicle. 
     * @param theX      the x coordinate.
     * @param theY      the y coordinate. 
     * @param theDirection  the direction. 
     */ 
    public Car(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME, NAME);
    }
    
    /**
     * Helper method to check if car should go turn.
     * @param theNeighbors  the neighboring terrain.
     * @param theDir        the direction to check
     * @return true if car can turn, false if it cannot
     */
    private boolean shouldCarTurn(final Map<Direction, Terrain> theNeighbors, 
                                  final Direction theDir) {
        return theNeighbors.get(theDir) == Terrain.STREET  
                        || theNeighbors.get(theDir) == Terrain.CROSSWALK
                        || theNeighbors.get(theDir) == Terrain.LIGHT;
    }

    /**
     * Chooses the direction for the car to go. 
     * @param theNeighbors  the map with neighboring terrains.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
                  
        final Direction carDirection;
        
        if (theNeighbors.get(this.getDirection()) == Terrain.STREET  
                        || theNeighbors.get(this.getDirection()) == Terrain.CROSSWALK
                        || theNeighbors.get(this.getDirection()) == Terrain.LIGHT) { 
            
            carDirection = this.getDirection();
            
        } else if (shouldCarTurn(theNeighbors, this.getDirection().left())) {
            carDirection = this.getDirection().left();
            
        } else if (shouldCarTurn(theNeighbors, this.getDirection().right())) {
           
            carDirection = this.getDirection().right();

        } else {
            carDirection = this.getDirection().reverse();
        }
        
        return carDirection;
    }
    
    /**
     * Checks if the terrain is unacceptable for a car.
     * @param theTerrain    the terrain.
     * @return true if the car can drive, false if it cannot.
     */
    private boolean isTerrainUnacceptable(final Terrain theTerrain) {
        return theTerrain == Terrain.WALL || theTerrain == Terrain.GRASS 
                        || theTerrain == Terrain.TRAIL;
    }
    
    /**
     * Checks if the car can pass or not.
     * returns  true if the car can pass, false if it cannot.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {

        return !(isTerrainUnacceptable(theTerrain)
                        || (theTerrain == Terrain.LIGHT && theLight == Light.RED)
                        || (theTerrain == Terrain.CROSSWALK && theLight != Light.GREEN));
    }


}
