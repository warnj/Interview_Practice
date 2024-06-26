package Specific_Questions;

public class Fibonacci {
	
	//Iteration method O(n)
	public static int fibIteration(int n) {
		int x = 0, y = 1, z = 1;
		for (int i = 0; i < n; i++) {
			x = y;
			y = z;
			z = x + y;
		}
		return x;
	}

	//Recursive method O(2^n)
	public static int fibRecursion(int  n) {
		if ((n == 1) || (n == 0)) {
			return n;
		}
		return fibRecursion(n - 1) + fibRecursion(n - 2);
	}	
	
	//recursion with caching results O(n)
	private int[] fib;
	public int fibonacci(int i) {
		if(fib == null || fib.length < i) {
			fib = new int[i];
		}
		return fib(i);
	}	
	private int fib(int i) {		
	 	if (i == 0) return 0;
	 	if (i == 1) return 1;
	 	if (fib[i] != 0) return fib[i]; // Return cached result.
	 	fib[i] = fibonacci(i - 1) + fibonacci(i - 2); // Cache result
		return fib[i];
	}
}



