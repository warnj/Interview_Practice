package Tests;

import General_Questions.ArrayQuestions;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayQuestionsTests {

	@Test
	public void testThirdMax() {
		assertEquals(1, ArrayQuestions.thirdMax(new int[]{1,2,3}));
		assertEquals(1, ArrayQuestions.thirdMax(new int[]{3,2,1}));
		assertEquals(1, ArrayQuestions.thirdMax(new int[]{2,2,3,1}));
		assertEquals(1, ArrayQuestions.thirdMax(new int[]{3,3,3,2,1}));
		assertEquals(2, ArrayQuestions.thirdMax(new int[]{1,2}));
		assertEquals(-2147483648, ArrayQuestions.thirdMax(new int[]{1,2,-2147483648}));
		assertEquals(-2147483648, ArrayQuestions.thirdMax(new int[]{1,-2147483648,2}));
		assertEquals(1, ArrayQuestions.thirdMax(new int[]{-2147483648,1,-2147483648}));
		assertEquals(2, ArrayQuestions.thirdMax(new int[]{1,2,2,5,3,5}));
	}

	@Test
	public void testRotate() {
		int[] input = new int[]{1,2,3,4};
		ArrayQuestions.rotate(input, 3);
		assertArrayEquals(new int[]{2,3,4,1}, input);

		input = new int[]{1,2,3,4};
		ArrayQuestions.rotate(input, 1);
		assertArrayEquals(new int[]{4,1,2,3}, input);

		input = new int[]{1,2,3,4};
		ArrayQuestions.rotate(input, 4);
		assertArrayEquals(new int[]{1,2,3,4}, input);

		input = new int[]{1,2,3,4};
		ArrayQuestions.rotate(input, 0);
		assertArrayEquals(new int[]{1,2,3,4}, input);

		input = new int[]{1,2,3,4,5,6,7};
		ArrayQuestions.rotate(input, 3);
		assertArrayEquals(new int[]{5,6,7,1,2,3,4}, input);

		input = new int[]{-1,-100,3,99};
		ArrayQuestions.rotate(input, 2);
		assertArrayEquals(new int[]{3,99,-1,-100}, input);
	}

	@Test
	public void testMaxProfit() {
		assertEquals(5, ArrayQuestions.maxProfit(new int[]{7,1,5,3,6,4}));
		assertEquals(0, ArrayQuestions.maxProfit(new int[]{7,6,4,3,1}));
	}

	@Test
	public void testNextPermutation() {
		// start from back
		//      if last number > 2nd to last, swap the 2 and return
		//      when find a current number smaller than previous, swap them. swap again if previous previous is < previous
		//			^ initial thought too simplistic, need to swap with next larger number to the right
		//		corner case when ending subarray is descending: 4,7,3,1 -> 7,1,3,4 -> swap from opposite ends until ending is sorted
		permutationArrayEquals(new int[]{1,2,3}, new int[]{1,3,2});
		permutationArrayEquals(new int[]{2,3,1}, new int[]{3,1,2});
		permutationArrayEquals(new int[]{3,2,1}, new int[]{1,2,3});
		permutationArrayEquals(new int[]{1,1,5}, new int[]{1,5,1});
		permutationArrayEquals(new int[]{1,7,3,4,2}, new int[]{1,7,4,2,3});
		permutationArrayEquals(new int[]{2,4,7,3,1}, new int[]{2,7,1,3,4});
		permutationArrayEquals(new int[]{2,7,4,3,1}, new int[]{3,1,2,4,7});
		permutationArrayEquals(new int[]{4,7,3,2,1}, new int[]{7,1,2,3,4});
		permutationArrayEquals(new int[]{4,7,4,4}, new int[]{7,4,4,4});
		permutationArrayEquals(new int[]{2,3,1,3,3}, new int[]{2,3,3,1,3});
	}
	public void permutationArrayEquals(int[] start, int[] expected) {
		ArrayQuestions.nextPermutation(start);
		assertArrayEquals(expected, start);
	}

	@Test
	public void testSearchInsert() {
		assertEquals(2, ArrayQuestions.searchInsert(new int[]{1,3,5,6}, 5));
		assertEquals(1, ArrayQuestions.searchInsert(new int[]{1,3,5,6}, 3));
		assertEquals(4, ArrayQuestions.searchInsert(new int[]{1,3,5,6}, 7));
		assertEquals(1, ArrayQuestions.searchInsert(new int[]{1,3,5,6}, 2));
		assertEquals(2, ArrayQuestions.searchInsert(new int[]{1,3,5,6}, 4));
		assertEquals(1, ArrayQuestions.searchInsert(new int[]{1,3,5}, 2));
		assertEquals(2, ArrayQuestions.searchInsert(new int[]{1,3,5}, 4));
	}

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

}
