package General_Questions;

import java.util.*;

public class LeetCodeMisc {

	public static void main(String[] args) {

	}

	// https://leetcode.com/problems/power-of-four
	public static boolean isPowerOfFour(int n) {
		if (n < 1) return false;
		if (n == 1) return true;
		int i = 0; // power
		int r = 1; // result of 4 to the power of i
		while (r < n) {
			i++;
			double d = Math.pow(4, i);
			if (d > Integer.MAX_VALUE) return false;
			r = (int)d;
			if (r==n) return true;
		}
		return false;
	}

	// https://leetcode.com/problems/excel-sheet-column-title/
	public static String convertToTitle(int columnNumber) {
		// converting base 10 number to base 26
		String result = "";
		while (columnNumber-- > 0) { // need to subtract 1 each time to get 0-based number that can be added to 'A'
			int remains = columnNumber % 26;
			result = (char)('A'+remains) + result;
			columnNumber /= 26;
		}
		return result;
	}

	// https://leetcode.com/problems/valid-mountain-array/
	public static boolean validMountainArray(int[] arr) {
		if (arr.length <= 2) return false;
		int prev = arr[0];
		boolean increasing = true; // in the "climb" phase of going up mountain
		for (int i = 1; i < arr.length; i++) {
			int cur = arr[i];
			if (prev == cur) return false;

			if (increasing) {
				if (prev > cur) {
					if (i == 1) return false; // can't start off going down
					increasing = false; // prev was top of mtn, now going down
				}
			} else {
				if (prev < cur) return false;
			}
			prev = cur;
		}
		return !increasing; // should be descending at the end
	}

	// https://leetcode.com/problems/house-robber/
	// O(n) runtime and O(1) space
	public static int rob(int[] n) {
		//   [2,4,8, 9, 9 ]            [6,1,1,6]
		// r  2,4,10,13,19           r  6,1,7,12
		// nr 0,2,4 ,10,13          nr  0,6,6,7
		int rob = n[0]; // money if rob current house
		int noRob = 0; // money if don't rob current house
		for (int i = 1; i < n.length; i++) {
			int prevNoRob = noRob;
			noRob = Math.max(noRob, rob);
			rob = prevNoRob + n[i];
		}
		return Math.max(rob, noRob);
	}
	public static int robBad(int[] n) {
		// start at the 1st num and sum every other number, then start at the 2nd and sum every other, take max
		// ^ is too simplistic, could have two large outliers that have 2 houses in between

		// look at 4 (5?) elements at a time, make a decision on the first pair and then move 2 or 3 over
		// 1,9,1,1,1,1,8,2,8,9
		// 1,9,1,1,1 -> choices lead to sum of 2 (choose index 0 and 2) or 10 (index 1 and 3)

		// which of the first 2 numbers do you choose?
		// 4,9,6,[1,9]    --> should choose index 0
		// 4,9,6,[9,1]    --> should choose index 1;  need to see more than 3 numbers
		// 4,9,6,1,[9]    --> should choose index 0
		// 4,9,6,1,[1]    --> should choose index 0;  enough data is available
		// 4,9,6,1,[1,2]  --> should choose index 0;  enough data is available
		// sliding window of 4 seems like it will work, after attempting seems like need a window of 5

		// 2,4,8,9,9   -> should pick index 0
		// 1,3,1,3,100 -> should pick index 1

		// consider starting in the middle of the array, maybe recursion, that doesn't seem to help though

		// the solution below misses a few corner cases and is not simplest way!

		int result = 0;
		int start = 0;
		int end = 4;
		while (end < n.length) {
			int sum1 = n[start] + n[start+2];
			int sum2 = n[start+1] + n[start+3];
			int sum3 = n[start+1] + n[start+2];
			if (sum1 >= sum2 || n[start] >= sum3) { // pick 1st house to rob
				result += n[start];
//				System.out.println("Choose index: " + start);
				start += 2;
				end += 2;
			} else { // pick 2nd house to rob
//				System.out.println("Choose index: " + (start+1));
				result += n[start+1];
				start += 3; // can't pick immediately adjacent house, so set start as a possible choice next time
				end += 3;
			}
		}
		// pick between start and n.length-1; could have 4,3,2,1,or 0 numbers to choose from
		if (n.length-start == 4) {
			result += Math.max(n[start] + n[start+2], Math.max(n[start] + n[start+3], n[start+1] + n[start+3]));
//			System.out.println("Choose between final 4");
		} else if (n.length-start == 3) {
			result += Math.max(n[start] + n[start+2], n[start+1]);
//			System.out.println("Choose between final 3");
		} else if (n.length-start == 2) {
//			System.out.println("Choose between final 2");
			result += Math.max(n[start], n[start+1]);
		} else if (n.length-start == 1) {
//			System.out.println("Choose between final 1");
			result += n[start];
		}
		return result;
	}

	// https://leetcode.com/problems/word-ladder/description/
	public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
//		String[] wordList = {"hot","dot","dog","lot","log","cog"};
//		System.out.println(ladderLength("", "", Arrays.asList(wordList)));
		return -1;
	}

	// https://leetcode.com/problems/gas-station/
	// O(n) runtime and O(1) space
	public static int canCompleteCircuit(int[] gas, int[] cost) {
		int difSum = 0;
		for (int i = 0; i < gas.length; i++) {
			difSum += gas[i] - cost[i];
		}
		if (difSum < 0) return -1;

		// not entirely obvious that if sum of differences >= 0 then a solution exists, but can be proven
		int start = 0; // potential route starting point
		int curSum = 0; // sum of difs since start
		for (int i = 0; i < gas.length; i++) {
			curSum += gas[i] - cost[i];
			if (curSum < 0) { // potential starting point didn't work, move to next one
				start = i+1;
				curSum = 0;
			}
		}
		return start;
	}
	// worst case runtime still O(n^2), but this is rarer with sort optimization; O(n) space
	public static int canCompleteCircuitOptimized(int[] gas, int[] cost) {
		// save best starting places; best starting places are where gas[i] is most greater than cost[i]
		List<Position> positions = new ArrayList<>();
		for (int i = 0; i < gas.length; i++) {
			if (gas[i] >= cost[i]) { // first step is possible, check if route is possible - may not need this line
				positions.add(new Position(i, gas[i]-cost[i]));
			}
		}
		positions.sort(Collections.reverseOrder());

		for (Position p : positions) {
			int fuel = 0;
			for (int j = p.index; j < p.index + gas.length; j++) {
				int curPos = j % gas.length;
				fuel += gas[curPos];
				if (fuel < cost[curPos]) {
					break; // not enough gas to continue route
				} else if (j == p.index + gas.length - 1) {
					return p.index; // completed route
				}
				fuel -= cost[curPos]; // continue to next stop
			}
		}
		return -1;
	}
	static class Position implements Comparable<Position>{
		int index, value;
		public Position(int i, int v) {
			this.index = i;
			this.value = v;
		}
		@Override
		public int compareTo(Position o) {
			return this.value - o.value;
		}
	}
	// O(n^2) runtime where n is length of input array, O(1) memory
	public static int canCompleteCircuitSimple(int[] gas, int[] cost) {
		// check possible starting places
		for (int i = 0; i < gas.length; i++) {
			if (gas[i] >= cost[i]) { // first step is possible, check if route is possible - do not need this line
				int fuel = 0;
				for (int j = i; j < i + gas.length; j++) {
					int curPos = j % gas.length;
					fuel += gas[curPos];
					if (fuel < cost[curPos]) {
						break; // not enough gas to continue route
						// later realize that inefficiency lies here, we can discard other starting places up to curPos
					} else if (j == i + gas.length - 1) {
						return i; // completed route
					}
					fuel -= cost[curPos]; // continue to next stop
				}
			}
		}
		return -1;
	}

	// https://leetcode.com/problems/koko-eating-bananas/
	// O(n * log m) runtime where n = piles.length and m is max possible value of a number in piles, O(1) space
	public static int minEatingSpeed(int[] piles, int h) {
		if (h < piles.length) return -1;

		// get sum/h and max of array, minimum eating speed is sum/h (rounded up to nearest int), max speed is max
		int max = Integer.MIN_VALUE;
		double sum = 0.0;
		for (int p : piles) {
			sum += p;
			if (max < p) max = p;
		}
		int min = (int) Math.ceil(sum / h);

		// corner cases - watch out for int overflow
		if (h == piles.length) return max;
		if (h >= sum) return 1;

		// check possible answers with BINARY SEARCH
		int mid = (min + max) / 2;
		while (min < max) {
			if (allEaten(piles, h, mid)) {
				// answer <= mid
				max = mid;
			} else {
				// answer > mid; need to eat at faster speed
				min = mid + 1;
			}
			mid = (min + max) / 2;
		}
		return mid;
	}
	// returns true if all piles are eaten in h hours with eating speed of test bananas per hour
	private static boolean allEaten(int[] piles, int h, int test) {
		// do all bananas get eaten by time h?
		int eatHr = 0; // hours spent eating
		for (int p = 0; p < piles.length; p++) {
			eatHr += piles[p] / test; // hrs to eat the bananas in pile p
			if (piles[p] % test > 0) {
				eatHr++; // if remainder, takes additional hour to finish the pile
			}
			if (eatHr > h) return false; // spent longer eating than allowed; did not finish bananas in time
		}
		return eatHr <= h;
	}
	// worst case O(n*m) where n = piles.length and m is max possible value of a number in piles
	public static int minEatingSpeedSimple(int[] piles, int h) {
		if (h < piles.length) return -1;

		// get sum/h and max of array, minimum eating speed is sum/h (rounded up to nearest int), max speed is max
		int max = Integer.MIN_VALUE;
		double sum = 0.0;
		for (int p : piles) {
			sum += p;
			if (max < p) max = p;
		}
		int min = (int) Math.ceil(sum / h);
//		System.out.println("min " + min + " max " + max);

		if (h == piles.length) return max; // corner case
		if (h >= sum) return 1;

		// check possible answers
		for (int i = min; i <= max; i++) {
			// do all bananas get eaten by time h?
			int hr = 0; // hours spent eating
			for (int p = 0; p < piles.length; p++) {
				int t = piles[p] / i; // hrs to eat the bananas in pile p
				int r = piles[p] % i; // remainder
				hr += t;
				if (r > 0) {
					hr++;
				}
				if (hr > h) break; // spent longer eating than allowed; did not finish bananas in time
			}
			if (hr <= h) return i; // found answer
		}
//		for (int i = min; i <= max; i++) {
//			// do all bananas get eaten by time h?
//			int curPos = 0;
//			int curPile = piles[0];
//			for (int hr = 0; hr < h; hr++) {
//				curPile -= i;
//				if (curPile <= 0) { // finished a pile, move to next one
//					curPos++;
//					if (curPos >= piles.length) return i; // best solution found
//					curPile = piles[curPos];
//				}
//			}
//		}
		return -1;
	}

	// Remove all duplicates that occur in the given sorted array, return the length of the new array
	public static int removeDuplicates(int[] nums) {
		int i = 0;
		for (int n : nums) 
			if (i < 1 || n > nums[i-1])
				nums[i++] = n;
		return i;
	}

	// https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/description/
	// Remove all duplicates that occur more than twice in the given sorted array.
	public static int removeMoreThanTwoDuplicates(int[] nums) {
		int i = 0;
	    for (int n : nums)
	        if (i < 2 || n > nums[i-2])
	            nums[i++] = n;
	    return i;
    }

	// return all elements of the matrix in spiral order.
	// https://leetcode.com/problems/spiral-matrix/description/
	// [[ 1, 2, 3 ], [ 4, 5, 6 ], [ 7, 8, 9 ]] => [1,2,3,6,9,8,7,4,5]
	public static List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> result = new ArrayList<>();
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return result;
		int colStart = 0;
		int colEnd = matrix[0].length-1;
		int rowStart = 0;
		int rowEnd = matrix.length-1;
		while (rowStart <= rowEnd && colStart <= colEnd) {
			for (int i = colStart; i <= colEnd; i++) { // across top
				result.add(matrix[rowStart][i]);
			}
			rowStart++;
			if (!(rowStart <= rowEnd && colStart <= colEnd)) break;
			for (int i = rowStart; i <= rowEnd; i++) { // down right
				result.add(matrix[i][colEnd]);
			}
			colEnd--;
			if (!(rowStart <= rowEnd && colStart <= colEnd)) break;
			for (int i = colEnd; i >= colStart; i--) { // across bottom
				result.add(matrix[rowEnd][i]);
			}
			rowEnd--;
			if (!(rowStart <= rowEnd && colStart <= colEnd)) break;
			for (int i = rowEnd; i >= rowStart; i--) { // up left
				result.add(matrix[i][colStart]);
			}
			colStart++;
		}
		return result;
	}

	// Given a 2D board and a word, find if the word exists in the grid. The word can be constructed from letters of sequentially adjacent cell, 
	// where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.
	// https://leetcode.com/problems/word-search
	public static boolean exist(char[][] board, String word) {
		if (board.length == 0 || board[0].length == 0) return false;
		if (word.isEmpty()) return true;
		int height = board.length, width = board[0].length;
		boolean[][] visited = new boolean[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (board[y][x] == word.charAt(0) && search(y, x, board, word, 0, visited)) { // found first letter
					return true;
				}
			}
		}
		return false;
	}
	private static boolean search (int y, int x, char[][] board, String word, int index, boolean[][] visited) {
		if (y >= 0 && y < board.length && x >= 0 && x < board[0].length && board[y][x] == word.charAt(index) && !visited[y][x]) {
			if (index+1 == word.length()) return true; // found path to the end
			visited[y][x] = true;
			if (search(y, x+1, board, word, index+1, visited) || // continue searching
					search(y, x-1, board, word, index+1, visited) ||
					search(y+1, x, board, word, index+1, visited) ||
					search(y-1, x, board, word, index+1, visited)) {
				return true;
			}
			visited[y][x] = false;
		}
		return false;
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
		while (high >= low) { // binary search on columns
			int mid = low + (high-low) / 2;
			if (matrix[mid][0] <= target && (mid == matrix.length-1 || matrix[mid+1][0] > target)) {
				return ArrayQuestions.indexOfBinarySearch(matrix[mid], target) != -1; // binary search on row
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
