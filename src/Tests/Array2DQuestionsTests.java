package Tests;

import General_Questions.Array2DQuestions;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Array2DQuestionsTests {

	@Test
	public void testSurroundedRegions() {
		char[][] board = new char[][]{{'X','X','X','X'},{'X','O','O','X'},{'X','X','O','X'},{'X','O','X','X'}};
		Array2DQuestions.solve(board);
		assertArrayEquals(new char[][]{{'X','X','X','X'},{'X','X','X','X'},{'X','X','X','X'},{'X','O','X','X'}}, board);
	}

	@Test
	public void testPacificAtlantic() {
		List<List<Integer>> expected = new ArrayList<>();
		expected.add(Arrays.asList(0,4));
		expected.add(Arrays.asList(1,3));
		expected.add(Arrays.asList(1,4));
		expected.add(Arrays.asList(2,2));
		expected.add(Arrays.asList(3,0));
		expected.add(Arrays.asList(3,1));
		expected.add(Arrays.asList(4,0));
		assertEquals(expected, Array2DQuestions.pacificAtlantic(new int[][]{{1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}}));

		expected.clear();
		expected.add(Arrays.asList(0,2));
		expected.add(Arrays.asList(1,0));
		expected.add(Arrays.asList(1,1));
		expected.add(Arrays.asList(1,2));
		expected.add(Arrays.asList(2,0));
		expected.add(Arrays.asList(2,1));
		expected.add(Arrays.asList(2,2));
		assertEquals(expected, Array2DQuestions.pacificAtlantic(new int[][]{{1,2,3},{8,9,4},{7,6,5}}));
	}

	@Test
	public void testCalculateMinimumHP() {
		assertEquals(7, Array2DQuestions.calculateMinimumHP(new int[][]{{-2,-3,3},{-5,-10,1},{10,30,-5}}));
		assertEquals(6, Array2DQuestions.calculateMinimumHP(new int[][]{{-2,-3,3,6,7,7},{-5,3,7,9,1,-30},{10,30,-5,-30,99,20}}));
		assertEquals(6, Array2DQuestions.calculateMinimumHP(new int[][]{{-2,-3,3,6,7,7},{-5,3,7,9,1,-30},{10,30,-5,-30,99,20},{10,30,-5,-30,99,20},{10,30,-5,-30,99,20},{10,30,-5,-30,99,20}}));
		assertEquals(1, Array2DQuestions.calculateMinimumHP(new int[][]{{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}}));
		assertEquals(3, Array2DQuestions.calculateMinimumHP(new int[][]{{1,-3,3},{0,-2,0},{-3,-3,-3}}));
		assertEquals(2, Array2DQuestions.calculateMinimumHP(new int[][]{{1,-3,3},{0,-2,0}}));
		assertEquals(3, Array2DQuestions.calculateMinimumHP(new int[][]{{1,-3,3}}));
		assertEquals(1, Array2DQuestions.calculateMinimumHP(new int[][]{{0}}));
		assertEquals(1, Array2DQuestions.calculateMinimumHP(new int[][]{{100}}));
	}

	@Test
	public void testRemoveCoveredIntervals() {
		assertEquals(1, Array2DQuestions.removeCoveredIntervals(new int[][]{{1,4},{3,6},{1,8}}));
		assertEquals(2, Array2DQuestions.removeCoveredIntervals(new int[][]{{1,4},{3,6},{2,8}}));
		assertEquals(1, Array2DQuestions.removeCoveredIntervals(new int[][]{{1,4},{2,3}}));
	}

	@Test
	public void testGameOfLife() {
		int[][] a = new int[][]{{0,1,0},{0,0,1},{1,1,1},{0,0,0}};
		Array2DQuestions.gameOfLife(a);
		assertArrayEquals(new int[][]{{0,0,0},{1,0,1},{0,1,1},{0,1,0}}, a);

		a = new int[][]{{1,1},{1,0}};
		Array2DQuestions.gameOfLife(a);
		assertArrayEquals(new int[][]{{1,1},{1,1}}, a);
	}

	@Test
	public void testKthSmallest() {
		int[][] a = new int[][]{{1,3, 7},
								{2,5, 9},
								{4,11,12}};
		assertEquals(5, Array2DQuestions.kthSmallest(a, 5));
		int[][] b = new int[][]{{5,5, 5},
						{5,5, 9},
						{5,11,12}};
		assertEquals(9, Array2DQuestions.kthSmallest(b, 7));
		int[][] c = new int[][]{{1, 5, 9},
						{10,11,13},
						{12,13,15}};
		assertEquals(13, Array2DQuestions.kthSmallest(c, 8));
		assertEquals(1, Array2DQuestions.kthSmallest(c, 1));
		assertEquals(9, Array2DQuestions.kthSmallest(c, 3));
		assertEquals(13, Array2DQuestions.kthSmallest(c, 7));
		assertEquals(12, Array2DQuestions.kthSmallest(c, 6));
		int[][] d = new int[][]{{-5}};
		assertEquals(-5, Array2DQuestions.kthSmallest(d, 1));
	}

	@Test
	public void testNumIslands() {
		char[][] g = new char[][]{
			{'1', '1', '1', '1', '0'},
			{'1', '1', '0', '1', '0'},
			{'1', '1', '0', '0', '0'},
			{'0', '0', '0', '0', '0'}
		};
		assertEquals(1, Array2DQuestions.numIslands(g));
		g = new char[][]{
				{'1', '1', '0', '1', '0'},
				{'1', '1', '0', '1', '1'},
				{'1', '1', '0', '0', '0'},
				{'0', '1', '0', '0', '0'}
		};
		assertEquals(2, Array2DQuestions.numIslands(g));
		g = new char[][]{
				{'1', '1', '0', '0', '0'},
				{'1', '1', '0', '0', '0'},
				{'0', '0', '1', '0', '0'},
				{'0', '0', '0', '1', '1'}
		};
		assertEquals(3, Array2DQuestions.numIslands(g));
		g = new char[][]{{'1'}};
		assertEquals(1, Array2DQuestions.numIslands(g));
		g = new char[][]{{'1', '0', '1', '0', '1'}};
		assertEquals(3, Array2DQuestions.numIslands(g));
		g = new char[][]{
				{'1', '1'},
				{'1', '1'},
		};
		assertEquals(1, Array2DQuestions.numIslands(g));
	}

	@Test
	public void testShiftGrid() {
		int[][] g = {{1,2,3},{4,5,6}};
		List<List<Integer>> result = new ArrayList<>();
		result.add(Arrays.asList(5,6,1));
		result.add(Arrays.asList(2,3,4));
		assertEquals(Array2DQuestions.shiftGrid(g, 2), result);
		int[][] g2 = {{3,8,1,9},{19,7,2,5},{4,6,11,10},{12,0,21,13}};
		result = new ArrayList<>();
		result.add(Arrays.asList(12,0,21,13));
		result.add(Arrays.asList(3,8,1,9));
		result.add(Arrays.asList(19,7,2,5));
		result.add(Arrays.asList(4,6,11,10));
		assertEquals(Array2DQuestions.shiftGrid(g2, 4), result);
		int[][] g3 = {{1,2},{4,5}};
		result = new ArrayList<>();
		result.add(Arrays.asList(1,2));
		result.add(Arrays.asList(4,5));
		assertEquals(Array2DQuestions.shiftGrid(g3, 4), result);
		assertEquals(Array2DQuestions.shiftGrid(g3, 8), result);
		result = new ArrayList<>();
		result.add(Arrays.asList(4,5));
		result.add(Arrays.asList(1,2));
		assertEquals(Array2DQuestions.shiftGrid(g3, 6), result);
	}

	@Test
	public void testContains() {
		int[][] in = new int[][] {{2,4,5}, {3,6,8}, {7,9,10}};
		assertTrue(Array2DQuestions.contains(in, 9));
		assertFalse(Array2DQuestions.contains(in, 11));
		assertFalse(Array2DQuestions.contains(in, 1));

		in = new int[][] {{2,4,5}, {6,6,16}, {9,10,18}};
		assertTrue(Array2DQuestions.contains(in, 9));
		assertFalse(Array2DQuestions.contains(in, 11));
		assertFalse(Array2DQuestions.contains(in, 1));

		in = new int[][] {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
		assertTrue(Array2DQuestions.contains(in, 5));
		assertFalse(Array2DQuestions.contains(in, 20));
	}

	@Test
	public void testSetZeros() {
		int[][] in = new int[][] {{0,1,0}, {1,1,1}, {1,1,1}};
		int[][] expected = new int[][] {{0,0,0}, {0,1,0}, {0,1,0}};
		Array2DQuestions.setZeroesEfficient(in);
		System.out.println(Arrays.deepToString(expected).replace("], ", "]\n"));
		System.out.println(Arrays.deepToString(in).replace("], ", "]\n"));
		assertArrayEquals(expected, in);
//		for (int i = 0; i < in.length; i++) {
//			assertArrayEquals(in[i], expected[i]);
//		}

		in = new int[][] {{1,1,1}, {1,0,1}, {1,1,1}};
		expected = new int[][] {{1,0,1}, {0,0,0}, {1,0,1}};
		Array2DQuestions.setZeroesEfficient(in);
		System.out.println(Arrays.deepToString(expected).replace("], ", "]\n"));
		System.out.println(Arrays.deepToString(in).replace("], ", "]\n"));
		assertArrayEquals(expected, in);
//		for (int i = 0; i < in.length; i++) {
//			assertArrayEquals(in[i], expected[i]);
//		}
	}

	@Test
	public void testTicTacToeWinner() {
		String[][] in = new String[][] {{"X", "X", "X"}, {"O", "O", "X"}, {"X", "O", "O"}};
		assertEquals(Array2DQuestions.ticTacToeWinner(in), "X");

		in = new String[][] {{"X", "O", "X"}, {"O", "O", ""}, {"X", "O", "X"}};
		assertEquals(Array2DQuestions.ticTacToeWinner(in), "O");

		in = new String[][] {{"X", "O", "X"}, {"O", "", ""}, {"X", "O", "X"}};
		assertEquals(Array2DQuestions.ticTacToeWinner(in), "");

		in = new String[][] {{"X", "O", "X"}, {"O", "X", ""}, {"O", "O", "X"}};
		assertEquals(Array2DQuestions.ticTacToeWinner(in), "X");
	}
}
