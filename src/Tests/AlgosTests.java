package Tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import General_Questions.*;

public class AlgosTests {


	@Test
	public void testReverseInteger() {
		assertEquals(Algos.reverse(123), 321);
		assertEquals(Algos.reverse(-123), -321);
		assertEquals(Algos.reverse(1534236469), 0);
	}

	@Test
	public void testLetterCombinations() {
		assertEquals(Algos.letterCombinations("23").toString(), "[ad, ae, af, bd, be, bf, cd, ce, cf]");
	}

	@Test
	public void testCoinChange() {
		assertEquals(Algos.coinChange(new int[]{1, 4, 5}, 12), 3);
		assertEquals(Algos.coinChange(new int[]{1}, 1), 1);
		assertEquals(Algos.coinChange(new int[]{2}, 1), -1);
		assertEquals(Algos.coinChange(new int[]{2}, 3), -1);
	}

}
