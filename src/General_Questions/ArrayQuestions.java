package General_Questions;

import java.awt.*;
import java.math.*;
import java.util.*;
import java.util.List;

public class ArrayQuestions {

	public static void main(String[] args) {
	}

	// https://leetcode.com/problems/third-maximum-number
	public static int thirdMax(int[] nums) {
		long max = nums[0];
		long max2 = Long.MIN_VALUE; // corner cases are ridiculous if you don't use long
		long max3 = Long.MIN_VALUE;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > max) {
				max3 = max2;
				max2 = max;
				max = nums[i];
			} else if (nums[i] > max2 && nums[i] < max) { // distinct values requires 2nd condition check to avoid when nums[i] = max
				max3 = max2;
				max2 = nums[i];
			} else if (nums[i] > max3 && nums[i] < max2) {
				max3 = nums[i];
			}
		}
		return max3 == Long.MIN_VALUE ? (int)max : (int)max3;
	}

	// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
	// greedy algo, single pass
	public static int maxProfit(int[] prices) {
		int profit = 0;
		int buy = prices[0];
		for (int i = 1; i < prices.length; i++) {
			if (prices[i] > buy) { // up in value
				profit = Math.max(profit, prices[i] - buy); // if sold today would make a profit
			} else if (prices[i] < buy) { // down in value, could have bought today
				buy = prices[i];
			}
		}
		return profit;
	}

	// https://leetcode.com/problems/next-permutation/
	public static void nextPermutation(int[] n) {
		if (n.length <= 1) return;
		if (n.length == 2) {
			swap(n,0,1);
			return;
		}
		int i = n.length - 1;
		for (; i > 0 && n[i-1] >= n[i]; i--);
		if (i == 0) { // n in decreasing order, sort it
			Arrays.sort(n);
		} else { // n[i-1] < n[i] - found the first place to swap
			// replace n[i-1] with the next number greater than itself lying to its right
			int nextGreater = i;
			for (int j = i+1; j < n.length; j++) {
				if (n[j] < n[nextGreater] && n[j] > n[i-1]) nextGreater = j;
			}
			swap(n, i-1, nextGreater);
			// numbers from end in decreasing order, swap above does not change this, need them in increasing order so reverse them
			for (int j = 0; i+j < n.length-1-j; j++) {
				swap(n, i+j, n.length-1-j);
			}
		}
	}
	private static void swap(int[] n, int a, int b) {
		if (a != b) {
			int temp = n[b];
			n[b] = n[a];
			n[a] = temp;
		}
	}

	// https://leetcode.com/problems/search-insert-position/
	public static int searchInsert(int[] a, int target) {
		int low = 0;
		int high = a.length-1;
		int mid = 0;
		if (target > a[high]) return a.length;
		if (target < a[low]) return 0;

		while (low <= high) {
			mid = (low + high) / 2; // not appropriate for int overflow
			if (a[mid] == target) {
				return mid;
			} else if (target > a[mid]) {
				if (mid+1 > high) return mid+1; // terminates next iteration, a stays sorted, so add above mid
				low = mid+1;
			} else { // target < a[mid]
				if (mid-1 < low) return mid; // search terminates next iteration, insert in current spot, so larger values shift to right
				high = mid-1;
			}
		}
		return mid;
	}

	// given an array with any number of int, there are only two different values, one number only occurs once, find
	// this number that occurs once without using any data structure provided by the language
	public static int uniqueNumber(int[] a) { // a.length >= 3
		// thoughts:
		// 		bucket sort - would need some sort of hash for larger ints - could maybe use mod, but collisions
		// 		save different values and compute array avg, one is closer to avg than the other (only for positive)
		//		save most recent 3 values, when one number doesn't match previous do 3-way compare
		int previousTwo = a[0];
		int previous = a[1];
		if (previous != previousTwo) {
			return threeWayCompare(a[0], a[1], a[2]);
		}
		for (int i = 2; i < a.length; i++) {
			int current = a[i];
			if (current != previous) {
				return threeWayCompare(current, previous, previousTwo);
			}
			previousTwo = previous;
			previous = current;
		}
		return -1;
	}
	private static int threeWayCompare(int a, int b, int c) {
		if (a == b) {
			return c;
		} else if (a == c) {
			return b;
		} else { // b == c
			return a;
		}
	}
	// follow up: array with any number of int, every number occurs twice, except one number that occurs once, find the unique number
	// if the inverviewee come up with nested loop solution ( which is o(n^2) ) ask can they solve it in o(nlogn), can you solve it in o(n) ( with hash map )
	// if the interviewee come up with solution use hashmap, ask can you solve it in constant space, possible answer with with n^2 or nlogn time complexity
	public static int uniqueNumber2(int[] a) { // a.length >= 3
		// sort and traverse = O(nlogn) space and time
		// hashmap counts, traverse array twice
		// nested loop = O(n^2) time and constant space
		Map<Integer, Integer> counts = new HashMap<>();
		for (int i = 0; i < a.length; i++) {
			Integer count = counts.get(a[i]);
			if (count == null) {
				counts.put(a[i], 1);
			} else {
				counts.put(a[i], ++count);
			}
		}
		for (int i = 0; i < a.length; i++) {
			if (counts.get(a[i]) != 2) {
				return a[i];
			}
		}
		return -1;
	}
	// follow up: can you solve it in constant space plus o(n) time?
	// 		Yes if input range is limited (i.e. ints 0-9) and use bucket sort or input known to be sorted.
	//		can this be done generally? - yes with XOR: XOR of a number with itself is 0 and with 0 is itself
	public static int uniqueNumber3(int[] a) {
		int r = a[0];
		for (int i = 1; i < a.length; i++) {
			r = r ^ a[i];
		}
		return r;
	}
	// follow up: what if every number occurs three times, except one number (is constant space and o(n) here possible)
	//		Hashmap counts still works, bitwise operators would not since there is an odd number of common occurrences
	//		There is a complicated bitwise solution: https://www.geeksforgeeks.org/find-the-element-that-appears-once/
	// follow up: what if every number occurs k times, except one number (can we generalize this solution)

	// https://leetcode.com/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/
	// non-overlapping makes this tricky, used hints, O(n) runtime to make pre, suf, and get result, O(n) space
	// pre: a.length > 0, no zeros in a, elements of a <= 1000
	public static int minSumOfLengths(int[] a, int target) {
		int[] prefix = new int[a.length]; // prefix[i] is the min length of sub-array ends before i that = target
		int[] suffix = new int[a.length+1]; // suffix[i] is the min length of sub-array starting at or after i that = target

		prefix[0] = Integer.MAX_VALUE; // prefix[0] never used
		int curSum = a[0]; // current sum of sub array
		int curPos = 0; // starting position of current sub array
		for (int i = 1; i < a.length; i++) {
			if (curSum == target) {
				int length = i-curPos;
				prefix[i] = Math.min(prefix[i-1], length);
				// start summing a new subarray
				curSum -= a[curPos];
				curPos++;
			} else if (curSum > target) { // start summing a new subarray
				prefix[i] = prefix[i-1];
				while (curSum >= target) { // find a new starting position for subarray where sum < target
					curSum -= a[curPos];
					curPos++;
					if (curSum == target) {
						prefix[i] = Math.min(prefix[i-1], i-curPos);
					}
				}
			} else { // curSum < target, continue summing a subarray
				prefix[i] = prefix[i-1];
			}
			curSum += a[i];
		}

		suffix[suffix.length-1] = Integer.MAX_VALUE; // last suffix position never used
		curSum = 0;
		curPos = a.length-1;
		for (int i = a.length-1; i >= 0; i--) {
			curSum += a[i]; // subarray from i to curPos inclusive of both
			if (curSum == target) {
				suffix[i] = Math.min(suffix[i+1], curPos-i+1);
				curSum -= a[curPos];
				curPos--;
			} else if (curSum > target) {
				suffix[i] = suffix[i+1];
				while (curSum >= target) {
					curSum -= a[curPos];
					curPos--;
					if (curSum == target) {
						suffix[i] = Math.min(suffix[i+1], curPos-i+1);
					}
				}
			} else {
				suffix[i] = suffix[i+1];
			}
		}

		long result = Integer.MAX_VALUE;
		for (int i = 0; i < prefix.length; i++) {
			result = Math.min(result, (long) prefix[i] + (long) suffix[i]);
		}
		return (int) (result == Integer.MAX_VALUE ? -1 : result);
	}
	public static int minSumOfLengthsBruteFailed(int[] a, int target) {
		SortedMap<Integer, Point> subs = new TreeMap<>();
		for (int i = 0; i < a.length; i++) {
			int sum = a[i];
			int j = i+1;
			while (sum < target && j < a.length) {
				sum += a[j];
				j++;
			}
			// sum >= target, ignore case where sum exceeded target
			if (sum == target) {
				subs.put(j-i, new Point(i,j)); // save possible subarray, length -> [start,end)
			}
		}
		// sort out the best non-overlapping options
		if (subs.size() < 2) {
			return -1;
		} else {
			Map.Entry<Integer, Point> prev = null;
			for (Map.Entry<Integer, Point> e : subs.entrySet()) {
				if (prev != null && overlaps(prev.getValue(), e.getValue())) {

				}
				prev = e;
			}
		}
		return -1;
	}
	private static boolean overlaps(Point a, Point b) {
		return a.y > b.x;
	}
	public static int minSumOfLengthsGreedyFailed(int[] arr, int target) {
		int[] results = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
		int[] sub1 = new int[2]; // start, end of the subarray that sums to results[0]
		int[] sub2 = new int[2]; // start, end of the subarray that sums to results[1]

		int curSum = 0; // current sum of sub array
		int curPos = -1; // 1 before current starting position of sub array
		for (int i = 0; i < arr.length; i++) {
			curSum += arr[i];
			if (curSum == target) {
				// save length of sub array
				int len = i-curPos;
				if (len < results[0]) {
					results[0] = len;
				} else if (len < results[1]) {
					results[1] = len;
				}
				// start over
				curSum = 0;
				curPos = i;
			} else if (curSum > target) {
				// start over
//				curSum -= ;
//				curPos++;
//				i = i - X;
			} else {
				// continue
			}
		}
		return -1;
	}

	// Given array a (values between 0 and 9) and number n, return the sum of the n largest elements in a
	//  examples: Input:a={1,2,3} n=2 Output:5    Input:a={2,8,1,3,7,1} n=3 Output:18
	//  Solutions
	//   Use a max heap. Add all elements to heap (log(n)), then pop off N and sum them. n*log(n) runtime, n space.
	//   Sort, then add the N largest. n*log(n) runtime, n space
	//   Bucket sort - use hashmap or array for buckets. O(n) runtime and constant space.
	public static int sumLargest(int[] a, int n) {
		if (n > a.length) return -1;

		int[] buckets = new int[10];
		for (int i = 0; i < a.length; i++) {
			if (a[i] > 9 || a[i] < 0) return -1;
			buckets[a[i]]++;
		}

		int sum = 0;
		for (int i = 9;  i > 0; --i) {
			if (buckets[i] < n) {
				sum += buckets[i] * i;
				n -= buckets[i];
			} else {
				sum += n * i;
				return sum;
			}
		}
		return -1; // should not happen
	}

	// Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
	//    The digits are stored such that the most significant digit is at the head of the list,
	//    and each element in the array contain a single digit.
	//    You may assume the integer does not contain any leading zero, except the number 0 itself.
	public static int[] addOne(int[] nums) {
		int i = nums.length-1;
		while (i >= 0) {
			int last = nums[i];

			if (last != 9) {
				nums[i]++;
				return nums;
			} else {
				nums[i] = 0;
				i--;
			}
		}

		int[] result = new int[nums.length+1];
		result[0] = 1;
		System.arraycopy(nums, 0, result, 1, nums.length);
		return result;
	}
	
	// https://leetcode.com/problems/jump-game/submissions/
	// Given an array of non-negative integers, you are initially positioned at the first index of the array.
	// Each element in the array represents your maximum jump length at that position.
	// Determine if you are able to reach the last index.
	public static boolean canJump(int[] nums) {
        if (nums.length == 0 || nums.length == 1) return true;
        
        int jumpPoint = 0;
        while (jumpPoint < nums.length) {
        	
        	int start = jumpPoint;
        	int end = jumpPoint+nums[jumpPoint];
        	int maxPoint = start;
        	int maxValue = end;
//        	System.out.println("jump range: " + start + " - " + end);
        	
        	for (int i = jumpPoint; i <= end; i++) {
        		if (i >= nums.length-1) return true;
        		
        		if (i + nums[i] > maxValue) {
        			maxPoint = i;
        			maxValue = i + nums[i];
        		}
        	}
//        	System.out.println("max point: " + maxPoint);
        	
        	if (maxPoint == start) return false; // no progress made, stuck
        	jumpPoint = maxPoint;
        }
        return false;
    }
	public static boolean canJumpNaive(int[] nums) {
        if (nums.length == 0) return true;
        
        return canJumpNaive(nums, 0);
    }
	private static boolean canJumpNaive(int[] nums, int start) {
        if (start == nums.length-1) return true;
        
        int jump = nums[start];
        for (int i = start+1; i <= start + jump; i++) {
        	if (canJumpNaive(nums, i)) {
        		return true;
        	}
        }
        return false;
    }

	/* Kth Largest Element in array:
	 * https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/
	 * 1. Sort, then take kth index in array. O(N lg N) running time + O(1) memory
	 * 2. Priority queue (min queue containing the k largest elements). O(N lg K) running time + O(K) memory
	 * 3. Selection Algo: https://en.wikipedia.org/wiki/Selection_algorithm. O(N) running time if input not sorted + O(1) space
	 */
	public int findKthLargest(int[] nums, int k) {
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for (int val : nums) {
			pq.add(val);
			if (pq.size() > k) pq.remove();
		}
		return pq.peek();
	}
	public int findKthLargestUsingSorting(int[] nums, int k) { 
		int n = nums.length;
		Arrays.sort(nums);
		return nums[n - k];
	}

	// https://leetcode.com/problems/rotate-array/ - O(n) time O(1) space
	public static void rotate(int[] a, int k) {
		k = k % a.length;
		reverse(a, a.length-k, a.length-1); // reverse last section
		reverse(a, 0, a.length-k-1); // reverse first section
		reverse(a, 0, a.length-1); // reverse entire array
	}
	private static void reverse(int[] a, int start, int end) {
		while (start < end) {
			int temp = a[start];
			a[start] = a[end];
			a[end] = temp;
			start++;
			end--;
		}
	}
	public static void rotateSimple(int[] a, int k) { // O(n) time and space
		k = k % a.length;
		int[] newA = new int[a.length];
		// index at new starting location and iterate from front of array moving things here
		for (int i = 0; i < a.length; i++) {
			int og = (i+a.length-k) % a.length;
			newA[i] = a[og];
		}
		System.arraycopy(newA, 0, a, 0, a.length);
	}

	// finds the target value in arr in O(lg(n)) time where arr is sorted array w/o duplicates that has been rotated (e.g. 45123)
	public static int indexOfRotated(int[] arr, int target) {
		if(arr.length == 0) return -1;
		if(arr.length == 1) return arr[0] == target ? 0 : -1;
		// find the rotation pivot point
		int low = 0;
		int high = arr.length - 1;
		int highest = -1;
		while (high >= low) {
			int mid = low + (high-low) / 2;
			if(arr[mid] > arr[(mid+1) % arr.length]) {
				highest = mid;
				break;
			} else if (arr[low] <= arr[mid]) {
				low = mid + 1;
			} else { // arr[low] > arr[mid]
				high = mid;
			}
		}
		if(target >= arr[0]) {
			low = 0;
			high = highest;
		} else {
			low = (highest + 1) % arr.length;
			high = arr.length-1;
		}
		while(high >= low) {
			int mid = low + (high-low) / 2;
			if(arr[mid] == target) {
				return mid;
			} else if (arr[mid] < target) {
				low = mid + 1;
			} else { // arr[mid] > key
				high = mid - 1;
			}
		}
		return -1;
	}

	public static int indexOfBinarySearch(int[] arr, int target) {
		int low = 0;
		int high = arr.length - 1;
		while(high >= low) {
			int mid = low + (high-low) / 2;
			if(arr[mid] == target) {
				return mid;
			} else if (arr[mid] < target) {
				low = mid + 1;
			} else { // arr[mid] > key
				high = mid - 1;
			}
		}
		return -1;
	}

	// find the missing k numbers in sequence: http://stackoverflow.com/questions/3492302/easy-interview-question-got-harder-given-numbers-1-100-find-the-missing-numbe
	// - sort the sequence and iterate through identifying differences between consecutive numbers that are greater than 1
	// - calculate the sum of the kth powers of all the numbers that should be in the sequence and compare to the sum of the kth powers of the actual numbers in the 
	//    sequence, solve a system of k equations with k unknowns for the values.

	// pre: sequence contains a single missing number and contains the unordered sequence of numbers from 1 to sequence.length
	// returns the missing number in sequence - O(n) time, O(1) space, works even with integer overflow
	public static int missingNumber(Object[] sequence) {
		int completeSeqLength = sequence.length + 1;
		int completeSeqSum = completeSeqLength*(completeSeqLength+1)/2; // math identity: sum of 1...n = n*(n+1)/2
		int sum = 0;
		for(int i = 0; i < sequence.length; i++) sum += (int)sequence[i];
		return completeSeqSum - sum;
	}

	// sequence contains two missing numbers and contains the unordered sequence of numbers from 1 to sequence.length
	public static int[] missingNumbers(Object[] sequence) {
		//			int completeSeqLength = sequence.length + 2;
		//			int completeSeqSum = completeSeqLength*(completeSeqLength+1)/2; // math identity: sum of 1...n = n*(n+1)/2
		//			int completeSeqSquareSum = completeSeqLength*(completeSeqLength+1)*(2*completeSeqLength + 1)/6; // math identity: sum of 1^2...n^2 = n*(n+1)*(2n+1)/6
		//			int sum = 0;
		//			int squareSum = 0;
		//			int i;
		//			for(i = 0; i < sequence.length; i++) {
		//				int n = (int)sequence[i];
		//				sum += n;
		//				squareSum += n*n;
		//				//completeSeqSquareSum += (i+1) * (i+1); // do this if do not know the formula to directly calculate sum of n squares
		//			}
		//			//completeSeqSquareSum += (i+1) * (i+1);
		//			
		//			// solve 2 eqns w/ 2 unknowns
		//			int missingNumSum = completeSeqSum - sum; // = x1 + x2
		//			int missingNumProduct = completeSeqSquareSum - squareSum; // = x1^2 + x2^2
		//			
		//			// PROBLEM
		//			int x1 = (int) Math.sqrt((missingNumProduct + 2*missingNumSum - missingNumSum * missingNumSum) / 2.0);
		//			
		//			return new int[] {missingNumSum, missingNumProduct, x1};

		int Sum = 0;
		int SumN = 0;
		BigInteger P = new BigInteger("1");
		BigInteger Np = new BigInteger("1");
		int a,b;
		int range = sequence.length+2;
		SumN = range*(range+1)/2;
		for(int i=0;i<sequence.length;i++){
			Sum += (int)sequence[i];
		}
		int s= SumN-Sum;
		int i = 0;
		for(; i<sequence.length; i++){
			P = P.multiply(new BigInteger(sequence[i].toString()));
			Np = Np.multiply(new BigInteger(Integer.toString(i+1)));
		}
		Np = Np.multiply(new BigInteger(Integer.toString(i++)));
		Np = Np.multiply(new BigInteger(Integer.toString(i++)));
		int product = Np.divide(P).intValue();
		int diffSqr = (int)Math.sqrt(s*s-4*product); // (a-b)^2 = (a+b)^2-4ab
		a = (s+diffSqr)/2;
		b= s-a;
		int [] result = {a,b};
		return result;
	}


	// returns the first two indexes in the given array that add up to the given target - O(n) time and space
	public static int[] twoSum(int[] nums, int target) {
		// https://leetcode.com/articles/two-sum/
		// only needs one pass since upon finding the second value that forms the complement, we return
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			int complement = target - nums[i];
			if (map.containsKey(complement)) {
				return new int[] { map.get(complement), i };
			}
			map.put(nums[i], i); // map values to indexes
		}
		return null;
	}

	// returns first triplet of indexes in the given array that add up to the given target - O(n^3) time O(1) space
	public static int[] threeSumN3(int[] nums, int target) {
		for(int i = 0; i < nums.length; i++) {
			for(int j = i + 1; j < nums.length; j++) {
				for(int k = j + 1; k < nums.length; k++) {
					if(nums[i] + nums[j] + nums[k] == target) {
						return new int[] {i, j, k};
					}
				}
			}
		}
		return null;
	}
	public static int[] threeSumN2(int[] nums, int target) { // requires nums to be sorted
		for (int i = 0; i < nums.length - 2; i++) {
			int l = i + 1; // index of the first element in the remaining elements
			int r = nums.length - 1; // index of the last element
			while (l < nums.length - 1) {
				if (nums[i] + nums[l] + nums[r] == target)
					return new int[] {i, l, r};
				else if (nums[i] + nums[l] + nums[r] < target)
					l++;
				else // A[i] + A[l] + A[r] > sum
					r--;
			}
		}
		return null;
	}

	// Fisher Yates shuffle: https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
	public static void shuffle(int[] arr) {
		Random r = new Random();
		for (int i = arr.length-1; i >= 1; i--) {
			int rand = r.nextInt(i+1); // random element from the original (unshuffled) part of the array
			int temp = arr[i];
			arr[i] = arr[rand]; // put the chosen element at the end of the original part of the array, building the shuffled part of the array from back to front
			arr[rand] = temp; // maintain the shrinking unshuffled part of the array at the front, with the swap
		}
	}
	
//	Given an array nums and a value val, remove all instances of that value in-place and return the new length.
//	Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
//	The order of elements can be changed. It doesn't matter what you leave beyond the new length.
//	Given nums = [3,2,2,3], val = 3,
//	Your function should return length = 2, with the first two elements of nums being 2.
	public int removeElement(int[] nums, int val) {
        int end = nums.length - 1;
        int sum = 0;
        for (int i = 0; i < nums.length && i <= end; i++) {
            if (nums[i] == val) {
                nums[i] = nums[end];
                nums[end] = val;
                end--;
                i--;
            } else {
                sum++;
            }
        }
        return sum;
    }
}
