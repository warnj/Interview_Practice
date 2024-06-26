package Tests;

import General_Questions.Intervals;
import General_Questions.Sorting;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class IntervalsTest {


    @Test
    public void testMeetingRooms() {
        assertEquals(3, Intervals.meetingRooms(new int[][]{{0,30},{5,10},{5,15},{15,20}}));
        assertEquals(3, Intervals.meetingRooms(new int[][]{{5,15},{40,70},{0,30},{5,10},{60,72},{15,20},{39,61}}));
        assertEquals(3, Intervals.meetingRooms(new int[][]{{5,15},{40,70},{5,10},{60,72},{15,20},{0,30},{39,59}}));
        assertEquals(4, Intervals.meetingRooms(new int[][]{{0,30},{5,10},{5,15},{9,20}}));
        assertEquals(4, Intervals.meetingRooms(new int[][]{{0,30},{5,10},{5,15},{9,20}}));
        assertEquals(0, Intervals.meetingRooms(new int[][]{}));
    }

    @Test
    public void testEraseOverlapIntervals() {
        assertEquals(1, Intervals.eraseOverlapIntervals(new int[][]{{1,2},{2,3},{3,4},{1,3}}));
        assertEquals(3, Intervals.eraseOverlapIntervals(new int[][]{{1,2},{1,3},{1,4},{2,4},{3,5}}));
        assertEquals(2, Intervals.eraseOverlapIntervals(new int[][]{{1,2},{1,2},{1,2}}));
        assertEquals(1, Intervals.eraseOverlapIntervals(new int[][]{{1,2},{4,5},{0,9}}));
        assertEquals(0, Intervals.eraseOverlapIntervals(new int[][]{{1,2},{2,3}}));
    }

    @Test
    public void testMerge() {
        assertArrayEquals(new int[][]{{0,5}}, Intervals.merge(new int[][]{{1,4},{0,2},{3,5}}));
        assertArrayEquals(new int[][]{{1,6},{8,10},{15,18}}, Intervals.merge(new int[][]{{1,3},{2,6},{8,10},{15,18}}));
        assertArrayEquals(new int[][]{{1,5}}, Intervals.merge(new int[][]{{1,4},{4,5}}));
        assertArrayEquals(new int[][]{{1,3}}, Intervals.merge(new int[][]{{1,3}}));
    }

    @Test
    public void testInsert() {
        // merging
        assertArrayEquals(new int[][]{{1,5},{6,9}}, Intervals.insert(new int[][]{{1,3},{6,9}}, new int[]{2,5}));
        assertArrayEquals(new int[][]{{1,3},{6,9}}, Intervals.insert(new int[][]{{1,3},{6,9}}, new int[]{8,9}));
        assertArrayEquals(new int[][]{{1,3},{6,11}}, Intervals.insert(new int[][]{{1,3},{6,9}}, new int[]{8,11}));
        assertArrayEquals(new int[][]{{1,9}}, Intervals.insert(new int[][]{{1,3},{6,9}}, new int[]{3,6}));
        assertArrayEquals(new int[][]{{1,2},{3,10},{12,16}}, Intervals.insert(new int[][]{{1,2},{3,5},{6,7},{8,10},{12,16}}, new int[]{4,8}));
        // no merging
        assertArrayEquals(new int[][]{{1,3},{6,9},{11,13}}, Intervals.insert(new int[][]{{1,3},{6,9}}, new int[]{11,13}));
        assertArrayEquals(new int[][]{{0,1},{2,3},{6,9}}, Intervals.insert(new int[][]{{2,3},{6,9}}, new int[]{0,1}));
        assertArrayEquals(new int[][]{{2,3},{4,5},{6,9}}, Intervals.insert(new int[][]{{2,3},{6,9}}, new int[]{4,5}));
    }
}
