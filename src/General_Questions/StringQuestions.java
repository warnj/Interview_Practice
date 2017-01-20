package General_Questions;

import java.util.Scanner;
import java.util.Stack;

public class StringQuestions {

	public static void main(String[] args) {
		System.out.println(reverseWordsInPlace(" a   FIRST second third  fourth   fifth   "));

	}

	// TEST THIS METHOD FOR CORRECTNESS
	public static int longestPalindromicSubstring(String s, int start, int end) {
		if(end-start == 1) {
			if(s.charAt(start)==s.charAt(end)) return 2;
			else return 1;
		} else if(s.charAt(start)==s.charAt(end)) {
			return 2 + longestPalindromicSubstring(s, start+1, end-1);
		} else {
			return 1 + Math.max(longestPalindromicSubstring(s, start+1, end), longestPalindromicSubstring(s, start, end-1));
		}
	}
	
	public static String reverseWordsInPlace(String s) {// reverse the entire string, then go through and reverse each word
	    char[] word = s.toCharArray();
	    for(int i = 0; i < word.length /2; i++) {
	        char temp = word[i];
	        word[i] = word[word.length-1-i];
	        word[word.length-1-i] = temp;
	    }
	    for(int i=0; i < word.length; i++) {
	        while(i < word.length && word[i] == ' ') {
	            i++;
	        }
	        if(i==word.length) break;
	        int end = i;
	        while(end < word.length && word[end] != ' ') {
	            end++;
	        }
	        if(i < end) {
	            for(int j = i; j < i+((end-i)/2); j++) {
	                char temp = word[j];
	                word[j] = word[end-1-(j-i)];
	                word[end-1-(j-i)] = temp;
	            }
	            i = end;
	        }
	    }
	    return new String(word);
	}
	
	public static String reverseWordsBackToFront(String s){
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
	
	public static String reverseWordsRegex(String s){
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
	public static String reverseWordsStack(String s){
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

	// Outputs all permutations of the given string.
	// https://courses.cs.washington.edu/courses/cse143/15au/lectures/11-09/17-recursive-backtracking.pdf
	public static void permute(String s) {
		permute(s, "");
	}
	private static void permute(String s, String chosen) {
		if (s.length() == 0) {
			// base case: no choices left to be made
			System.out.println(chosen);
		} else {
			// recursive case: choose each possible next letter
			for (int i = 0; i < s.length(); i++) {
				// choose; cut out a letter from each index in s, add to chosen
				char c = s.charAt(i); // choose
				s = s.substring(0, i) + s.substring(i + 1);
				chosen += c;
				// explore
				permute(s, chosen);
				// un-choose; put s back the way it was, remove last letter of chosen
				s = s.substring(0, i) + c + s.substring(i);
				chosen = chosen.substring(0, chosen.length() - 1);
			}
		}
	}

	// shorter way of doing same thing
	public static void permutation(String str) {
		permutation("", str);
	}

	private static void permutation(String prefix, String str) {
		int n = str.length();
		if (n == 0) System.out.println(prefix);
		else {
			for (int i = 0; i < n; i++)
				permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
		}
	}


	/**
	 * Compares two version strings.
	 *
	 * Use this instead of String.compareTo() for a non-lexicographical
	 * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
	 *
	 * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
	 *
	 * @param str1 a string of ordinal numbers separated by decimal points.
	 * @param str2 a string of ordinal numbers separated by decimal points.
	 * @return The result is a negative integer if str1 is _numerically_ less than str2.
	 *         The result is a positive integer if str1 is _numerically_ greater than str2.
	 *         The result is zero if the strings are _numerically_ equal.
	 */
	public static int versionCompare(String str1, String str2) {
		String[] vals1 = str1.split("\\.");
		String[] vals2 = str2.split("\\.");
		int i = 0;
		// set index to first non-equal ordinal or length of shortest version string
		while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
			i++;
		}
		// compare first non-equal ordinal number
		if (i < vals1.length && i < vals2.length) {
			int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
			return Integer.signum(diff);
		}
		// the strings are equal or one string is a substring of the other
		// e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
		return Integer.signum(vals1.length - vals2.length);
	}

}
