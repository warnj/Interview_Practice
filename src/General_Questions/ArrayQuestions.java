package General_Questions;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ArrayQuestions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static int indexOfBinarySearch(int[] arr, int target) {
		int low = 0;
		int high = arr.length - 1;
		while(high >= low) {
			int mid = (low + high) / 2;
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

	public static int[] shuffle(int[] arr) {
		Random r = new Random();
		for(int i = arr.length-1; i > 0; i--) {
			int rand = r.nextInt(i+1);
			int temp = arr[i];
			arr[i] = arr[rand];
			arr[rand] = temp;
		}
		return arr;
	}

}
