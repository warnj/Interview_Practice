package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import General_Questions.*;

public class StringQuestionsTests {

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
	
}
