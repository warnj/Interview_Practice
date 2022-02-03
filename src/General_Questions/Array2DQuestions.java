package General_Questions;

import java.util.List;
import java.util.*;

public class Array2DQuestions {

	public static void main(String[] args) {
		boolean[][] explored = new boolean[5][3];
		System.out.println(explored.length);
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

	// Given 2D array contains rows that are sorted and cols that are sorted. Duplicates may occur.
	// worstcase runtime O(n+m)
	public static boolean contains(int[][] arr, int target) {
		//	Pick upper right number
		//	If greater, go left
		//	If less than, go down

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
	// Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
	public static void setZeroes(int[][] matrix) {
		List<Integer> rows = new ArrayList<>();
		List<Integer> cols = new ArrayList<>();

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					rows.add(i);
					cols.add(j);
				}
			}
		}

		for (int n = 0; n < rows.size(); n++) {
			int i = rows.get(n);
			int j = cols.get(n);
			for (int k = 0; k < matrix.length; k++) {
				matrix[k][j] = 0;
			}
			for (int k = 0; k < matrix[0].length; k++) {
				matrix[i][k] = 0;
			}
		}
	}
//	public static void setZeroesEfficient(int[][] matrix) {
//		// use first row and first col as flags indicating the row needs to be set to 0
//		for (int i = 0; i < matrix.length; i++) {
//			for (int j = 0; j < matrix[0].length; j++) {
//				if (matrix[i][j] == 0) {
//					matrix[i][0] = 0;
//					matrix[0][j] = 0;
//				}
//			}
//		}
//
//		for (int i = 0; i < matrix.length; i++) {
//			for (int j = 0; j < matrix[0].length; j++) {
//				if (matrix[i][0] == 0 || matrix[0][j] == 0) {
//					matrix[i][j] = 0;
//				}
//			}
//		}

	// zero the rows
//		for (int i = 0; i < matrix.length; i++) {
//			if (matrix[i][0] == 0) {
//				for (int k = 0; k < matrix[i].length; k++) {
//					matrix[i][k] = 0;
//				}
//			}
//		}
//
//		// zero the cols
//		for (int i = 0; i < matrix[0].length; i++) {
//			if (matrix[0][i] == 0) {
//				for (int k = 0; k < matrix.length; k++) {
//					matrix[k][i] = 0;
//				}
//			}
//		}
//    }

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
}
