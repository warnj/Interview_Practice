package Specific_Questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WordDict {

    public static void main(String[] args) {
        WordDict d = new WordDict();
        d.addWord("bad");
        d.addWord("dad");
        d.addWord("mad");
        assertFalse(d.search("pad"));
        assertTrue(d.search("bad"));
        assertTrue(d.search(".ad"));
        assertTrue(d.search("b.."));
    }

    private Set<String> dict;

    public WordDict() {
        dict = new HashSet<>();
    }

    public void addWord(String word) {
        dict.add(word);
    }

    public boolean search(String word) {
        if (word.contains(".")) {
            List<String> words = new ArrayList<>();
            getCombinations(words, new StringBuilder(), word, 0);
            for (String w : words) {
                if (dict.contains(w)) {
                    return true;
                }
            }
            return false;
        } else {
            return dict.contains(word);
        }
    }

    private void getCombinations(List<String> words, StringBuilder pre, String word, int i) {
        if (i == word.length()) {
            words.add(pre.toString());
        } else {
            if (word.charAt(i) == '.') {
                for (int j = 0; j < 26; j++) {
                    StringBuilder newSb = new StringBuilder(pre);
                    newSb.append((char) ('a' + j));
                    getCombinations(words, newSb, word, i + 1);
                }
            } else {
                pre.append(word.charAt(i));
                getCombinations(words, pre, word, i + 1);
            }
        }
    }
}
