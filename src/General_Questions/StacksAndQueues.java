package General_Questions;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class StacksAndQueues {
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
			for(int i = 0; i < size - 1; i++) {
				one.add(one.remove());
			}
		}
		
		public T pop() {return one.remove();}
	}
	
	// Efficient for push O(1), not for pop O(n)
	public static class StackWithQueuePush<T> {
		private Queue<T> one;
		
		public StackWithQueuePush() {one = new LinkedList<T>();}
		
		public boolean isEmpty() {return one.isEmpty();}
		
		public void push(T item) {one.add(item);}
		
		public T pop() {
			int size = one.size();
			for(int i = 0; i < size - 1; i++) {
				one.add(one.remove());
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
