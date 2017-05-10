package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import General_Questions.*;

public class StringQuestionsTests {

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
	public void testStrStr() {
		assertEquals(StringQuestions.strStr("THISIS The Haystack", "he"), 8);
		assertEquals(StringQuestions.strStr("aaa", "aaaa"), -1);
		assertEquals(StringQuestions.strStr("mississippi", "issipi"), -1);
	}
	
	@Test
	public void testLongestPrefix() {
		assertEquals(StringQuestions.longestCommonPrefix(new String[] {"there", "the", "them"}), "the");
		assertEquals(StringQuestions.longestCommonPrefix(new String[] {"the", "there", "them"}), "the");
	}
	
}
