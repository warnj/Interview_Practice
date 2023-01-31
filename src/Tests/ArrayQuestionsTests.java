package Tests;

import General_Questions.ArrayQuestions;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayQuestionsTests {

	@Test
	public void testMaxSubarraySumCircular() {
		assertEquals(3, ArrayQuestions.maxSubarraySumCircular(new int[]{1,-2,3,-2}));
		assertEquals(10, ArrayQuestions.maxSubarraySumCircular(new int[]{5,-3,5}));
		assertEquals(-2, ArrayQuestions.maxSubarraySumCircular(new int[]{-3,-2,-3}));
	}

	@Test
	public void testFirstMissingPositive() {
		assertEquals(3, ArrayQuestions.firstMissingPositive(new int[]{1,2,0}));
		assertEquals(2, ArrayQuestions.firstMissingPositive(new int[]{3,4,-1,1}));
		assertEquals(1, ArrayQuestions.firstMissingPositive(new int[]{7,8,9,11,12}));
	}

	@Test
	public void testRemoveDuplicates2() {
		int[] a = new int[]{0,0,1,1,1,1,2,3,3};
		assertEquals(7, ArrayQuestions.removeMoreThanTwoDuplicates(a));
		assertArrayEquals(new int[]{0,0,1,1,2,3,3,3,3}, a);
		assertEquals(3, ArrayQuestions.removeMoreThanTwoDuplicates(new int[]{1,2,2,2}));
	}

	@Test
	public void testLengthOfLIS() {
		assertEquals(4, ArrayQuestions.lengthOfLIS(new int[]{10,9,2,5,3,7,101,18}));
		assertEquals(5, ArrayQuestions.lengthOfLIS(new int[]{10,9,2,5,3,4,5,6}));
		assertEquals(4, ArrayQuestions.lengthOfLIS(new int[]{0,1,0,3,2,3}));
		assertEquals(1, ArrayQuestions.lengthOfLIS(new int[]{7,7,7,7,7,7,7}));
	}

	@Test
	public void testLongestConsecutive() {
		assertEquals(4, ArrayQuestions.longestConsecutive(new int[]{100,3,4,2,200,1}));
		assertEquals(13, ArrayQuestions.longestConsecutive(new int[]{-1,9,-3,-6,7,-8,-6,2,9,2,3,-2,4,-1,0,6,1,-9,6,8,6,5,2}));
	}

	@Test
	public void testShelves() {
		assertEquals(Arrays.asList(1,3,2,3), ArrayQuestions.assignShelves(Arrays.asList(1,12,4,12)));
		assertEquals(Arrays.asList(1,3,2,3), ArrayQuestions.assignShelves(Arrays.asList(1,3,2,3)));
		assertEquals(Arrays.asList(7,4,3,2,3,5,1,6), ArrayQuestions.assignShelves(Arrays.asList(99,44,43,42,43,45,41,47)));
	}

	@Test
	public void testSearchRotated() {
		assertEquals(1, ArrayQuestions.search(new int[]{1,2,3,4,5}, 2));
		assertEquals(1, ArrayQuestions.search(new int[]{5,1,2,3,4}, 1));
		assertEquals(-1, ArrayQuestions.search(new int[]{4,5,1,2,3}, 6));
		assertEquals(2, ArrayQuestions.search(new int[]{3,4,5,1,2}, 5));
		assertEquals(1, ArrayQuestions.search(new int[]{1,2,3,4}, 2));
		assertEquals(1, ArrayQuestions.search(new int[]{4,1,2,3}, 1));
		assertEquals(3, ArrayQuestions.search(new int[]{3,4,1,2}, 2));
		assertEquals(2, ArrayQuestions.search(new int[]{2,3,4,1}, 4));
		assertEquals(1, ArrayQuestions.search(new int[]{1,2}, 2));
	}

	@Test
	public void testFindMinRotated() {
		assertEquals(1, ArrayQuestions.findMin(new int[]{1,2,3,4,5}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{5,1,2,3,4}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{4,5,1,2,3}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{3,4,5,1,2}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{2,3,4,5,1}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{1,2,3,4}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{4,1,2,3}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{3,4,1,2}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{2,3,4,1}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{1,2}));
		assertEquals(1, ArrayQuestions.findMin(new int[]{2,1}));
	}

	@Test
	public void testMaxSubarray() {
		assertEquals(6, ArrayQuestions.maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
		assertEquals(1, ArrayQuestions.maxSubArray(new int[]{1}));
		assertEquals(23, ArrayQuestions.maxSubArray(new int[]{5,4,-1,7,8}));
	}

	@Test
	public void testDeleteAndEarn() {
		assertEquals(6, ArrayQuestions.deleteAndEarn(new int[]{3,4,2}));
		assertEquals(9, ArrayQuestions.deleteAndEarn(new int[]{2,2,3,3,3,4}));
	}

	@Test
	public void testNumberOfArithmeticSlices() {
		assertEquals(3, ArrayQuestions.numberOfArithmeticSlices(new int[]{1,2,3,4}));
		assertEquals(6, ArrayQuestions.numberOfArithmeticSlices(new int[]{1,2,3,4,5}));
		assertEquals(10, ArrayQuestions.numberOfArithmeticSlices(new int[]{1,2,3,4,5,6}));
		assertEquals(15, ArrayQuestions.numberOfArithmeticSlices(new int[]{1,2,3,4,5,6,7}));
		assertEquals(3, ArrayQuestions.numberOfArithmeticSlices(new int[]{3,-1,-5,-9}));
		assertEquals(2, ArrayQuestions.numberOfArithmeticSlices(new int[]{1,2,3,5,7}));
		assertEquals(0, ArrayQuestions.numberOfArithmeticSlices(new int[]{1}));
	}

	@Test
	public void testFindMedian() {
		double delta = .01;
		assertEquals(2.5, ArrayQuestions.findMedianSortedArrays(new int[]{2,3}, new int[]{}), delta);
		assertEquals(2.0, ArrayQuestions.findMedianSortedArrays(new int[]{1,2,3}, new int[]{}), delta);
		assertEquals(2.0, ArrayQuestions.findMedianSortedArrays(new int[]{1,3}, new int[]{2}), delta);
		assertEquals(3.0, ArrayQuestions.findMedianSortedArrays(new int[]{1,3,5}, new int[]{2,4}), delta);
		assertEquals(2.5, ArrayQuestions.findMedianSortedArrays(new int[]{1,2}, new int[]{3,4}), delta);
		assertEquals(4.0, ArrayQuestions.findMedianSortedArrays(new int[]{1,2,3,4}, new int[]{5,6,7}), delta);
		assertEquals(4.0, ArrayQuestions.findMedianSortedArrays(new int[]{1,2,3,4}, new int[]{97,98,99}), delta);
		assertEquals(50.0, ArrayQuestions.findMedianSortedArrays(new int[]{1,2,4}, new int[]{96,97,99}), delta);
		assertEquals(25.0, ArrayQuestions.findMedianSortedArrays(new int[]{1,22,33,44}, new int[]{2,15,25,35,45}), delta);
		assertEquals(29.0, ArrayQuestions.findMedianSortedArrays(new int[]{1,33,44}, new int[]{2,15,25,35,45}), delta);
		assertEquals(29.0, ArrayQuestions.findMedianSortedArrays(new int[]{1}, new int[]{2,15,25,33,34,35,45}), delta);
		assertEquals(29.0, ArrayQuestions.findMedianSortedArrays(new int[]{1,15,25,33,34,35,45}, new int[]{2}), delta);
	}

	@Test
	public void testMajorityElement() {
		assertEquals(0, ArrayQuestions.majorityElement(new int[]{3,2,1}).size());
		assertEquals(0, ArrayQuestions.majorityElement(new int[]{4,3,2,1}).size());
		assertEquals(0, ArrayQuestions.majorityElement(new int[]{6,5,4,3,2}).size());
		assertEquals(0, ArrayQuestions.majorityElement(new int[]{1,2,3,4,4,5}).size());
		assertEquals(0, ArrayQuestions.majorityElement(new int[]{6,6,7,7,8,8}).size());
		assertEquals(1, ArrayQuestions.majorityElement(new int[]{1,2,3,4,4,4}).size());
		assertEquals(1, ArrayQuestions.majorityElement(new int[]{1,3,3,4,4,4}).size());
		assertEquals(2, ArrayQuestions.majorityElement(new int[]{2,2,2,4,4,4}).size());
		List<Integer>  r = ArrayQuestions.majorityElement(new int[]{3,2,3});
		assertEquals(1, r.size());
		assertEquals(3, (int)r.get(0));
		r = ArrayQuestions.majorityElement(new int[]{3,3,2,2,3,2,2});
		assertEquals(2, r.size());
		assertTrue(r.contains(2));
		assertTrue(r.contains(3));
	}

	@Test
	public void testFourSum() {
		assertEquals(Arrays.asList(2,2,2,2), ArrayQuestions.fourSum(new int[]{2,2,2,2}, 8).get(0));
		assertEquals(1, ArrayQuestions.fourSum(new int[]{2,2,2,2,2}, 8).size());
		assertEquals(Arrays.asList(2,2,2,2), ArrayQuestions.fourSum(new int[]{2,2,2,2,2}, 8).get(0));
		System.out.println(ArrayQuestions.fourSum(new int[]{1,0,-1,0,-2,2}, 0));
		assertEquals(3, ArrayQuestions.fourSum(new int[]{1,0,-1,0,-2,2}, 0).size());
		assertEquals(2, ArrayQuestions.fourSum(new int[]{-5,5,4,-3,0,0,4,-2}, 4).size());
	}

	@Test
	public void testThreeSum() {
		assertEquals(1, ArrayQuestions.threeSum(new int[]{1,2,-3}).size());
//		assertEquals(5, ArrayQuestions.threeSum(new int[]{1,2,-3,1,2,-3}).size());

		List<List<Integer>> r = ArrayQuestions.threeSum(new int[]{-1,0,1,2,-1,-4});
		System.out.println(r);
		assertEquals(2, r.size());
		assertEquals(Arrays.asList(-1,-1,2), r.get(0));
		assertEquals(Arrays.asList(-1,0,1), r.get(1));

		r = ArrayQuestions.threeSum(new int[]{1,2,3});
		assertEquals(0, r.size());
		r = ArrayQuestions.threeSum(new int[]{0});
		assertEquals(0, r.size());
		r = ArrayQuestions.threeSum(new int[]{});
		assertEquals(0, r.size());
	}

	@Test
	public void testProductExceptSelf() {
		assertArrayEquals(new int[]{24,12,8,6}, ArrayQuestions.productExceptSelf(new int[]{1,2,3,4}));
		assertArrayEquals(new int[]{0,0,9,0,0}, ArrayQuestions.productExceptSelf(new int[]{-1,1,0,-3,3}));
	}

	@Test
	public void testTrap() {
		assertEquals(6, ArrayQuestions.trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
		assertEquals(9, ArrayQuestions.trap(new int[]{4,2,0,3,2,5}));
		assertEquals(1, ArrayQuestions.trap(new int[]{1,1,1,1,2,1,2}));
		assertEquals(0, ArrayQuestions.trap(new int[]{1,1,1,1,1,1}));
		assertEquals(0, ArrayQuestions.trap(new int[]{1,2,3,4,5,6}));
		assertEquals(0, ArrayQuestions.trap(new int[]{6,5,4,3,2,1}));
	}

	@Test
	public void testTopKFrequent() {
		// output doesn't have to be sorted, so arrayequals not good way to assert this
		assertArrayEquals(new int[]{1,2}, ArrayQuestions.topKFrequent(new int[]{1,1,1,2,2,3}, 2));
		assertArrayEquals(new int[]{1,2,3}, ArrayQuestions.topKFrequent(new int[]{1,1,1,2,2,3}, 3));
		assertArrayEquals(new int[]{1}, ArrayQuestions.topKFrequent(new int[]{1,1,1,2,2,3}, 1));
		assertArrayEquals(new int[]{1}, ArrayQuestions.topKFrequent(new int[]{1}, 1));
	}

	@Test
	public void testSubarraySum() {
		assertEquals(2, ArrayQuestions.subarraySum(new int[]{1,1,1}, 2));
		assertEquals(2, ArrayQuestions.subarraySum(new int[]{1,2,3}, 3));
		assertEquals(5, ArrayQuestions.subarraySum(new int[]{-1,3,0,-1,3}, 2));
		assertEquals(7, ArrayQuestions.subarraySum(new int[]{1,2,3,3,2,1,2,1,1,1}, 3));
	}

	@Test
	public void testFindPeak() {
		int result = ArrayQuestions.findPeakElement(new int[]{1,2,1,3,5,6,4});
		assertTrue(1 == result || 5 == result);
		assertEquals(2, ArrayQuestions.findPeakElement(new int[]{1,2,3,1}));
		assertEquals(2, ArrayQuestions.findPeakElement(new int[]{1,2,3}));
		assertEquals(0, ArrayQuestions.findPeakElement(new int[]{1}));
		assertEquals(1, ArrayQuestions.findPeakElement(new int[]{1,2,1}));
	}

	@Test
	public void testMaxArea() {
		assertEquals(49, ArrayQuestions.maxArea(new int[]{1,8,6,2,5,4,8,3,7}));
		assertEquals(1, ArrayQuestions.maxArea(new int[]{1,1}));
	}

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
	public void testMaxProfitFee() {
		assertEquals(8, ArrayQuestions.maxProfitFee(new int[]{1,3,2,8,4,9}, 2));
		assertEquals(6, ArrayQuestions.maxProfitFee(new int[]{1,3,7,5,10,3}, 3));
		assertEquals(9, ArrayQuestions.maxProfitFee(new int[]{1,3,7,5,10,3}, 1));
		assertEquals(6, ArrayQuestions.maxProfitFee(new int[]{2,3,1,9,7,10}, 3));
		assertEquals(0, ArrayQuestions.maxProfitFee(new int[]{2,3}, 3));
		assertEquals(0, ArrayQuestions.maxProfitFee(new int[]{2}, 1));
		assertEquals(1, ArrayQuestions.maxProfitFee(new int[]{2,3}, 0));
		assertEquals(13, ArrayQuestions.maxProfitFee(new int[]{1,4,6,2,8,3,10,14}, 3));
		assertEquals(4, ArrayQuestions.maxProfitFee(new int[]{4,5,2,4,3,3,1,2,5,4}, 1));
	}

	@Test
	public void testMaxProfit2() {
		assertEquals(7, ArrayQuestions.maxProfit2(new int[]{7,1,5,3,6,4}));
		assertEquals(4, ArrayQuestions.maxProfit2(new int[]{1,2,3,4,5}));
		assertEquals(0, ArrayQuestions.maxProfit2(new int[]{7,6,4,3,1}));
	}

	@Test
	public void testMaxProfit() {
		assertEquals(5, ArrayQuestions.maxProfit(new int[]{7,1,5,3,6,4}));
		assertEquals(0, ArrayQuestions.maxProfit(new int[]{7,6,4,3,1}));
		assertEquals(4, ArrayQuestions.maxProfit(new int[]{1,2,3,4,5}));
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

	@Test
	public void testJump() {
		assertEquals(2, ArrayQuestions.jump(new int[] {1,2,3}));
		assertEquals(2, ArrayQuestions.jump(new int[] {2,3,1,1,4}));
		assertEquals(2, ArrayQuestions.jump(new int[] {2,3,0,1,4}));
		assertEquals(0, ArrayQuestions.jump(new int[] {0}));
	}

	@Test
	public void testJump3() {
		assertTrue(ArrayQuestions.canReach(new int[] {4,2,3,0,3,1,2}, 5));
		assertTrue(ArrayQuestions.canReach(new int[] {4,2,3,0,3,1,2}, 0));
		assertFalse(ArrayQuestions.canReach(new int[] {3,0,2,1,2}, 2));
	}

	@Test
	public void testAsteroidCollision() {
		assertArrayEquals(new int[]{}, ArrayQuestions.asteroidCollision(new int[] {8,-8}));
		assertArrayEquals(new int[]{-8,8}, ArrayQuestions.asteroidCollision(new int[] {-8,8}));
		assertArrayEquals(new int[]{5,10}, ArrayQuestions.asteroidCollision(new int[] {5,10,-5}));
		assertArrayEquals(new int[]{10}, ArrayQuestions.asteroidCollision(new int[] {10,2,-5}));
		assertArrayEquals(new int[]{-2,-2}, ArrayQuestions.asteroidCollision(new int[] {1,-1,-2,-2}));
	}

	@Test
	public void testFourSumCount() {
		assertEquals(2, ArrayQuestions.fourSumCount(new int[] {1,2}, new int[]{-2,-1}, new int[]{-1,2}, new int[]{0,2}));
		assertEquals(1, ArrayQuestions.fourSumCount(new int[] {0}, new int[]{0}, new int[]{0}, new int[]{0}));
		assertEquals(17, ArrayQuestions.fourSumCount(new int[] {0,1,-1}, new int[]{-1,1,0}, new int[]{0,0,1}, new int[]{-1,1,1}));
	}

	@Test
	public void testKthLargest() {
		assertEquals(5, ArrayQuestions.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
		assertEquals(5, ArrayQuestions.findKthLargest(new int[]{1,2,3,4,5,6}, 2));
		assertEquals(4, ArrayQuestions.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));
	}

	@Test
	public void testPlusOne() {
		assertArrayEquals(new int[]{1,2,4}, ArrayQuestions.plusOne(new int[]{1,2,3}));
		assertArrayEquals(new int[]{4,3,2,2}, ArrayQuestions.plusOne(new int[]{4,3,2,1}));
		assertArrayEquals(new int[]{1,0}, ArrayQuestions.plusOne(new int[]{9}));
		assertArrayEquals(new int[]{1,0,0,0}, ArrayQuestions.plusOne(new int[]{9,9,9}));
		assertArrayEquals(new int[]{3,0,0}, ArrayQuestions.plusOne(new int[]{2,9,9}));
	}
}
