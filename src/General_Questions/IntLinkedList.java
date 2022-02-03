package General_Questions;

import General_Questions.IntTree.TreeNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class IntLinkedList {
	private ListNode head;
	// in reality probably best to add a size field, but this object is for bare-list practice

	public IntLinkedList() { }

	// local constructor to use to return custom new IntLinkedLists
	private IntLinkedList(ListNode head) {	this.head = head; }

	// Adds the given value to the end of the list.
	public void add(int value) {
		if (head == null) {
			head = new ListNode(value);
		} else {
			ListNode current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = new ListNode(value);
		}
	}

	// Adds a value at a given index.
	// Pre: 0 <= index <= size, throws a NullPointerException if index > size.
	public void add(int index, int value) {
		if (index == 0) {
			head = new ListNode(value, head);
		} else {
			ListNode current = head;
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
		current.val = value;
	}

	// Returns the index of the first occurrence of the given value in the list,
	// or -1 if the value is not found in the list.
	public int indexOf(int value) {
		int index = 0;
		ListNode current = head;
		while (current != null) {
			if (current.val == value) {
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
		if (head == null || value < head.val) {
			head = new ListNode(value, head);
		} else {
			ListNode current = head;
			while (current.next != null && current.next.val < value) {
				current = current.next;
			}
			current.next = new ListNode(value, current.next);
		}
	}

	// Returns the value at the given index.
	// Pre: 0 <= index < size
	public int get(int index) {
		ListNode current = head;
		for(int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.val;
	}

	// Returns a comma-separated String representation of this list.
	public String toString() {
		if (head == null) {
			return "[]";
		} else {
			String result = "[" + head.val;
			ListNode current = head.next;
			while (current != null) {
				result += ", " + current.val;
				current = current.next;
			}
			result += "]";
			return result;
		}
	}

	// Returns the size of this list (inefficient -- could use a size field).
	public int size() {
		ListNode current = head;
		int size = 0;
		while (current != null) {
			size++;
			current = current.next;
		}
		return size;
	}

	// True if the list is empty, false otherwise
	public boolean isEmpty() {
		return head == null;
	}

	// Removes the value at the given index.
	// Pre: 0 <= index < size
	public void remove(int index) {
		if (index == 0) {
			head = head.next;
		} else {
			ListNode current = head;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			current.next = current.next.next;
		}
	}

	// Returns a reference to the node object representing the index'th element
	// in the list.  Used as a helper by many of the public methods.
	private ListNode goTo(int index) {
		ListNode current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

	/*********************** My added Methods ***********************************************************************************/


	public void reverse() {
		ListNode prev = null;
		ListNode cur = head;
		while (cur != null) {
			ListNode next = cur.next;
			cur.next = prev;

			prev = cur;
			cur = next;
		}
		head = prev;
	}

	/**@requires all elements of lists are sorted
	 * @return a new sorted list with the contents of all of the given lists
	 * @modifies lists
	 */
	public static IntLinkedList mergeSortedListsRecursive(List<IntLinkedList> lists) {
		return mergeSortedListsRecursive(lists, 0, lists.size()-1);
	}
	// basically does mergesort on lists. This method is the "partition" and mergeLists(l1, l2) is the "merge"
	private static IntLinkedList mergeSortedListsRecursive(List<IntLinkedList> lists, int low, int high) {
		if (high == low) {
			return lists.get(low);
		} else if (low < high) {
			int mid = low + (high - low) / 2;
			IntLinkedList one = mergeSortedListsRecursive(lists, low, mid);
			IntLinkedList two = mergeSortedListsRecursive(lists, mid+1, high);
			//System.out.println("one: " + one);
			//System.out.println("two: " + two);
			return mergeSortedLists(one, two);
		} else {
			return null;
		}
	}

	/**@requires all elements of lists are sorted
	 * @return a new sorted list with the contents of all of the given lists
	 * @modifies lists */
	public static IntLinkedList mergeSortedLists(List<IntLinkedList> lists) {
		// pairs the given lists, merges them, and repeats until have 1 big list
		// probably more efficient to pair and merge similarly sized lists, but not a big issue
		while (lists.size() > 1) {
			IntLinkedList mergeTo = mergeSortedLists(lists.get(0), lists.get(1));
			lists.remove(0);
			lists.set(0, mergeTo);
		}
		return lists.get(0);
	}

	// requires l1 and l2 are sorted, returns a new sorted list with the contents of l1 and l2
	public static IntLinkedList mergeSortedLists(IntLinkedList l1, IntLinkedList l2) {
		if (l1 == null) return l2;
		if (l2 == null) return l1;

		ListNode head = new ListNode(0);
		ListNode p = head;

		ListNode p1 = l1.head;
		ListNode p2 = l2.head;
		while (p1 != null && p2 != null){
			if (p1.val < p2.val) {
				p.next = p1;
				p1 = p1.next;
			} else {
				p.next = p2;
				p2 = p2.next;
			}
			p = p.next;
		}

		if (p1 != null) p.next = p1;
		else p.next = p2; // (p2!=null) 

		return new IntLinkedList(head.next);
	}
	// recursive way to do the same as above
	public ListNode mergeSortedLists(ListNode l1, ListNode l2){
		if (l1 == null) return l2;
		if (l2 == null) return l1;
		if (l1.val < l2.val){
			l1.next = mergeSortedLists(l1.next, l2);
			return l1;
		} else{
			l2.next = mergeSortedLists(l1, l2.next);
			return l2;
		}
	}
	
	// https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/discuss/
	public IntTree sortedListToBST() {
        if (head == null) return null;
        return new IntTree(toBST(head, null));
    }
	private TreeNode toBST(ListNode start, ListNode end) {
		if (start == end) return null;
		ListNode fast = start;
        ListNode slow = start;
        while (fast != end && fast.next != end) {
        	fast = fast.next.next;
        	slow = slow.next;
        }
        TreeNode root = new TreeNode(slow.val);
        root.left = toBST(start, slow);
        root.right = toBST(slow.next, end);
        return root;
	}
	
	// rotate the list to the right by k places, where k is non-negative.
	// Given 1->2->3->4->5->NULL and k = 2, return 4->5->1->2->3->NULL
	// https://leetcode.com/problems/rotate-list/description/
	public ListNode rotateRight(int k) {
		if (head == null) return null;
		ListNode copyHead = head;
		int len = 1;
		while (copyHead.next != null) {
			copyHead = copyHead.next;
			len++;
		}
		copyHead.next = head; // connect end to head
		for (int i = len - k % len; i > 1; i--)
			head = head.next;
		copyHead = head.next; // set the new head
		head.next = null; // set the end of new list
		return copyHead;
	}

	// returns the kth to last element in the list. k=1 returns last element, k=2 returns 2nd to last. requires k <= size, throws NullPointerException if k > size
	public int kthToLast(int k) {
		ListNode first = head;
		for(int i = 0; i < k; i++) {
			first = first.next;
		}
		ListNode last = head;
		while (first != null) {
			first = first.next;
			last = last.next;
		}
		return last.val;
	}

	public ListNode removeNthFromEnd(int n) {
		ListNode start = new ListNode(0);
		start.next = head;
		ListNode first = start;
		for(int i = 0; i <= n; i++) { // move n+1 times forward
			first = first.next;
		}
		ListNode last = start;
		while (first != null) {
			first = first.next;
			last = last.next;
		}
		last.next = last.next.next;
		return start.next; // the placeholder for the head of the list
	}

	// returns null if there is no loop, otherwise returns the int at the point where the loop begins
	public Integer hasLoop() {
		ListNode fast = head;
		ListNode slow = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) break;
		}
		if (fast == null || fast.next == null) return null;
		// fast is k (the # of nodes before the start of the loop) ahead of slow when slow enters loop
		// slow and fast meet after LOOP_SIZE - k turns so both are k nodes from the head of the loop
		// to find loop start, set one ptr to head and they will collide at the first node of the loop
		slow = head;
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		return fast.val;
	}

	// O(n) space and time
	public void removeDuplicates() {
		if (head != null && head.next != null){ // size of list >= 2
			Set<Integer> contents = new HashSet<>();
			contents.add(head.val);
			ListNode cur = head;
			while (cur.next != null) {
				if (contents.contains(cur.next.val)) { // remove
					cur.next = cur.next.next; // delete
				} else {
					contents.add(cur.next.val); // remember
					cur = cur.next;
				}
			}
		}
	}
	
	// Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
	// https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/description/
	// Given 1->1->1->2->3, return 2->3
	public void deleteDuplicates() {
		if (head != null && head.next != null){ // size of list >= 2
			
			while (head != null && head.next != null && head.val == head.next.val) {
				while (head.next != null && head.val == head.next.val) {
					head = head.next;
				}
				head = head.next; // start at the next different number
			}
			
			ListNode cur = head;
			while (cur != null && cur.next != null) {
				ListNode fast = cur.next;
				while (fast != null && cur.val == fast.val) {
					fast = fast.next;
				}
				if (fast == null) {
					cur = null; // problem here
				} else {
                    cur.next = fast;
                    cur = cur.next;
                }
			}
			
		}
    }
	
	// https://leetcode.com/problems/partition-list/description/
	// partition list such that all nodes less than x come before nodes greater than or equal to x.
	public void partitionKeepOrder(int val) {
		ListNode cur = head;
		ListNode less = null, lessEnd = null, greater = null, greaterEnd = null;
		// make 2 lists - one < val the other >= val
		while (cur != null) {
			if (cur.val < val) {
				if (lessEnd == null) { // setup head of less list
					lessEnd = cur;
					less = lessEnd;
				} else { // append to less list
					lessEnd.next = cur;
					lessEnd = lessEnd.next;
				}
			} else {
				if (greaterEnd == null) { // setup head of greater list
					greaterEnd = cur;
					greater = greaterEnd;
				} else { // append to greater list
					greaterEnd.next = cur;
					greaterEnd = greaterEnd.next;
				}
			}
			cur = cur.next;
		}
		if (greaterEnd != null) greaterEnd.next = null;
		// merge lists
		if (less == null) {
			head = greater;
		} else {
			lessEnd.next = greater;
			head = less;
		}
	}

	// modifies this list so all values < val are before values >= to val, does not retain original relative order
	public void partition(int val) {
		ListNode cur = head;
		ListNode less = null; ListNode lessEnd = null;
		ListNode greater = null; ListNode greaterEnd = null;
		// make 2 lists - one < val the other >= val
		while (cur != null) {
			ListNode temp = cur.next;
			if (cur.val < val) {
				cur.next = less; // adding to head of less list
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
			while (cur.next != null) cur = cur.next;
			cur.next = greater;
		}
		head = less;
	}

	// O(n) time, O(n/2) space
	// can do in constant space if able to reverse the last half and then compare
	public boolean isPalindrome() {
		Stack<Integer> s = new Stack<>();
		ListNode slow = head;
		ListNode fast = head;
		while (fast != null && fast.next != null) {
			s.add(slow.val);
			slow = slow.next;
			fast = fast.next.next;
		}
		if (fast != null) slow = slow.next; // odd length, then middle element won't equal the top of stack
		while (slow != null) { // slow will traverse last half of list
			if (s.pop() != slow.val) {
				return false;
			}
			slow = slow.next;
		}
		return true;
	}

	// Input: (7-> 1 -> 6) + (5 -> 9 -> 2). That is, 617 + 295. Output: (2->1->9) or 912
	public static IntLinkedList sumBackwardsNumbers(IntLinkedList a, IntLinkedList b) {
		ListNode curA = a.head;
		ListNode curB = b.head;
		ListNode result = null;
		ListNode resultEnd = null;
		boolean carry = false;
		while (curA != null && curB != null) {
			int sum = curA.val + curB.val;
			assert(sum <= 18): "a and b must only contain single digits in each node";
			if (carry) sum++;
			carry = sum > 9;

			if (result == null) {
				result = new ListNode(sum % 10);
				resultEnd = result;
			} else {
				resultEnd.next = new ListNode(sum % 10);
				resultEnd = resultEnd.next;
			}

			curA = curA.next;
			curB = curB.next;
		}
		while (curA != null) {
			assert(curA.val <= 9): "a and b must only contain single digits in each node";
			int toAdd = curA.val;
			if (carry) toAdd++;
			carry = toAdd > 9;
			resultEnd.next = new ListNode(toAdd % 10);
			resultEnd = resultEnd.next;
			curA = curA.next;
		}
		while (curB != null) {
			assert(curB.val <= 9): "a and b must only contain single digits in each node";
			int toAdd = curB.val;
			if (carry) toAdd++;
			carry = toAdd > 9;
			resultEnd.next = new ListNode(toAdd % 10);
			resultEnd = resultEnd.next;
			curB = curB.next;
		}
		if (carry) {
			resultEnd.next = new ListNode(1);
		}
		// differing digit numbers
		return new IntLinkedList(result);
	}

	public static ListNode getIntersectionNode(IntLinkedList a, IntLinkedList b) {
		// O(n+m) time and O(1) space; align the lists since any intersection point must be in both lists
		int aSize = a.size();
		int bSize = b.size();
		ListNode aCur = a.head;
		ListNode bCur = b.head;
		while (aSize > bSize) {
			aCur = aCur.next;
			aSize--;
		}
		while (bSize > aSize) {
			bCur = bCur.next;
			bSize--;
		}
		while (aCur != null && bCur != null) {
			if (aCur == bCur) return aCur;
			aCur = aCur.next;
			bCur = bCur.next;
		}
		return null;
	}
	public static ListNode getIntersectionNodeSave(IntLinkedList a, IntLinkedList b) {
		// easy O(n+m) time and O(n) space with pass through each list and hashmap saving pointers from 1st list
		HashSet<ListNode> aSet = new HashSet<>();
		for (ListNode aCur = a.head; aCur != null; aCur = aCur.next) {
			aSet.add(aCur);
		}
		for (ListNode bCur = b.head; bCur != null; bCur = bCur.next) {
			if (aSet.contains(bCur)) return bCur;
		}
		return null;
	}
	public static ListNode getIntersectionNodeSlow(IntLinkedList a, IntLinkedList b) {
		// easy O(n*m) time and O(1) space with double loop
		ListNode aCur = a.head;
		while (aCur != null) {
			ListNode bCur = b.head;
			while (bCur != null) {
				if (aCur == bCur) return aCur;
				bCur = bCur.next;
			}
			aCur = aCur.next;
		}
		return null;
	}


	/*********************** Practice-It Methods **********************************************************************************************/

	// pre: the list is sorted by absolute value
	public void sort() {
		if (head != null) {
			ListNode cur = head;
			int n = head.val;
			while (cur.next != null) {
				if (cur.next.val < n) {
					//remove, put at head
					ListNode temp = head;
					head = cur.next;
					cur.next = head.next;
					head.next = temp;
				} else {
					cur = cur.next;
				}
			}
		}
	}

	// O(1) space, O(n^2) time
	public void removeDuplicatesConstantSpace() {
		if (head != null && head.next != null){
			ListNode cur = head;
			ListNode pre = head;
			while (pre !=null && pre.next !=null) {
				int n = pre.val;
				while (cur.next != null) {
					if (cur.next.val == n) {
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

	// removes n elements from the head and back of the list
	public void trimEnds(int k) {
		if (k>0){
			int size = 0;
			ListNode cur = head;
			while (cur !=null) {
				size++;
				cur=cur.next;
			}
			if (2*k > size) throw new IllegalArgumentException();
			else {
				// remove from head
				cur = head;
				for(int i=0; i <k; i++) {
					cur = cur.next;
				}
				head = cur;
				// set up 2 ptrs k elems apart, move until 1st hits the end, set remaining to null
				ListNode pre = head;
				for(int i = 0; i <k+1; i++) {
					cur = cur.next;
				}
				while (cur != null){
					cur = cur.next;
					pre=pre.next;
				}
				pre.next = null;
			}
		}
	}

	public void sortPairs() {
		ListNode cur = head;
		if (cur != null && cur.next != null) {
			if (cur.val > cur.next.val) {
				ListNode temp = cur;
				cur = cur.next;
				temp.next = cur.next;
				cur.next = temp;
				head = cur;
			}
			cur = cur.next;
			while (cur.next != null && cur.next.next != null) {
				if (cur.next.val > cur.next.next.val) {
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
		if (head != null) {
			int count = 0;
			ListNode cur = head;
			while (cur != null && cur.val %2 == 0) {
				count++;
				cur = cur.next;
			}
			//either cur points to odd or at end of list
			if (cur == null) head = new ListNode(count);
			else {
				head = cur;
				while (cur.next != null) {
					if (cur.next.val % 2 ==0) {
						cur.next = cur.next.next;
						count++;
					} else
						cur = cur.next;
				}
				cur.next = new ListNode(count);
			}
		} else {
			head = new ListNode(0);
		}
	}

	// precondition: this list and other list are sorted
	// removes the values from this list that are present in the other list
	public void removeAll(IntLinkedList other) {
		if (head != null && other.head != null) {
			ListNode current2 = other.head;
			while (current2 != null && head != null && current2.val <= head.val) {
				if (current2.val < head.val) {
					current2 = current2.next;
				} else { // current2.val == head.val
					head = head.next;
				}
			}
			if (head != null) {
				ListNode current1 = head;
				while (current1.next != null && current2 != null) {
					if (current1.next.val < current2.val) {
						current1 = current1.next;
					} else if (current1.next.val == current2.val) {
						current1.next = current1.next.next;
					} else { // current1.next.val > current2.val
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
		public int val;       // val stored in this node
		public ListNode next;  // link to next node in the list

		public ListNode(int val) {
			this(val, null);
		}

		public ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}
}