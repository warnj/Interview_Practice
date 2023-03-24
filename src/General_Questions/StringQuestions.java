package General_Questions;

import java.util.*;

public class StringQuestions {

	public static void main(String[] args) {
		System.out.println();
	}

	// returns true if a permutation of s1 is in s2: https://leetcode.com/problems/permutation-in-string
	// optimized sliding window - O(l1 + (l2-l1)) time and O(1) space
	public static boolean checkInclusion(String s1, String s2) {
		if (s1.length() > s2.length()) return false;
		int[] s1map = new int[26]; // char counts in s1
		int[] s2map = new int[26]; // char counts in s2 sliding window with length = len(s1)
		for (int i = 0; i < s1.length(); i++) {
			s1map[s1.charAt(i) - 'a']++;
			s2map[s2.charAt(i) - 'a']++;
		}

		int count = 0; // count of matching char counts between s1map and s2map
		for (int i = 0; i < 26; i++)
			if (s1map[i] == s2map[i])
				count++;

		for (int i = 0; i < s2.length() - s1.length(); i++) {
			int r = s2.charAt(i + s1.length()) - 'a';
			int l = s2.charAt(i) - 'a';
			if (count == 26) return true; // all char counts match
			s2map[r]++; // move upper end of window right
			if (s2map[r] == s1map[r]) {
				count++; // previously char counts didn't match, now they do
			} else if (s2map[r] == s1map[r] + 1) {
				count--; // previously char counts were matching, now they aren't
			}
			s2map[l]--; // move lower end of window right to keep size of window = len(s1)
			if (s2map[l] == s1map[l]) {
				count++;
			} else if (s2map[l] == s1map[l] - 1) {
				count--;
			}
		}
		return count == 26;
	}
	// O(l1 * (l2-l1)) time and O(26) = O(1) space
	public static boolean checkInclusionSquared(String s1, String s2) {
		int[] counts = getCountsArray(s1);
		int[] countsTemp = counts.clone();
		for (int i = 0; i < s2.length()-s1.length(); i++) {
			char c = s2.charAt(i);
			int j = i;
			if (counts[c-'a'] > 0) { // char c is in s1, explore following chars to see if all from s1 are present
				// fill countsTemp with contents of counts
				System.arraycopy(counts, 0, countsTemp, 0, counts.length);
				int start = j;
				while (j < s2.length() && countsTemp[s2.charAt(j)-'a'] > 0) {
					countsTemp[s2.charAt(j)-'a']--;
					j++;
				}
				if (j - start == s1.length()) return true;
				// consider possibly going from O(n^2) time to O(n) by now working up from start rather than starting over from i+1
			}
		}
		return false;
	}
	// works for lowercase english letters only
	private static int[] getCountsArray(String s) {
		int[] result = new int[26];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			result[c-'a']++;
		}
		return result;
	}

	// https://leetcode.com/problems/zigzag-conversion
	// O(n) time where n = s.length, O(1) space
	public static String convert(String s, int numRows) {
		if (numRows == 1) return s;
		StringBuilder result = new StringBuilder();
		int start = 0;
		int row = 1;
		int interval1 = (numRows-1)*2; // intervals are same at row 1
		int interval2 = (numRows-1)*2;
		while (result.length() < s.length()) {
			int i = start++;
			while (i < s.length()) {
				result.append(s.charAt(i));
				i += interval1;
				if (i < s.length()) {
					result.append(s.charAt(i));
					i += interval2;
				}
			}
			row++;
			if (row == numRows) { // at the end, need same interval as beginning
				interval1 = (numRows-1)*2;
				interval2 = (numRows-1)*2;
			} else {
				interval2 = row == 2 ? 2 : interval2 + 2; // start at 2 and increase
				interval1 -= 2; // start at (numRows-1)*2 and decrease by 2 each row
			}
		}
		return result.toString();
	}

	// https://leetcode.com/problems/verifying-an-alien-dictionary
	public static boolean isAlienSorted(String[] words, String order) {
		for (int i = 1; i < words.length; i++) {
			int c = 0;
			String prev = words[i-1];
			String cur = words[i];
			while (c < prev.length() && c < cur.length() && prev.charAt(c) == cur.charAt(c)) {
				c++;
			}
			if (c == prev.length() && c == cur.length()) {
				// they were the same word, do nothing as this is lexicographically sorted
			} else if (c == prev.length()) {
				// went off the end of the first word, do nothing as this is lexicographically sorted
			} else if (c == cur.length()) {
				// went off the end of the 2nd word, 2nd is a prefix of the 1st so return false
				return false;
			} else { // chars don't match, compare against alphabet
				for (int j = 0; j < order.length(); j++) {
					char a = order.charAt(j);
					if (a == prev.charAt(c)) {
						break; // expect the leading word's char to be found 1st, exit the inner loop and continue
					} else if (a == cur.charAt(c)){
						return false; // following word's char found 1st, not alphabetic
					}
				}
			}
		}
		return true;
	}
	
	// https://leetcode.com/problems/greatest-common-divisor-of-strings
	// doing things the hard way
	public static String gcdOfStrings(String str1, String str2) {
		String gcd = str2;
		while (!gcd.isEmpty()) {
			if (str1.replaceAll(gcd, "").isEmpty() && str2.replaceAll(gcd, "").isEmpty()) {
				return gcd;
			}
			int i = 1;
			while (gcd.length()-i > 0 && str2.length() % (gcd.length()-i) != 0 && str1.length() % (gcd.length()-i) != 0) {
				i++;
			}
			gcd = gcd.substring(0, gcd.length()-i);
		}
		return "";
	}

	// https://leetcode.com/problems/reorganize-string
	// technically O(n) runtime, but have to go through all 26 options to find max with each character in s
	public static String reorganizeString(String s) {
		int[] buckets = new int[26];
		for (int i = 0; i < s.length(); i++) {
			buckets[s.charAt(i)-'a']++;
		}
		StringBuilder sb = new StringBuilder(s.length());
		int prev = -1;
		while (sb.length() < s.length()) {
			// find max that isn't the same as the previous
			int max = -1;
			int maxVal = -1;
			for (int j = 0; j < 26; j++) {
				if (j == prev) continue;
				if (buckets[j] > maxVal) {
					max = j;
					maxVal = buckets[j];
				}
			}
			if (buckets[max] == 0) return "";
			prev = max;
			buckets[max]--;
			sb.append((char)(max+'a'));
		}
		return sb.toString();
	}

	// very similar to: https://leetcode.com/problems/decode-ways
	// getRepresentations("123") -> [MD, BX, BCD]
	public static List<String> getRepresentations(String s) {
		List<String> result = new ArrayList<>();
		if (s == null) return result;
		getRepresentations(result, s, 0, new StringBuilder());
		return result;
	}
	private static void getRepresentations(List<String> result, String s, int n, StringBuilder sb) {
		if (n < s.length()) {
			char c = s.charAt(n);
			// if number 2 digits, explore that case
			if ((c == '1' || c == '2') && n < s.length()-1) {
				StringBuilder newSb = new StringBuilder(sb);
				int temp = Integer.parseInt(s.substring(n, n+2));
				newSb.append((char)(temp+'A'));
				getRepresentations(result, s, n+2, newSb);
			}
			// todo: check if non-number
			sb.append((char)(c-'0'+'A')); // 0->A, 1->B ... 25->Z
			// explore single digit case
			getRepresentations(result, s, n+1, sb);
		} else {
			result.add(sb.toString());
		}
	}

	public boolean isIsomorphic(String s, String t) {
		// note: maps could be int[26] since lowercase chars
		if (s == null || t == null || s.length() != t.length()) return false;
		Map<Character,Integer> sCounts = getCounts(s);
		Map<Character,Integer> tCounts = getCounts(t);
		Map<Character,Character> map = new HashMap<>();

		for (int i = 0; i < s.length(); i++) {
			if (map.containsKey(s.charAt(i))) {
				char expected = map.get(s.charAt(i));
				if (expected != t.charAt(i)) return false;
			} else {
				int sCount = sCounts.get(s.charAt(i));
				int tCount = tCounts.get(t.charAt(i));
				if (sCount != tCount) return false;
				map.put(s.charAt(i), t.charAt(i));
			}
		}
		return true;
	}

	public String addBinary(String a, String b) {
		int i = a.length()-1;
		int j = b.length()-1;
		String result = "";
		boolean carry = false;
		while (i >= 0 && j >= 0) {
			if (a.charAt(i) == '1' && b.charAt(j) == '1') {
				if (carry) {
					result = "1" + result;
				} else {
					result = "0" + result;
					carry = true;
				}
			} else if (a.charAt(i) == '0' && b.charAt(j) == '0') {
				if (carry) result = "1" + result;
				else result = "0" + result;
				carry = false;
			} else { // a 1 and a 0
				if (carry) {
					result = "0" + result;
					// leave carry true
				} else {
					result = "1" + result;
					// leave carry false
				}
			}
			i--;
			j--;
		}
		while (i >= 0) {
			if (a.charAt(i) == '1') {
				if (carry) {
					result = "0" + result;
				} else {
					result = "1" + result;
				}
			} else {
				if (carry) {
					result = "1" + result;
					carry = false;
				} else {
					result = "0" + result;
				}
			}
			i--;
		}
		while (j >= 0) {
			if (b.charAt(j) == '1') {
				if (carry) {
					result = "0" + result;
				} else {
					result = "1" + result;
				}
			} else {
				if (carry) {
					result = "1" + result;
					carry = false;
				} else {
					result = "0" + result;
				}
			}
			j--;
		}
		if (carry) result = "1" + result;
		return result;
	}

	public static String frequencySort(String s) {
		if (s == null) return null;
		Map<Character,Integer> counts = getCounts(s);
		// need Character, not char for comparator, can't use s.toCharArray() and then return new String(chararray)
		Character[] charObj = new Character[s.length()];
		for (int i = 0; i < s.length(); i++) {
			charObj[i] = s.charAt(i);
		}
		// keep same chars together if they have same counts
		Arrays.sort(charObj, (a,b) -> counts.get(b) != counts.get(a) ? counts.get(b) - counts.get(a) : b-a);
		StringBuilder sb = new StringBuilder();
		for (Character c : charObj) sb.append(c);
		return sb.toString();
	}
	private static Map<Character,Integer> getCounts(String s) {
		Map<Character,Integer> m = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			m.put(c, 1 + m.getOrDefault(c, 0));
		}
		return m;
	}

	// https://leetcode.com/problems/longest-repeating-character-replacement
	// O(n) time and O(1) space
	public static int characterReplacement(String s, int k) {
		int[] chars = new int[26];
		int lo=0, maxRepeatingCh=0, max=0;
		for (int hi=0; hi < s.length(); hi++){
			maxRepeatingCh = Math.max(maxRepeatingCh, ++chars[s.charAt(hi)-'A']);
			while (hi-lo+1-maxRepeatingCh > k){
				chars[s.charAt(lo)-'A']--;
				lo++;
			}
			max = Math.max(max, hi-lo+1);
		}
		return max;
	}
	public static int characterReplacement2(String s, int k) {
		int lo = 0, hi = 0, max = 1;
		int[] chars = new int[26];
		int curRepl = 0; // number of current letters that would be replaced in the window
		while (hi < s.length()) {
			if (curRepl > k) { // shrink from lower end
				char lower = s.charAt(lo);
				chars[lower-'A']--;
				lo++;
			} else { // increase upper end
				max = Math.max(max, hi-lo); // window is valid, so check size
				char upper = s.charAt(hi);
				chars[upper-'A']++;
				hi++;
			}
			curRepl = getReplaceCount(chars);
		}
		if (curRepl <= k) max = Math.max(max, hi-lo); // window may also be valid
		return max;
	}
	// probably a better way of tracking this, but it works for now and is technically constant time
	private static int getReplaceCount(int[] a) {
		int sum = a[0];
		int max = a[0];
		for (int i = 1; i < a.length; i++) {
			sum += a[i];
			max = Math.max(max, a[i]);
		}
		return sum - max;
	}
	// O(n) time and O(1) space
	public static int characterReplacementOG(String s, int k) {
		int n = s.length();
		if (k-1 >= n) return n;
		int result = k+1;
		// sliding window
		int lo = 0;
		int hi = k+1; // look at substrings of len k+2 and greater
		int[] counts = new int[26];
		for (int i = lo; i <= hi; i++) { // initialize counts
			counts[s.charAt(i) - 'A']++;
		}
		while (hi < n) {
			int max = getMax(counts);
			int len = hi-lo+1;
			if (max + k >= len) {
				// could be a valid replacement
				result = Math.max(len, result);
				hi++;
				if (hi < n) counts[s.charAt(hi)-'A']++; // add the new count
			} else {
				counts[s.charAt(lo)-'A']--; // remove the old count
				lo++;
			}
		}
		return result;
	}
	private static int getMax(int[] a) {
		int m = a[0];
		for (int i = 1; i < a.length; i++) {
			if (a[i] > m) m = a[i];
		}
		return m;
	}
	// same as below brute but optimized to O(n^2) by reducing duplicate calculations
	public static int characterReplacementSlow(String s, int k) {
		if (k >= s.length()) return s.length();
		int n = s.length();
		int result = k+1; // single char is always present in the string, so k+1 is guaranteed to be possible
		int[] counts = new int[26];
		// look at substrings of len k+2 and greater
		for (int i = 0; i < n-k-1; i++) { // starting points
			int max = 1;
			for (int j = i; j < i+k+1; j++) {
				if (++counts[s.charAt(j) - 'A'] > max) max = counts[s.charAt(j) - 'A'];
			}
			for (int j = i+k+1; j < n; j++) { // ending points inclusive
				// largest count of a single char >= j-i
				if (++counts[s.charAt(j) - 'A'] > max) max = counts[s.charAt(j) - 'A'];
				int len = j-i+1;
				if (max + k >= len) {
					// could be a valid replacement
					result = Math.max(len, result);
				}
			}
			Arrays.fill(counts, 0);
		}
		return result;
	}
	// brute force O(n^3) time, O(1) space
	public static int characterReplacementBrute(String s, int k) {
		if (k >= s.length()) return s.length();
		int n = s.length();
		int result = k+1; // single char is always present in the string, so k+1 is guaranteed to be possible
		int[] counts = new int[26];
		// look at substrings of len k+2 and greater
		for (int i = 0; i < n-k-1; i++) { // starting points
			for (int j = i+k+1; j < n; j++) { // ending points inclusive
				// largest count of a single char >= j-i
				int mostRepeated = getMostRepeated(s, i, j, counts);
				int len = j-i+1;
				if (mostRepeated + k >= len) {
					// could be a valid replacement
					result = Math.max(len, result);
				}
			}
		}
		return result;
	}
	private static int getMostRepeated(String s, int lo, int hi, int[] counts) {
		Arrays.fill(counts, 0);
		int max = 1;
		for (int i = lo; i <= hi; i++) {
			if (++counts[s.charAt(i) - 'A'] > max) max = counts[s.charAt(i) - 'A'];
		}
		return max;
	}

	// https://leetcode.com/problems/palindromic-substrings/
	// O(n^2) time, O(1) extra space
	public static int countSubstrings(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) { // all palindrome middle points
			count += palindromeCount(s, i, i); // odd length palindromes
			count += palindromeCount(s, i, i+1); // even length palindromes
		}
		return count;
	}
	private static int palindromeCount(String s, int lo, int hi) {
		int count = 0;
		while (lo >= 0 && hi < s.length() && s.charAt(lo--) == s.charAt(hi++)) count++;
		return count;
	}
	// O(n^3) time, O(1) extra space
	public static int countSubstringsBrute(String s) {
		char[] c = s.toCharArray();
		int count = 0;
		for (int i = 0; i < s.length(); i++) { // all starting points
			for (int j = i; j < s.length(); j++) { // all ending points
				if (isPalindrome(c, i, j)) count++;
			}
		}
		return count;
	}
	private static boolean isPalindrome(char[] c, int lo, int hi) {
		while (lo <= hi) {
			if (c[lo] != c[hi]) return false;
			lo++;
			hi--;
		}
		return true;
	}

	// https://leetcode.com/problems/decode-ways/
	// https://leetcode.com/problems/decode-ways/discuss/30451/Evolve-from-recursion-to-dp
	// O(n) time and space with memoization, O(2^n) without! Can convert to dp solution.
	public static int numDecodings(String s) {
		Integer[] mem = new Integer[s.length()];
		return numDecodings(s, 0, mem);
	}
	private static int numDecodings(String s, int p, Integer[] mem) {
		int n = s.length();
		if (p == n) return 1; // at the end, add a valid way to decode
		if (s.charAt(p) == '0') return 0; // not a valid way to decode s, exit with no additional way
		if (mem[p] != null) return mem[p];
		int res = numDecodings(s, p+1, mem); // single letter ways to decode
		if (p < n-1 && (s.charAt(p) == '1' || s.charAt(p) == '2' && s.charAt(p+1) < '7')) // can group pair and decode
			res += numDecodings(s, p+2, mem); // add ways to decode after pairing
		mem[p] = res;
		return res;
	}

	// https://leetcode.com/problems/compare-version-numbers/
	public static int compareVersion(String v1, String v2) {
		String[] revs1 = v1.split("\\.");
		String[] revs2 = v2.split("\\.");
		int minLen = Math.min(revs1.length, revs2.length);
		for (int i = 0; i < minLen; i++) {
			if (!revs1[i].isEmpty() && !revs2[i].isEmpty()) {
				int r1 = Integer.parseInt(revs1[i]);
				int r2 = Integer.parseInt(revs2[i]);
				if (r1 > r2) return 1;
				if (r2 > r1) return -1;
			} else if (revs1[i].isEmpty()) {
				int r2 = Integer.parseInt(revs2[i]);
				if (r2 != 0) return -1;
			} else if (revs2[i].isEmpty()) {
				int r1 = Integer.parseInt(revs1[i]);
				if (r1 != 0) return 1;
			}
		}
		// verify remaining revisions are 0
		int maxLen = Math.max(revs1.length, revs2.length);
		for (int i = minLen; i < maxLen; i++) {
			String rev = revs1.length > revs2.length ? revs1[i] : revs2[i];
			if (rev.isEmpty()) continue;
			int r = Integer.parseInt(rev);
			if (r != 0) return revs1.length > revs2.length ? 1 : -1;
		}
		return 0;
	}

	// https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/
	// keep in mind: chars are perfect for bucket sort. There is a sliding window solution with better runtime
	// O(n*(n+k)) time, O(1) space - maintain sum as each possible substring changes, find min count is constant since 26 letters max
	public static int longestSubstring(String s, int k) {
		if (k > s.length()) return 0;
		int result = 0;
		int[] counts = new int[26];
		for (int z = 0; z < k-1; z++) {
			counts[s.charAt(z)-'a']++;
		}
		for (int i = 0; i <= s.length()-k; i++) {
			for (int j = i+k; j <= s.length(); j++) {
				counts[s.charAt(j-1)-'a']++;
//				System.out.printf("Considering substring of length %d from [%d,%d)\n", j-i, i, j);
//				System.out.println("Substring: " + s.substring(i, j));
//				for (int z = 0; z < 26; z++)
//					if (counts[z] > 0) System.out.println(Character.toString(z+'a') + " count: " + counts[z]);
				if (j-i <= result) continue;
				int min = Integer.MAX_VALUE;
				for (int z = 0; z < 26; z++)
					if (counts[z] > 0) min = Math.min(min, counts[z]);
//				System.out.printf("Min count found was %d, result is already %d\n", min, result);
				if (min >= k) result = Math.max(result, j-i);
			}
			if (i < s.length()-k) { // reset counts for next starting location
				counts = new int[26];
				for (int z = i+1; z < i+k; z++) {
					counts[s.charAt(z) - 'a']++;
				}
			}
		}
		return result;
	}
	// O(n^3) time, O(1) space - re-sum the substring between every start and end
	public static int longestSubstringBrute(String s, int k) {
		int result = 0;
		for (int i = 0; i <= s.length()-k; i++) { // starting points
			for (int j = i+k; j <= s.length(); j++) { // ending points (exclusive)
				if (j-i <= result) continue; // if a long substring has already been found no need to examine small one
				int[] counts = new int[26];
				for (int z = i; z < j; z++) {
					char c = s.charAt(z);
					counts[c-'a']++;
				}
				int min = Integer.MAX_VALUE;
				for (int z = 0; z < 26; z++) {
					if (counts[z] > 0) min = Math.min(min, counts[z]);
				}
				if (min >= k) result = Math.max(result, j-i);
			}
		}
		return result;
	}

	// https://leetcode.com/problems/remove-k-digits/
	// more efficient O(n) time and space solution using a stack
	// worst case runtime O(n * k) since we have a contains() call and while loop to find max, both run in O(n) time, space O(1)
	// will do up to k recursions. k in worst case can be n-1. recursive equation is T(x)=T(x-1)+n
	public static String removeKdigits(String num, int k) {
		if (k >= num.length()) return "0";
		if (k == 0) return num;

		// if there is a 0 in first k+1 digits, it counts as a "* 10" so will always be the choice to remove, remove the digits before it
		if (num.substring(0, k+1).contains("0")) {
			int i = 0;
			while (num.charAt(i) != '0') i++;
			k -= i; // i = index of first 0, remove the number of leading non-zeros from k digits
			while (i < num.length() && num.charAt(i) == '0') i++;
			if (i == num.length()) return "0";
			// removing leading 0s doesn't count as numbers lost from k
			num = num.substring(i);
			if (k > 0) return removeKdigits(num, k); // still have digits to remove, recurse
			return num;
		}
		// remove 1 digit at a time with recursion:
		// digit to remove can be chosen as the first digit that occurs with a smaller digit on it's right
		char max = num.charAt(0);
		int i = 1;
		while (i < num.length() && num.charAt(i) >= max) { // comparing </> as ints/chars doesn't matter, larger nums still greater
			max = (char) Math.max(max, num.charAt(i));
			i++;
		}
		i--; // i is either at the first digit that decreased or end of array, move back to largest digit
//		System.out.println("Comparing " + a + " and " + num.charAt(i));
		// remove whichever is larger: char at 0 or i
		if (num.charAt(0) >= num.charAt(i)) {
			return removeKdigits(num.substring(1), k-1); // remove first digit
		} else if (i == num.length() - 1) {
			return removeKdigits(num.substring(0, i), k-1); // remove last digit
		} else {
			return removeKdigits(num.substring(0, i) + num.substring(i+1), k-1); // remove a middle digit
		}
	}

	// https://leetcode.com/problems/find-the-difference/
	// O(n) runtime, O(1) memory
	public static char findTheDifference(String s, String t) {
		int charCode = t.charAt(s.length());
		for (int i = 0; i < s.length(); i++) {
			charCode -= s.charAt(i);
			charCode += t.charAt(i);
		}
		return (char)charCode;
	}
	// O(n) runtime, O(1) memory
	public static char findTheDifferenceCounts(String s, String t) {
		int[] counts = new int[26]; // input is lowercase letters
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			counts[c-'a']++;
		}
		for (int i = 0; i < t.length(); i++) {
			char c = t.charAt(i);
			counts[c-'a']--;
		}
		for (int i = 0; i < 26; i++) {
			if (counts[i] != 0) {
				return (char)(i+'a');
			}
		}
		return 'X'; // error
	}

	// https://leetcode.com/problems/valid-palindrome/
	public static boolean isPalindrome(String s) {
		s = s.toLowerCase();
		int lo = 0;
		int hi = s.length()-1;
		while (lo <= hi) {
			while (lo < s.length() && !Character.isLetterOrDigit(s.charAt(lo))) lo++;
			while (hi >= 0 && !Character.isLetterOrDigit(s.charAt(hi))) hi--;
			if (lo <= hi && s.charAt(lo) != s.charAt(hi)) return false;
			lo++;
			hi--;
		}
		return true;
	}

	// https://leetcode.com/problems/string-to-integer-atoi/
	public static int myAtoi(String s) {
		// get start and end of number
		int j = 0;
		while (j < s.length() && s.charAt(j) == ' ') { j++; } // skip leading whitespace
		if (j >= s.length()) return 0; // all whitespace
		while (j < s.length() && s.charAt(j) == '0') { j++; } // skip leading 0s
		if (j >= s.length()) return 0; // all 0s
		if (!Character.isDigit(s.charAt(j)) && s.charAt(j) != '-' && s.charAt(j) != '+') return 0;
		if (j-1 >= 0 && s.charAt(j-1) == '0' && !Character.isDigit(s.charAt(j))) return 0; // leading 0s and then + or -

		int start = j++; // charAt(j) = digit or - or +
		while (j < s.length() && Character.isDigit(s.charAt(j))) { j++;	}
		int end = j-1;
		if (start == end && !Character.isDigit(s.charAt(end))) return 0; // no digits after "-" or "+"

		int result = s.charAt(end--) - '0';
		int pow = 10;
		boolean overflow = false;
		while (end >= start && Character.isDigit(s.charAt(end))) {
			int digit = s.charAt(end)-'0';
			int addition = digit * pow;
			if ((long) result + addition > Integer.MAX_VALUE || (long) pow * 10 > Integer.MAX_VALUE) {
				overflow = true;
			}
			result += addition;
			pow *= 10;
			end--;
		}
		if (end >= start && s.charAt(start) == '-') {
			if (overflow) return Integer.MIN_VALUE;
			result = -result;
		} else if (overflow) {
			return Integer.MAX_VALUE;
		}
		return result;
	}
	public static int myAtoiOriginal(String s) {
		int i = s.length()-1; // easiest to work back to front
		while (i >= 0 && !Character.isDigit(s.charAt(i))) {
			i--;
		}
		if (i == -1) return 0; // no digits

		int result = s.charAt(i--) - '0';
		int pow = 10;
		boolean overflow = false;
		while (i >= 0 && Character.isDigit(s.charAt(i))) {
			if (result + ((s.charAt(i)-'0') * pow) < result) { // overflow
				overflow = true;
			}
			result += (s.charAt(i)-'0') * pow;
			pow *= 10;
			i--;
		}
		if (i >= 0 && s.charAt(i) == '-') {
			if (overflow) return Integer.MIN_VALUE;
			result = -result;
		} else if (overflow) {
			return Integer.MAX_VALUE;
		}
		return result;
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
	public static boolean wordBreakDP(String s, List<String> wordDict) {
		Set<String> wordDictSet = new HashSet<>(wordDict);
		boolean[] dp = new boolean[s.length() + 1];
		dp[0] = true;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[s.length()];
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
		while (high < s.length()) { // inv: set contains the chars from s[low] to s[high-1] inclusive
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
	public int lengthOfLongestSubstring2(String s) {
		Set<Character> set = new HashSet<>();
		int lo = 0, hi = 0, max = 0;
		while (hi < s.length()) {
			// move lo pointer up until sliding window doesn't contain current (hi) char
			while (set.contains(s.charAt(hi)) && lo < hi) {
				set.remove(s.charAt(lo));
				lo++;
			}
			// add current char
			set.add(s.charAt(hi));
			hi++;
			max = Math.max(max, hi-lo);
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

	// https://leetcode.com/problems/longest-palindrome
	// longest palindrome can be built with chars that occur in even counts, if it's odd then make it even
	public static int longestPalindrome(String s) {
		int[] counts = new int[26*2];
		for (int i = 0; i < s.length(); i++) {
			int index = getIndex(s.charAt(i));
			counts[index]++;
		}
		int result = 0;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] % 2 == 0) {
				result += counts[i];
			} else {
				result += counts[i] - 1;
			}
		}
		return result < s.length() ? result+1 : result; // can put odd char in middle of palindrome if there is a spare char for it
	}
	// squish upper and lowercase array into single array of counts, returns the index
	private static int getIndex(char c) {
		if (Character.isUpperCase(c)) {
			return c - 'A' + 26;
		} else {
			return c - 'a';
		}
	}

	// https://leetcode.com/problems/longest-palindromic-substring
	// runtime = O(n^2) where n = s.length. Check n possible centers, checking for palindrome at
	// each location could be O(n) worst case
	public String longestPalindromeSubstring(String s) {
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
	public String expandAroundCenter(String s, int l, int r) {
		while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
			l--;
			r++;
		}
		return s.substring(l+1, r);
	}
	// better solution that would be O(n) if call to substring didn't always create a new string
	public String longestPalindromeSubstringBest(String s) {
		String res = "";
		int curLen = 0;
		for (int i = 0; i < s.length(); i++) {
			if (isPalindrome(s, i-curLen-1, i)) {
				res = s.substring(i-curLen-1, i+1);
				curLen += 2;
			} else if (isPalindrome(s, i-curLen, i)) {
				res = s.substring(i-curLen, i+1);
				curLen++;
			}
		}
		return res;
	}
	private boolean isPalindrome(String s, int begin, int end) {
		if (begin < 0) return false;
		while (begin < end) {
			if (s.charAt(begin++) != s.charAt(end--)) return false;
		}
		return true;
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
