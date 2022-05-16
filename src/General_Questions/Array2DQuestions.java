package General_Questions;

import java.util.*;

public class Array2DQuestions {

	public static void main(String[] args) {
	}

	// https://leetcode.com/problems/surrounded-regions/
	// O(n+m) time, O(1) space
	public static void solve(char[][] board) {
		int n = board.length;
		int m = board[0].length;
		// top
		for (int i = 0; i < m; i++)
			if (board[0][i] == 'O')
				explore(board, i, 0);
		// bottom
		for (int i = 0; i < m; i++)
			if (board[n-1][i] == 'O')
				explore(board, i, n-1);
		// left
		for (int i = 1; i < n-1; i++)
			if (board[i][0] == 'O')
				explore(board, 0, i);
		// right
		for (int i = 1; i < n-1; i++)
			if (board[i][m-1] == 'O')
				explore(board, m-1, i);
		// turn 'O' to 'X' and 'Z' back to 'O'
		for (int y = 0; y < n; y++)
			for (int x = 0; x < m; x++)
				if (board[y][x] == 'O') board[y][x] = 'X';
				else if (board[y][x] == 'Z') board[y][x] = 'O';
	}
	private static void explore(char[][] board, int x, int y) {
		board[y][x] = 'Z';
		// left, right, down, up
		if (x-1 >= 0 && board[y][x-1] == 'O' && board[y][x-1]!='Z') {
			explore(board, x-1, y);
		}
		if (x+1 < board[0].length && board[y][x+1] == 'O' && board[y][x+1]!='Z') {
			explore(board, x+1, y);
		}
		if (y+1 < board.length && board[y+1][x] == 'O' && board[y+1][x]!='Z') {
			explore(board, x, y+1);
		}
		if (y-1 >=0 && board[y-1][x] == 'O' && board[y-1][x]!='Z') {
			explore(board, x, y-1);
		}
	}

	// https://leetcode.com/problems/pacific-atlantic-water-flow
	// this is actually like a graph question
	// O(n*m) time and space
	public static List<List<Integer>> pacificAtlantic(int[][] h) {
		// start at ocean, if adjacent cell is higher or same height then it can be reached
		int n = h.length;
		int m = h[0].length;
		boolean[][] vP = new boolean[n][m]; // visited array
		Queue<int[]> q = new LinkedList<>(); // worklist, could be a stack, contents are {x, y}
		for (int y = 0; y < n; y++) {
			q.add(new int[]{0, y}); // add first col
			vP[y][0] = true;
		}
		for (int x = 1; x < m; x++) {
			q.add(new int[]{x, 0}); // add first row
			vP[0][x] = true;
		}
		while (!q.isEmpty()) {
			int[] reached = q.remove();
			int x = reached[0];
			int y = reached[1];
			// go right
			if (x+1 < m && !vP[y][x+1] && h[y][x+1] >= h[y][x]) {
				vP[y][x+1] = true;
				q.add(new int[]{x+1, y});
			}
			// go down
			if (y+1 < n && !vP[y+1][x] && h[y+1][x] >= h[y][x]) {
				vP[y+1][x] = true;
				q.add(new int[]{x, y+1});
			}
			// go left
			if (x-1 >= 0 && !vP[y][x-1] && h[y][x-1] >= h[y][x]) {
				vP[y][x-1] = true;
				q.add(new int[]{x-1, y});
			}
			// go up
			if (y-1 >= 0 && !vP[y-1][x] && h[y-1][x] >= h[y][x]) {
				vP[y-1][x] = true;
				q.add(new int[]{x, y-1});
			}
		}
//		System.out.println(Arrays.deepToString(vP).replace("], ", "]\n"));

		// now flow in from Atlantic
		boolean[][] vA = new boolean[n][m];
		for (int y = 0; y < n; y++) {
			q.add(new int[]{m-1, y}); // add last col
			vA[y][m-1] = true;
		}
		for (int x = m-2; x >= 0; x--) {
			q.add(new int[]{x, n-1}); // add bottom row
			vA[n-1][x] = true;
		}
		while (!q.isEmpty()) {
			int[] reached = q.remove();
			int x = reached[0];
			int y = reached[1];
			// go right
			if (x+1 < m && !vA[y][x+1] && h[y][x+1] >= h[y][x]) {
				vA[y][x+1] = true;
				q.add(new int[]{x+1, y});
			}
			// go down
			if (y+1 < n && !vA[y+1][x] && h[y+1][x] >= h[y][x]) {
				vA[y+1][x] = true;
				q.add(new int[]{x, y+1});
			}
			// go left
			if (x-1 >= 0 && !vA[y][x-1] && h[y][x-1] >= h[y][x]) {
				vA[y][x-1] = true;
				q.add(new int[]{x-1, y});
			}
			// go up
			if (y-1 >= 0 && !vA[y-1][x] && h[y-1][x] >= h[y][x]) {
				vA[y-1][x] = true;
				q.add(new int[]{x, y-1});
			}
		}
//		System.out.println("\n"+Arrays.deepToString(vA).replace("], ", "]\n"));

		// return the union of vP and vA
		List<List<Integer>> r = new ArrayList<>();
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < m; x++) {
				if (vP[y][x] && vA[y][x]) r.add(Arrays.asList(y,x));
			}
		}
		return r;
	}
	// makes wrong assumption: water can only flow up or left to Pacific and down or right to Atlantic
	public List<List<Integer>> pacificAtlanticWrong(int[][] h) {
		// Initially thought each cell cares about max value above or left (to get to Pacific)
		// and the max value below or right (to get to Atlantic). But water can't flow uphill.

		// brute force: for each cell, find the max value in path to A and path to P
		// ^ would involve a ton of recalculating
		// matrix of obstacles to P & additional one for obstacles to A

		// bottom left and upper right corners always can reach both
		int n = h.length;
		int m = h[0].length;

		// p[y][x] is the max obstacle height in the lowest path to Pacific Ocean
		int[][] p = new int[n][m];
		// obstacle height to get to pacific is 0 from first row and col
		for (int y = 1; y < n; y++) {
			for (int x = 1; x < m; x++) {
				// water can't flow uphill, so use MAX_VALUE
				int upOb = h[y-1][x] > h[y][x] ? Integer.MAX_VALUE : Math.max(p[y-1][x], h[y-1][x]);
				int leftOb = h[y][x-1] > h[y][x] ? Integer.MAX_VALUE : Math.max(p[y][x-1], h[y][x-1]);
				p[y][x] = Math.min(upOb, leftOb);
			}
		}

		// a[y][x] is the max obstacle height in the lowest path to Atlantic Ocean
		int[][] a = new int[n][m];
		// obstacle height to get to atlantic is 0 from last row and col
		for (int y = n-2; y >= 0; y--) {
			for (int x = m-2; x >= 0; x--) {
				int downOb = h[y+1][x] > h[y][x] ? Integer.MAX_VALUE : Math.max(a[y+1][x], h[y+1][x]);
				int rightOb = h[y][x+1] > h[y][x] ? Integer.MAX_VALUE : Math.max(a[y][x+1], h[y][x+1]);
				a[y][x] = Math.min(downOb, rightOb);
			}
		}

		List<List<Integer>> r = new ArrayList<>();
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < m; x++) {
				if (h[y][x] >= a[y][x] && h[y][x] >= p[y][x]) {
					r.add(Arrays.asList(y, x));
				}
			}
		}
		return r;
	}

	// https://leetcode.com/problems/dungeon-game  - similar to: uniquePathsWithObstacles
	// O(n*m) time
	// bottom up dp is best solution: cannot determine health without knowing how much health loss is waiting for in
	// the future, so start at the end
	public static int calculateMinimumHP(int[][] d) {
		int y = d.length;
		int x = d[0].length;
		// d[y][x] will hold the required health at this point to end with health 1
		d[y-1][x-1] = Math.max(1, -d[y-1][x-1] + 1);
		for (int i = y-2; i >= 0; i--) { // right col
			d[i][x-1] = Math.max(1, d[i+1][x-1] - d[i][x-1]);
		}
		for (int i = x-2; i >= 0; i--) { // bottom row
			d[y-1][i] = Math.max(1, d[y-1][i+1] - d[y-1][i]);
		}
		for (int i = y-2; i >= 0; i--) {
			for (int j = x-2; j >= 0; j--) {
				int need = Math.min(d[i+1][j], d[i][j+1]) - d[i][j];
				if (need <= 0) need = 1; // would be dead, minimum health required is 1
				d[i][j] = need;
			}
		}
//		System.out.println(Arrays.deepToString(d).replace("], ", "]\n"));
		return d[0][0];
	}
	// recursive solution that tries all possible paths (brute force), runtime 2^(x+y) - 2 choices at each box
	static int c = 0;
	public static int calculateMinimumHPRecu(int[][] dungeon) {
		c=0;
		if (dungeon.length == 1 && dungeon[0].length == 1) return Math.max(1, -dungeon[0][0] + 1);
		int right = Integer.MIN_VALUE;
		if (1 < dungeon[0].length)
			right = calculateMinimumHP(dungeon, 1, 0, dungeon[0][0], dungeon[0][0]);
		int down = Integer.MIN_VALUE;
		if (1 < dungeon.length)
			down = calculateMinimumHP(dungeon, 0, 1, dungeon[0][0], dungeon[0][0]);
		System.out.println("Calls: " + c);
		return Math.max(1, -Math.max(right, down) + 1);
	}
	// returns the lowest enroute health to the given position (x,y) in dungeon
	// right and down != down and right, will walk all possible paths - possible to add memoization
	private static int calculateMinimumHP(int[][] dungeon, int x, int y, int sum, int min) {
		c++;
		sum += dungeon[y][x];
		min = Math.min(min, sum);
		if (x == dungeon[0].length - 1 && y == dungeon.length - 1) {
			// terminate
			return min;
		} else if (x+1 < dungeon[0].length && y+1 < dungeon.length) {
			// go down and right
			int right = calculateMinimumHP(dungeon, x+1, y, sum, min);
			int down = calculateMinimumHP(dungeon, x, y+1, sum, min);
			return Math.max(right, down);
		} else if (y+1 < dungeon.length) {
			// only go down
			return calculateMinimumHP(dungeon, x, y+1, sum, min);
		} else {
			// only go right
			return calculateMinimumHP(dungeon, x+1, y, sum, min);
		}
	}
	// Does not work since we are trying to maximize the minimum cost at each step, if the real solution is going to be
	// less than what we've been optimizing for at the beginning, it may be possible to reach a position with a larger current sum.
	public static int calculateMinimumHPWrong(int[][] dungeon) {
		int y = dungeon.length;
		int x = dungeon[0].length;
		int[][] sums = new int[y][x]; // save the current sum to get to each coord; could do this with dungeon to save space
		int[][] mins = new int[y][x]; // save the lowest value to get to each coord (want to maximize this)
		sums[0][0] = dungeon[0][0];
		mins[0][0] = dungeon[0][0];
		for (int i = 1; i < x; i++) { // top row; only one way to reach each spot
			sums[0][i] = sums[0][i-1] + dungeon[0][i];
			mins[0][i] = Math.min(mins[0][i-1], sums[0][i]);
		}
		for (int i = 1; i < y; i++) { // first col
			sums[i][0] = sums[i-1][0] + dungeon[i][0];
			mins[i][0] = Math.min(mins[i-1][0], sums[i][0]);
		}
		// maximize the score at each spot
		for (int i = 1; i < y; i++) {
			for (int j = 1; j < x; j++) {
//				int prevSum = mins[i-1][j] > mins[i][j-1] ? sums[i-1][j] : sums[i][j-1];
				int prevSum = Math.max(sums[i-1][j], sums[i][j-1]);
				sums[i][j] = dungeon[i][j] + prevSum;
				int prevMin = Math.max(mins[i-1][j], mins[i][j-1]); // arrive at current spot from either above or left
				mins[i][j] = Math.min(prevMin, sums[i][j]);
			}
		}
		System.out.println(Arrays.deepToString(sums).replace("], ", "]\n"));
		System.out.println();
		System.out.println(Arrays.deepToString(mins).replace("], ", "]\n"));
		return Math.max(1, -mins[y-1][x-1]+1); // need at least score of 1 at end since 0=dead
	}

	// https://leetcode.com/problems/remove-covered-intervals
	// brute O(n^2) time, better O(n logn) using sorting
	public static int removeCoveredIntervals(int[][] intervals) {
		int removed = 0;
		// intervals that start at the same location cannot both exist in final combined result
		// fancy comparator that sorts increasing low intervals and decreasing high intervals for equal lows
		Arrays.sort(intervals, (a,b) -> a[0]==b[0] ? b[1]-a[1] : a[0]-b[0]);
		int prevMin = intervals[0][0];
		int prevMax = intervals[0][1];
		for (int i = 1; i < intervals.length; i++) {
			int[] interval = intervals[i];
			int min = interval[0];
			int max = interval[1];
			if (min == prevMin || max <= prevMax) {
				removed++;
			}
			prevMin = min;
			prevMax = Math.max(prevMax, max);
		}
		return intervals.length-removed;
	}

	// https://leetcode.com/problems/game-of-life/
	// O(n*m) time, no extra space, in-place
	public static void gameOfLife(int[][] board) {
		// only 1st bit of int is used, use the 2nd bit to hold next state, applying the 4 rules
		// current state: board[y][x] & 1 , future state: board[y][x] >> 1
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				int count = neighborCountOneLowBit(board, x, y);
				// set 2nd bit (future state) to 1, default future state is 0
				if ((board[y][x] & 1) == 0 && count == 3) {
					board[y][x] |= 0b10; // could also set board[y][x] = 2 since we know current state is 0
				} else if ((board[y][x] & 1) == 1 && count >= 2 && count <= 3) {
					board[y][x] |= 0b10; // could also set = 3
				}
			}
		}
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				board[y][x] = board[y][x] >> 1;
			}
		}
	}
	// O(n*m) time and space
	public static void gameOfLifeOriginal(int[][] board) {
		int[][] neighborCounts = new int[board.length][board[0].length];
		// save a new board with the counts of each neighbor, can't update as we go since will impact subsequent square results
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				// not most efficient possible since will be overlapping when counting neighbor values, however this is a constant with max of 8
				neighborCounts[y][x] = neighborCount(board, x, y, 1);
			}
		}
		// update board applying the 4 rules
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[0].length; x++) {
				if (board[y][x] == 0 && neighborCounts[y][x] == 3) {
					board[y][x] = 1;
				} else if (board[y][x] == 1 && (neighborCounts[y][x] < 2 || neighborCounts[y][x] > 3)) {
					board[y][x] = 0;
				}
			}
		}
	}
	// returns a count (0 to 8) of the neighbors of the given (x,y) in board b with given value
	private static int neighborCount(int[][] b, int x, int y, int value) {
		int result = 0;
		// count 3 above
		if (y-1 >= 0) {
			for (int i = -1; i < 2; i++) {
				if (x+i >= 0 && x+i < b[0].length && b[y-1][x+i] == value) result++;
			}
		}
		// count 3 below
		if (y+1 < b.length) {
			for (int i = -1; i < 2; i++) {
				if (x+i >= 0 && x+i < b[0].length && b[y+1][x+i] == value) result++;
			}
		}
		if (x-1 >= 0 && b[y][x-1] == value) result++; // count left
		if (x+1 < b[0].length && b[y][x+1] == value) result++; // count right
		return result;
	}
	// returns a count (0 to 8) of the neighbors of the given (x,y) in board b with 1 at lowest bit
	private static int neighborCountOneLowBit(int[][] b, int x, int y) {
		int result = 0;
		// count 3 above
		if (y-1 >= 0) {
			for (int i = -1; i < 2; i++) {
				if (x+i >= 0 && x+i < b[0].length && (b[y-1][x+i] & 1) == 1) result++;
			}
		}
		// count 3 below
		if (y+1 < b.length) {
			for (int i = -1; i < 2; i++) {
				if (x+i >= 0 && x+i < b[0].length && (b[y+1][x+i] & 1) == 1) result++;
			}
		}
		if (x-1 >= 0 && (b[y][x-1] & 1) == 1) result++; // count left
		if (x+1 < b[0].length && (b[y][x+1] & 1) == 1) result++; // count right
		return result;
	}

	// https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
	// binary search solution, O(n * log(max-min))
	public static int kthSmallest(int[][] matrix, int k) {
		int n = matrix.length;
		int lo = matrix[0][0], hi = matrix[n - 1][n - 1];
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2; // mid is not an index, it is the middle value, need to search for it
			int count = getLessEqual(matrix, mid);
			if (count < k) lo = mid + 1; // target is in upper half of elements between lo and hi
			else hi = mid - 1;
		}
		return lo;
	}
	// returns count of numbers in sorted matrix <= mid, O(n) runtime where n is matrix.length
	private static int getLessEqual(int[][] matrix, int target) {
		int res = 0;
		int n = matrix.length, x = n - 1, y = 0; // start upper right, move toward left bottom
		while (x >= 0 && y < n) {
			if (matrix[y][x] > target) // move left
				x--;
			else { // move down
				res += x + 1;
				y++;
			}
		}
		return res;
	}
	// O(min(n,k)+klog(min(n,k))) runtime, O(n) space - k could be n^2 worst case
	public static int kthSmallest2(int[][] matrix, int k) {
		int n = matrix.length;
		PriorityQueue<Tuple> pq = new PriorityQueue<>();
		for (int j = 0; j <= n-1; j++) pq.offer(new Tuple(j, 0, matrix[0][j])); // add top of columns to queue to start
		for (int i = 0; i < k-1; i++) {
			Tuple t = pq.poll(); // remove from queue
			if (t.y == n-1) continue; // finished with current column
			pq.offer(new Tuple(t.x, t.y+1, matrix[t.y+1][t.x])); // move down, insert number below into queue
		}
		return pq.poll().val;
	}
	static class Tuple implements Comparable<Tuple> {
		int x, y, val;
		public Tuple (int x, int y, int val) {
			this.x = x;
			this.y = y;
			this.val = val;
		}
		@Override
		public int compareTo (Tuple that) {
			return this.val - that.val;
		}
	}
	// O(n^2) runtime, O(n) space
	public static int kthSmallestSlow(int[][] matrix, int k) {
		int n = matrix.length;
		if (k > n*n) return -1;
		int[] cols = new int[n]; // how far down each column we have explored
		int count = 1;
		cols[0] = 1;
		int min = matrix[0][0];
		while (count < k) {
			// can move down in any of the columns, find the next min in the columns
			min = Integer.MAX_VALUE;
			int minI = -1;
			for (int i = 0; i < n; i++) {
				int colY = cols[i];
				if (colY >= n) continue; // already looked at all numbers in this col
				if (matrix[colY][i] < min) {
					min = matrix[colY][i];
					minI = i;
				}
				if (colY == 0) break; // next cols are all larger, don't need to look at them
			}
			cols[minI]++;
			count++;
		}
		return min;
	}

	// https://leetcode.com/problems/number-of-islands/
	// O(n*m) time and space
	public static int numIslands(char[][] grid) {
		int xLen = grid[0].length;
		int yLen = grid.length;
		int islands = 0;
		// scan array, when find land explore the land, when exploration terminates, increment island count
		boolean[][] b = new boolean[yLen][xLen]; // unexplored land = false, explored land = true
		for (int i = 0; i < xLen; i++) {
			for (int j = 0; j < yLen; j++) {
				if (!b[j][i] && grid[j][i] == '1') { // explore the land with BFS
					Queue<int[]> search = new LinkedList<>();
					search.add(new int[]{i, j});
					b[j][i] = true;
					while (!search.isEmpty()) {
						int[] coords = search.remove();
						int x = coords[0];
						int y = coords[1];
						// up, down, left, right
						if (y-1 >= 0 && !b[y-1][x] && grid[y-1][x] == '1') {
							search.add(new int[]{x, y-1});
							b[y-1][x] = true; // important in case with mostly land to avoid duplicate exploration
						}
						if (y+1 < yLen && !b[y+1][x] && grid[y+1][x] == '1') {
							search.add(new int[]{x, y+1});
							b[y+1][x] = true;
						}
						if (x-1 >= 0 && !b[y][x-1] && grid[y][x-1] == '1') {
							search.add(new int[]{x-1, y});
							b[y][x-1] = true;
						}
						if (x+1 < xLen && !b[y][x+1] && grid[y][x+1] == '1') {
							search.add(new int[]{x+1, y});
							b[y][x+1] = true;
						}
					}
					islands++;
				}
			}
		}
		return islands;
	}

	// https://leetcode.com/problems/shift-2d-grid/
	//  [[1,2,3],                  [[5,6,1],
	//   [4,5,6]]   shift 2   ->    [2,3,4]]
	public static List<List<Integer>> shiftGrid(int[][] grid, int k) {
		// thoughts: add all to single array/list, shift and rebuild
		// I want to do this in place

		int m = grid[0].length; // width, grid is m x n 	// 3
		int n = grid.length; // height 						// 2
		List<List<Integer>> result = new ArrayList<>(n);
		List<Integer> row = new ArrayList<>(m);

		int size = n*m; // 6
		k = k % size; // 2
		int start = size-k; // 4 (zero-based starting location)

		for (int i = 0; i < size; i++) {
			// starting location with first position being 1 and last position being size
			int pos = (i + start) % size; // 4,5,0,1,2,3

			int x = pos % m; // horizontal position
			int y = pos / m; // vertical position
			// (x,y) should be (1,1) > (2,1) > (0,0) > (1,0) > (2,0) > (0,1)
			row.add(grid[y][x]);
			if (row.size() == m) {
				result.add(row);
				if (result.size() < n) {
					row = new ArrayList<>(m);
				}
			}
		}
		return result;
	}

	// https://leetcode.com/problems/search-a-2d-matrix-ii
	// Given 2D array contains rows that are sorted and cols that are sorted. Duplicates may occur.
	// worstcase runtime O(n+m)
	public static boolean contains(int[][] arr, int target) {
		//	Pick upper right number; if greater, go left, if less than, go down

		// ensure arr not zero length
		int x = arr[0].length-1; // col
		int y = 0; // row

		while (x >= 0 && y < arr.length) {
			int mid = arr[y][x];

			if (mid == target) {
				return true;
			} else if (mid > target) {
				x--;
			} else {
				y++;
			}
		}
		return false;
	}

	// https://leetcode.com/problems/set-matrix-zeroes/
	// Given m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
	// O(n*m) time and space
	public static void setZeroes(int[][] matrix) {
		Set<Integer> zeroXs = new HashSet<>();
		Set<Integer> zeroYs = new HashSet<>();
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				if (matrix[y][x] == 0) {
					zeroXs.add(x);
					zeroYs.add(y);
				}
			}
		}
		// set cols to 0
		for (Integer x : zeroXs) {
			for (int y = 0; y < matrix.length; y++) {
				matrix[y][x] = 0;
			}
		}
		// set rows to 0
		for (Integer y : zeroYs) {
			for (int x = 0; x < matrix[0].length; x++) {
				matrix[y][x] = 0;
			}
		}
	}
	// O(n*m) time and O(1) extra space
	public static void setZeroesEfficient(int[][] matrix) {
		// use first row and first col as flags indicating the row needs to be set to 0
		// matrix[0][0] == 0 indicates that first col has a zero and should be zero'd
		// firstRowZero == true indicates same as above for first row
		boolean firstRowZero = Arrays.stream(matrix[0]).anyMatch(i -> i == 0);
		for (int y = 1; y < matrix.length; y++) { // skip first row
			for (int x = 0; x < matrix[0].length; x++) {
				if (matrix[y][x] == 0) {
					matrix[y][0] = 0; // first col
					matrix[0][x] = 0; // first row
				}
			}
		}
	 	// zero the rows
		for (int y = 1; y < matrix.length; y++) { // can't zero the first row until the end since using it to save the cols to zero
			if (matrix[y][0] == 0) {
				for (int x = 1; x < matrix[0].length; x++) {
					matrix[y][x] = 0;
				}
			}
		}
		// zero the cols
		for (int x = 0; x < matrix[0].length; x++) {
			if (matrix[0][x] == 0) {
				for (int y = 1; y < matrix.length; y++) {
					matrix[y][x] = 0;
				}
			}
		}
		if (firstRowZero) Arrays.fill(matrix[0], 0);
    }

	// returns the winner of the given tic tac toe board. Board is 3x3. If no winner, returns empty string.
	// [[X, X, X],         [[X, O, X],       [[X, O, X],       [[X, O, X],
	//  [O, O, X],  = X     [O, O, ],  = O    [O, , ],  = ""    [O, X, ],  = "X"
	//  [X, O, O]]          [X, O, X]]        [X, O, X]]        [O, O, X]]
	private static String threeEqual(String a, String b, String c) {
		if (a.equals(b) && b.equals(c)) {
			return a;
		} else {
			return "";
		}
	}
	public static String ticTacToeWinner(String[][] board) {
		// rows
		for (int i = 0; i < board.length; i++) {
			String result = threeEqual(board[i][0], board[i][1], board[i][2]);
			if (!result.equals("")) {
				return result;
			}
		}
		// columns
		for (int i = 0; i < board[0].length; i++) {
			String result = threeEqual(board[0][i], board[1][i], board[2][i]);
			if (!result.equals("")) {
				return result;
			}
		}
		// diagonals
		String result = threeEqual(board[0][0], board[1][1], board[2][2]);
		if (!result.equals("")) {
			return result;
		}
		result = threeEqual(board[0][2], board[1][1], board[2][0]);
		if (!result.equals("")) {
			return result;
		}
		return "";
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

	// https://leetcode.com/problems/rotate-image
	public static void rotate(int[][] matrix) {
		// reverse rows, then swap across diagonal that runs lower left to upper right
		for (int[] row : matrix) {
			reverse(row);
		}
		int n = matrix.length; // take advantage of square-ness
		int y = 0;
		for (int x = n-1; x >= 0; x--) {
			for (int i = x-1; i >= 0; i--) { // swap nums in row x that are smaller than x across the diagonal
				int temp = matrix[y][i];
				matrix[y][i] = matrix[n-1-i][n-1-y];
				matrix[n-1-i][n-1-y] = temp;
			}
			y++;
		}
	}
	private static void reverse(int[] a) {
		for (int i = 0; i < a.length/2; i++) {
			int temp = a[i];
			a[i] = a[a.length-1-i];
			a[a.length-1-i] = temp;
		}
	}
}
