package General_Questions;
import java.util.*;
import java.math.*;

public class Algos {
	public static void main(String[] args) {
//		System.out.println(coinChange(new int[]{1, 4, 5}, 12));//3
//		System.out.println(coinChange(new int[]{1}, 1));//1
//		System.out.println(coinChange(new int[]{2}, 1));//-1
		System.out.println(coinChange(new int[]{2}, 3));//-1
	}

	// Write a function to compute the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1
	// coins = [1, 2, 5], amount = 11	return 3 (11 = 5 + 5 + 1)
	// coins = [2], amount = 3	return -1
	// coins = [1, 4, 5], amount = 12 return 3
	// O(n) runtime where n = value of amount
	// to find exactly which coins to use - could keep backpointers in the table that tell which coin added at each step, then traverse back
	public static int coinChange(int[] coins, int amount) {
		if (amount == 0) return 0;
		Arrays.sort(coins);
		int[] table = new int[amount]; // index 0 = # coins for change w/ amount = 1, index 4 = # coins for change w/ amount = 5
		for (int i = 0; i < table.length; i++) { // go over all possible coin values up to amount
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < coins.length; j++) { // go through the possible coins
				if (i+1 == coins[j]) {
					min = Math.min(min, 1); // no way to do better than a single coin
					break;
				}
				if (i - coins[j] >= 0) { // there is a way to sum to this value
					System.out.println("i: " + i);
					min = Math.min(min, table[i-coins[j]] + 1); // check how many coins it took to get to the previous value without coins[j], then add 1
				}
			}
			if (min != Integer.MAX_VALUE) table[i] = min;
		}
		System.out.println(Arrays.toString(table));
		return table[amount-1] == 0 ? -1 : table[amount-1]; // the illegal values will have the initial value of 0, return -1 in this case
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

	// Given a phone number digit string, return all possible letter combinations that the number could represent.
	public static List<String> letterCombinationsBacktracing(String digits) {
		Map<Character, String> phoneLetters = new HashMap<>();
		phoneLetters.put('2', "abc");
		phoneLetters.put('3', "def");
		phoneLetters.put('4', "ghi");
		phoneLetters.put('5', "jkl");
		phoneLetters.put('6', "mno");
		phoneLetters.put('7', "pqrs");
		phoneLetters.put('8', "tuv");
		phoneLetters.put('9', "wxyz");

		List<String> result = new ArrayList<>();
		if (digits.isEmpty()) return result;
		List<String> letters = new ArrayList<>();
		for(int i = 0; i < digits.length(); i++) {
			letters.add(phoneLetters.get(digits.charAt(i)));
		}
		// now find all combinations from the list of lists
		letterCombinationsBacktracing(letters, result, "", 0, digits.length());
		return result;
	}
	private static void letterCombinationsBacktracing(List<String> combs, List<String> result, String data, int index, int length) {
		if (data.length() == length) {
			result.add(data);
		} else {
			String s = combs.get(index);
			for (int i = 0; i < s.length(); i++) {
				letterCombinationsBacktracing(combs, result, data + s.charAt(i), index + 1, length);
			}
		}
	}
	// iterative method to solve the same problem as above
	public static List<String> letterCombinations(String digits) {
		LinkedList<String> ans = new LinkedList<String>();
		String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
		ans.add("");
		for(int i = 0; i<digits.length();i++){ // iterate over digits
			int x = Character.getNumericValue(digits.charAt(i));
			while(ans.peek().length()==i){ // while the strings in ans are shorter than digits.length, add a character to them
				String t = ans.remove(); // remove the short string
				for(char s : mapping[x].toCharArray())
					ans.add(t+s); // add all combinations of the short string + each letter that corresponds to digits[x]
			}
		}
		return ans;
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

	// Given an input string and a dictionary of words, segment the input string into a space-separated
	// sequence of dictionary words if possible. For example, if the input string is "applepie" and
	// dictionary contains a standard set of English words,	then we would return the string "apple pie" as output.
	// http://thenoisychannel.com/2011/08/08/retiring-a-great-interview-problem
	// SOLUTION: use recursive backtracing. Worst case, explore every possible segmentation of input so runtime is O(2^n) - just like finding the powerset
	public static String spaceWords(String input, Set<String> dict) {
		return spaceWords(input, dict, "", 0);
	}
	private static String spaceWords(String input, Set<String> dict, String result, int index) { // my solution
		String end = input.substring(index);
		if (dict.contains(end)) {
			return result + end;
		} else {
			for (int i = index+1; i < input.length(); i++) { // substrings don't include last char since that was checked in base case
				String part = input.substring(index, i);
				if (dict.contains(part)) {
					String s = spaceWords(input, dict, result + part + " ", i);
					if(s != null) {
						return s;
					}
				}
			}
			return null;
		}
	}
	public static String spaceWordsPretty(String input, Set<String> dict) { // example solution
		if (dict.contains(input)) return input;
		for (int i = 1; i < input.length(); i++) {
			String prefix = input.substring(0, i);
			if (dict.contains(prefix)) {
				String suffix = input.substring(i);
				String segSuffix = spaceWordsPretty(suffix, dict);
				if (segSuffix != null) {
					return prefix + " " + segSuffix;
				}
			}
		}
		return null;
	}
	private static Map<String, String> memoized = new HashMap<>(); // uses to remember previously calculated input divisions
	public static String spaceWordsEfficient(String input, Set<String> dict) { // O(n^2) - assuming substring is O(1)
		if (dict.contains(input)) return input;
		if (memoized.containsKey(input)) {
			return memoized.get(input);
		}
		for (int i = 1; i < input.length(); i++) {
			String prefix = input.substring(0, i);
			if (dict.contains(prefix)) {
				String suffix = input.substring(i);
				String segSuffix = spaceWordsEfficient(suffix, dict);
				if (segSuffix != null) {
					memoized.put(input, prefix + " " + segSuffix);
					return prefix + " " + segSuffix;
				}
			}
		}
		memoized.put(input, null);
		return null;
	}

	// prints all subsets (the powerset) of the given array
	public static void printSubsets(char[] set) {
		// loop runs 2^n times - once for each subset & uses this value as a bitmask to select the #s that are in the subset
		for (int i = 0; i < Math.pow(2, set.length); i++) { // Math.pow(2, n) = (1 << n)
			System.out.print("{ ");
			// print current subset
			for (int j = 0; j < set.length; j++) {
				// (1<<j) is a number with jth bit 1 so when we 'and' them with the
				// subset number we get which numbers are present in the subset and which are not
				if ((i & (1 << j)) > 0) 
					System.out.print(set[j] + " ");
			}
			System.out.println("}");
		}
		// Example with [a,b,c] - know there are 8 subsets:
		// i:		subset:
		// 000		{}
		// 001		{a}			1's place with value 1 means 'a' is in set
		// 010		{b}			2's place with value 1 means 'b' is in set
		// 011		{b,a}
		// 100		{c}			4's place with value 1 means 'c' is in set
		// 101		{c,a}
		// 110		{c,b}
		// 111		{c,b,a}
	}

	// SEE StringQuestions.java for combinations and permutations

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

	// Returns the count of ways we can sum  S[0...m-1] coins to get sum n
	public static int countWaysRecursive( int S[], int m, int n ) {
		if (n == 0) return 1;// 1 solution
		if (n < 0) return 0; //no solution exists
		if (m <= 0 && n >= 1) return 0; //no coins and n is greater than 0, no solution

		// count is sum of solutions (i) including S[m-1] (ii) excluding S[m-1]
		return countWaysRecursive( S, m - 1, n ) + countWaysRecursive( S, m, n-S[m-1] );
	}
	// uses dynamic programming to avoid redundant coin combinations and be more efficient
	public static long countWaysDynamic(int S[], int m, int n) {
		//Time complexity of this function: O(mn)
		//Space Complexity of this function: O(n)

		// table[i] will be storing the number of solutions
		// for value i. We need n+1 rows as the table is
		// constructed in bottom up manner using the base
		// case (n = 0)
		long[] table = new long[n+1];

		// Initialize all table values as 0
		Arrays.fill(table, 0);   //O(n)

		// Base case (If given value is 0)
		table[0] = 1;

		// Pick all coins one by one and update the table[]
		// values after the index greater than or equal to
		// the value of the picked coin
		for (int i=0; i<m; i++)
			for (int j=S[i]; j<=n; j++)
				table[j] += table[j-S[i]];

		return table[n];
	}




}
