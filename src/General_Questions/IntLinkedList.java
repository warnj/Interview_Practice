package General_Questions;
import java.util.*;

public class IntLinkedList {
	private ListNode front;
	// in reality probably best to add a size field, but this object is for bare-list practice
	
	public IntLinkedList() { }
	
	// local constructor to use to return custom new IntLinkedLists
	private IntLinkedList(ListNode front) {	this.front = front; }
	
	// Adds the given value to the end of the list.
	public void add(int value) {
		if(front == null) {
			front = new ListNode(value);
		} else {
			ListNode current = front;
			while(current.next != null) {
				current = current.next;
			}
			current.next = new ListNode(value);
		}
	}
	
	// Adds a value at a given index.
	// Pre: 0 <= index <= size, throws a NullPointerException if index > size.
	public void add(int index, int value) {
		if (index == 0) {
			front = new ListNode(value, front);
		} else {
			ListNode current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			current.next = new ListNode(value, current.next);
		}
	}
	
	// Sets the element at the specified index in the list to have the given value.
	// Precondition: 0 <= index < size
	// Throws a NullPointerException if index >= size.
	public void set(int index, int value) {
		ListNode current = goTo(index);
		current.data = value;
	}
	
	// Returns the index of the first occurrence of the given value in the list,
	// or -1 if the value is not found in the list.
	public int indexOf(int value) {
		int index = 0;
		ListNode current = front;
		while (current != null) {
			if (current.data == value) {
				return index;
			}
			index++;
			current = current.next;
		}
		return -1;
	}
	
	// Adds the value in sorted (non-decreasing) order.
	// Pre: list is sorted
	// Post: list is sorted
	public void addSorted(int value) {
		if(front == null || value < front.data) {
			front = new ListNode(value, front);
		} else {
			ListNode current = front;
			while(current.next != null && current.next.data < value) {
				current = current.next;
			}
			current.next = new ListNode(value, current.next);
		}
	}
	
	// Returns the value at the given index.
	// Pre: 0 <= index < size
	public int get(int index) {
		ListNode current = front;
		for(int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}
	
	// Returns a comma-separated String representation of this list.
	public String toString() {
		if (front == null) {
			return "[]";
		} else {
			String result = "[" + front.data;
			ListNode current = front.next;
			while (current != null) {
				result += ", " + current.data;
				current = current.next;
			}
			result += "]";
			return result;
		}
	}
	
	// Returns the size of this list (inefficient -- could use a size field).
	public int size() {
		ListNode current = front;
		int size = 0;
		while(current != null) {
			size++;
			current = current.next;
		}
		return size;
	}
	
	// True if the list is empty, false otherwise
	public boolean isEmpty() {
		return front == null;
	}
	
	// Removes the value at the given index.
	// Pre: 0 <= index < size
	public void remove(int index) {
		if (index == 0) {
			front = front.next;
		} else {
			ListNode current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			current.next = current.next.next;
		}
	}
	
	// Returns a reference to the node object representing the index'th element
	// in the list.  Used as a helper by many of the public methods.
	private ListNode goTo(int index) {
		ListNode current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}
	
	/*********************** My added Methods ***********************************************************************************/
	
	public void reverse() {
		ListNode prev = null;
		ListNode next = null;
		ListNode cur = front;
		while(cur != null) {
			next = cur.next;
			cur.next = prev;
			
			prev = cur;
			cur = next;
		}
		front = prev;
	}
	
	// requires l1 and l2 are sorted, returns a new sorted list with the contents of l1 and l2
	public static IntLinkedList mergeLists(IntLinkedList l1, IntLinkedList l2) {
		if(l1 == null) return l2;
		else if (l2 == null) return l1;
		
	    ListNode head = new ListNode(0);
	    ListNode p = head;
	 
	    ListNode p1 = l1.front;
	    ListNode p2 = l2.front;
	    while (p1 != null && p2 != null){
	        if (p1.data < p2.data) {
	            p.next = p1;
	            p1 = p1.next;
	        } else {
	            p.next = p2;
	            p2 = p2.next;
	        }
	        p = p.next;
	    }
	 
	    if(p1 != null) p.next = p1;
	    else p.next = p2; // (p2!=null) 
	 
	    return new IntLinkedList(head.next);
	}
	
	/**@requires all elements of lists are sorted
	 * @return a new sorted list with the contents of all of the given lists
	 * @modifies lists
	 */
	public static IntLinkedList mergeListsRecursive(List<IntLinkedList> lists) {
		return sort(lists, 0, lists.size()-1);
	}
	// basically does mergesort on lists. This method is the "sort" and mergeLists(l1, l2) is the "merge"
	private static IntLinkedList sort(List<IntLinkedList> lists, int low, int high) {
		if(high - low == 0) {
			return lists.get(low);
		} else if (high - low == 1) {
			return mergeLists(lists.get(low), lists.get(high));
		} else {
			int mid = (low + high) / 2;
			IntLinkedList one = sort(lists, low, mid);
			IntLinkedList two = sort(lists, mid+1, high);
			//System.out.println("one: " + one);
			//System.out.println("two: " + two);
			return mergeLists(one, two);
		}
	}
	
	/**@requires all elements of lists are sorted
	 * @return a new sorted list with the contents of all of the given lists
	 * @modifies lists
	 */
	public static IntLinkedList mergeLists(List<IntLinkedList> lists) {
		// pairs the given lists, merges them, and repeats until have 1 big list
		// probably more efficient to pair and merge similarly sized lists, but not a big issue
		while(lists.size() > 1) {
			IntLinkedList mergeTo = mergeLists(lists.get(0), lists.get(1));
			lists.remove(0);
			lists.set(0, mergeTo);
		}
		return lists.get(0);
	}
	
	// returns the kth to last element in the list. k=1 returns last element, k=2 returns 2nd to last. requires k <= size, throws NullPointerException if k > size
	public int kthToLast(int k) {
		ListNode first = front;
		for(int i = 0; i < k; i++) {
			first = first.next;
		}
		ListNode last = front;
		while(first != null) {
			first = first.next;
			last = last.next;
		}
		return last.data;
	}
	
	// returns null if there is no loop, otherwise returns the int at the point where the loop begins
	public Integer hasLoop() {
		ListNode fast = front;
		ListNode slow = front;
		while(fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if(slow == fast) break;
		}
		if(fast == null || fast.next == null) return null;
		// fast is k (the # of nodes before the start of the loop) ahead of slow when slow enters loop
		// slow and fast meet after LOOP_SIZE - k turns so both are k nodes from the front of the loop
		// to find loop start, set one ptr to front and they will collide at the first node of the loop
		slow = front;
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		return fast.data;
	}
	
	// O(n) space and time
	public void removeDuplicates() {
		if(front != null && front.next != null){
			Set<Integer> contents = new HashSet<Integer>();
			contents.add(front.data);
			ListNode cur = front;
			while(cur.next != null) {
				if(contents.contains(cur.next.data)) { // remove
					cur.next = cur.next.next;
				} else {
					contents.add(cur.next.data);
					cur = cur.next;
				}
			}
		}
	}
	
	// deletes the given node from the list. n cannot be the last node in the list
	private static boolean deleteNode(ListNode n) {
		if(n==null || n.next==null) {
			return false;
		} else { // copy the data from next node to given node and delete next node
			n.data = n.next.data;
			n.next = n.next.next;
			return true;
		}
	}
	
	// modifies this list so all values < val are before values >= to val
	public void partition(int val) {
		ListNode cur = front;
		ListNode less = null;
		ListNode greater = null;
		// make 2 lists - one < val the other >= val
		while(cur != null) {
			ListNode temp = cur.next;
			if(cur.data < val) {
				cur.next = less;
				less = cur;
			} else {
				cur.next = greater;
				greater = cur;
			}
			cur = temp;
		}
		// merge lists
		if (less == null) {
			less = greater;
		} else if (greater != null) {
			cur = less;
			while(cur.next != null) cur = cur.next;
			cur.next = greater;
		}
		front = less;
	}
	
	public boolean isPalindrome() {
		Stack<Integer> s = new Stack<Integer>();
		ListNode slow = front;
		ListNode fast = front;
		while(fast != null && fast.next != null) {
			s.add(slow.data);
			slow = slow.next;
			fast = fast.next.next;
		}
		if(fast != null) slow = slow.next; // odd length, then middle element won't equal the top of stack
		while(slow != null) {
			if(s.pop() != slow.data) {
				return false;
			}
			slow = slow.next;
		}
		return true;
	}
	
	// Input: (7-> 1 -> 6) + (5 -> 9 -> 2). That is, 617 + 295. Output: (2->1->9) or 912
	public static IntLinkedList sumBackwardsNumbers(IntLinkedList a, IntLinkedList b) {
		ListNode curA = a.front;
		ListNode curB = b.front;
		ListNode result = null;
		ListNode resultEnd = null;
		boolean carry = false;
		while(curA != null && curB != null) {
			int sum = curA.data + curB.data;
			assert(sum <= 18): "a and b must only contain single digits in each node";
			if(carry) sum++;
			carry = sum > 9;
			
			if(result == null) {
				result = new ListNode(sum % 10);
				resultEnd = result;
			} else {
				resultEnd.next = new ListNode(sum % 10);
				resultEnd = resultEnd.next;
			}
			
			curA = curA.next;
			curB = curB.next;
		}
		while(curA != null) {
			assert(curA.data <= 9): "a and b must only contain single digits in each node";
			resultEnd.next = new ListNode(curA.data);
			resultEnd = resultEnd.next;
			curA = curA.next;
		}
		while(curB != null) {
			assert(curB.data <= 9): "a and b must only contain single digits in each node";
			resultEnd.next = new ListNode(curB.data);
			resultEnd = resultEnd.next;
			curB = curB.next;
		}
		if(carry) {
			resultEnd.next = new ListNode(1);
		}
		// differing digit numbers
		return new IntLinkedList(result);
	}
	
	
	
	/*********************** Practice-It Methods **********************************************************************************************/
	
	// pre: the list is sorted by absolute value
	public void sort() {
		if(front != null) {
			ListNode cur = front;
			int n = front.data;
			while(cur.next != null) {
				if(cur.next.data < n) {
					//remove, put at front
					ListNode temp = front;
					front = cur.next;
					cur.next = front.next;
					front.next = temp;
				} else {
					cur = cur.next;
				}
			}
		}
	}
	
	// O(1) space, O(n^2) time
	public void removeDuplicatesConstantSpace() {
		if(front != null && front.next != null){
			ListNode cur = front;
			ListNode pre = front;
			while(pre !=null && pre.next !=null) {
				int n = pre.data;
				while(cur.next != null) {
					if(cur.next.data == n) {
						cur.next = cur.next.next;
					} else {
						cur = cur.next;
					}
				}
				cur = pre.next;
				pre = pre.next;
			}
		}
	}
	
	// removes n elements from the front and back of the list
	public void trimEnds(int k) {
		if(k>0){
			int size = 0;
			ListNode cur = front;
			while(cur !=null) {
				size++;
				cur=cur.next;
			}
			if(2*k > size) throw new IllegalArgumentException();
			else {
				// remove from front
				cur = front;
				for(int i=0; i <k; i++) {
					cur = cur.next;
				}
				front = cur;
				// set up 2 ptrs k elems apart, move until 1st hits the end, set remaining to null
				ListNode pre = front;
				for(int i = 0; i <k+1; i++) {
					cur = cur.next;
				}
				while(cur != null){
					cur = cur.next;
					pre=pre.next;
				}
				pre.next = null;
			}
		}
	}
	
	public void sortPairs() {
		ListNode cur = front;
		if(cur != null && cur.next != null) {
			if(cur.data > cur.next.data) {
				ListNode temp = cur;
				cur = cur.next;
				temp.next = cur.next;
				cur.next = temp;
				front = cur;
			}
			cur = cur.next;
			while(cur.next != null && cur.next.next != null) {
				if(cur.next.data > cur.next.next.data) {
					ListNode temp = cur.next;
					cur.next=temp.next;
					temp.next = cur.next.next;
					cur.next.next=temp;
				}
				cur = cur.next.next;
			}
		}
	}
	
	// removes even-numbered elements and places a node at the end with the number removed
	public void removeEvens() {
		if(front != null) {
			int count = 0;
			ListNode cur = front;
			while(cur != null && cur.data %2 == 0) {
				count++;
				cur = cur.next;
			}
			//either cur points to odd or at end of list
			if(cur == null) front = new ListNode(count);
			else {
				front = cur;
				while(cur.next != null) {
					if(cur.next.data % 2 ==0) {
						cur.next = cur.next.next;
						count++;
					} else
						cur = cur.next;
				}
				cur.next = new ListNode(count);
			}
		} else {
			front = new ListNode(0);
		}
	}
	
	// precondition: this list and other list are sorted
	// removes the values from this list that are present in the other list
	public void removeAll(IntLinkedList other) {
		if (front != null && other.front != null) {
			ListNode current2 = other.front;
			while (current2 != null && front != null && current2.data <= front.data) {
				if (current2.data < front.data) {
					current2 = current2.next;
				} else { // current2.data == front.data
					front = front.next;
				}
			}
			if (front != null) {
				ListNode current1 = front;
				while (current1.next != null && current2 != null) {
					if (current1.next.data < current2.data) {
						current1 = current1.next;
					} else if (current1.next.data == current2.data) {
						current1.next = current1.next.next;
					} else { // current1.next.data > current2.data
						current2 = current2.next;
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
//		ListNode end = new ListNode(21);
//		ListNode next = end;
//		for(int i = 20; i >= 0; i--) {
//			ListNode node = new ListNode(i, next);
//			next = node;
//		}
//		IntLinkedList l = new IntLinkedList(next);
//		end.next = l.goTo(5);
//		System.out.println(l.hasLoop());
		
		
//		List<IntLinkedList> lists = new ArrayList<IntLinkedList>();
//		for(int i = -10; i <= 10; i++) {
//			IntLinkedList l = new IntLinkedList();
//			l.add(i);
//			l.add(i+3);
//			lists.add(l);
//		}
//		IntLinkedList merged = IntLinkedList.mergeLists(lists);
//		System.out.println(merged);
//		System.out.println(merged.size());
		
		IntLinkedList a = new IntLinkedList();
		a.add(7);
		a.add(1);
		a.add(6);

		IntLinkedList b = new IntLinkedList();
		b.add(5);
		b.add(9);
		b.add(2);
		
		System.out.println(IntLinkedList.sumBackwardsNumbers(a, b));
		

		a = new IntLinkedList();
		a.add(9);

		b = new IntLinkedList();
		b.add(0);
		b.add(1);
		
		System.out.println(IntLinkedList.sumBackwardsNumbers(a, b));
	}
	
	
	
	
	private static class ListNode {
		public int data;       // data stored in this node
		public ListNode next;  // link to next node in the list
		
		public ListNode(int data) {
			this(data, null);
		}
		
		public ListNode(int data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
}