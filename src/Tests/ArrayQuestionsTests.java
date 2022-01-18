package Tests;

import General_Questions.ArrayQuestions;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayQuestionsTests {

	@Test
	public void testUniqueNumber() {
		assertEquals(ArrayQuestions.uniqueNumber(new int[]{1,7,1,1,1}), 7);
		assertEquals(ArrayQuestions.uniqueNumber(new int[]{5,3,3,3,3,3}), 5);
		assertEquals(ArrayQuestions.uniqueNumber(new int[]{-99,-5,-99}), -5);
		assertEquals(ArrayQuestions.uniqueNumber(new int[]{10000,10000,10000,10000,10000,999}), 999);

		assertEquals(ArrayQuestions.uniqueNumber2(new int[]{1,1,2,2,3,3,4}), 4);
		assertEquals(ArrayQuestions.uniqueNumber2(new int[]{2,2,1,3,1,3,4}), 4);

		assertEquals(ArrayQuestions.uniqueNumber3(new int[]{1,1,2,2,3,3,4}), 4);
		assertEquals(ArrayQuestions.uniqueNumber3(new int[]{2,2,1,3,1,3,4}), 4);
	}

	@Test
	public void testMinSumLengths() {
		assertEquals(ArrayQuestions.minSumOfLengths(new int[]{3,2,2,4,3}, 3), 2);
		assertEquals(ArrayQuestions.minSumOfLengths(new int[]{7,3,4,7}, 7), 2);
		assertEquals(ArrayQuestions.minSumOfLengths(new int[]{7,3,4,1}, 7), 3);
		assertEquals(ArrayQuestions.minSumOfLengths(new int[]{3,3,2,2,7}, 7), 4);
		assertEquals(ArrayQuestions.minSumOfLengths(new int[]{3,4,3}, 7), -1);
		assertEquals(ArrayQuestions.minSumOfLengths(new int[]{4,3,2,6,2,3,4}, 6), -1);
		assertEquals(ArrayQuestions.minSumOfLengths(new int[]{5,5,4,4,5}, 3), -1);
	}

	@Test
	public void testShiftGrid() {
		int[][] g = {{1,2,3},{4,5,6}};
		List<List<Integer>> result = new ArrayList<>();
		result.add(Arrays.asList(5,6,1));
		result.add(Arrays.asList(2,3,4));
		assertEquals(ArrayQuestions.shiftGrid(g, 2), result);
		int[][] g2 = {{3,8,1,9},{19,7,2,5},{4,6,11,10},{12,0,21,13}};
		result = new ArrayList<>();
		result.add(Arrays.asList(12,0,21,13));
		result.add(Arrays.asList(3,8,1,9));
		result.add(Arrays.asList(19,7,2,5));
		result.add(Arrays.asList(4,6,11,10));
		assertEquals(ArrayQuestions.shiftGrid(g2, 4), result);
		int[][] g3 = {{1,2},{4,5}};
		result = new ArrayList<>();
		result.add(Arrays.asList(1,2));
		result.add(Arrays.asList(4,5));
		assertEquals(ArrayQuestions.shiftGrid(g3, 4), result);
		assertEquals(ArrayQuestions.shiftGrid(g3, 8), result);
		result = new ArrayList<>();
		result.add(Arrays.asList(4,5));
		result.add(Arrays.asList(1,2));
		assertEquals(ArrayQuestions.shiftGrid(g3, 6), result);
	}

	@Test
	public void testSumLargest() {
		assertEquals(ArrayQuestions.sumLargest(new int[]{1,2,3}, 2), 5);
		assertEquals(ArrayQuestions.sumLargest(new int[]{2,8,1,3,7,1}, 3), 18);
		assertEquals(ArrayQuestions.sumLargest(new int[]{2,1}, 3), -1);
	}

	@Test
	public void testAddOne() {
		assertArrayEquals(ArrayQuestions.addOne(new int[]{1,2,3}), new int[]{1,2,4});
		assertArrayEquals(ArrayQuestions.addOne(new int[]{9,9}), new int[]{1,0,0});
		assertArrayEquals(ArrayQuestions.addOne(new int[]{2,9}), new int[]{3,0});
		assertArrayEquals(ArrayQuestions.addOne(new int[]{0}), new int[]{1});
		assertArrayEquals(ArrayQuestions.addOne(new int[]{9}), new int[]{1,0});
	}

	@Test
	public void testContains() {
		int[][] in = new int[][] {new int[] {2,4,5}, new int[] {3,6,8}, new int[] {7,9,10}};
		assertTrue(ArrayQuestions.contains(in, 9));
		assertFalse(ArrayQuestions.contains(in, 11));
		assertFalse(ArrayQuestions.contains(in, 1));

		in = new int[][] {new int[] {2,4,5}, new int[] {6,6,16}, new int[] {9,10,18}};
		assertTrue(ArrayQuestions.contains(in, 9));
		assertFalse(ArrayQuestions.contains(in, 11));
		assertFalse(ArrayQuestions.contains(in, 1));
	}

	@Test
	public void testCanJump() {
		assertTrue(ArrayQuestions.canJump(new int[] {2,3,1,1,4}));
		assertFalse(ArrayQuestions.canJump(new int[] {3,2,1,0,4}));
		assertTrue(ArrayQuestions.canJump(new int[] {0}));
		assertTrue(ArrayQuestions.canJump(new int[] {1,2}));
	}

	@Test
	public void testCanJumpNaive() {
		assertTrue(ArrayQuestions.canJumpNaive(new int[] {2,3,1,1,4}));
		assertFalse(ArrayQuestions.canJumpNaive(new int[] {3,2,1,0,4}));
		assertTrue(ArrayQuestions.canJumpNaive(new int[] {0}));
		assertTrue(ArrayQuestions.canJumpNaive(new int[] {1,2}));
	}

	@Test
	public void testSetZeros() {
		int[][] in = new int[][] {new int[] {0,1,0}, new int[] {1,1,1}, new int[] {1,1,1}};
		int[][] expected = new int[][] {new int[] {0,0,0}, new int[] {0,1,0}, new int[] {0,1,0}};
		ArrayQuestions.setZeroes(in);
		for (int i = 0; i < in.length; i++) {
			assertArrayEquals(in[i], expected[i]);
		}

		in = new int[][] {new int[] {1,1,1}, new int[] {1,0,1}, new int[] {1,1,1}};
		expected = new int[][] {new int[] {1,0,1}, new int[] {0,0,0}, new int[] {1,0,1}};
		ArrayQuestions.setZeroes(in);
		for (int i = 0; i < in.length; i++) {
			assertArrayEquals(in[i], expected[i]);
		}
	}

	@Test
	public void testTicTacToeWinner() {
		String[][] in = new String[][] {new String[] {"X", "X", "X"}, new String[] {"O", "O", "X"}, new String[] {"X", "O", "O"}};
		assertEquals(ArrayQuestions.ticTacToeWinner(in), "X");

		in = new String[][] {new String[] {"X", "O", "X"}, new String[] {"O", "O", ""}, new String[] {"X", "O", "X"}};
		assertEquals(ArrayQuestions.ticTacToeWinner(in), "O");

		in = new String[][] {new String[] {"X", "O", "X"}, new String[] {"O", "", ""}, new String[] {"X", "O", "X"}};
		assertEquals(ArrayQuestions.ticTacToeWinner(in), "");

		in = new String[][] {new String[] {"X", "O", "X"}, new String[] {"O", "X", ""}, new String[] {"O", "O", "X"}};
		assertEquals(ArrayQuestions.ticTacToeWinner(in), "X");
	}

}
