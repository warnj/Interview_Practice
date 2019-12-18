package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import General_Questions.*;

public class StringQuestionsTests {

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
