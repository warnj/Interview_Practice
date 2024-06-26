package Specific_Questions;

import General_Questions.Trie;
import java.io.IOException;
import java.util.*;

public class Autocomplete {

	// for trie solution
	private static Trie trie = new Trie();
	private static Map<String,Integer> wordIndex = new HashMap<>(); // map word to line number

	// for brute force solution, preserves insertion order so lower-numbered lines come first
	private static List<Entry> allWords = new ArrayList<>();

	// saves a word entry and line number pair, allows comparing by line numbers
	static class Entry implements Comparable<Entry> {
		String val;
		int line;
		public Entry(String v, int l) {
			val = v;
			line = l;
		}
		@Override
		public int compareTo(Entry o) {
			return line - o.line;
		}
		@Override
		public String toString() {
			return val + " (" + line + ")";
		}
	}

	// returns a sorted list of entries that start with the given prefix using the trie
	public static List<Entry> getTrieCompletion(String prefix) {
		List<Entry> result = new ArrayList<>();
		List<String> words = trie.getAllStartsWith(prefix);
		// get first 5 by smallest line numbers, there is a better way to do this for huge word dict than sorting
		for (String word : words) {
			result.add(new Entry(word, wordIndex.get(word)));
		}
		Collections.sort(result);
		return result;
	}

	// returns a list of up to 5 entries in allWords that start with given prefix
	public static List<Entry> getBruteCompletion(String prefix) {
		List<Entry> result = new ArrayList<>();
		for (Entry word : allWords) {
			if (word.val.startsWith(prefix)) {
				result.add(word);
				if (result.size() == 5) {
					return result;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		int n = Integer.parseInt(in.nextLine());
		int m = Integer.parseInt(in.nextLine());
		// create dictionary
		for (int i = 1; i <= n; i++) {
			String word = in.nextLine();
//			allWords.add(new Entry(word, i)); // brute force solution
			trie.insert(word);
			wordIndex.put(word, i);
		}
		// run autocomplete
		for (int i = 0; i < m; i++) {
			String prefix = in.nextLine();
			System.out.println(prefix + ":");

			// trie solution
			List<Entry> results = getTrieCompletion(prefix);
			for (int j = 0; j < Math.min(5, results.size()); j++) {
				System.out.println(results.get(j));
			}

			// brute force solution
//			List<Entry> results = getBruteCompletion(prefix);
//			for (Entry e : results) {
//				System.out.println(e);
//			}

			System.out.println();
		}
	}
}
