package General_Questions;
import java.util.*;

public class CCI_Random_Practice {
	
	public static void main(String[] args) {
		System.out.println(matchedParenthesis("[({}())([])]"));
	}
	
	
	
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
			if (--letters[c] < c) return false;
		}
		return true;
	}
	
	public static String reverseWordsInPlace(String s){
		// to truely do in place, should reverse the entire string, then go through and reverse each word
		StringBuilder sb = new StringBuilder();
        for(int i = s.length() - 1; i >= 0;){
            if(s.charAt(i) != ' '){
                int j = i - 1;
                //locate the starting index of the word
                while(j >= 0 && s.charAt(j) != ' ')
                    j--;
                sb.append(s.substring(j + 1, i + 1));
                sb.append(" ");
                i = j - 1;
            } else i--;
        }
        if(sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
	}
	
	public static String reverseWords(String s){
		String[] words = s.split("\\s+"); // split over whitespace
		StringBuilder sb = new StringBuilder();
		for(int i = words.length - 1; i >= 0; i--) {
			sb.append(words[i] + " ");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}
	
	// Reverse words in a string (words are separated by one or more spaces). Now do it in-place.
	public static String reverseWords2(String s){
		Scanner scan = new Scanner(s);
		Stack<String> stack = new Stack<String>();
		while(scan.hasNext()){
			stack.push(scan.next());
		}
		StringBuilder sb = new StringBuilder();
		while(!stack.isEmpty()){
			sb.append(stack.pop() + " ");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}
	
	// replace all spaces in a string with'%20'.
	public static String add20 (String s){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i< s.length(); i++){
			if(s.charAt(i) == ' '){
				sb.append("%20");
			} else{
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}	
	
}