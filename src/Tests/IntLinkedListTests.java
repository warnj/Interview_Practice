package Tests;

import General_Questions.IntLinkedList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IntLinkedListTests {

	@Test
	public void testIsPalindrome() {
		assertTrue(create(new int[]{7,1,1,7}).isPalindrome());
		assertTrue(create(new int[]{7,1,7}).isPalindrome());
		assertTrue(create(new int[]{7,7}).isPalindrome());
		assertTrue(create(new int[]{7}).isPalindrome());
		assertFalse(create(new int[]{7,1}).isPalindrome());
		assertFalse(create(new int[]{1,-7}).isPalindrome());
		assertFalse(create(new int[]{1,-7,0}).isPalindrome());
	}

	@Test
	public void testSumBackwardsNumbers() {
		IntLinkedList a = create(new int[]{7,1,6});
		IntLinkedList b = create(new int[]{5,9,2});
		IntLinkedList c = IntLinkedList.sumBackwardsNumbers(a, b);
		assertEquals(c.toString(), "[2, 1, 9]");
		
		a = create(new int[]{9,8});
		b = create(new int[]{1});
		c = IntLinkedList.sumBackwardsNumbers(a, b);
		assertEquals(c.toString(), "[0, 9]");
	}
	
	@Test
	public void testMergeSortedLists() {
		IntLinkedList a = new IntLinkedList();
		a.add(1); a.add(6);	a.add(6);
		IntLinkedList b = new IntLinkedList();
		b.add(3); b.add(8);	b.add(9);
		IntLinkedList c = IntLinkedList.mergeSortedLists(a,b);
		assertTrue(isSorted(c));
		assertEquals(6, c.size());
		
		a = new IntLinkedList();
		a.add(1); a.add(6);	a.add(6); a.add(7);	a.add(88);
		b = new IntLinkedList();
		b.add(3); b.add(8);	b.add(9);
		c = new IntLinkedList();
		c.add(-6); c.add(8); c.add(75);
		List<IntLinkedList> d = new ArrayList<>();
		d.add(a); d.add(b); d.add(c);
		IntLinkedList s = IntLinkedList.mergeSortedLists(d);
		assertTrue(isSorted(s));
		assertEquals(11, s.size());
		
		a = new IntLinkedList();
		a.add(1); a.add(6);	a.add(6); a.add(7);	a.add(88);
		b = new IntLinkedList();
		b.add(3); b.add(8);	b.add(9);
		c = new IntLinkedList();
		c.add(-6); c.add(8); c.add(75);
		d = new ArrayList<>();
		d.add(a); d.add(b); d.add(c);
		s = IntLinkedList.mergeSortedListsRecursive(d);
		assertTrue(isSorted(s));
		assertEquals(11, s.size());
	}
	private boolean isSorted(IntLinkedList l) {
		for (int i = 0; i < l.size() - 1; i++) {
	        if (l.get(i) > l.get(i+1)) {
	            return false;
	        }
	    }
	    return true;
	}

	private IntLinkedList create(int[] contents) {
		IntLinkedList result = new IntLinkedList();
		for (int n : contents) {
			result.add(n);
		}
		return result;
	}
}
