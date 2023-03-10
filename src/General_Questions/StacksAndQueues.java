package General_Questions;

import java.util.*;

// todo: these should implement corresponding interfaces to simplify testing
public class StacksAndQueues {

	// https://leetcode.com/problems/implement-queue-using-stacks
	// amortized O(1) for all operations
	public static class QueueWithStacks {
		private Stack<Integer> s1;
		private Stack<Integer> s2;
		private int front; // stores the bottom of s1, the "front" of the stack we add to
		public QueueWithStacks() {
			s1 = new Stack<>();
			s2 = new Stack<>();
		}
		public void push(int x) {
			if (s1.empty()) front = x;
			s1.push(x); // add to s1, objects at bottom are the ones to remove first
		}
		public int pop() {
			if (s2.isEmpty())
				while (!s1.isEmpty()) // move everything from s1 to s2 - only do when s2 is empty so ordering is valid
					s2.push(s1.pop());
			return s2.pop(); // remove from s2, which stores items in reversed order from s1
		}
		public int peek() {
			if (!s2.isEmpty()) return s2.peek();
			return front;
		}
		public boolean empty() {
			return s1.isEmpty() && s2.isEmpty();
		}
	}

	/*
	push(i):    Add i to the top of the stack
    pop():      Remove and return the value on the top of the stack, exception if stack empty
    peek():     Return, but do not remove, the value on the top of the stack, exception if stack empty
    is_empty(): Return a boolean that is true if there is nothing on the stack, otherwise false
    getMax():   returns (does not remove) the max value in the stack at the time O(1)
	 */
	public static class MaxStack {
		private Stack<Integer> stack;
		private Stack<Integer> maxes; // holds the sorted unique contents of stack biggest to smallest
		private Map<Integer,Integer> counts; // map int -> count of int in stack

		public MaxStack() {
			stack = new Stack<>(); // contents of this MaxStack
			maxes = new Stack<>(); // top of max always holds the current max
			counts = new HashMap<>(); // space optimization so maxes.size can be < stack.size
		}

		// @Override
		public int push(int n) {
			counts.put(n, 1 + counts.getOrDefault(n, 0));
			if (maxes.isEmpty()) {
				maxes.push(n);
			} else {
				int max = maxes.peek();
				if (n > max) {
					maxes.push(n);
				}
			}
			stack.push(n);
			return n;
		}

		public int pop() {
			if (stack.isEmpty()) throw new EmptyStackException();
			int remove = stack.peek();
			int count = counts.get(remove);
			if (count == 1) {
				if (remove == maxes.peek()) maxes.pop(); // if all of max val removed, pop from maxes & since we only
				// add larger vals to maxes it is sorted and 2nd largest val is now on the top
				counts.remove(remove);
			} else {
				counts.put(remove, count-1);
			}
			return stack.pop();
		}

		public int getMax() {
			return maxes.peek();
		}

		public boolean isEmpty() {
			return stack.isEmpty();
		}

		public String toString() {
			return "Stack: " + stack + "  Maxes: " + maxes;
		}
	}

	// https://leetcode.com/problems/min-stack
	// store the min val in the stack with each node since this is known at insertion time
	public static class MinIntStack {
		public MinIntStack() {}
		public void push(int val) {
			if (front == null) {
				front = new Node(val, val, null);
			} else {
				front = new Node(val, Math.min(front.min, val), front);
			}
		}
		public void pop() {
			front = front.next;
		}
		public int top() {
			return front.data;
		}
		public int getMin() {
			return front.min;
		}
		private Node front;
		private static class Node {
			int data;
			int min;
			Node next;
			public Node(int data, int min, Node next) {
				this.data = data;
				this.min = min;
				this.next = next;
			}
		}
	}
	// Min stack above extended to work with any comparable class
	public static class MinStack<E extends Comparable<E>> { // O(1) push, pop, and getMin
		private Node<E> front;
		private int size;
		
		public MinStack() {}
		
		public void push(E data) {
			if(front == null) {
				front = new Node<E>(data, data, null);
			} else {
				front = new Node<E>(data, min(front.min, data), front);
			}
			size++;
		}
		
		public E pop() {
			if(front == null) throw new EmptyStackException();
			size--;
			E result = front.data;
			front = front.next;
			return result;
		}
		
		public E peek() {
			if(front == null) throw new EmptyStackException();
			return front.data;
		}
		
		public E getMin() {
			if(front == null) throw new EmptyStackException();
			return front.min;
		}
		
		public boolean isEmpty() {return size == 0;}
		public int size() {return size;}
		
		private E min(E one, E two) {
			if(one.compareTo(two) < 0)
				return one;
			else
				return two;
		}
		
		private static class Node<E extends Comparable<E>> {
			E data;
			E min;
			Node<E> next;
			
			public Node(E data, E min, Node<E> next) {
				this.data = data;
				this.min = min;
				this.next = next;
			}
		}
	}
	
	// Efficient for pop O(1), not for push O(n)
	public static class StackWithQueuePop<T> {
		private Queue<T> one;
		public StackWithQueuePop() {one = new LinkedList<T>();}
		public boolean isEmpty() {return one.isEmpty();}
		public void push(T item) {
			one.add(item);
			int size = one.size();
			for (int i = 0; i < size - 1; i++) {
				one.add(one.remove()); // remove from front and add the prev contents to the end
			}
		}
		public T pop() {return one.remove();}
	}
	// O(1) push and O(n) pop
	public static class StackWithQueuePush<T> {
		private Queue<T> one;
		public StackWithQueuePush() {one = new LinkedList<T>();}
		public boolean isEmpty() {return one.isEmpty();}
		public void push(T item) {one.add(item);}
		public T pop() {
			int size = one.size();
			for (int i = 0; i < size - 1; i++) {
				one.add(one.remove()); // remove from front until most recently added item at front while adding items back to the end
			}
			return one.remove();
		}
	}

	// Efficient for pop O(1), not for push O(n)
	public static class StackWithQueuesPop<T> {
		private Queue<T> one;
		private Queue<T> two;
		public StackWithQueuesPop() {
			one = new LinkedList<T>();
			two = new LinkedList<T>();
		}
		public boolean isEmpty() {return one.isEmpty();}
		public void push(T item) {
			two.add(item);
			while(!one.isEmpty()) {
				two.add(one.remove());
			}
			Queue<T> temp = one;
			one = two;
			two = temp;
		}
		public T pop() {return one.remove();}
	}
	
	// Efficient O(1) for push, not for pop O(n) - preferable to O(n) 
	// for push and O(1) for pop since # of pops will always be <= # of pushes
	public static class StackWithQueuesPush<T> {
		private Queue<T> one;
		private Queue<T> two;
		public StackWithQueuesPush() {
			one = new LinkedList<T>();
			two = new LinkedList<T>();
		}
		public boolean isEmpty() {return one.isEmpty();}
		
		public void push(T item) {one.add(item);}
		public T pop() {
			if(one.size() < 1) throw new EmptyStackException();
			
			while(one.size() > 1) {
				two.add(one.remove());
			}
			T returnVal = one.remove();
			Queue<T> temp = one;
			one = two;
			two = temp;
			return returnVal;
		}
	}

	// code to recursively sort a stack using only stack operations and a single stack
	// basically uses the call stack as a second temporary stack
	public static void sort(Stack<Integer> s) {
		if (!s.isEmpty()) {
			int x = s.pop();
			sort(s);
			insert(s, x);
		}
	}	
	private static void insert(Stack<Integer> s, int x) {
		if (!s.isEmpty()) {
			int y = s.peek();
			if (x < y) {
				s.pop();
				insert(s, x);
				s.push(y);
			} else {
				s.push(x);
			}
		} else {
			s.push(x);
		}
	}

	public static void main(String[] args) {
		StackWithQueuePush<Integer> s = new StackWithQueuePush<>();
		for(int i = 0; i < 10; i++) {
			s.push(i);
		}
		for(int i = 0; i < 10; i++) {
			System.out.println(s.pop());
		}
	}

}
