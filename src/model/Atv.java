package model;

import java.util.Map;

/**
 * Subclass of the Vehicle class for the ATV vehicle.
 * 
 * @author Jessica Medrzycki
 * @version October 20, 2017
 */
public class Atv extends AbstractVehicle implements Vehicle {

    /** The given time an ATV stays dead.*/
    private static final int DEATH_TIME = 20;
    
    /** The Vehicle's name. */
    private static final String NAME = "ATV";
    
    /**
     * Creates an ATV of type Vehicle.
     * 
     * @param theX      the x coordinate.
     * @param theY      the y coordinate. 
     * @param theDirection  the Direction.
     */
    public Atv(final int theX, final int theY, final Direction theDirection) {
        super(theX,  theY, theDirection, DEATH_TIME, NAME);
                   
    }
    
    /** 
     * The method that chooses the direction for the atv.
     * a wall or the direction it just came from. 
     * @return the direction the ATV would like to move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        final Direction atvPreviousDirection = this.getDirection().reverse();
        Direction atvDirection = Direction.random();
        
        while (atvDirection == atvPreviousDirection 
                        || !canPass(theNeighbors.get(atvDirection), null)) {
            atvDirection = Direction.random();
        }
        
        return atvDirection;
    }

    /**
     * ATVs can pass on anything except walls.
     * 
     * @param theTerrain    the terrain under an ATV.
     * @param theLight      the light under an ATV.
     * @return returns if the ATV can pass over a terrain or light.
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        return theTerrain != Terrain.WALL;
    }
}
