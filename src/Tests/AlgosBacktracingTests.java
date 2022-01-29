package Tests;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import General_Questions.AlgosBacktracing;

public class AlgosBacktracingTests {

	@Test
	public void testPermutations() {
//		System.out.println(AlgosBacktracing.permutations("abcde"));
		System.out.println(AlgosBacktracing.permutations("14372"));
		List<String> c = AlgosBacktracing.permutations("14372");
		Collections.sort(c);
		System.out.println(c);
	}

	@Test
	public void testSpaceWordsEfficient() {
		Set<String> dict = new HashSet<String>();
		dict.add("sales");
		dict.add("sale");
		dict.add("force");
		dict.add("for");
		dict.add("a");
		dict.add("how");
		assertEquals("sales force", AlgosBacktracing.spaceWordsEfficient("salesforce", dict));
	}

	@Test
	public void testSubsets() {
		System.out.println(AlgosBacktracing.permuteIterative(new int[] {1,2,3}));
	}
}
