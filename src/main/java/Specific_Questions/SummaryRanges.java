package Specific_Questions;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;

// this is a great interview question!
// https://leetcode.com/problems/data-stream-as-disjoint-intervals/
public class SummaryRanges {

    public static void main(String[] args) {
        SummaryRanges summaryRanges = new SummaryRanges();
        summaryRanges.addNum(1);      // arr = [1]
        assertArrayEquals(new int[][]{{1,1}}, summaryRanges.getIntervals()); // return [[1, 1]]
        summaryRanges.addNum(3);      // arr = [1, 3]
        assertArrayEquals(new int[][]{{1,1},{3,3}}, summaryRanges.getIntervals()); // return [[1, 1], [3, 3]]
        summaryRanges.addNum(7);      // arr = [1, 3, 7]
        assertArrayEquals(new int[][]{{1,1},{3,3},{7,7}}, summaryRanges.getIntervals()); // return [[1, 1], [3, 3], [7, 7]]
        summaryRanges.addNum(2);      // arr = [1, 2, 3, 7]
        assertArrayEquals(new int[][]{{1,3},{7,7}}, summaryRanges.getIntervals()); // return [[1, 3], [7, 7]]
        summaryRanges.addNum(6);      // arr = [1, 2, 3, 6, 7]
        assertArrayEquals(new int[][]{{1,3},{6,7}}, summaryRanges.getIntervals()); // return [[1, 3], [6, 7]]
    }

    // consider using BitSet and List<int[]> for intervals but the bitset needs quick search into list for interval
    // merge so use a large array of int[] pointers instead and a sortedset for O(1) remove and pre-sorted
    private int[][] allNums = new int[10002][]; // array of possible vals holding pointers to interval including the val
    private SortedSet<int[]> intervals;

    public SummaryRanges() {
        intervals = new TreeSet<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
    }

    public void addNum(int value) {
        if (allNums[value] == null) {
            boolean lowerSet = value - 1 >= 0 && allNums[value - 1] != null;
            boolean upperSet = allNums[value + 1] != null;
            if (lowerSet && upperSet) {
                // merge both
                int[] lower = allNums[value - 1];
                intervals.remove(lower); // remove lower interval from list & before modifying upper as upper will now
                // be = to lower in the treeset comparator
                int[] upper = allNums[value + 1];
                upper[0] = lower[0]; // use the upper interval
                allNums[value] = upper;
                for (int i = value - 1; i >= 0 && allNums[i] == lower; i--) {
                    allNums[i] = upper; // update the references that have lower interval
                }

            } else if (lowerSet) {
                int[] lower = allNums[value - 1];
                lower[1] = value;
                allNums[value] = lower;
                // update by reference, so nothing needs to be done to intervals
            } else if (upperSet) {
                int[] higher = allNums[value + 1];
                higher[0] = value;
                allNums[value] = higher;
            } else {
                int[] newInterval = new int[]{value, value};
                intervals.add(newInterval);
                allNums[value] = newInterval;
            }
        }
    }

    public int[][] getIntervals() {
        int[][] temp = new int[intervals.size()][2];
        int i = 0;
        for (int[] interval : intervals) {
            temp[i++] = interval; // deep copy not needed
        }
        return temp;
    }
}
