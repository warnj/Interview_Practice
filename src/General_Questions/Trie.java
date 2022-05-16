package General_Questions;

import java.util.ArrayList;
import java.util.List;

// trie for lowercase english letters, mostly taken from
// https://leetcode.com/problems/implement-trie-prefix-tree/solution
public class Trie {
    static class TrieNode {
        private TrieNode[] links;
        private final int R = 26;
        private boolean isEnd;
        public TrieNode() {
            links = new TrieNode[R];
        }
        public boolean containsKey(char ch) {
            return links[ch -'a'] != null;
        }
        public TrieNode get(char ch) {
            return links[ch -'a'];
        }
        public void put(char ch, TrieNode node) {
            links[ch -'a'] = node;
        }
        public void setEnd() {
            isEnd = true;
        }
        public boolean isEnd() {
            return isEnd;
        }
        public String toString() {
            StringBuilder sb = new StringBuilder();
            List<TrieNode> children = new ArrayList<>();
            for (int i = 0; i < R; i++) {
                if (links[i] != null) {
                    String end = links[i].isEnd ? "(end) " : " ";
                    sb.append((char) (i + 'a')).append(end);
                    children.add(links[i]);
                }
            }
            sb.append("\n"); // put children below parent
            for (TrieNode child : children) {
                sb.append(child.toString());
            }
            return sb.toString();
        }
    }
    private TrieNode root;
    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (!node.containsKey(currentChar)) {
                node.put(currentChar, new TrieNode());
            }
            node = node.get(currentChar);
        }
        node.setEnd();
    }

    // search a prefix or whole key in trie and
    // returns the node where search ends
    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char curLetter = word.charAt(i);
            if (node.containsKey(curLetter)) {
                node = node.get(curLetter);
            } else {
                return null;
            }
        }
        return node;
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEnd();
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null;
    }

    public String toString() {
        return root.toString();
    }

    // dfs to get all words starting with given prefix
    public List<String> getAllStartsWith(String prefix) {
        List<String> result = new ArrayList<>();
        TrieNode start = searchPrefix(prefix);
        if (start == null) return result;
        getAllHelper(result, start, prefix);
        return result;
    }
    private void getAllHelper(List<String> result, TrieNode cur, String p) {
        if (cur.isEnd) {
            result.add(p);
        }
        for (int i = 0; i < 26; i++) {
            if (cur.links[i] != null) {
                getAllHelper(result, cur.links[i], p + ((char)(i + 'a')));
            }
        }
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("abcdef");
        t.insert("abc");
        t.insert("abczx");
        t.insert("zqr");
        System.out.println(t);
        System.out.println(t.getAllStartsWith("a"));
    }
}
