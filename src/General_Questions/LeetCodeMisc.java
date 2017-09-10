package General_Questions;

import java.util.*;

public class LeetCodeMisc {

	public static void main(String[] args) {
		System.out.println(maxProduct(new int[] {0,2}));
	}
	
	// https://leetcode.com/problems/maximum-product-subarray/discuss/
	// Find the contiguous subarray within an array (containing at least one number) which has the largest product.
	public static int maxProduct(int[] nums) { // ingenious O(n) time
		int max = nums[0];
	    // keep track of the previous max (and min since product of 2 negatives = positive)
		int maxherepre = nums[0];
	    int minherepre = nums[0];
	    
	    for (int i = 1; i < nums.length; i++) {
	    	int maxhere = Math.max(Math.max(maxherepre * nums[i], minherepre * nums[i]), nums[i]);
	    	int minhere = Math.min(Math.min(maxherepre * nums[i], minherepre * nums[i]), nums[i]);
	        max = Math.max(maxhere, max);
	        maxherepre = maxhere;
	        minherepre = minhere;
	    }
	    return max;
    }
	public static int maxProductSlow(int[] nums) { // brute force O(n^2) time
		int max = nums[0];
        for (int i = 0; i < nums.length; i++) {
        	for (int j = i; j < nums.length; j++) {
//        		System.out.println(i+" "+j);
        		int p = product(nums, i, j); 
        		if (p > max) max = p;
        	}
        }
        return max;
    }
	private static int product(int[] a, int lo, int hi) {
		int prod = 1;
		for (int i = lo; i <= hi; i++) {
			prod *= a[i];
		}
		return prod;
	}
	
	// https://leetcode.com/problems/generate-parentheses/discuss/
	// n = 2 => [()(), (())]
	// n = 3 => [((())), (()()), (())(), ()(()), ()()()]
	public static List<String> generateParenthesis(int n) {
		List<String> result = new ArrayList<>();
		generateParenthesis(result, n, 0, 0, "");
		return result;
    }
	private static void generateParenthesis(List<String> result, int max, int open, int close, String s) {
		if (s.length() == 2*max) {
			result.add(s);
		} else {
			if (open < max) // it is legal to add a "("
				generateParenthesis(result, max, open+1, close, s+"(");
			if (open > close) // it is legal to add a ")"
				generateParenthesis(result, max, open, close+1, s+")");
		}
	}

	// Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
	// Integers in each row are sorted from left to right.
	// The first integer of each row is greater than the last integer of the previous row.
	// https://leetcode.com/problems/search-a-2d-matrix/description/
	public static boolean searchMatrix(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
		int low = 0;
		int high = matrix.length - 1;
		while (high >= low) {
			int mid = low + (high-low) / 2;
			if (matrix[mid][0] <= target && (mid == matrix.length-1 || matrix[mid+1][0] > target)) {
				return ArrayQuestions.indexOfBinarySearch(matrix[mid], target) != -1;
			} else if (matrix[mid][0] < target) {
				low = mid + 1;
			} else { // arr[mid] > key
				high = mid - 1;
			}
		}
		return false;
	}
	public boolean searchMatrixSimpler(int[][] matrix, int target) { // simpler, cleaner solution to above
		int i = 0, j = matrix[0].length - 1;
		while (i < matrix.length && j >= 0) {
			if (matrix[i][j] == target) {
				return true;
			} else if (matrix[i][j] > target) {
				j--;
			} else {
				i++;
			}
		}
		return false;
	}


	public static class Interval {
		int start; 
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
		public String toString() {
			return "["+Integer.toString(start)+","+Integer.toString(end)+"]";
		}
	}
	// merges overlapping intervals in given list
	// https://leetcode.com/problems/merge-intervals/description/
	public static List<Interval> merge(List<Interval> intervals) {
		List<Interval> result = new ArrayList<>();
		Collections.sort(intervals, new Comparator<Interval>() {
			public int compare(Interval a, Interval b) {
				return a.start - b.start;
			}
		});
		for (int i = 1; i < intervals.size(); i++) {
			Interval prev = intervals.get(i-1);
			Interval cur = intervals.get(i);
			if (prev.end >= cur.start) {
				intervals.set(i, new Interval(prev.start, Math.max(prev.end, cur.end)));
				intervals.remove(i-1);
				i--;
			}
		}
		return intervals;
	}

	// Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], 
	// reconstruct the itinerary in order. Always depart from JFK.
	// https://leetcode.com/problems/reconstruct-itinerary/description/
	public static List<String> findItinerary(String[][] tickets) {
		List<String> it = new LinkedList<String>();
		Map<String, PriorityQueue<String>> graph = new HashMap<>();
		for (String[] t : tickets) {
			graph.putIfAbsent(t[0], new PriorityQueue<>());
			graph.get(t[0]).add(t[1]);
		}
		//		System.out.println(graph);
		Stack<String> toVisit = new Stack<>();
		toVisit.add("JFK");
		while (!toVisit.isEmpty()) {
			while (graph.containsKey(toVisit.peek()) && !graph.get(toVisit.peek()).isEmpty()) {
				toVisit.push(graph.get(toVisit.peek()).remove());
				// subsequent additions to the stack will be visited after completing a cycle of the graph
			}
			it.add(0, toVisit.pop());
		}
		return it;
	}

	// Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path
	// https://leetcode.com/problems/minimum-path-sum/description/
	public static int minPathSum(int[][] grid) {
		// modify grid to put the smallest path sum to each spot in each spot
		int xLen = grid[0].length, yLen = grid.length;
		for (int y = 0; y < yLen; y++) {
			for (int x = 0; x < xLen; x++) {
				if (x == 0 && y != 0) // across left side, sum from above
					grid[y][x] = grid[y-1][x] + grid[y][x];
				else if	(x != 0 && y == 0) // across top, sum from left
					grid[y][x] = grid[y][x-1] + grid[y][x];
				else if (x != 0 && y != 0) // sum the min of left and up cells
					grid[y][x] = Math.min(grid[y-1][x], grid[y][x-1]) + grid[y][x];
			}
		}
		return grid[yLen-1][xLen-1];
	}
	// INEFFICIENT BRUTE FORCE RECURSION SOLUTION:
	//	public static int minPathSum(int[][] grid) {
	//        return minPathSum(grid, 0, 0, 0);
	//    }
	//	private static int minPathSum(int[][] grid, int x, int y, int sum) {
	//        if (x == grid[0].length-1 && y == grid.length-1) {
	//        	return sum + grid[y][x];
	//        } else if (x < grid[0].length-1 && y < grid.length-1) {
	//        	int rightSum = minPathSum(grid, x+1, y, sum + grid[y][x]);
	//        	int downSum = minPathSum(grid, x, y+1, sum + grid[y][x]);
	//        	return Math.min(rightSum, downSum);
	//        } else if (x < grid[0].length-1) {
	//        	return minPathSum(grid, x+1, y, sum + grid[y][x]);
	//        } else { // (y < grid.length-1)
	//        	return minPathSum(grid, x, y+1, sum + grid[y][x]);
	//        }
	//    }

	// n = strs.length, m = avg length of string in strs. Generally m << n.  Runtime = O(n * m * log(m))  Space = O(n * m)
	// https://leetcode.com/problems/group-anagrams/description/
	public static List<List<String>> groupAnagrams(String[] strs) {
		Map<String, List<String>> map = new HashMap<>();
		for (String s : strs) {
			String sSorted = sortString(s);
			List<String> val = map.get(sSorted);
			if (val == null) val = new ArrayList<>();
			val.add(s);
			map.put(sSorted, val);
		}
		return new ArrayList<>(map.values());
	}
	private static String sortString(String s) {
		char[] c = s.toCharArray();
		Arrays.sort(c);
		return String.valueOf(c);
	}
	// INEFFICIENT BRUTE FORCE SOLUTION:
	//  Runtime = O(n * n), Space = O(n * m)
	//	public static List<List<String>> groupAnagrams(String[] strs) {
	//		List<List<String>> result = new ArrayList<>();
	//		for (String s : strs) {
	//			boolean found = false;
	//			for (List<String> anagrams : result) {
	//				if (CCI_Random_Practice.isAnagramBetterVersion(s, anagrams.get(0))) {
	//					found = true;
	//					anagrams.add(s);
	//				}
	//			}
	//			if (!found) {
	//				List<String> newAnagram = new ArrayList<>();
	//				newAnagram.add(s);
	//				result.add(newAnagram);
	//			}
	//		}
	//		return result;
	//    } 



}
