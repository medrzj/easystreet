/*
 * TCSS 305 - Easy Street
 */
package model;

import java.util.Map;

/**
 * Subclass for creating a bicycle of type Vehicle.
 * 
 * @author Jessica Medrzycki
 * @version October 25, 2017
 */
public class Bicycle extends AbstractVehicle implements Vehicle {
    
    /** The given time a bicycle stays dead. */
    private static final int DEATH_TIME = 30;
    
    /** The Vehicle's name. */
    private static final String NAME = "Bicycle";
    
    /**
     * Creates a bicycle of type vehicle. 
     * @param theX      the x coordinate.
     * @param theY      the y coordinate. 
     * @param theDirection  the direction. 
     */ 
    public Bicycle(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME, NAME);
    }

    /**
     * Helper method that decides if the bike can turn.
     * @param theNeighbors  the neighboring terrain.
     * @param theDir        the direction to check
     * @return  true if the bike can turn, false if it cannot.
     */
    private boolean shouldBikeTurn(final Map<Direction, Terrain> theNeighbors, 
                                   final Direction theDir) {
        return theNeighbors.get(theDir) == Terrain.STREET 
                        || theNeighbors.get(theDir) == Terrain.CROSSWALK
                        || theNeighbors.get(theDir) == Terrain.LIGHT;
    }

    /**
     * Decides which direction the bike will go. 
     * @return  the Direction the bike will take.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction bikeDirection = this.getDirection();
        final Direction bikePrevDirection = this.getDirection().reverse();
        
        if (theNeighbors.get(bikeDirection) == Terrain.TRAIL) {
            bikeDirection = this.getDirection();
        } else if (theNeighbors.get(bikeDirection.left()) == Terrain.TRAIL) {
            bikeDirection = this.getDirection().left();
        } else if (theNeighbors.get(bikeDirection.right()) == Terrain.TRAIL) {
            bikeDirection = this.getDirection().right();
        } else if (theNeighbors.get(bikeDirection) == Terrain.STREET 
                        || theNeighbors.get(bikeDirection) == Terrain.CROSSWALK
                        || theNeighbors.get(bikeDirection) == Terrain.LIGHT) {
            bikeDirection = this.getDirection();
        } else if (shouldBikeTurn(theNeighbors, this.getDirection().left())) {
            bikeDirection = this.getDirection().left();
        } else if (shouldBikeTurn(theNeighbors, this.getDirection().right())) {
            bikeDirection = this.getDirection().right();
        } else {
            bikeDirection = bikePrevDirection;
        }

        return bikeDirection;
    }
    
    /**
     * Method that decides if the bicycle can pass.
     * returns true if it can, false if it cannot.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean passable = true;
        
        if ((theTerrain == Terrain.LIGHT && theLight != Light.GREEN) 
                        || (theTerrain == Terrain.CROSSWALK && theLight != Light.GREEN)) {
            passable = false;
        } 

        return passable;
    }


}
