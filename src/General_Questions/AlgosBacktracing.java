package General_Questions;

import java.util.*;

public class AlgosBacktracing {
	public static void main(String[] args) {
		System.out.println(permute(new int[] {1,2,3}));
	}
	
	// https://leetcode.com/problems/permutations/#/solutions
	
	public static List<List<Integer>> permute(int[] nums) {// requires nums to not contain any duplicates
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		permute(nums, new ArrayList<Integer>(), result);
		return result;
	}
	private static void permute(int[] nums, List<Integer> data, List<List<Integer>> result) {
		if (data.size() == nums.length) {
			result.add(new ArrayList<Integer>(data));
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
	
	// Given a collection of numbers that might contain duplicates, return all possible unique permutations.
	// [1,1,2] -> [[1,1,2], [1,2,1], [2,1,1]]
	public List<List<Integer>> permuteUnique(int[] nums) {
	    List<List<Integer>> list = new ArrayList<>();
	    Arrays.sort(nums);
	    permuteUnique(list, new ArrayList<>(), nums, new boolean[nums.length]);
	    return list;
	}
	private void permuteUnique(List<List<Integer>> list, List<Integer> tempList, int [] nums, boolean [] used){
	    if(tempList.size() == nums.length){
	        list.add(new ArrayList<>(tempList));
	    } else{
	        for(int i = 0; i < nums.length; i++){
	            if(used[i] || i > 0 && nums[i] == nums[i-1] && !used[i - 1]) continue;
	            used[i] = true; 
	            tempList.add(nums[i]);
	            permuteUnique(list, tempList, nums, used);
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
		for (int i = start; i < nums.length; i++) { // only look at the values between start and end
			tempList.add(nums[i]);
			list.add(new ArrayList<>(tempList));
			subsets(list, tempList, nums, i + 1);  // recursive calls explore progressively smaller pieces of nums
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
}
