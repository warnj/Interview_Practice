package Tests;

import General_Questions.LeetCodeMisc;
import org.junit.Test;

import static org.junit.Assert.*;

public class LeetCodeMiscTests {

    @Test
    public void testAddDigits() {
        assertEquals(2, LeetCodeMisc.addDigits(38));
        assertEquals(0, LeetCodeMisc.addDigits(0));
    }

    @Test
    public void testConvertToTitle() {
        assertEquals("A", LeetCodeMisc.convertToTitle(1));
        assertEquals("Z", LeetCodeMisc.convertToTitle(26));
        assertEquals("AB", LeetCodeMisc.convertToTitle(28));
        assertEquals("ZY", LeetCodeMisc.convertToTitle(701));
        assertEquals("AAA", LeetCodeMisc.convertToTitle(703));
    }

    @Test
    public void testMountainArray() {
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{2,1}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{3,5,5}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{0,2,3,3,5,2,1,0}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{0,2,3,4,5,2,1,0,1,2,5,2,1}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{0,1,2,3,4,5,6,7,8,9}));
        assertFalse(LeetCodeMisc.validMountainArray(new int[]{9,8,7,6,5,4,3,2,1,0}));

        assertTrue(LeetCodeMisc.validMountainArray(new int[]{0,3,2,1}));
        assertTrue(LeetCodeMisc.validMountainArray(new int[]{0,2,3,4,5,2,1,0}));
    }

    @Test
    public void testHouseRobber() {
        assertEquals(4, LeetCodeMisc.rob(new int[]{1,2,3,1}));
        assertEquals(12, LeetCodeMisc.rob(new int[]{2,7,9,3,1}));
        assertEquals(12, LeetCodeMisc.rob(new int[]{6,1,1,6}));
        assertEquals(24, LeetCodeMisc.rob(new int[]{1,9,1,1,6,1,9}));
        assertEquals(27, LeetCodeMisc.rob(new int[]{1,9,1,1,1,1,8,2,8,9}));
        assertEquals(19, LeetCodeMisc.rob(new int[]{4,9,6,1,9}));
        assertEquals(14, LeetCodeMisc.rob(new int[]{4,1,2,7,5,3,1}));
        assertEquals(19, LeetCodeMisc.rob(new int[]{2,4,8,9,9,3}));
        assertEquals(103, LeetCodeMisc.rob(new int[]{1,3,1,3,100}));
    }

    @Test
    public void testCanCompleteCircuit() {
        assertEquals(3, LeetCodeMisc.canCompleteCircuitSimple(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));
        assertEquals(-1, LeetCodeMisc.canCompleteCircuitSimple(new int[]{1,2,3,4,5}, new int[]{3,4,6,1,2}));
        assertEquals(3, LeetCodeMisc.canCompleteCircuitSimple(new int[]{1,2,2,4,5}, new int[]{3,4,3,2,2}));
        assertEquals(0, LeetCodeMisc.canCompleteCircuitSimple(new int[]{0,2,1}, new int[]{0,1,2}));
        assertEquals(-1, LeetCodeMisc.canCompleteCircuitSimple(new int[]{2,3,4}, new int[]{3,4,3}));
    }

    @Test
    public void testMinEatingSpeed() {
        // as a human to solve this:
        //    length of array important & number of times output goes into each pile is important
        //    if h == piles.length, then output = max(piles), if h < piles.length then not possible
        //    min possible output is if all piles are even: sum(piles) / h
        //    may be able to sort largest to smallest, then iterate through, actually order doesn't matter
        //    the bigger h is, the smaller the output (infinite h = output 1)
        //    the smaller h is (closer to piles.length), the bigger the output
        //    write out lots of examples and look for pattern
        assertEquals(4, LeetCodeMisc.minEatingSpeed(new int[]{3,6,7,11}, 8));
        assertEquals(30, LeetCodeMisc.minEatingSpeed(new int[]{30,11,23,4,20}, 5));
        assertEquals(23, LeetCodeMisc.minEatingSpeed(new int[]{30,11,23,4,20}, 6));
        assertEquals(4, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 4));
        assertEquals(3, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 5));
        assertEquals(2, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 6));
        assertEquals(2, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 7));
        assertEquals(2, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 9));
        assertEquals(1, LeetCodeMisc.minEatingSpeed(new int[]{4,3,2,1}, 10));
        assertEquals(1000000000, LeetCodeMisc.minEatingSpeed(new int[]{1000000000, 1000000000}, 3));
        assertEquals(500000000, LeetCodeMisc.minEatingSpeed(new int[]{1000000000, 1000000000}, 4));
    }
	
}
