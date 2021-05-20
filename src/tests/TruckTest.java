/*
 * TCSS 305 - Easy Street
 */
package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;
import org.junit.Before;
import org.junit.Test;



/**
 * Unit tests for the Truck class. 
 * Most of the code has been taken from HumanTest
 * written by Alan Fowler. 
 * 
 * @author Jessica Medrzycki
 * @version October 25, 2017
 */
public class TruckTest {

    /** A Truck object to be used in the tests. */
    private Truck myTruck;
    
 
    
    /** A method to initialize the test fixture before each test. */
    @Before
    public void setUp() {
        myTruck = new Truck(1, 1, Direction.NORTH);
    }

    /**
     * Tests the truck constructor. 
     */
    @Test
    public void testTruckConstructor() {
        myTruck = new Truck(10, 15, Direction.NORTH);
        
        assertEquals("Truck x coordinate not initialized correctly!", 10, myTruck.getX());
        assertEquals("Truck coordinate not initialized correctly!", 15, myTruck.getY());
        assertEquals("Truck direction not initialized correctly!",
                     Direction.NORTH, myTruck.getDirection());
        assertEquals("Truck death time not initialized correctly!", 0, myTruck.getDeathTime());
        assertTrue("Truck isAlive() fails initially!", myTruck.isAlive());
    }
    
    /**
     * Tests the canPass method, code outline taken from
     * HumanTest.
     */
    @Test
    public void testCanPass() {
        final List<Terrain> validTerrain = new ArrayList<>();
      
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
        validTerrain.add(Terrain.LIGHT);
        
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        
        for (final Terrain destinationTerrain : Terrain.values()) {
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {
               
                    assertTrue("Truck should be able to pass on a Street"
                               + ", with light " + currentLightCondition,
                               truck.canPass(destinationTerrain, currentLightCondition));
                } else if (destinationTerrain == Terrain.CROSSWALK) {


                    if (currentLightCondition == Light.RED) {
                        assertFalse("Truck should NOT be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    } else { // light is yellow or red
                        assertTrue("Truck should be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
 
                    assertFalse("Truck should NOT be able to pass " + destinationTerrain
                        + ", with light " + currentLightCondition,
                        truck.canPass(destinationTerrain, currentLightCondition));
                }
            } 
        }
    }
    
    /**
     * Test the mustTruckReverse method for a false value.
     */
    @Test 
    public void testMustTruckReverse() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        neighbors.put(Direction.WEST, Terrain.GRASS);
        neighbors.put(Direction.NORTH, Terrain.GRASS);
        neighbors.put(Direction.EAST, Terrain.GRASS);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        
        
        assertEquals("testMustTruckReverse failed to say it must reverse, "
                        + "when surrounded by grass", 
                  Direction.SOUTH, truck.chooseDirection(neighbors));
        
        
    }


    /**
     * Tests the mustTruckReverse method for crosswalk in the north direction.
     */
    @Test 
    public void testMustTruckReverseCrosswalkNorth() {

        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, Terrain.CROSSWALK); 
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                
                assertEquals("Trcuk chooseDirection() failed "
                                + "when north was the only valid choice!",
                             Direction.NORTH, truck2.chooseDirection(neighbors));
            }
                
        }
    }
    
    /**
     * Tests the mustTruckReverse method when the terrain is a 
     * crosswalk and the direction is east.
     */
    @Test
    public void testMustTruckReverseCrosswalkEast() {
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.CROSSWALK);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                
                // the Human must reverse and go SOUTH
                assertEquals("Truck chooseDirection() failed "
                                + "when east was the only valid choice!",
                             Direction.EAST, truck2.chooseDirection(neighbors));
            }
                
        }
    }

    /**
     * Test for mustTruckReverse when the terrain is a crosswalk and 
     * the direction is west.
     */
    @Test
     public void testMustTruckReverseCrosswalkWest() {
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, Terrain.CROSSWALK);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when west was the only valid choice!",
                             Direction.WEST, truck2.chooseDirection(neighbors));
            }
                
        }
    }
    
    /**
     * Tests the mustTruckReverse method when the direction is North
     * and the terrain is a light.
     */
    @Test 
    public void testMustTruckReverseLightNorth() {
    
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, Terrain.LIGHT); 
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when north was the only valid choice!",
                             Direction.NORTH, truck2.chooseDirection(neighbors));
            }
                
        }
    }
    /**
     * Tests the mustTruckReverse method when the direction is East
     * and the terrain is a light.
     */
    @Test 
    public void testMustTruckReverseLightEast() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t); 
                neighbors.put(Direction.EAST, Terrain.LIGHT);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when east was the only valid choice!",
                             Direction.EAST, truck2.chooseDirection(neighbors));
            }
                
        }
    }
    /**
     * Tests the mustTruckReverse method when the direction is West
     * and the terrain is a light.
     */
    @Test 
    public void testMustTruckReverseLightWest() {
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, Terrain.LIGHT);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when west was the only valid choice!",
                             Direction.WEST, truck2.chooseDirection(neighbors));
            }
            
            for (final Terrain t1 : Terrain.values()) {
                if (t1 != Terrain.STREET && t1 != Terrain.CROSSWALK && t1 != Terrain.LIGHT) {
                    
                    final Map<Direction, Terrain> neighbors = 
                                    new HashMap<Direction, Terrain>();
                    neighbors.put(Direction.WEST, t1);
                    neighbors.put(Direction.NORTH, t1);
                    neighbors.put(Direction.EAST, t1);
                    neighbors.put(Direction.SOUTH, Terrain.LIGHT);
                    
                    final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                    
                    // the Human must reverse and go SOUTH
                    assertEquals("Truck chooseDirection() failed "
                                    + "when reverse was the only valid choice!",
                                 Direction.SOUTH, truck2.chooseDirection(neighbors));
                }
            } 
        }
    }
    
    /**
     * Tests the mustTruckReverse method for when the direction is North
     * and the terrain is a street.
     */
    @Test 
    public void testMustTruckReverseStreetNorth() {

        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, Terrain.STREET);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when North was the only valid choice!",
                             Direction.NORTH, truck2.chooseDirection(neighbors));
            }
                
        }
    }
    
    /**
     * Tests the mustTruckReverse method for when the direction is East
     * and the terrain is a street.
     */
    @Test 
    public void testMustTruckReverseStreetEast() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.STREET);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when east was the only valid choice!",
                             Direction.EAST, truck2.chooseDirection(neighbors));
            }
                
        }
    }
    
    /**
     * Tests the mustTruckReverse method for when the direction is West
     * and the terrain is a street.
     */
    @Test 
    public void testMustTruckReverseStreetWest() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, Terrain.STREET);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck2 = new Truck(0, 0, Direction.NORTH);
                
                assertEquals("Truck chooseDirection() failed "
                                + "when west was the only valid choice!",
                             Direction.WEST, truck2.chooseDirection(neighbors));
            }
                
        }
 
        
    }
    
}
