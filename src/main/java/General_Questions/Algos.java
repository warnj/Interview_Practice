package General_Questions;

import java.util.*;

public class Algos {
	public static void main(String[] args) {
	
	}

	// https://leetcode.com/problems/coin-change/
	// O(n*c) runtime, O(n) space, where n = value of amount and c = # of coins
	// to find exactly which coins to use - could keep backpointers in the table that tell which coin added at each step, then traverse back
	public static int coinChange(int[] coins, int amount) {
		if (amount == 0) return 0;
		Arrays.sort(coins);
		int[] counts = new int[amount]; // counts[i] = # of coins to sum to amount i+1
		for (int i = 0; i < counts.length; i++) {
			// find the least number of coins to sum to amounts i+1 from 1 to amount
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < coins.length; j++) {
				if (coins[j] == i+1) {
					min = 1; // no way to do better than a single coin
					break;
				} else {
					int prevAmountIndex = i - coins[j];
					if (prevAmountIndex >= 0 && counts[prevAmountIndex] > 0) // there is a way to sum to this value
						min = Math.min(min, counts[prevAmountIndex] + 1);
				}
			}
			if (min != Integer.MAX_VALUE) counts[i] = min; // leave amounts with no way to sum to with value 0
		}
		return counts[amount-1] == 0 ? -1 : counts[amount-1];
	}

	// Given an array of integers sorted in ascending order, find the starting and ending position of a given target value in O(lg(n))
	public static int[] searchRange(int[] nums, int target) {
		int index = ArrayQuestions.indexOfBinarySearch(nums, target);
		if(index < 0) return new int[] {-1, -1};
		else {
			int low = index, high = index;
			while (low > 0 && nums[--low] == target);
			if (nums[0] != target) low++;
			while (high < nums.length - 1 && nums[++high] == target);
			if (nums[nums.length - 1] != target) high--;
			return new int[] {low, high};
		}
	}

	// returns the int obtained by reversing the digits of x. Returns 0 when the reversed integer overflows.
	public static int reverse(int x) {
		long rev = 0;
		while (x != 0) {
			rev = rev * 10 + x % 10;
			x /= 10;
			if (rev > Integer.MAX_VALUE || rev < Integer.MIN_VALUE) return 0;
		}
		return (int) rev;
	}

	// given String s and dictionary of words find all of the words in s
	public static Set<String> allWordsBruteForce(String input, Set<String> dict) {
		Set<String> result = new HashSet<String>();
		// find all substrings of input & see if they are words in dict - O(n^2)
		for (int from = 0; from < input.length(); from++) {
			for (int to = from + 1; to <= input.length(); to++) {
				String part = input.substring(from, to);
				if(dict.contains(part)) {
					result.add(part);
				}
			}
		}
		return result;
	}

	// prints the whitespace-separated words in the given string in order of descending freq 
	public static void printDescendingFreq(String words) {
		String[] wds = words.split("\\s+");
		Map<String, Integer> freqs = new HashMap<String, Integer>();
		for(String s : wds) {
			Integer n = freqs.get(s);
			if(n == null) {
				freqs.put(s, 1);
			} else {
				freqs.put(s, ++n);
			}
		}
		List<Map.Entry<String, Integer>> list = new LinkedList<>( freqs.entrySet() );
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ) {
				return (o2.getValue()).compareTo( o1.getValue() );
			}
		});
		for(Map.Entry<String, Integer> e : list) {
			System.out.println("Word: " + e.getKey() + "\t\tFreq: " + e.getValue());
		}
	}

	// HASH A RANGE OF VALUES - want all the values within the range to have same hashcode
	// Simply populate a Map for all valid key values, with multiple keys mapping to the same value.
	// Assuming that you use HashMaps, this should be the most time efficient (O(1) lookups),
	// though you have more work at setup time and you use more space (when compared to a NavigableMap and use floorEntry(key)
	// to do the lookups. This should be less time efficient (O(log(N) lookups) but more space efficient.)
	// http://stackoverflow.com/questions/1314650/using-java-map-for-range-searches

	public static List<Integer> primeFactors(int n) {
		List<Integer> factors = new ArrayList<Integer>();
		int d = 2;
		while (n > 1) {
			while (n % d == 0) {
				factors.add(d);
				n /= d;
			}
			d = d + 1;
			if (d*d > n) {
				if (n > 1) factors.add(n);
				break;
			}
		}
		return factors;
	}
}
