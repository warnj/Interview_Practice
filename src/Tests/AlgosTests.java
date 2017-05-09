package Tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import General_Questions.*;

public class AlgosTests {

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
		//System.out.println(Algos.spaceWordsEfficient("salesforce", dict));
	}
	
	@Test
	public void testReverseInteger() {
		assertEquals(Algos.reverse(123), 321);
		assertEquals(Algos.reverse(-123), -321);
		assertEquals(Algos.reverse(1534236469), 0);
	}

}
