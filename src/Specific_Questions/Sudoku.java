package Specific_Questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sudoku {

	// https://leetcode.com/problems/sudoku-solver/
	public static void solveSudokuBacktracing(char[][] b) {
		// use recursive backtracing to place a number, if board is invalid, then backtrack
		System.out.println(Arrays.deepToString(b).replace("], ", "]\n"));
		sudokuBacktracing(b, 0);
//		System.out.println(Arrays.deepToString(b).replace("], ", "]\n"));
	}
	private static boolean sudokuBacktracing(char[][] b, int start) {
		// iterate across board to find next empty box
		for (int i = start; i < 81; i++) {
			int x = i % 9;
			int y = i / 9;
			System.out.printf("looking at (%d,%d), start %d\n", x, y, start);
			if (b[y][x] != '.') continue;

			boolean solved = false;
			boolean valid = false;
			for (int n = 1; n <= 9; n++) {
				// place number
				b[y][x] = (char)(n + '0');
				System.out.printf("Placing %d at (%d,%d), start %d\n", n, x, y, start);
				solved = false;
				valid = isValidSudoku(b);
				if (valid) {
					// explore
					System.out.printf("Exploring, start %d\n", start);
					solved = sudokuBacktracing(b, start+1);
					if (solved) return true; // stop trying, don't mess with board contents
				}
			}
			System.out.printf("Done with (%d,%d), solved=%b, valid=%b, start=%d\n", x, y, solved, valid, start);
			System.out.println(Arrays.deepToString(b).replace("], ", "]\n"));
			// nothing worked in this square, remove number and exit
			b[y][x] = '.';
			return false;
		}
		// completed board, is it solved?
		return isValidSudoku(b);
	}


	public static void solveSudoku(char[][] b) {
		// use process of elimination on row, col, and box just like a human would do
		// int[][] rows, cols, boxes
		// 		rows[0-8] = array of number counts (length 9)
		// 		cols[0-8] = array of number counts (length 9)
		// 		boxes[0-8] = array of number counts (length 9)

		int toFill = 0;
		int[][] rows = new int[9][9];
		int[][] cols = new int[9][9];
		int[][] boxes = new int[9][9]; // box[0] upper left = (0,0), box[1]=(0,3), box[2]=(0,6), box[3]=(2,0)..

		// fill row counts
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				char c = b[y][x];
				if (c == '.') {
					toFill++;
				} else {
					int i = (int) c - '1';
					rows[y][i]++;
				}
			}
		}
		// fill col counts
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				char c = b[y][x];
				if (c != '.') {
					int i = (int) c - '1';
					cols[x][i]++;
				}
			}
		}
		// fill box counts
		int boxI = 0;
		for (int y = 0; y < 9; y+=3) {
			for (int x = 0; x < 9; x+=3) { // across the 3x3 boxes starting at b[y][x]
				for (int j = y; j < y+3; j++) { // within the 3x3 boxes
					for (int i = x; i < x+3; i++) {
						char c = b[j][i];
						if (c != '.') {
							int n = (int) c - '1';
							boxes[boxI][n]++;
						}
					}
				}
				boxI++;
			}
		}
//		System.out.println(Arrays.deepToString(rows).replace("], ", "]\n"));
//		System.out.println(Arrays.deepToString(cols).replace("], ", "]\n"));
//		System.out.println(Arrays.deepToString(boxes).replace("], ", "]\n"));

		boolean advancedSearch = false;

		// solve
		while (toFill > 0) {
			int prevFill = toFill;
			for (int y = 0; y < 9; y++) {
				for (int x = 0; x < 9; x++) {
					if (b[y][x] != '.') continue;
//					System.out.println("checking box (x,y): (" + x+","+y+")");

					// relevant number counts in current row, col, and box
					int[] row = rows[y];
					int[] col = cols[x];
					int boxCoord = x/3 + (y/3)*3; // using int division to get rid of remainder (2nd term = 0,3,or 6)
					int[] box = boxes[boxCoord];

					boolean[] options = getBasicOptions(row, col, box);

					if (advancedSearch) { // much less efficient, do the easy squares 1st before advanced search
						for (int i = 0; i < 9; i++) {
							if (options[i]) {
								// if n is not allowed in other spaces in 3x3 box, then it must be the only choice
								int n = i+1;
								if (isOnlyChoice(n, x, y, b, rows, cols, boxes)) {
									System.out.printf("only choice for (%d,%d) is %d\n", x, y, n);
									options = new boolean[9];
									options[i] = true;
									break;
								}
							}
						}
					}

					// if only single option remains, fill it
					List<Integer> opts = getOptionList(options, x, y);
					if (opts.size() == 0) {
						System.out.println("ERROR, unsolvable board at ("+x+","+y+"), exiting");
						System.out.println(Arrays.deepToString(b).replace("], ", "]\n"));
						toFill = 0;
					} else if (opts.size() == 1) {
						System.out.println("filling box (" + x+","+y+")");
						int option = opts.get(0);
						b[y][x] = (char) (option + '0');
						toFill--;

						row[option-1]++;
						col[option-1]++;
						box[option-1]++;
					}
				}
			}
			if (toFill == prevFill) { // no progress was made after going through entire board
				if (advancedSearch) {
					System.out.println("ERROR can not fill boxes even with advanced search");
					toFill = 0;
				} else {
					System.out.println("can not fill any boxes, enabling advanced search");
					advancedSearch = true;
				}
				System.out.println(Arrays.deepToString(b).replace("], ", "]\n"));
			}
		}
	}
	// returns true if target is the only valid choice at (x,y) because it does not appear in the options lists of other
	// locations in the 3x3 box
	private static boolean isOnlyChoice(int target, int x, int y, char[][] b, int[][] rows, int[][] cols, int[][] boxes) {
		// iterate through boxes in 3x3 box
		System.out.printf("checking if %d is only choice for (%d,%d)\n", target, x, y);
		int boxX = x/3*3; // upper left coords of 3x3 box
		int boxY = y/3*3;
		for (int j = boxX; j < boxX+3; j++) {
			for (int k = boxY; k < boxY+3; k++) {
				if (j == x && k == y) continue;
				if (b[k][j] == '.') {
					boolean[] options = getBasicOptions(j, k, rows, cols, boxes);
					List<Integer> opts = getOptionList(options, j, k);
					if (opts.contains(target)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	// returns bool array where int i+1 is a valid option at space (x,y) if result[i] is true
	// row, col, and box contain the number counts for the row, col, and box (x,y) is in
	private static boolean[] getBasicOptions(int x, int y, int[][] rows, int[][] cols, int[][] boxes) {
		int[] row = rows[y];
		int[] col = cols[x];
		int boxCoord = x/3 + (y/3)*3; // using int division to get rid of remainder (2nd term = 0,3,or 6)
		int[] box = boxes[boxCoord];
		return getBasicOptions(row, col, box);
	}
	private static boolean[] getBasicOptions(int[] row, int[] col, int[] box) {
		boolean[] options = new boolean[9];
		Arrays.fill(options, true); // true = guessable, false = not guessable

		// process of elimination for row, col, box
		for (int i = 0; i < row.length; i++) {
			if (row[i] != 0) { // count of value i+1 is non-zero in this row, not an option to fill
				options[i] = false;
			}
		}
		for (int i = 0; i < col.length; i++) {
			if (col[i] != 0) {
				options[i] = false;
			}
		}
		for (int i = 0; i < box.length; i++) {
			if (box[i] != 0) {
				options[i] = false;
			}
		}

		return options;
	}
	// returns the guessable numbers (1-9) in the given array of options
	private static List<Integer> getOptionList(boolean[] options, int x, int y) { // does a little more work, but prints options
		List<Integer> result = new ArrayList<>(9);
		for (int i = 0; i < 9; i++) {
			if (options[i]) {
				result.add(i+1);
			}
		}
		System.out.printf("options for (%d,%d): %s\n", x, y, result);
		return result;
	}
	// returns the single guessable number (1-9) in the given array of options, otherwise -1
//	private static int getOption(boolean[] options) {
//		int option = -1;
//		for (int i = 0; i < 9; i++) {
//			if (options[i]) {
//				if (option != -1) {
//					// multiple options, return error
//					return -1;
//				}
//				option = i+1;
//			}
//		}
//		return option;
//	}


	// runtime is technically constant since board size fixed
	// if size of b was variable, runtime O(n^2) space O(1)
	// https://leetcode.com/problems/valid-sudoku/
	public static boolean isValidSudoku(char[][] b) {
		// iterate across rows
		for (int i = 0; i < 9; i++) {
			char[] row = b[i];
			// save count of numbers (1-9) at the index equal to their value-1, could be bool[] since only 1 allowed
			int[] buckets = new int[9];
			for (char c : row) {
				if (c != '.') {
					int index = (int) c - '1';
					if (buckets[index] > 0)	return false;
					buckets[index]++;
				}
			}
		}
		// iterate across cols
		for (int x = 0; x < 9; x++) {
			int[] buckets = new int[9];
			for (int y = 0; y < 9; y++) {
				char c = b[y][x];
				if (c != '.') {
					int index = (int) c - '1';
					if (buckets[index] > 0)	return false;
					buckets[index]++;
				}
			}
		}
		// check the nine 3x3 boxes
		for (int i = 0; i < 9; i+=3) { // 0 -> 3 -> 6
			for (int j = 0; j < 9; j+=3) { // 0 -> 3 -> 6
				// b[i][j] = top left corner of 3x3 box
				int[] buckets = new int[9];
				for (int y = i; y < i+3; y++) {
					for (int x = j; x < j+3; x++) {
						char c = b[y][x];
						if (c != '.') {
							int index = (int) c - '1';
							if (buckets[index] > 0)	return false;
							buckets[index]++;
						}
					}
				}
			}
		}
		return true;
	}
}
