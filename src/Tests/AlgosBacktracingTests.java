package Tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import General_Questions.AlgosBacktracing;

public class AlgosBacktracingTests {

	@Test
	public void testSpaceWordsEfficient() {
		Set<String> dict = new HashSet<String>();
		dict.add("sales");
		dict.add("sale");
		dict.add("force");
		dict.add("for");
		dict.add("justin");
		dict.add("a");
		dict.add("how");
		System.out.println(AlgosBacktracing.spaceWordsEfficient("salesforce", dict));
	}

}
