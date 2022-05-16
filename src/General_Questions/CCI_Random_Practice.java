package General_Questions;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CCI_Random_Practice {
	
	public static void main(String[] args) {
		System.out.println(matchedParenthesis("[({}())([])]"));
	}
	
	
	// https://leetcode.com/problems/valid-parentheses
	public static boolean matchedParenthesis(String s) {
		Stack<Character> chars = new Stack<Character>();
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '(' || c == '{' || c =='[') {
				chars.push(c);
			} else if (c == ')' || c == '}' || c == ']') {
				if(chars.isEmpty()) return false;
				if(!matchedParens(chars.pop(), c)) return false;
			}
		}
		return chars.size() == 0;
	}
	private static boolean matchedParens(char open, char close) {
		return (open == '(' && close == ')') || (open == '{' && close == '}') || (open == '[' && close == ']');  
	}
	
	// i.e. for "aboutabou" will return indexOf 't' = 4
	public static int firstUniqueChar(String s){
		// could also populate a HashMap<Character, Integer> instead of int[256]
		int[] freq = new int[256]; // all ascii characters
        for(int i = 0; i < s.length(); i++)
            freq[s.charAt(i)]++;
        for(int i = 0; i < s.length(); i++)
            if(freq[s.charAt(i)] == 1) return i;
		return -1;
	}
	
	// returns the number that is the reverse of the digits of n; ie 1234 returns 4321
	public static int reverse(int n){
		int result = 0;
		while(n > 0) {
			int digit = n % 10;
			n /= 10;
			result = result * 10 + digit;
		}
		return result;
	}
	
	public static boolean isAnagram(String a, String b) {
		Map<Character, Integer> m = new HashMap<Character, Integer>();
		for(int i = 0; i < a.length(); i++) {
			char c = Character.toLowerCase(a.charAt(i));
			Integer in = m.get(c);
			if(in == null)	m.put(c, 1);
			else m.put(c, in + 1);
		}		
		for(int i = 0; i < b.length(); i++) {
			char c = Character.toLowerCase(b.charAt(i));
			Integer in = m.get(c);
			if(in == null) return false;
			else m.put(c, in - 1);
		}		
		for(char c : m.keySet()) {
			if(m.get(c) != 0) return false;
		}
		return true;
	}

	public static boolean isAnagramBetterVersion(String s, String t) {
		if (s.length() != t.length()) return false;
		int[] letters = new int[256]; // Assumption - ASCII
		char[] s_array = s.toCharArray();
		for (char c : s_array) letters[c]++;
		for (int i = 0; i < t.length(); i++) {
			int c = (int) t.charAt(i);
			if (--letters[c] < 0) return false;
		}
		return true;
	}
}