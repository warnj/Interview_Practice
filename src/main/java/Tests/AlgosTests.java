package Tests;

import General_Questions.Algos;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlgosTests {


	@Test
	public void testReverseInteger() {
		assertEquals(Algos.reverse(123), 321);
		assertEquals(Algos.reverse(-123), -321);
		assertEquals(Algos.reverse(1534236469), 0);
	}

	@Test
	public void testCoinChange() {
		assertEquals(3, Algos.coinChange(new int[]{1,2,5}, 11));
		assertEquals(3, Algos.coinChange(new int[]{1, 4, 5}, 12));
		assertEquals(1, Algos.coinChange(new int[]{1}, 1));
		assertEquals(-1, Algos.coinChange(new int[]{2}, 1));
		assertEquals(-1, Algos.coinChange(new int[]{2}, 3));
	}

}
