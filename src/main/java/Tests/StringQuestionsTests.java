package Tests;

import General_Questions.StringQuestions;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class StringQuestionsTests {

	@Test
	public void testMaxConsecutiveAnswers() {
		assertEquals(5, StringQuestions.maxConsecutiveAnswers("TTFTTFTT", 1));
		assertEquals(3, StringQuestions.maxConsecutiveAnswers("TTFF", 1));
	}

	@Test
	public void testLongestCommonSuffix() {
		assertEquals("ant", StringQuestions.longestCommonSuffix(new String[]{"pendant", "chant", "plant", "supplant"}));
		assertEquals("ant", StringQuestions.longestCommonSuffix(new String[]{"pendant", "chant", "plant", "supplant", "ant"}));
		assertEquals("t", StringQuestions.longestCommonSuffix(new String[]{"pendant", "chant", "planet", "supplant", "ant"}));
		assertEquals("", StringQuestions.longestCommonSuffix(new String[]{"e", "a", "z", "x", "p"}));
	}

	@Test
	public void testPermutationInString() {
		assertTrue(StringQuestions.checkInclusion("ab", "eidbaooo"));
		assertTrue(StringQuestions.checkInclusion("abc", "eidbbbacooo"));
		assertFalse(StringQuestions.checkInclusion("abc", "eidcbbbaooo"));
		assertFalse(StringQuestions.checkInclusion("ab", "eidboaoo"));
	}

	@Test
	public void testZigZagString() {
		assertEquals("PYAIHRNAPLSIIG", StringQuestions.convert("PAYPALISHIRING", 2));
		assertEquals("PAHNAPLSIIGYIR", StringQuestions.convert("PAYPALISHIRING", 3));
		assertEquals("PINALSIGYAHRPI", StringQuestions.convert("PAYPALISHIRING", 4));
		assertEquals("PHASIYIRPLIGAN", StringQuestions.convert("PAYPALISHIRING", 5));
		assertEquals("A", StringQuestions.convert("A", 1));
	}

	@Test
	public void testAlienDict() {
		assertTrue(StringQuestions.isAlienSorted(new String[]{"hello", "land"}, "hlabcdefgijkmnopqrstuvwxyz"));
	}

	@Test
	public void testGCD() {
		assertEquals("AB", StringQuestions.gcdOfStrings("ABABAB", "ABAB"));
	}

	@Test
	public void testFrequencySort() {
		assertEquals("BBBAA", StringQuestions.frequencySort("ABABB"));
		assertEquals("aaaabbbAA", StringQuestions.frequencySort("AAaaaabbb"));
		assertEquals(".../", StringQuestions.frequencySort(".../"));
		assertEquals("yyy11z", StringQuestions.frequencySort("1zyy1y"));
		assertEquals("eeeeoollvtdc", StringQuestions.frequencySort("loveleetcode"));
	}

	@Test
	public void testCharacterReplacement() {
		assertEquals(4, StringQuestions.characterReplacement("ABAB", 2));
		assertEquals(4, StringQuestions.characterReplacement("AABABBA", 1));
		assertEquals(2, StringQuestions.characterReplacement("ABAA", 0));
	}

	@Test
	public void testCountSubstrings() {
		assertEquals(6, StringQuestions.countSubstrings("aaa"));
		assertEquals(3, StringQuestions.countSubstrings("abc"));
	}

	@Test
	public void testNumDecodings() {
		assertEquals(3, StringQuestions.numDecodings("226"));
		assertEquals(4, StringQuestions.numDecodings("2626"));
		assertEquals(5, StringQuestions.numDecodings("2126"));
		assertEquals(2, StringQuestions.numDecodings("12"));
		assertEquals(0, StringQuestions.numDecodings("06"));
		assertEquals(0, StringQuestions.numDecodings("60"));
		assertEquals(1, StringQuestions.numDecodings("1010"));
		assertEquals(2, StringQuestions.numDecodings("11106"));
	}

	@Test
	public void testVersionStrings() {
		assertEquals(0, StringQuestions.compareVersion("1.001", "1.01"));
		assertEquals(0, StringQuestions.compareVersion("1.0", "1.0.0"));
		assertEquals(-1, StringQuestions.compareVersion(".1", "1.1"));
	}

	@Test
	public void testLongestSubstring() {
		assertEquals(3, StringQuestions.longestSubstring("aaabb", 3));
		assertEquals(5, StringQuestions.longestSubstring("ababbc", 2));
		assertEquals(7, StringQuestions.longestSubstring("acbabbc", 2));
		assertEquals(0, StringQuestions.longestSubstring("abcdefgh", 2));
		assertEquals(0, StringQuestions.longestSubstring("ababacb", 3));
		assertEquals(1, StringQuestions.longestSubstring("a", 1));
		assertEquals(2, StringQuestions.longestSubstring("aa", 2));
		assertEquals(3, StringQuestions.longestSubstring("bbaaacbd", 3));
	}

	// brute force n^k solution: k nested loops trying to each remove a number and saving the min
	@Test
	public void testRemoveKDigits() {
		// want the smallest numbers at the front of resulting string
		//  - so large numbers near end can stay there
		//  - what if k is always 1, can we do it recursively?
		assertEquals("1219", StringQuestions.removeKdigits("1432219", 3));
		assertEquals("4193", StringQuestions.removeKdigits("8467193", 3)); // 1st remove 8, then 7, then 6.
		// choose 7 over 9 because 9 isn't the next digit to the right, there is a smaller digit between them on right side of 7
		// digit to remove can be chosen as the first digit that occurs with a smaller digit on it's right
		assertEquals("123", StringQuestions.removeKdigits("123456", 3));
		assertEquals("321", StringQuestions.removeKdigits("654321", 3));
		assertEquals("11", StringQuestions.removeKdigits("112", 1));
		// ^ remove largest k digits
		assertEquals("200", StringQuestions.removeKdigits("10200", 1));
		assertEquals("99", StringQuestions.removeKdigits("11099", 2));
		assertEquals("9", StringQuestions.removeKdigits("10099", 2));
		assertEquals("1034", StringQuestions.removeKdigits("12034", 1));
		// ^ if there is a zero in first k+1 digits, it counts as a "* 10" so will always be the choice to remove, remove the digits before 1st 0
		assertEquals("0", StringQuestions.removeKdigits("10", 2));
		assertEquals("0", StringQuestions.removeKdigits("10", 1));
		assertEquals("0", StringQuestions.removeKdigits("0", 1));
		// ^ base case = "0"
	}

	@Test
	public void testFindTheDifference() {
		assertEquals('e', StringQuestions.findTheDifference("abcd", "abcde"));
		assertEquals('y', StringQuestions.findTheDifference("", "y"));
		assertEquals('b', StringQuestions.findTheDifference("zyxw", "wyxbz"));
	}

	@Test
	public void testPalindrome() {
		assertTrue(StringQuestions.isPalindrome("A man, a plan, a canal: Panama"));
		assertFalse(StringQuestions.isPalindrome("race a car"));
		assertTrue(StringQuestions.isPalindrome(" "));
	}

	@Test
	public void testAtoi() {
		assertEquals(42, StringQuestions.myAtoi("42"));
		assertEquals(42, StringQuestions.myAtoi("0000042"));
		assertEquals(0, StringQuestions.myAtoi("00000"));
		assertEquals(12345678, StringQuestions.myAtoi("  0000000000012345678"));
		assertEquals(-42, StringQuestions.myAtoi("    -42"));
		assertEquals(42, StringQuestions.myAtoi("    +42  "));
		assertEquals(4193, StringQuestions.myAtoi("4193 with words"));
		assertEquals(0, StringQuestions.myAtoi(" with words"));
		assertEquals(1, StringQuestions.myAtoi("1"));
		assertEquals(Integer.MAX_VALUE, StringQuestions.myAtoi("9999999999999999999999999999999999999999999"));
		assertEquals(Integer.MIN_VALUE, StringQuestions.myAtoi("-9999999999999999999999999999999999999999999"));
		assertEquals(Integer.MIN_VALUE, StringQuestions.myAtoi("-6147483648"));
		assertEquals(0, StringQuestions.myAtoi("words and 987"));
		assertEquals(0, StringQuestions.myAtoi(""));
		assertEquals(0, StringQuestions.myAtoi("00000-42a1234"));
		assertEquals(-1, StringQuestions.myAtoi("-000000000000001"));
	}

	@Test
	public void testWordBreak() {
		assertTrue(StringQuestions.wordBreak("leetcode", Arrays.asList("leet", "code")));
		assertTrue(StringQuestions.wordBreak("applepenapple", Arrays.asList("apple", "pen")));
		assertFalse(StringQuestions.wordBreak("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")));

		assertTrue(StringQuestions.wordBreakPartitioning("leetcode", Arrays.asList("leet", "code")));
		assertTrue(StringQuestions.wordBreakPartitioning("applepenapple", Arrays.asList("apple", "pen")));
		assertFalse(StringQuestions.wordBreakPartitioning("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat")));
	}

	@Test
	public void testLengthOfLongestSubstring() {
		assertEquals(StringQuestions.lengthOfLongestSubstring("abcabcbb"), 3);
		assertEquals(StringQuestions.lengthOfLongestSubstring("pwwkew"), 3);
		assertEquals(StringQuestions.lengthOfLongestSubstring("bbbbb"), 1);
		assertEquals(StringQuestions.lengthOfLongestSubstring(""), 0);
		assertEquals(StringQuestions.lengthOfLongestSubstring("au"), 2);
		assertEquals(StringQuestions.lengthOfLongestSubstring("dvdf"), 3);
	}

	@Test
	public void testindexOf() {
		assertEquals(StringQuestions.indexOf("THISIS The Haystack", "he"), 8);
		assertEquals(StringQuestions.indexOf("aaa", "aaaa"), -1);
		assertEquals(StringQuestions.indexOf("mississippi", "issipi"), -1);
	}
	
	@Test
	public void testLongestPrefix() {
		assertEquals(StringQuestions.longestCommonPrefix(new String[] {"there", "the", "them"}), "the");
		assertEquals(StringQuestions.longestCommonPrefix(new String[] {"the", "there", "them"}), "the");
	}

	@Test
	public void testLongestPalindrome() {
		assertEquals(7, StringQuestions.longestPalindrome("abccccdd"));
		assertEquals(12, StringQuestions.longestPalindrome("aabbccddeeff"));
		assertEquals(1, StringQuestions.longestPalindrome("a"));
		String in = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
		assertEquals(983, StringQuestions.longestPalindrome(in));
	}
}
