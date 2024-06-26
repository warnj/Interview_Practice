package General_Questions;

import java.util.*;

public class Intervals {

	// given array of meeting time intervals with start and end times, find min number of conference rooms required
	// solve: find the maximum # of overlapping intervals at any time
	// O(n^2) time and O(1) space solution:
	public static int meetingRoomsOG(int[][] intervals) {
		Arrays.sort(intervals, (a,b)->a[0]-b[0]);
		int max = 0;
		for (int i = 0; i < intervals.length; i++) {
			int overlaps = 1;
			int hi = intervals[i][1];
			for (int j = i+1; j < intervals.length && intervals[j][0] < hi; j++) {
				overlaps++;
				hi = Math.min(hi, intervals[j][1]);
			}
			max = Math.max(overlaps, max);
		}
		return max;
	}
	// O(nlogn) time and O(n) space
	public static int meetingRooms(int[][] intervals) {
		int[] start = new int[intervals.length];
		int[] end = new int[intervals.length];
		for (int i = 0; i < intervals.length; i++) {
			start[i] = intervals[i][0];
			end[i] = intervals[i][1];
		}
		Arrays.sort(start);
		Arrays.sort(end);
		int i = 1; // pointer to starting times
		int j = 0; // pointer to ending times
		int cur = 1; // current # of meetings
		int answer = 0; // max meetings at one time
		while (i < start.length && j < end.length) {
			if (start[i] < end[j]) { // simultaneous meetings, incr room count, check for more
				cur++;
				i++;
			} else {
				cur--;
				j++;
			}
			answer = Integer.max(answer, cur);
		}
		return answer;
	}
	// O(nlogn) time and O(overlapping)=O(n) space
	public static int meetingRoomsPQ(int[][] intervals) {
		if (intervals == null || intervals.length == 0) return 0;
		Arrays.sort(intervals, (a,b)->a[0]-b[0]);
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		int count = 1;
		queue.add(intervals[0][1]); // ending times in min heap
		for (int i = 1; i < intervals.length; i++) {
			if (intervals[i][0] < queue.peek()) { // starts before soonest-ending meeting ends, need extra room
				count++;
			} else {
				queue.remove(); // cur meeting starts after soonest-ending meeting, don't need complete meetings in pq
			}
			queue.add(intervals[i][1]); // consider cur meeting end time
		}
		return count;
	}

	// https://leetcode.com/problems/non-overlapping-intervals/
	// return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping
	// O(nlogn) time, O(1) space
	public static int eraseOverlapIntervalsReverse(int[][] intervals) {
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
	public static int eraseOverlapIntervals(int[][] intervals) {
		Arrays.sort(intervals, (a,b) -> a[0]-b[0]); // sort by start lo to hi
		int removed = 0;
		int[] prev = intervals[0];
		for (int i = 1; i < intervals.length; i++) {
			int[] cur = intervals[i];
			if (prev[1] > cur[0]) { // overlap
				if (cur[1] < prev[1]) { // remove prev
					prev = cur;
				}
				removed++;
			} else { // no overlap, no remove
				prev = cur;
			}
		}
		return removed;
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
	public static int[][] merge2023(int[][] intervals) {
		List<int[]> result = new ArrayList<>();
		Arrays.sort(intervals, (a,b)->a[0]-b[0]);
		int[] cur = intervals[0];
		for (int i = 1; i < intervals.length; i++) {
			int max = cur[1];
			while (i < intervals.length && max >= intervals[i][0]) {
				max = Math.max(max, intervals[i][1]);
				i++;
			}
			cur[1] = max;
			result.add(cur);
			if (i < intervals.length) cur = intervals[i];
		}
		// add final interval if it wasn't part of previous merge
		if (result.isEmpty() || result.get(result.size()-1)[1] != cur[1]) result.add(cur);
		int[][] r = new int[result.size()][];
		for (int i = 0; i < r.length; i++) r[i] = result.get(i);
		return r;
	}

	// https://leetcode.com/problems/insert-interval/
	// O(n) time where n is number of intervals, O(1) extra space, note: doing a deep copy here that may not be needed
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
