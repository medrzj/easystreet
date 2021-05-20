/*
 * TCSS 305 - Easy Street
 */
package model;

import java.util.Locale;

/**
 * Gives some default behavior and attributes to vehicle children. 
 * 
 * @author Jessica Medrzycki
 * @version October 25, 2017
 *
 */
public abstract class AbstractVehicle implements Vehicle {
    
    /** the vehicle's x coordinate. */
    private int myX;
    
    /** the vehicle's y coordinate.*/
    private int myY;
    
    /** the vehicle's starting x coordinate. */
    private final int myStartX;
    
    /** the vehicle's starting y coordinate.*/
    private final int myStartY;
    
    /** the counter for the amount of death time.*/
    private int myDeathCounter;
    
    /** the amount of time the vehicle is dead for. */
    private final int myDeathTime;
    
    /** the vehicle's direction. */
    private Direction myDirection;
    
    /** the vehicle's starting direction. */
    private final Direction myStartDirection;
    
    /** the vehicle's alive/dead status. */
    private boolean myAliveStatus = true;
    
    /** The name of the vehicle. */
    private final String myVehicleName;
    
    /**
     * Creates an abstract vehicle with a set x and y coordinates, a direction
     * and a time for how long they are dead. 
     * 
     * @param theX      the x coordinate.
     * @param theY      the y coordinate.
     * @param theDirection  the Direction of the vehicle.
     * @param theDeathTime  the time the vehicle is dead.
     * @param theVehicleName the name of the vehicle.
     */
    protected AbstractVehicle(final int theX, final int theY, final Direction theDirection,
                              final int theDeathTime, final String theVehicleName) {
        myX = theX;
        myY = theY;
        myStartX = theX;
        myStartY = theY;
        myDirection = theDirection;
        myStartDirection = theDirection;
        myDeathTime = theDeathTime;
        myDeathCounter = theDeathTime;
        myVehicleName = theVehicleName;
        
    }
    

    /** 
     * Checks if two vehicles have collided. The vehicle with the 
     * smaller death time is dead and the other is alive. 
     */
    @Override
    public void collide(final Vehicle theOther) {
        if ((isAlive() && theOther.isAlive()) && (myDeathTime > theOther.getDeathTime())) {
            myAliveStatus = false;
            myDeathCounter = 0;
        } else if ((isAlive() && theOther.isAlive()) 
                        && (myDeathTime < theOther.getDeathTime())) {
            myAliveStatus = true;
            myDeathCounter = myDeathTime;
        }
    }

    /** 
     * Returns the death time of the vehicle. 
     */
    @Override
    public int getDeathTime() {
        return myDeathTime;
    }

    /** 
     * Returns the correct file name the GUI will use for a 
     * vehicle object.
     * @return  the string file name for the vehicle image.
     */
    @Override
    public String getImageFileName() {
        final StringBuilder fileName = new StringBuilder();
        fileName.append(getClass().getSimpleName().toLowerCase(Locale.US));
        
        if (isAlive()) {
            fileName.append(".gif");
        
        } else {
            fileName.append("_dead.gif");
        }
        
        return fileName.toString();
    
    }

    /** 
     * Returns the vehicle's direction.
     */
    @Override
    public Direction getDirection() {
        return myDirection;
    }

    /** 
     * Returns the vehicle's x coordinate.
     */
    @Override
    public int getX() {
        return myX;
    }

    /** 
     * Returns the vehicle's y coordinate.
     */
    @Override
    public int getY() {
        return myY;
    }

    /** 
     * Returns the vehicle's alive or dead status.
     */
    @Override
    public boolean isAlive() {
        return myAliveStatus;       
    }

    /**
     * Runs once every animation update to check if the dead vehicle should
     * be alive. If the vehicle has completed their death time, their direciton
     * is made to be random. 
     */
    @Override
    public void poke() {
        if (isAlive()) { 
            myAliveStatus = true;
            myDirection = Direction.random();
        } else if (myDeathCounter == myDeathTime) {
            myAliveStatus = true;
            myDirection = Direction.random();

        } else  {         // else if(myAliveStatus = false) {
            myDeathCounter++;
        }
        
        
    }

    /** 
     * Sets the vehicle to it's original states. 
     */
    @Override
    public void reset() {
        myX = myStartX;
        myY = myStartY;
        myDirection = myStartDirection;
        myAliveStatus = true;
        myDeathCounter = myDeathTime;
    }

    /** 
     * Sets the direction to the given direction.
     */
    @Override
    public void setDirection(final Direction theDir) {
        myDirection = theDir;

    }

    /** 
     * Sets the x-coordinate to the given value.
     */
    @Override
    public void setX(final int theX) {
        myX = theX;
    }

    /** 
     * Sets the y-coordinate to the given value.
     */
    @Override
    public void setY(final int theY) {
        myY = theY;
    }
    
    /**
     * Overrides the toString method to display the Vehicle name 
     * when debugging. 
     */
    @Override 
    public String toString() {
        return myVehicleName;
        
    }

}
