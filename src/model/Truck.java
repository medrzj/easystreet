/*
 * TCSS 305 - Easy Street
 */

package model;

import java.util.Map;

/**
 * Subclass for creating a Truck of type Vehicle.
 * 
 * @author Jessica Medrzycki
 * @version October 25, 2017
 */
public class Truck extends AbstractVehicle implements Vehicle {
    
    /** The given time a truck stays dead (NEVER IS DEAD).*/
    private static final int DEATH_TIME = 0;
    
    /** The Vehicle's name. */
    private static final String NAME = "Truck";

    /**
     * Creates a Truck of type vehicle. 
     * 
     * @param theX      the x coordinate.
     * @param theY      the y coordinate. 
     * @param theDirection  the direction. 
     */ 
    public Truck(final int theX, final int theY, final Direction theDirection) {
        super(theX, theY, theDirection, DEATH_TIME, NAME);
    }
    
    /**
     * Helper method to check if the truck must reverse 
     * (not next to a street or light or crosswalk).
     * @param theNeighbors   the neighboring terrain
     * @return               if the truck must reverse
     */
    private boolean mustTruckReverse(final Map<Direction, Terrain> theNeighbors) {
        final Direction truckDirection = this.getDirection();
        return theNeighbors.get(truckDirection) != Terrain.STREET 
                        && theNeighbors.get(truckDirection.right()) != Terrain.STREET 
                        && theNeighbors.get(truckDirection.left()) != Terrain.STREET 
                        && theNeighbors.get(truckDirection) != Terrain.LIGHT
                        && theNeighbors.get(truckDirection.right()) != Terrain.LIGHT
                        && theNeighbors.get(truckDirection.left()) != Terrain.LIGHT
                        && theNeighbors.get(truckDirection) != Terrain.CROSSWALK
                        && theNeighbors.get(truckDirection.right()) != Terrain.CROSSWALK
                        && theNeighbors.get(truckDirection.left()) != Terrain.CROSSWALK;
    }
    
    /**
     * Chooses the direction of the truck. 
     * @param  theNeighbors    the map of neighboring terrains. 
     * @return the direction the truck will move.
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction truckPrevDirection = this.getDirection().reverse();
        Direction truckDirection = Direction.random();

        while (truckDirection == truckPrevDirection) {
            truckDirection = Direction.random();
            truckPrevDirection = this.getDirection().reverse();
        }
        if (mustTruckReverse(theNeighbors)) {
            truckDirection = truckPrevDirection;
        } else {
            while (theNeighbors.get(truckDirection) != Terrain.STREET 
                            && theNeighbors.get(truckDirection) != Terrain.LIGHT
                            && theNeighbors.get(truckDirection) != Terrain.CROSSWALK
                            || truckDirection == truckPrevDirection) {
                truckDirection = Direction.random();
                truckPrevDirection = this.getDirection().reverse();
            }
            
            
        }
       
        
        return truckDirection;
    }
    
    /**
     * Method that can determine if a truck can pass. 
     * @return  if the Truck can pass
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        
        boolean passable = true;
        
        if (theTerrain == Terrain.WALL 
                        || (theTerrain == Terrain.CROSSWALK && theLight == Light.RED)
                        || theTerrain == Terrain.GRASS || theTerrain == Terrain.TRAIL) {
            passable = false; 
        } 
        
        return passable;
    }


}
