package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import General_Questions.*;

public class IntLinkedListTests {

	@Test
	public void testSumBackwardsNumbers() {
		IntLinkedList a = new IntLinkedList();
		a.add(7);
		a.add(1);
		a.add(6);
		IntLinkedList b = new IntLinkedList();
		b.add(5);
		b.add(9);
		b.add(2);
		IntLinkedList c = IntLinkedList.sumBackwardsNumbers(a, b);
		assertEquals(c.toString(), "[2, 1, 9]");
		
		a = new IntLinkedList();
		a.add(9);
		a.add(8);
		b = new IntLinkedList();
		b.add(1);
		c = IntLinkedList.sumBackwardsNumbers(a, b);
		assertEquals(c.toString(), "[0, 9]");
	}

}
