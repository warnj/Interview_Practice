package Tests;

import General_Questions.Array2DQuestions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Array2DQuestionsTests {

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
	}

	@Test
	public void testSetZeros() {
		int[][] in = new int[][] {{0,1,0}, {1,1,1}, {1,1,1}};
		int[][] expected = new int[][] {{0,0,0}, {0,1,0}, {0,1,0}};
		Array2DQuestions.setZeroes(in);
		for (int i = 0; i < in.length; i++) {
			assertArrayEquals(in[i], expected[i]);
		}

		in = new int[][] {{1,1,1}, {1,0,1}, {1,1,1}};
		expected = new int[][] {{1,0,1}, {0,0,0}, {1,0,1}};
		Array2DQuestions.setZeroes(in);
		for (int i = 0; i < in.length; i++) {
			assertArrayEquals(in[i], expected[i]);
		}
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
