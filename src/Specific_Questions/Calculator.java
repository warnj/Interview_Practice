package Specific_Questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
	public static void main(String[] args) {
		System.out.println(calc("1  + 2"));
		System.out.println(calc("   1  - 2 "));
		System.out.println(calc("1  * 2"));
		System.out.println(calc("1  / 2"));
	}

	// https://leetcode.com/problems/basic-calculator/
	public static int calculate(String s) {
		int result = 0;
		int sign = 1;
		int num = 0;
		Stack<Integer> stack = new Stack<>();
		stack.push(sign);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= '0' && c <= '9') {
				num = num * 10 + (c - '0');
			} else if (c == '+' || c == '-') {
				result += sign * num;
				sign = stack.peek() * (c == '+' ? 1 : -1);
				num = 0;
			} else if (c == '(') {
				stack.push(sign);
			} else if (c == ')') {
				stack.pop();
			}
		}
		return result + sign * num;
	}

	// https://leetcode.com/problems/basic-calculator-ii/
	// non-negative input, order of operations expected to be followed, intermediate values fit in 32bit int
	public static int calculate2(String s) {
		Pattern numP = Pattern.compile("[0-9]+"); // match numbers
		Matcher nums = numP.matcher(s);
		List<Integer> numbers = new ArrayList<>(); // make a list of numbers
		while (nums.find()) {
			numbers.add(Integer.parseInt(nums.group()));
		}
		int prev = numbers.get(0); // save previous result
		int result = 0;
		int i = 1; // index in numbers list
		Pattern opP = Pattern.compile("[+\\-/*]"); // match operators
		Matcher ops = opP.matcher(s);
		while (ops.find()) {
			char op = ops.group().charAt(0);
			if (op == '+' || op == '-') {
				result += prev; // can safely update total prior to this point, then look ahead
				prev = op == '+' ? numbers.get(i) : -numbers.get(i);
			} else {
				prev = doOperation(op, prev, numbers.get(i));
			}
			i++;
		}
		return result + prev;
	}
	// no order of operations
	public static int calculateSimple(String s) {
		Pattern p = Pattern.compile("[0-9]+|[+\\-/*]");
		Matcher m = p.matcher(s);
		int result = -1;
		char op = 0;
		while (m.find()) {
			String numOrOp = m.group();
			if (Character.isDigit(numOrOp.charAt(0))) {
				int cur = Integer.parseInt(numOrOp);
				if (result == -1) {
					result = cur; // first number, don't perform an operation
				} else {
					result = doOperation(op, result, cur);
				}
			} else {
				op = numOrOp.charAt(0); // match is an operator
			}
		}
		return result;
	}
	private static int doOperation(char op, int a, int b) {
		if (op == '*') return a*b;
		if (op == '/') return a/b;
		if (op == '-') return a-b;
		if (op == '+') return a+b;
		throw new IllegalArgumentException();
	}

	// how to deal with negatives?
	public static float calc(String s) {
		String[] elements = s.split("[+\\-/*]"); // need to escape the - in regex and the \ in the java string
		if (elements.length != 2) throw new IllegalArgumentException();
		float a = Float.parseFloat(elements[0].trim());
		float b = Float.parseFloat(elements[1].trim());
		char opChar = getOp(s);
		if (opChar == '*') return a * b;
		if (opChar == '/') return a / b; // check if b == 0
		if (opChar == '+') return a + b;
		if (opChar == '-') return a - b;
		throw new IllegalArgumentException();
	}
	private static char getOp(String s) {
		if (s.contains("*")) return '*';
		if (s.contains("+")) return '+';
		if (s.contains("-")) return '-';
		if (s.contains("/")) return '/';
		return ' '; // never happen
	}
}
