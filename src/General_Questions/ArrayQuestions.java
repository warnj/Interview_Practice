package General_Questions;

import java.math.*;
import java.util.*;

public class ArrayQuestions {

	public static void main(String[] args) {
//		int[][] a = new int[][] {new int[] {0,1,0}, new int[] {1,1,1}, new int[] {1,1,1}};
//		setZeroesEfficient(a);
//		for (int i = 0; i < a.length; i++) {
//			System.out.println(Arrays.toString(a[i]));
//		}
//		System.out.println();
		
		System.out.println(canJump(new int[] {2,3,1,1,4}));
		System.out.println(canJump(new int[] {3,2,1,0,4}));
		System.out.println(canJump(new int[] {0}));
		System.out.println(canJump(new int[] {1,2}));
	}
	
	// https://leetcode.com/problems/jump-game/submissions/
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
	public static void setZeroesEfficient(int[][] matrix) {
		// use first row and first col as flags indicating the row needs to be set to 0
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					matrix[0][j] = 0;
				}
			}
		}
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][0] == 0 || matrix[0][j] == 0) {
					matrix[i][j] = 0;
				}
			}
		}
		
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
    }

	// returns the winner of the given tic tac toe board. Board is 3x3. If no winner, returns empty string.
	// [[X, X, X],         [[X, O, X],       [[X, O, X],       [[X, O, X],
	//  [O, O, X],  = X     [O, O, ],  = O    [O, , ],  = ""    [O, X, ],  = "X"
	//  [X, O, O]]          [X, O, X]]        [X, O, X]]        [O, O, X]]
	private String threeEqual(String a, String b, String c) {
		if (a.equals(b) && b.equals(c)) {
			return a;
		} else {
			return "";
		}
	}
	public String ticTacToeWinner(String[][] board) {
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

	// Fisher�Yates shuffle: https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
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
