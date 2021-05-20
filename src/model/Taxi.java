/*
 * TCSS 305 - Easy Street
 */
package model;

import java.util.Map;

/**
 * Subclass for creating a taxi of type Vehicle.
 * 
 * @author Jessica Medrzycki
 * @version October 25, 2017
 */
public class Taxi extends AbstractVehicle implements Vehicle {

    /** The given time a taxi stays dead. */
    private static final int DEATH_TIME = 10;
    
    /** The Vehicle's name. */
    private static final String NAME = "Taxi";
    
    /** The time a taxi is waiting at a crosswalk. */
    private int myCrosswalkCounter;
    
    /**
     * Creates a Taxi of type vehicle. 
     * @param theX      the x coordinate.
     * @param theY      the y coordinate. 
     * @param theDirection  the direction. 
     */ 
    public Taxi(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME, NAME);
    }
    
    /**
     * Decides which direction the taxi will go.
     * @return the direction the taxi will go.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction taxiDirection;
        if ((theNeighbors.get(this.getDirection()) == Terrain.STREET  
                        || theNeighbors.get(this.getDirection()) == Terrain.CROSSWALK
                        || theNeighbors.get(this.getDirection()) == Terrain.LIGHT)
                        && canPass(theNeighbors.get(getDirection()), null)) {
            
            taxiDirection = this.getDirection();

            
        } else if (shouldTaxiTurn(theNeighbors, this.getDirection().left())) {
            taxiDirection = this.getDirection().left();
            
        } else if (shouldTaxiTurn(theNeighbors, this.getDirection().right())) {
           
            taxiDirection = this.getDirection().right();

        } else {
            taxiDirection = this.getDirection().reverse();
        }
        return taxiDirection;
    }
    
    /**
     * Helper method to check if taxi should go turn.
     * @param theNeighbors  the neighboring terrain.
     * @param theDir        the direction it's checking.
     * @return true if taxi can turn, false if it cannot
     */
    private boolean shouldTaxiTurn(final Map<Direction, Terrain> theNeighbors, 
                                   final Direction theDir) {
        return (theNeighbors.get(theDir) == Terrain.STREET  
                        || theNeighbors.get(theDir) == Terrain.CROSSWALK
                        || theNeighbors.get(theDir) == Terrain.LIGHT)
                        && canPass(theNeighbors.get(theDir), null);
    }

    
    /**
     * Decides whether or not the taxi can pass through. 
     * @return true if the taxi can pass, false if it cannot.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean passable = true;
        final int crosswalkMaxTime = 3;
        
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            myCrosswalkCounter++;
            if (myCrosswalkCounter == crosswalkMaxTime) {
                passable = true;
                myCrosswalkCounter = 0;
            } else {
                passable = false;
            }
        }
        if (theTerrain == Terrain.WALL || theTerrain == Terrain.GRASS 
                        || theTerrain == Terrain.TRAIL 
                        || (theTerrain == Terrain.LIGHT && theLight == Light.RED)) {
            passable = false;
        }
        return passable;
    }


}
