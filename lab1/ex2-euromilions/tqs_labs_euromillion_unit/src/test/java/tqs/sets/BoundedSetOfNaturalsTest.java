/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = new BoundedSetOfNaturals(5);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        /* 
        setB.add(11);
        assertTrue(setB.contains(11), "add: added element not found in set.");
        assertEquals(7, setB.size(), "add: elements count not as expected.");
        */

        // when set is created from array, its maxsize becomes the array size so no more elements can be added
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            setB.add(11);
        });

        setD.add(11);

        // repeated value
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            setD.add(11);
        });

        // negative value

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            setD.add(-39);
        });



    }


    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }
    

    @Test
    public void testIntersects() {
        assertTrue(setB.intersects(setC), "intersects: sets do intersect.");
        assertFalse(setA.intersects(setB), "intersects: sets do not intersect.");
    }


}
