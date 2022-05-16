package General_Questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Intervals {

	// https://leetcode.com/problems/non-overlapping-intervals/
	// return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping
	// O(nlogn) time, O(1) space
	public static int eraseOverlapIntervals(int[][] intervals) {
		Arrays.sort(intervals, (a,b) -> a[1]-b[1]); // trick is to sort by upper limits
		int nonOverlap = 1; // solve the reverse problem; count max number of non-overlapping intervals
		int end = intervals[0][1];
		for (int i = 1; i < intervals.length; i++) {
			if (end <= intervals[i][0]) {
				end = intervals[i][1];
				nonOverlap++;
			}
		}
		return intervals.length - nonOverlap;
	}

	// https://leetcode.com/problems/merge-intervals/
	// O(nlogn + n) time, extra space proportional to that needed in returning the result
	public static int[][] merge(int[][] intervals) {
		Arrays.sort(intervals, (a,b) -> a[0] - b[0]);
		List<int[]> result = new LinkedList<>(); // use list for variable size
		for (int i = 0; i < intervals.length; i++) {
			int[] cur = intervals[i];
			// if current overlaps with next interval, merge them
			while (i < intervals.length-1 && overlaps(cur, intervals[i+1])) {
				cur[1] = Math.max(cur[1], intervals[i+1][1]); // only upper bound can change since intervals sorted
				i++;
			}
			result.add(cur);
		}
		// convert list to int[][]
		return result.toArray(new int[result.size()][]);
	}
	private static boolean overlaps(int[] a, int[] b) {
		return a[1] >= b[0];
	}

	// https://leetcode.com/problems/insert-interval/
	// O(n) time where n is number of intervals, O(1) extra space
	public static int[][] insert(int[][] intervals, int[] newInterval) {
		// find index in intervals where new lo is less than an existing high
		// may be able to do binary search, linear here for simplicity
		int newIndex = 0;
		for (; newIndex < intervals.length; newIndex++) {
			if (newInterval[0] <= intervals[newIndex][1]) {
				break;
			}
		}
//		System.out.println("Inserting newInterval at newIndex: " + newIndex);
		if (newIndex == intervals.length) {
			// lo of newInterval greater than all existing interval hi values; insert at the end
			int[][] result = new int[intervals.length+1][2];
			// direct copy intervals into result
			for (int i = 0; i < intervals.length; i++) {
				System.arraycopy(intervals[i], 0, result[i], 0, intervals[0].length);
			}
			result[intervals.length] = newInterval;
			return result;
		} else {
			// insert at newIndex, may need to merge with multiple following intervals
			int merged = 0; // can't assume that a merge will happen, may be inserted at front of array
			int i = newIndex;
			// new high >= existing interval lows (they overlap), so must be merged
			while (i < intervals.length && newInterval[1] >= intervals[i][0]) {
				i++;
				merged++;
			}
			int newLo = Math.min(newInterval[0], intervals[newIndex][0]);
			int newHi = merged == 0 ? newInterval[1] : Math.max(newInterval[1], intervals[i-1][1]);

			int newSize = intervals.length+1-merged;
			int[][] result = new int[newSize][2];
			// insert newInterval with all merges
			result[newIndex] = new int[]{newLo, newHi};
			// copy the non-merged intervals before newInterval
			for (int k = 0; k < newIndex; k++) {
				System.arraycopy(intervals[k], 0, result[k], 0, 2);
			}
			// copy the non-merged intervals after newInterval
			for (int k = newIndex+1; k < result.length; k++, i++) {
				System.arraycopy(intervals[i], 0, result[k], 0, 2);
			}
//			System.out.println(Arrays.deepToString(result));
//			System.out.println(Arrays.deepToString(intervals));
			return result;
		}
	}



}
