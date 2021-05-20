/*
 * TCSS 305 - Easy Street
 */

package model;

import java.util.Map;


/**
 * Subclass for creating a human of type Vehicle.
 * 
 * @author Jessica Medrzycki
 * @version October 25, 2017
 */
public class Human extends AbstractVehicle implements Vehicle {

    /** The given time a human stays dead. */
    private static final int DEATH_TIME = 50;
    
    /** The Vehicle's name. */
    private static final String NAME = "Human";
    
    /**
     * Creates a human of type vehicle. 
     * @param theX      the x coordinate.
     * @param theY      the y coordinate. 
     * @param theDirection  the direction. 
     */ 
    public Human(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME, NAME);
    } 
    
    /**
     * Helper method that decides if a human can reverse.
     * @param  theNeighbors     the neighboring terrain.
     * @return true if the human can reverse, false if it cannot.
     */
    private boolean canHumanReverse(final Map<Direction, Terrain> theNeighbors) {
       
        return theNeighbors.get(this.getDirection()) != Terrain.GRASS 
                        && theNeighbors.get(this.getDirection().right()) != Terrain.GRASS
                        && theNeighbors.get(this.getDirection().left()) != Terrain.GRASS;
    }

    
    /**
     * Decides which direction the human moves.
     * @param theNeighbors  the neighboring terrain.
     * @return  the direction the human will take.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction humanDirection = this.getDirection();
        final Direction humanPrevDirection = humanDirection.reverse();  
        
        
        if (theNeighbors.get(humanDirection) == Terrain.CROSSWALK) {
            humanDirection = this.getDirection();
        } else if (theNeighbors.get(humanDirection.left()) == Terrain.CROSSWALK) {
            humanDirection = this.getDirection().left();
        } else if (theNeighbors.get(humanDirection.right()) == Terrain.CROSSWALK) {
            humanDirection = this.getDirection().right();
        } else {
            humanDirection = Direction.random();
            if (canHumanReverse(theNeighbors)) {
                humanDirection = humanPrevDirection;
            } else {
                while (theNeighbors.get(humanDirection) != Terrain.GRASS 
                                || humanDirection == this.getDirection().reverse()
                                && canPass(theNeighbors.get(humanDirection), null)) {
                    humanDirection = Direction.random();
                }
            }

        }
        return humanDirection;
    }
    

    /**
     * Decides if the human can pass on a terrain 
     * (acceptable being a crosswalk with red or yellow lights, and grass).
     * returns true if it can pass, false if it cannot.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {

        return (theTerrain == Terrain.CROSSWALK && theLight != Light.GREEN) 
                        || theTerrain == Terrain.GRASS;
    }

}
