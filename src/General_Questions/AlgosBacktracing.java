package General_Questions;

import java.util.*;

public class AlgosBacktracing {
	public static void main(String[] args) {
		System.out.println(partitionPalindrome("aab"));
	}

	// https://leetcode.com/problems/permutations/#/solutions

	// Given a string s, partition s such that every substring of the partition is a palindrome. "aab" returns [["aa","b"], ["a","a","b"]]
	public static List<List<String>> partitionPalindrome(String s) {
		List<List<String>> result = new ArrayList<>();
		partitionPalindrome(s, new ArrayList<>(), result, 0);
		return result;
	}
	private static void partitionPalindrome(String s, List<String> data, List<List<String>> result, int start) {
		if(start == s.length()) {
			result.add(new ArrayList<>(data));
		} else {
			for(int i = start; i < s.length(); i++) { // start to end ensures no duplicate palindrome partitions
				if (isPalindrome(s, start, i)) {
					data.add(s.substring(start, i + 1));
					partitionPalindrome(s, data, result, i + 1);
					data.remove(data.size() - 1);
				}
			}
		}
	}
	private static boolean isPalindrome(String s, int start, int end) {
		while (end > start) {
			if (s.charAt(end--) != s.charAt(start++)) return false;
		}
		return true;
	}

	// Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
	// The same repeated number may be chosen from C unlimited number of times. The solution set must not contain duplicate combinations.
	// candidates will not contain any duplicates & all numbers (target included) will be positive
	public static List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<>();
		combinationSum(candidates, target, new ArrayList<>(), result, 0);
		return result;
	}
	private static void combinationSum(int[] nums, int target, List<Integer> data, List<List<Integer>> result, int start) {
		if (0 == target) {
			result.add(new ArrayList<>(data));
		} else {
			for (int i = start; i < nums.length; i++) { // avoid duplicate results using start to end rather than 0 to end
				if (target - nums[i] < 0) continue; // the next sum is > target, skip it
				data.add(nums[i]);
				combinationSum(nums, target-nums[i], data, result, i); // not i + 1 because we can reuse same elements
				data.remove(data.size()-1);
			}
		}
	}
	// same as above, but numbers in candidates can only be used once
	public static List<List<Integer>> combinationSumNoRepeat(int[] candidates, int target) {
		Arrays.sort(candidates);
		List<List<Integer>> result = new ArrayList<>();
		combinationSumNoRepeat(candidates, target, new ArrayList<>(), result, 0);
		return result;
	}
	private static void combinationSumNoRepeat(int[] nums, int target, List<Integer> data, List<List<Integer>> result, int start) {
		if (0 == target) {
			result.add(new ArrayList<>(data));
		} else {
			for (int i = start; i < nums.length; i++) { // avoid duplicate results using start to end rather than 0 to end
				if (target - nums[i] < 0 || (i > start && nums[i] == nums[i-1])) continue; // skip duplicates
				data.add(nums[i]);
				combinationSumNoRepeat(nums, target-nums[i], data, result, i+1);
				data.remove(data.size()-1);
			}
		}
	}

	public static List<List<Integer>> permute(int[] nums) {// requires nums to not contain any duplicates
		List<List<Integer>> result = new ArrayList<>();
		permute(nums, new ArrayList<Integer>(), result);
		return result;
	}
	private static void permute(int[] nums, List<Integer> data, List<List<Integer>> result) {
		if (data.size() == nums.length) {
			result.add(new ArrayList<>(data));
		} else {
			for (int i = 0; i < nums.length; i++) { // explore all values of nums that != nums[i]
				if (data.contains(nums[i])) continue; // would allow any number of duplicates if this was not 
				// here: [1, 2] = [[1, 1], [1, 2], [2, 1], [2, 2]]: runtime = O(n^n) where n = nums.length 
				data.add(nums[i]);
				permute(nums, data, result);
				data.remove(data.size()-1);
			}
		}
	}
	public static List<List<Integer>> permuteIterative(int[] nums) {
	    LinkedList<List<Integer>> result = new LinkedList<>();
	    result.add(new ArrayList<>());
	    for (int n : nums) {
	        int size = result.size();
	        for (; size > 0; size--) {// get each partial list previously built
	            List<Integer> partial = result.pollFirst(); // taking partial lists from front
	            for (int i = 0; i <= partial.size(); i++) {
	                List<Integer> t = new ArrayList<>(partial);
	                t.add(i, n); // insert n into every possible location within partial list
	                result.add(t); // adding longer, more complete lists to back
	            }
	        }
	    }
	    return result;
	}
	// Given a collection of numbers that might contain duplicates, return all possible unique permutations.
	// [1,1,2] -> [[1,1,2], [1,2,1], [2,1,1]]
	public static List<List<Integer>> permuteUnique(int[] nums) {
		List<List<Integer>> list = new ArrayList<>();
		Arrays.sort(nums);
		permuteUnique(list, new ArrayList<>(), nums, new boolean[nums.length], 0);
		return list;
	}
	private static void permuteUnique(List<List<Integer>> list, List<Integer> tempList, int[] nums, boolean[] used, int level){
		if (tempList.size() == nums.length){
			//	    	for (int j=0;j<level;j++)System.out.print('\t');
			//        	System.out.println(tempList);
			list.add(new ArrayList<>(tempList));
		} else{
			for(int i = 0; i < nums.length; i++){
				if (used[i] || (i > 0 && nums[i] == nums[i-1] && !used[i-1])) continue; // don't re-explore where we started or 
				// do what we've done before (on the 2nd duplicate when the first isn't used indicates a duplicate scenario - still not 100% how)

				//	            for (int j=0;j<level;j++)System.out.print('\t');
				//	        	System.out.println(tempList);
				used[i] = true; 
				tempList.add(nums[i]);
				permuteUnique(list, tempList, nums, used, level+1);
				used[i] = false; 
				tempList.remove(tempList.size() - 1);
			}
		}
	}

	public static List<List<Integer>> subsets(int[] nums) {// requires nums to not contain any duplicates
		List<List<Integer>> list = new ArrayList<>();
		subsets(list, new ArrayList<>(), nums, 0);
		return list;
	}
	private static void subsets(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
		System.out.println(start + "\t" + tempList);
		for (int i = start; i < nums.length; i++) { // only look at the values between start and end
			tempList.add(nums[i]);
			list.add(new ArrayList<>(tempList));
			subsets(list, tempList, nums, i + 1);  // explore remaining section of nums
			tempList.remove(tempList.size() - 1);
		}
	}
	// Given a collection of integers that might contain duplicates, nums, return all possible subsets. The solution set must not contain duplicate subsets.
	public List<List<Integer>> subsetsWithDup(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> list = new ArrayList<>();
		subsetsWithDup(list, new ArrayList<>(), nums, 0);
		return list;
	}
	private void subsetsWithDup(List<List<Integer>> list, List<Integer> tempList, int [] nums, int start){
		list.add(new ArrayList<>(tempList));
		for (int i = start; i < nums.length; i++){
			if (i > start && nums[i] == nums[i-1]) continue; // skip duplicates since we know nums is sorted
			tempList.add(nums[i]);
			subsetsWithDup(list, tempList, nums, i + 1);
			tempList.remove(tempList.size() - 1);
		}
	}

	// prints all subsets (the powerset) of the given array
	public static void printSubsetsIterative(char[] set) {
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

	// Returns all distinct results of choosing the given number of items from the given array. Order does not matter.
	// example of input: combinations(new String[]{"1","2","3","4"}, 2);
	// example of output: [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]
	public static List<String> combinations(String s, int choose) {
		if (choose > s.length() || choose < 0) throw new IllegalArgumentException();
		List<String> list = new ArrayList<>();
		combinations(s.toCharArray(), 0, choose, new char[choose], list);
		return list;
	}
	private static void combinations(char[] arr, int startPosition, int len, char[] result, List<String> resultList) {
		if (len == 0) {
			resultList.add(new String(result));
		} else { // this is incredibly brilliant, potent code that is really hard to wrap your head around
			for (int i = startPosition; i <= arr.length-len; i++) { // loop through # of ways to pick the next-lowest element in the result array between startPosition & arr.length-1 choosing len items
				result[result.length - len] = arr[i]; // fill in the next-lowest index of the temp result array with the chosen element
				combinations(arr, i+1, len-1, result, resultList); // explore, picking one-less item from the remaining section of the array
			}
		}
	}

	// Outputs all permutations of the given string.
	// https://courses.cs.washington.edu/courses/cse143/15au/lectures/11-09/17-recursive-backtracking.pdf
	public static List<String> permutations(String s) {
		List<String> list = new ArrayList<String>();
		permutations(s, "", list);
		return list;
	}
	private static void permutations(String s, String chosen, List<String> list) {
		if (s.length() == 0) {
			// base case: no choices left to be made
			list.add(chosen);
		} else {
			// recursive case: choose each possible next letter
			for (int i = 0; i < s.length(); i++) {
				// choose; cut out a letter from each index in s, add to chosen
				char c = s.charAt(i); // choose
				s = s.substring(0, i) + s.substring(i + 1);
				chosen += c;
				// explore
				permutations(s, chosen, list);
				// un-choose; put s back the way it was, remove last letter of chosen
				s = s.substring(0, i) + c + s.substring(i);
				chosen = chosen.substring(0, chosen.length() - 1);
			}
		}
	}

	// shorter way of doing same thing as above
	public static void permutation(String str) {
		permutation("", str);
	}
	private static void permutation(String prefix, String str) {
		int n = str.length();
		if (n == 0) System.out.println(prefix);
		else {
			for (int i = 0; i < n; i++)
				permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
		}
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
					if(s != null) return s;
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
}
