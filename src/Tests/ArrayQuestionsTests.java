package Tests;

import General_Questions.ArrayQuestions;
import org.junit.*;
import static org.junit.Assert.*;

public class ArrayQuestionsTests {

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
