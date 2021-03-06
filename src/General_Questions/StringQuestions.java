package General_Questions;

import java.util.*;

public class StringQuestions {

	public static void main(String[] args) {
		wordBreak("leetcode", Arrays.asList("leet", "code"));
	}
	
//	// https://leetcode.com/problems/zigzag-conversion/
//	public static String convert(String s, int numRows) {
//		int hop = 2*numRows-2;
//
//		int hop2 = hop - 2;
//
////		// 3
////		0, 4, 8, 12
////		1, 3, 5, 7, 9, 11, 13
////		2, 6, 10
////
////		4, 4, 4
////		2, 2, 2, 2
////		4, 4
////
////		// 4
////		0,      6,       12
////		1,    5, 7,    11, 13
////		2, 4,     8, 10
////		3,         9
////
////		6, 6
////		4, 2, 4, 2
////		2, 4, 2, 4
////		6
//
//		String result = "";
//		for (int i = 0; i < numRows; i++) {
//			for (int j = i; j < s.length(); j += hop) {
//				result += s.charAt(j);
//			}
//		}
//        return result;
//    }

	// Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
	// The same word in the dictionary may be reused multiple times in the segmentation.
	// https://leetcode.com/problems/word-break/
	// Runtime without noMatch: 2^n: https://stackoverflow.com/questions/31370674/time-complexity-of-the-word-break-recursive-solution
	public static boolean wordBreak(String s, List<String> wordDict) {
		return wordBreak(s, wordDict, new HashSet<>());
	}
	private static boolean wordBreak(String s, List<String> dict, Set<String> noMatch) {
		if (s.length() < 1) {
			return true;
		}
		if (noMatch.contains(s)) {
			return false;
		}

		for (int i = 0; i < dict.size(); i++) {
			String word = dict.get(i);
			if (s.startsWith(word) && wordBreak(s.substring(word.length()), dict, noMatch)) {
				return true;
			}
		}
		noMatch.add(s);
		return false;
	}
	// Runtime:
	public static boolean wordBreakPartitioning(String s, List<String> wordDict) {
		return wordBreakPartitioning(s, new HashSet<>(wordDict), new HashSet<>());
	}
	private static boolean wordBreakPartitioning(String s, Set<String> dict, Set<String> noMatch) {
		if (s.length() < 1 || dict.contains(s)) {
			return true;
		}
		if (noMatch.contains(s)) {
			return false;
		}

		for (int i = 1; i < s.length(); i++) {
			String b = s.substring(0, i);
			String e = s.substring(i);
			if (dict.contains(b)) {
				if (dict.contains(e) || wordBreakPartitioning(e, dict, noMatch)) {
					return true;
				}
			}
		}
		noMatch.add(s);
		return false;
	}

	// Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*'.
	// '?' Matches any single character.
	// '*' Matches any sequence of characters (including the empty sequence).
	// O(m+n) time and space
	public static boolean isMatch(String s, String p) {
		int m = p.length();
		int n = s.length();
		boolean[][] dp = new boolean[m+1][n+1]; // dp[i][j] is true if first i characters in pattern match the first j characters of string

		dp[0][0] = true;

		for(int i = 0; i < m; i++) { // iterate over pattern
			for(int j = 0; j <= n; j++) { // iterate over string
				if(dp[i][j]) { // do not check old combinations that do not match
					if(p.charAt(i) == '*') {
						dp[i+1][j] = true; // set entire remainder of i+1 column to true since wildcard can match the entire remainder of s
						while(j < n) { // don't set the top of column true otherwise we go out of bounds on next iteration
							dp[i+1][j+1] = true;
							j++;
						}
						break;
					} else if (j < n && (p.charAt(i) == '?' || p.charAt(i) == s.charAt(j)) ) {
						dp[i+1][j+1] = true;
					}
				}
			}
		}

		return dp[m][n];
	}

	// example: {"the", "there", "them"} returns "the"
	public static String longestCommonPrefix(String[] strs) {
		if (strs.length == 0 || strs[0].isEmpty()) return "";
		int common = 0;
		while (common < strs[0].length()) {
			char c = strs[0].charAt(common);
			for (int i = 1; i < strs.length; i++) {
				if (common >= strs[i].length() || strs[i].charAt(common) != c) return strs[0].substring(0, common);
			}
			common++;
		}
		return strs[0].substring(0, common);
	}

	// Returns indexOf needle in haystack, -1 if not found. This is not an ideal solution - ideal solution is to build a FSM for the needle, then run over haystack.
	public static int indexOf(String haystack, String needle) {
		if(needle.length() > haystack.length()) return -1;
		else if (needle.length() == haystack.length()) return needle.equals(haystack) ? 0 : -1;
		else if (needle.isEmpty()) return 0;

		for (int i = 0; i <= haystack.length() - needle.length(); i++) {
			int j = i, k = 0;
			while (j < haystack.length() && k < needle.length() && haystack.charAt(j++) == needle.charAt(k++)) {
				if (k == needle.length()) return i;
			}
		}
		return -1;
	}
	public static int strStrElegant(String haystack, String needle) {
		for (int i = 0; ; i++) {
			for (int j = 0; ; j++) {
				if (j == needle.length()) return i;
				if (i + j == haystack.length()) return -1;
				if (needle.charAt(j) != haystack.charAt(i + j)) break;
			}
		}
	}

	// Given a string, find the length of the longest substring without repeating characters. O(n) solution since low and high only increase.
	public static int lengthOfLongestSubstring(String s) {
		Set<Character> set = new HashSet<>(); // technically might be better to use bitset since only 256 possible chars
		int low = 0, high = 0, max = 0;
		while (high < s.length()) { // inv: set contains the chars from s[low] to s[high-1]
			char c = s.charAt(high);
			if (set.contains(c)) { // pause high, remove from the chars from the low end until no more duplicates 
				set.remove(s.charAt(low));
				low++;
			} else { // add to set and increment high
				set.add(c); // set contains the chars from s[low] to s[high]
				high++;		// set contains the chars from s[low] to s[high-1]
				if (set.size() > max) max = set.size();
			}
		}
		return max;
	}

	// prints all substrings of the given string - O(n^2)
	public static void printSubstrings(String word) {
		for (int from = 0; from < word.length(); from++) {
			for (int to = from + 1; to <= word.length(); to++) {
				System.out.println(word.substring(from, to));
			}
		}
	}

	// runtime = O(n^2) where n = s.length. Check n possible centers, checking for palindrome at
	// each location could be O(n) worst case
	public String longestPalindrome(String s) {
		int n = s.length();
		if (n == 0) return "";
		String longest = s.substring(0, 1);  // a single char itself is a palindrome
		for (int i = 0; i < n-1; i++) { // try to find a palindrome at all possible centers in s
			String p1 = expandAroundCenter(s, i, i);
			if (p1.length() > longest.length())
				longest = p1;

			String p2 = expandAroundCenter(s, i, i+1);
			if (p2.length() > longest.length())
				longest = p2;
		}
		return longest;
	}
	// returns the longest odd-length palindrome with a single center when c1=c2 or the longest even-length palindrome
	// when c1 = c2-1.
	public String expandAroundCenter(String s, int c1, int c2) {
		int l = c1, r = c2;
		int n = s.length();
		while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
			l--;
			r++;
		}
		return s.substring(l+1, r);
	}

	// reverses the string preserving positions of spaces; i.e. " a if" -> " f ia"
	public static String reverseIgnoringSpace(String s) {
		char[] arr = s.toCharArray();
		int front = 0;
		int end = arr.length-1;
		while(front < end) {
			while(arr[front] == ' ') front++;
			while(arr[end] == ' ') end--;
			if(front < end) {
				//swap
				char temp = arr[front];
				arr[front] = arr[end];
				arr[end] = temp;
			}
			front++;
			end--;
		}
		return new String(arr);
	}

	// Amazon interview question 1-2017
	public static String reverseWordsInPlace(String s) { // reverse the entire string, then go through and reverse each word
		char[] word = s.toCharArray();
		for(int i = 0; i < word.length/2; i++) {
			char temp = word[i];
			word[i] = word[word.length-1-i];
			word[word.length-1-i] = temp;
		}
		for(int i=0; i < word.length; i++) {
			while(i < word.length && word[i] == ' ') {
				i++;
			}
			if(i == word.length) break;
			int end = i;
			while(end < word.length && word[end] != ' ') {
				end++;
			}
			if(i < end) {
				for(int j = i; j < i+((end-i)/2); j++) {
					char temp = word[j];
					word[j] = word[end-1-(j-i)];
					word[end-1-(j-i)] = temp;
				}
				i = end;
			}
		}
		return new String(word);
	}

	// start at the end of s, find words, pull them out and add to a new stringbuilder
	public static String reverseWordsBackToFront(String s){
		StringBuilder sb = new StringBuilder();
		for(int i = s.length() - 1; i >= 0;){
			if(s.charAt(i) != ' '){
				int j = i - 1;
				//locate the starting index of the word
				while(j >= 0 && s.charAt(j) != ' ')
					j--;
				sb.append(s.substring(j + 1, i + 1));
				sb.append(" ");
				i = j - 1;
			} else i--;
		}
		if(sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String reverseWordsRegex(String s){
		String[] words = s.split("\\s+"); // split over whitespace
		StringBuilder sb = new StringBuilder();
		for(int i = words.length - 1; i >= 0; i--) {
			sb.append(words[i] + " ");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	// Reverse words in a string (words are separated by one or more spaces). Now do it in-place.
	public static String reverseWordsStack(String s){
		Scanner scan = new Scanner(s);
		Stack<String> stack = new Stack<String>();
		while(scan.hasNext()){
			stack.push(scan.next());
		}
		StringBuilder sb = new StringBuilder();
		while(!stack.isEmpty()){
			sb.append(stack.pop() + " ");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	// replace all spaces in a string with'%20'.
	public static String add20 (String s){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i< s.length(); i++){
			if(s.charAt(i) == ' '){
				sb.append("%20");
			} else{
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}

	/**
	 * Compares two version strings.
	 *
	 * Use this instead of String.compareTo() for a non-lexicographical
	 * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
	 *
	 * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
	 *
	 * @param str1 a string of ordinal numbers separated by decimal points.
	 * @param str2 a string of ordinal numbers separated by decimal points.
	 * @return The result is a negative integer if str1 is _numerically_ less than str2.
	 *         The result is a positive integer if str1 is _numerically_ greater than str2.
	 *         The result is zero if the strings are _numerically_ equal.
	 */
	public static int versionCompare(String str1, String str2) {
		String[] vals1 = str1.split("\\.");
		String[] vals2 = str2.split("\\.");
		int i = 0;
		// set index to first non-equal ordinal or length of shortest version string
		while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
			i++;
		}
		// compare first non-equal ordinal number
		if (i < vals1.length && i < vals2.length) {
			int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
			return Integer.signum(diff);
		}
		// the strings are equal or one string is a substring of the other
		// e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
		return Integer.signum(vals1.length - vals2.length);
	}

}
