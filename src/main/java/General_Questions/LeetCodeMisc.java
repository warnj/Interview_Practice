package General_Questions;

import java.util.*;

public class LeetCodeMisc {

	public static void main(String[] args) {
	}




	// https://leetcode.com/problems/minimum-cost-for-tickets/editorial/
	private int mincostTickets(int[] days, int[] costs, int i, int d) {
		// tickets are paid for up to d so ignore the days we can travel for free
		while (i < days.length && days[i] <= d) {
			i++;
		}
		if (i >= days.length) {
			return 0;
		}
		return Math.min(
				costs[0] + mincostTickets(days, costs, i+1, i+1),
				Math.min(
						costs[1] + mincostTickets(days, costs, i+1, i+7),
						costs[2] + mincostTickets(days, costs, i+1, i+30)
				)
		);
	}
	public int mincostTickets(int[] days, int[] costs) {
		return mincostTickets(days, costs, 0, 0);
	}

	// https://leetcode.com/problems/integer-to-roman
	private static final String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}; // roman numerals for 0 to 9
	private static final String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}; // roman numerals for 0, 10, 20, 30, 40, ... 90
	private static final String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}; // roman for 0, 100, 200, 300, 400, ... 900
	private static final String[] thousands = {"", "M", "MM", "MMM"}; // roman for 0, 1000, 2000
	public String intToRoman(int num) {
		return thousands[num/1000] + hundreds[(num % 1000)/100] + tens[(num % 100)/10] + ones[num % 10];
	}

	// https://leetcode.com/problems/minimum-number-of-operations-to-make-array-empty
	public int minOperations(int[] nums) {
		Map<Integer,Integer> counts = new HashMap<>();
		for (int n : nums) {
			counts.put(n, 1+counts.getOrDefault(n, 0));
		}
		int ops = 0;
		for (int k : counts.keySet()) {
			int v = counts.get(k);
			if (v == 1) {
				return -1;
			}
			if (v % 3 == 0) {
				ops += v/3;
			} else if (v%3 == 2) {
				ops += v/3;
				ops++; // a single two
			} else { // v%3==1  i.e. 4, 7, etc
				ops += v/3-1;
				ops += 2; // final 2 twos
			}
		}
		return ops;
	}

	// https://leetcode.com/problems/number-of-laser-beams-in-a-bank
	public int numberOfBeams(String[] bank) {
		int result = 0;
		int prev = -1;
		for (int i = 0; i < bank.length; i++) {
			int ones = countOnes(bank[i]);
			if (ones > 0) {
				if (prev < 0) {
					prev = ones;
				} else {
					result += prev * ones;
					prev = ones;
				}
			}
		}
		return result;// == 1 ? 0 : result;
	}
	public int countOnes(String s) {
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '1') {
				count++;
			}
		}
		return count;
	}

	// https://leetcode.com/problems/maximum-product-difference-between-two-pairs/
	public static int maxProductDifference(int[] nums) {
		int max = Integer.MIN_VALUE;
		int max2 = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		int min2 = Integer.MAX_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > max) {
				max2 = max;
				max = nums[i];
			} else if (nums[i] > max2) {
				max2 = nums[i];
			}
			if (nums[i] < min) {
				min2 = min;
				min = nums[i];
			} else if (nums[i] < min2) {
				min2 = nums[i];
			}
		}
		return max * max2 - min * min2;
	}

	// https://leetcode.com/problems/evaluate-reverse-polish-notation/
	public static int evalRPN(String[] tokens) {
		Stack<Integer> s = new Stack<>();
		for (String t : tokens) {
			if (t.equals("+")) {
				int cur = s.pop();
				int prev = s.pop();
				s.push(prev + cur);
			} else if (t.equals("-")) {
				int cur = s.pop();
				int prev = s.pop();
				s.push(prev - cur);
			} else if (t.equals("*")) {
				int cur = s.pop();
				int prev = s.pop();
				s.push(prev * cur);
			} else if (t.equals("/")) {
				int cur = s.pop();
				int prev = s.pop();
				s.push(prev / cur);
			} else {
				s.push(Integer.parseInt(t));
			}
		}
		return s.peek();
	}

	public static int leastInterval(char[] tasks, int n) {
		int[] counts = new int[26];
		for (char c : tasks) counts[c-'A']++;
		Arrays.sort(counts);
		int max = counts[25]-1;
		int spaces = max * n; // number of empty spaces in final sequence
		for (int i = 24; i >= 0; i--)
			spaces -= Math.min(max, counts[i]); // most frequent char may not be unique
		spaces = Math.max(0, spaces); // spaces shouldn't be < 0
		return tasks.length + spaces;
	}
	public static int leastIntervalOG(char[] tasks, int n) {
		int[] c = new int[26];
		for (char t : tasks) c[t - 'A']++;
		Arrays.sort(c);
		int i = 24; // 25-i = number of unique chars that share the max count
		while (i >= 0 && c[i] == c[25]) i--;
		// directly calculate the total length including chars and spaces
		// if more chars than n share large counts, they can be interspaced such that this formula is valid
		return Math.max(tasks.length, (c[25] - 1) * (n + 1) + 25 - i);
	}

	// https://leetcode.com/problems/count-odd-numbers-in-an-interval-range
	public int countOdds(int low, int high) {
		int result = (high - low + 1) / 2;
		if (high % 2 == 1 && low % 2 == 1) result++;
		return result;
	}

	// https://leetcode.com/problems/accounts-merge
	// emails as nodes, edges indicate
	private static HashSet<String> visited = new HashSet<>();
	private static Map<String, List<String>> adjacent = new HashMap<>();

	private static void DFS(List<String> mergedAccount, String email) {
		visited.add(email);
		// Add the email vector that contains the current component's emails
		mergedAccount.add(email);
		if (!adjacent.containsKey(email)) return;
		for (String neighbor : adjacent.get(email))
			if (!visited.contains(neighbor))
				DFS(mergedAccount, neighbor);
	}

	public static List<List<String>> accountsMerge(List<List<String>> accountList) {
		for (List<String> account : accountList) {
			int accountSize = account.size();
			// Building adjacency list
			// Adding edge between first email to all other emails in the account
			String accountFirstEmail = account.get(1);
			for (int j = 2; j < accountSize; j++) {
				String accountEmail = account.get(j);
				if (!adjacent.containsKey(accountFirstEmail)) {
					adjacent.put(accountFirstEmail, new ArrayList<>());
				}
				adjacent.get(accountFirstEmail).add(accountEmail);
				if (!adjacent.containsKey(accountEmail)) {
					adjacent.put(accountEmail, new ArrayList<>());
				}
				adjacent.get(accountEmail).add(accountFirstEmail);
			}
		}
		// Traverse over all th accounts to store components
		List<List<String>> mergedAccounts = new ArrayList<>();
		for (List<String> account : accountList) {
			String accountName = account.get(0);
			String accountFirstEmail = account.get(1);
			// If email is visited, then it's a part of different component
			// Hence perform DFS only if email is not visited yet
			if (!visited.contains(accountFirstEmail)) {
				List<String> mergedAccount = new ArrayList<>();
				// Adding account name at the 0th index
				mergedAccount.add(accountName);
				DFS(mergedAccount, accountFirstEmail);
				Collections.sort(mergedAccount.subList(1, mergedAccount.size()));
				mergedAccounts.add(mergedAccount);
			}
		}
		return mergedAccounts;
	}
	// can't solve with hashset only approach, only works for very simple cases
	public static List<List<String>> accountsMergeWrong(List<List<String>> accounts) {
		Map<String,String> duplicates = new HashMap<>(); // map duplicate unique names to original unique names
		Map<String,String> emails = new HashMap<>(); // map email -> unique name
		Map<String,String> names = new HashMap<>(); // unique name -> name
		// find the duplicate names
		for (int i = 0; i < accounts.size(); i++) {
			String uniqueName = accounts.get(i).get(0) + i;
			names.put(uniqueName, accounts.get(i).get(0));
			for (int j = 1; j < accounts.get(i).size(); j++) {
				String email = accounts.get(i).get(j);
				if (emails.containsKey(email)) {
					String ogName = emails.get(email);
					duplicates.put(uniqueName, ogName);
					emails.put(email, ogName); // important that all emails map back to the 1st unique name
				} else {
					emails.put(email, uniqueName);
				}
			}
		}
		System.out.println(duplicates);
		// duplicates need to map back to the 1st unique name
		boolean mod = true;
		while (mod) {
			List<String> fix = new ArrayList<>(); // don't modify the map while iterating it
			for (Map.Entry<String,String> d : duplicates.entrySet()) {
				if (duplicates.containsKey(d.getValue())) {
					fix.add(d.getKey());
				}
			}
			mod = !fix.isEmpty();
			for (String s : fix) {
				duplicates.put(s, duplicates.get(duplicates.get(s)));
			}
		}
		System.out.println(duplicates);
		System.out.println(emails);
		System.out.println(names);
		// store all emails associated with a person by unique name (remove duplicates)
		Map<String,Set<String>> r = new HashMap<>(); // unique name -> emails
		for (int i = 0; i < accounts.size(); i++) {
			String uniqueName = accounts.get(i).get(0) + i;
			if (duplicates.containsKey(uniqueName)) {
				uniqueName = duplicates.get(uniqueName);
			}
			Set<String> es = r.get(uniqueName);
			if (es == null) {
				es = new HashSet<>();
				r.put(uniqueName, es);
			}
			for (int j = 1; j < accounts.get(i).size(); j++) {
				String email = accounts.get(i).get(j);
				es.add(email);
			}
		}
		System.out.println(r);
		// format output from map to list<list> and sorted
		List<List<String>> results = new ArrayList<>();
		for (Map.Entry<String, Set<String>> e : r.entrySet()) {
			List<String> result = new ArrayList<>(e.getValue());
			Collections.sort(result);
			result.add(0, names.get(e.getKey()));
			results.add(result);
		}
		return results;
	}

	// https://leetcode.com/problems/text-justification
	// k = # words in s, n = len(s)     0(n+k+n) time roughly
	public static List<String> textJustify(String[] words, int cols) {
		List<String> lines = new ArrayList<>();
		String removed = null;
		for (int i = 0; i < words.length; ) {
			List<String> line = new ArrayList<>();
			int lineChars = 0;
			if (removed != null) {
				line.add(removed);
				lineChars += removed.length() + 1;
				removed = null;
			}
			// fill a line
			while (i < words.length && lineChars < cols) {
				String word = words[i];
				line.add(word);
				lineChars += word.length() + 1; // need at least 1 space between words
				i++;
			}
			if (lineChars > cols + 1) { // gone over by one word
				removed = line.remove(line.size() - 1);
			}
			if (i == words.length && removed == null) { // last line
				String start = String.join(" ", line);
				lines.add(start + " ".repeat(cols - start.length()));
			} else {
				lines.add(fillLine(line, cols));
			}
		}
		if (removed != null) lines.add(removed + " ".repeat(cols - removed.length()));
		return lines;
	}
	// returns the given list of words in a string of cols length with spaces allocated evenly between
	private static String fillLine(List<String> words, int cols) {
		if (words.size() == 1) return words.get(0) + " ".repeat(cols - words.get(0).length());
		int wordsLen = 0;
		for (String w : words) wordsLen += w.length();
		int spaceSize = (cols - wordsLen) / (words.size() - 1); // total # spaces / # gaps between words
		int spacesUsed = (words.size() - 1) * spaceSize; // after int division, multiply again to get # spaces to fill
		int extra = cols - (wordsLen + spacesUsed); // remainder, allocate these 1 per gap until used up

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < words.size(); i++) {
			String w = words.get(i);
			sb.append(w);
			if (i != words.size() - 1) {
				for (int j = 0; j < spaceSize; j++) {
					sb.append(" ");
				}
				if (extra > 0) { // allocate the extra 1-off spaces to the first gaps, 1 per gap
					sb.append(" ");
					extra--;
				}
			}
		}
		return sb.toString();
	}

	// https://leetcode.com/problems/best-team-with-no-conflicts/description/
	public static int bestTeamScore(int[] scores, int[] ages) {
		// O(n) extra space, O(n*logn + n^2) time
		List<int[]> players = new ArrayList<>(scores.length);
		for (int i = 0; i < scores.length; i++) {
			players.add(new int[]{scores[i], ages[i]});
		}
		players.sort((o1, o2) -> {
			int ageDiff = o1[1] - o2[1];
			int scoreDiff = o1[0] - o2[0];
			return ageDiff != 0 ? ageDiff : scoreDiff;
		}); // sort by age (low to high), then by score (low to high)

//		for (int[] p : players) System.out.println(Arrays.toString(p));

		int[] maxes = new int[scores.length]; // maxes[i] = max sum for all sequences ending at i
		maxes[0] = players.get(0)[0];
		for (int hi = 1; hi < scores.length; hi++) { // ending sequence points
			int max = players.get(hi)[0]; // seq of len 1
			for (int lo = 0; lo < hi; lo++) { // starting points - seq not required to be consecutive
				if (players.get(hi)[0] >= players.get(lo)[0]) {
					// if the later player can be in a team with the starting player, consider team score
					max = Math.max(max, maxes[lo] + players.get(hi)[0]);
				}
			}
			maxes[hi] = max;
		}
//		System.out.println(Arrays.toString(maxes));
		int max = maxes[0];
		for (int i = 1; i < scores.length; i++) max = Math.max(max, maxes[i]);
		return max;
	}

	// https://leetcode.com/problems/cheapest-flights-within-k-stops
	// dijkstra's or bfs or bellman ford
	public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
		// build graph
		Map<Integer, Map<Integer, Integer>> graph = new HashMap<>(); // city -> {dest -> price}
		for (int[] flight : flights) {
			Map<Integer,Integer> vals = graph.get(flight[0]);
			if (vals == null) {
				vals = new HashMap<>();
				graph.put(flight[0], vals);
			}
			vals.put(flight[1], flight[2]);
		}
//		System.out.println(graph);
		// traverse graph
		int[] dist = new int[n];
		Arrays.fill(dist, Integer.MAX_VALUE); // dist[dest] = cheapest price from src
		Queue<int[]> worklist = new LinkedList<>();
		worklist.add(new int[]{src, 0}); // key is to also keep track of current distance to current node
		// bfs with max k stops between src and dest
		for (int i = 0; !worklist.isEmpty() && i < k+1; i++) {
			int size = worklist.size();
			// expand search outward 1 stop
			for (int j = 0; j < size; j++) {
				int[] cur = worklist.remove(); // current leg starting point
				Map<Integer, Integer> dests = graph.get(cur[0]);
				if (dests != null) {
					for (int dest : dests.keySet()) {
						int price = dests.get(dest);
						if (price + cur[1] >= dist[dest]) continue;
						dist[dest] = price + cur[1];
						worklist.add(new int[]{dest, dist[dest]});
					}
				}
			}
//			System.out.println(dist);
		}
		return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
	}

	// https://leetcode.com/problems/additive-number/
	// find all combinations of starting number pairs and call helper to see if sum continues
	// O(n^2) time
	public static boolean isAdditiveNumber(String num) {
		for (int hi = 2; hi < num.length(); hi++) {
			for (int mid = 1; mid < hi; mid++) {
				if (isAdditiveSeq(num, 0, mid, hi)) return true;
			}
		}
		return false;
	}
	// returns true if the entire string is additive sequence starting with given two numbers ending at index i
	private static boolean isAdditiveSeq(String num, int lo, int mid, int hi) {
		boolean valid;
		do {
			if (mid - lo > 1 && num.charAt(lo) == '0') return false; // no leading 0s
			if (hi - mid > 1 && num.charAt(mid) == '0') return false;
			if (hi - mid > num.length() - hi || mid - lo > num.length() - hi) return false; // sum will not fit, not worth checking

			long one = Long.parseLong(num.substring(lo, mid));
			long two = Long.parseLong(num.substring(mid, hi));
			String sum = String.valueOf(one + two);
			System.out.println("checking " + one + " + " + two + " = " + sum);
			int end = hi + sum.length();
			if (end > num.length()) return false; // sum too long

			valid = sum.equals(num.substring(hi, end)); // sum is part of string, still valid seq
			if (valid) System.out.println("found " + one + " + " + two + " = " + sum);
			if (valid && end == num.length()) return true; // at the end

			lo = mid;
			mid = hi;
			hi = end;
		} while (valid);
		return false;
	}
	// initial ideas too complex: a couple sliding windows, work backwards from end, recursive divide and conquer
	public static boolean isAdditiveNumberRecursiveWrong(String num) {
		if (num.length() < 3) return false;
		return isAdditiveNumWrong(num, 0, 1, 2);
	}
	private static boolean isAdditiveNumWrong(String num, int lo, int mid, int hi) {
		if (lo == mid || mid == hi || hi == num.length()) return false;
		if (mid - lo > 1 && num.charAt(lo) == '0') return false; // no leading 0s
		if (hi - mid > 1 && num.charAt(mid) == '0') return false;

		if (isAdditiveSeq(num, lo, mid, hi)) return true;

		// explore other options
		return isAdditiveNumWrong(num, lo, mid+1, hi) ||
				isAdditiveNumWrong(num, lo, mid, hi+1);
	}

	// https://leetcode.com/problems/flip-string-to-monotone-increasing
	public static int minFlipsMonoIncr(String s) {
		// 2 cases: # of zeros after the first 1  OR  # of ones before last 0
		// ^ misses the case where you can change 1 to 0 at front and 0 to 1 at end
		// solution using all spots as pivot point, updating the # swaps, tracking the min

		int sum = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '1') sum++;
		}

		int swaps = sum; // start here where all 1s become 0s
		// all spots < i are 0 and >= i are 1
		int oneOnLeft = 0;
		int zeroOnRight = s.length() - sum;
		for (int i = 0; i < s.length(); i++) {
			swaps = Math.min(swaps, oneOnLeft + zeroOnRight);
			if (s.charAt(i) == '1') {
				oneOnLeft++;
			} else {
				zeroOnRight--;
			}
		}
		return swaps;
	}

	// https://leetcode.com/problems/two-city-scheduling
	// O(n*log(n)) time using sorting - put the ones that have greatest difference between 1st and 2nd cities on the corresponding far ends of the sorted array
	public static int twoCitySchedCost(int[][] costs) {
		Arrays.sort(costs, (a,b) -> (b[1]-b[0]) - (a[1]-a[0]));
		int sum = 0;
		for (int i = 0; i < costs.length/2; i++) sum += costs[i][0]; // city one is 1st half
		for (int i = costs.length/2; i < costs.length; i++) sum += costs[i][1]; // city two is 2nd half
		return sum;
	}

	// https://leetcode.com/problems/triangle/
	// O(total # of ints) time, O(size of largest level) space
	public static int minimumTotal(List<List<Integer>> triangle) {
		// bottom up dp
		// dp[i] = min sum from bottom to i-th number in current level
		int[] dp = new int[triangle.get(triangle.size()-1).size() + 1]; // last val always 0, but j+1 will be valid
		for (int i = triangle.size()-1; i >= 0; i--) { // levels from bottom to top
			for (int j = 0; j < triangle.get(i).size(); j++) {
				dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j+1]);
			}
		}
		return dp[0];
	}
	// dfs traversing every path, not efficient since overlapping sub-problems in shared paths are summed multiple times
	public static int minimumTotalBrute(List<List<Integer>> triangle) {
		int min = Integer.MAX_VALUE;
		Stack<Integer> levels = new Stack<>(); // y coord
		levels.add(0);
		Stack<Integer> horiz = new Stack<>(); // x coord
		horiz.add(0);
		Stack<Integer> sums = new Stack<>(); // sum of the path including current int
		sums.add(triangle.get(0).get(0));
		while (!sums.isEmpty()) {
			int l = levels.pop();
			int h = horiz.pop();
			int s = sums.pop();

			if (l == triangle.size()-1) {
				min = Math.min(min, s);
			} else {
				sums.add(s + triangle.get(l+1).get(h));
				levels.add(l+1);
				horiz.add(h);

				sums.add(s + triangle.get(l+1).get(h+1));
				levels.add(l+1);
				horiz.add(h+1);
			}
		}
		return min;
	}

	// https://leetcode.com/problems/roman-to-integer/
	public static int romanToInt(String s) {
		if (s.isEmpty()) return 0;
		int result = getValue(s.charAt(0));
		int prev = result;
		for (int i = 1; i < s.length(); i++) {
			int val = getValue(s.charAt(i));
			if ((val == 5 || val == 10) && prev == 1) {
				result += val - prev - prev; // val was added in last iteration, must reverse that and add correct val
			} else if ((val == 50 || val == 100) && prev == 10) {
				result += val - prev - prev;
			} else if ((val == 500 || val == 1000) && prev == 100) {
				result += val - prev - prev;
			} else {
				result += val;
			}
			prev = val;
		}
		return result;
	}
	private static int getValue(char c) {
		if (c == 'I') return 1;
		if (c == 'V') return 5;
		if (c == 'X') return 10;
		if (c == 'L') return 50;
		if (c == 'C') return 100;
		if (c == 'D') return 500;
		return 1000; // if (c == 'M')
	}

	// https://leetcode.com/problems/unique-paths-ii
	// O(m*n) time and space
	public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
		int y = obstacleGrid.length;
		int x = obstacleGrid[0].length;
		int[][] grid = new int[y][x]; // could use obstacleGrid for O(1) space if messing it up is allowed
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (obstacleGrid[i][j] == 1) {
					grid[i][j] = 0;
				} else if (i == 0) {
					if (j-1 >= 0 && grid[0][j-1] == 0) grid[0][j] = 0; // may be blocked to the left
					else grid[0][j] = 1;
				} else if (j == 0) {
					if (grid[i-1][0] == 0) grid[i][0] = 0; // may be blocked above
					else grid[i][0] = 1;
				} else {
					int up = grid[i - 1][j];
					int left = grid[i][j - 1];
					grid[i][j] = up + left;
				}
			}
		}
		return grid[y - 1][x - 1];
	}

	// https://leetcode.com/problems/unique-paths/
	// O(m*n) time and space, bottom-up solution with dp
	public static int uniquePaths(int m, int n) {
		int[][] grid = new int[n][m];
		// filling in recursion base cases first, then iterating down progressively larger grid sizes
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (i == 0) grid[0][j] = 1;
				else if (j == 0) grid[i][0] = 1;
				else {
					int up = grid[i - 1][j];
					int left = grid[i][j - 1];
					grid[i][j] = up + left;
				}
			}
		}
		return grid[n - 1][m - 1];
	}
	// O(m*n) time and space, top-down solution with memoization
	static Map<String, Integer> cache; // map coordinate string to unique path count, could also use m x n matrix
	public static int uniquePathsRecursive(int m, int n) {
		if (cache == null) cache = new HashMap<>(); // could also use m x n array
		if (m == 0 || n == 0) return 0;
		if (m == 1 || n == 1) return 1;
		// smallest value first in key since 2x3 has same answer as 3x2, key for both is "2,3"
		// can't use product as key since 2x6 has different answer than 3x4
		// could cache this solution (m,n) instead of both (m-1,n) and (n-1,m)
		String keyDown = m-1 < n ? (m-1) + "," + n : n + "," + (m-1);
		Integer down = cache.get(keyDown);
		if (down == null) {
			down = uniquePathsRecursive(m-1, n);
			cache.put(keyDown, down);
		}
		String keyRight = m < n-1 ? m + "," + (n-1) : (n-1) + "," + m;
		Integer right = cache.get(keyRight);
		if (right == null) {
			right = uniquePathsRecursive(m, n-1);
			cache.put(keyRight, right);
		}
		return right + down;
	}
	// O(2^(m+n)) time, per leetcode: (branches ^ depth)
	// O(m+n) space taken on stack since that is the depth (time to go from one corner in board to opposite)
	public static int uniquePathsSlow(int m, int n) {
		// each square has 2 future options: right or down
		// wonder if there is a simple math function O(1) that results in this? take a look at sample inputs
		// seems like a ripe question for recursion, that may have horrible runtime
		if (m == 0 || n == 0) {
			return 0;
		} else if (m == 1 || n == 1) {
			return 1;
		} else {
			return uniquePathsSlow(m-1, n) + uniquePathsSlow(m, n-1);
		}
	}

	// https://leetcode.com/problems/add-digits/
	// O(n) solution, clever O(1) solution that uses "% 9"
	public static int addDigits(int num) {
		while (num > 9) {
			int sum = 0; // sum of digits
			for (int n = num; n > 0; n /= 10) {
				int digit = n % 10;
				sum += digit;
			}
			num = sum; // next loop starting value is the sum of digits
		}
		return num;
	}

	// https://leetcode.com/problems/power-of-four
	public static boolean isPowerOfFour(int n) {
		if (n < 1) return false;
		if (n == 1) return true;
		int i = 0; // power
		int r = 1; // result of 4 to the power of i
		while (r < n) {
			i++;
			double d = Math.pow(4, i);
			if (d > Integer.MAX_VALUE) return false;
			r = (int)d;
			if (r==n) return true;
		}
		return false;
	}

	// https://leetcode.com/problems/excel-sheet-column-number/
	// title to number
	public static int titleToNumber(String columnTitle) {
		int len = columnTitle.length();
		if (len == 0) return 0;
		int result = 0;
		int n = 1;
		for (int i = len-1; i >= 0; i--, n*=26) {
			result += n * (columnTitle.charAt(i) - 'A' + 1);
		}
		return result;
	}

	// https://leetcode.com/problems/excel-sheet-column-title/
	// number to title
	public static String convertToTitle(int columnNumber) {
		// converting base 10 number to base 26
		String result = "";
		while (columnNumber-- > 0) { // need to subtract 1 each time to get 0-based number that can be added to 'A'
			int remains = columnNumber % 26;
			result = (char)('A'+remains) + result;
			columnNumber /= 26;
		}
		return result;
	}

	// https://leetcode.com/problems/valid-mountain-array/
	public static boolean validMountainArray(int[] arr) {
		if (arr.length <= 2) return false;
		int prev = arr[0];
		boolean increasing = true; // in the "climb" phase of going up mountain
		for (int i = 1; i < arr.length; i++) {
			int cur = arr[i];
			if (prev == cur) return false;

			if (increasing) {
				if (prev > cur) {
					if (i == 1) return false; // can't start off going down
					increasing = false; // prev was top of mtn, now going down
				}
			} else {
				if (prev < cur) return false;
			}
			prev = cur;
		}
		return !increasing; // should be descending at the end
	}

	// https://leetcode.com/problems/house-robber-ii/
	// quick and dirty modification of original house robber; try it two different ways: rob 1st & not last OR rob last & not 1st
	public static int rob2(int[] nums) {
		int rob = nums[0];
		int noRob = 0;
		for (int i = 1; i < nums.length; i++) {
			int prevRob = rob;
			if (i != nums.length-1) rob = noRob + nums[i];
			noRob = Math.max(noRob, prevRob);
		}
		int robbingFirst = Math.max(rob, noRob);
		rob = nums[nums.length-1];
		noRob = 0;
		for (int i = nums.length-2; i >= 0; i--) {
			int prevRob = rob;
			if (i != 0 ) rob = noRob + nums[i];
			noRob = Math.max(noRob, prevRob);
		}
		int robbingLast = Math.max(rob, noRob);
		return Math.max(robbingFirst, robbingLast);
	}

	// https://leetcode.com/problems/house-robber/
	// O(n) runtime and O(1) space
	public static int rob(int[] n) {
		//   [2,4,8, 9, 9 ]            [6,1,1,6]
		// r  2,4,10,13,19           r  6,1,7,12
		// nr 0,2,4 ,10,13          nr  0,6,6,7
		int rob = n[0]; // money if rob current house
		int noRob = 0; // money if don't rob current house
		for (int i = 1; i < n.length; i++) {
			int prevNoRob = noRob;
			noRob = Math.max(noRob, rob);
			rob = prevNoRob + n[i];
		}
		return Math.max(rob, noRob);
	}
	public static int robBad(int[] n) {
		// start at the 1st num and sum every other number, then start at the 2nd and sum every other, take max
		// ^ is too simplistic, could have two large outliers that have 2 houses in between

		// look at 4 (5?) elements at a time, make a decision on the first pair and then move 2 or 3 over
		// 1,9,1,1,1,1,8,2,8,9
		// 1,9,1,1,1 -> choices lead to sum of 2 (choose index 0 and 2) or 10 (index 1 and 3)

		// which of the first 2 numbers do you choose?
		// 4,9,6,[1,9]    --> should choose index 0
		// 4,9,6,[9,1]    --> should choose index 1;  need to see more than 3 numbers
		// 4,9,6,1,[9]    --> should choose index 0
		// 4,9,6,1,[1]    --> should choose index 0;  enough data is available
		// 4,9,6,1,[1,2]  --> should choose index 0;  enough data is available
		// sliding window of 4 seems like it will work, after attempting seems like need a window of 5

		// 2,4,8,9,9   -> should pick index 0
		// 1,3,1,3,100 -> should pick index 1

		// consider starting in the middle of the array, maybe recursion, that doesn't seem to help though

		// the solution below misses a few corner cases and is not simplest way!

		int result = 0;
		int start = 0;
		int end = 4;
		while (end < n.length) {
			int sum1 = n[start] + n[start+2];
			int sum2 = n[start+1] + n[start+3];
			int sum3 = n[start+1] + n[start+2];
			if (sum1 >= sum2 || n[start] >= sum3) { // pick 1st house to rob
				result += n[start];
//				System.out.println("Choose index: " + start);
				start += 2;
				end += 2;
			} else { // pick 2nd house to rob
//				System.out.println("Choose index: " + (start+1));
				result += n[start+1];
				start += 3; // can't pick immediately adjacent house, so set start as a possible choice next time
				end += 3;
			}
		}
		// pick between start and n.length-1; could have 4,3,2,1,or 0 numbers to choose from
		if (n.length-start == 4) {
			result += Math.max(n[start] + n[start+2], Math.max(n[start] + n[start+3], n[start+1] + n[start+3]));
//			System.out.println("Choose between final 4");
		} else if (n.length-start == 3) {
			result += Math.max(n[start] + n[start+2], n[start+1]);
//			System.out.println("Choose between final 3");
		} else if (n.length-start == 2) {
//			System.out.println("Choose between final 2");
			result += Math.max(n[start], n[start+1]);
		} else if (n.length-start == 1) {
//			System.out.println("Choose between final 1");
			result += n[start];
		}
		return result;
	}

	// https://leetcode.com/problems/word-ladder/description/
	public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
//		String[] wordList = {"hot","dot","dog","lot","log","cog"};
//		System.out.println(ladderLength("", "", Arrays.asList(wordList)));
		return -1;
	}

	// https://leetcode.com/problems/gas-station/
	// O(n) runtime and O(1) space
	public static int canCompleteCircuit(int[] gas, int[] cost) {
		int difSum = 0;
		for (int i = 0; i < gas.length; i++) {
			difSum += gas[i] - cost[i];
		}
		if (difSum < 0) return -1;

		// not entirely obvious that if sum of differences >= 0 then a solution exists, but can be proven
		int start = 0; // potential route starting point
		int curSum = 0; // sum of difs since start
		for (int i = 0; i < gas.length; i++) {
			curSum += gas[i] - cost[i];
			if (curSum < 0) { // potential starting point didn't work, move to next one
				start = i+1;
				curSum = 0;
			}
		}
		return start;
	}
	// worst case runtime still O(n^2), but this is rarer with sort optimization; O(n) space
	public static int canCompleteCircuitOptimized(int[] gas, int[] cost) {
		// save best starting places; best starting places are where gas[i] is most greater than cost[i]
		List<Position> positions = new ArrayList<>();
		for (int i = 0; i < gas.length; i++) {
			if (gas[i] >= cost[i]) { // first step is possible, check if route is possible - may not need this line
				positions.add(new Position(i, gas[i]-cost[i]));
			}
		}
		positions.sort(Collections.reverseOrder());

		for (Position p : positions) {
			int fuel = 0;
			for (int j = p.index; j < p.index + gas.length; j++) {
				int curPos = j % gas.length;
				fuel += gas[curPos];
				if (fuel < cost[curPos]) {
					break; // not enough gas to continue route
				} else if (j == p.index + gas.length - 1) {
					return p.index; // completed route
				}
				fuel -= cost[curPos]; // continue to next stop
			}
		}
		return -1;
	}
	static class Position implements Comparable<Position>{
		int index, value;
		public Position(int i, int v) {
			this.index = i;
			this.value = v;
		}
		@Override
		public int compareTo(Position o) {
			return this.value - o.value;
		}
	}
	// O(n^2) runtime where n is length of input array, O(1) memory
	public static int canCompleteCircuitSimple(int[] gas, int[] cost) {
		// check possible starting places
		for (int i = 0; i < gas.length; i++) {
			if (gas[i] >= cost[i]) { // first step is possible, check if route is possible - do not need this line
				int fuel = 0;
				for (int j = i; j < i + gas.length; j++) {
					int curPos = j % gas.length;
					fuel += gas[curPos];
					if (fuel < cost[curPos]) {
						break; // not enough gas to continue route
						// later realize that inefficiency lies here, we can discard other starting places up to curPos
					} else if (j == i + gas.length - 1) {
						return i; // completed route
					}
					fuel -= cost[curPos]; // continue to next stop
				}
			}
		}
		return -1;
	}

	// https://leetcode.com/problems/koko-eating-bananas/
	// O(n * log m) runtime where n = piles.length and m is max possible value of a number in piles, O(1) space
	public static int minEatingSpeed(int[] piles, int h) {
		if (h < piles.length) return -1;

		// get sum/h and max of array, minimum eating speed is sum/h (rounded up to nearest int), max speed is max
		int max = Integer.MIN_VALUE;
		double sum = 0.0;
		for (int p : piles) {
			sum += p;
			if (max < p) max = p;
		}
		int min = (int) Math.ceil(sum / h);

		// corner cases - watch out for int overflow
		if (h == piles.length) return max;
		if (h >= sum) return 1;

		// check possible answers with BINARY SEARCH
		int mid = (min + max) / 2;
		while (min < max) {
			if (allEaten(piles, h, mid)) {
				// answer <= mid
				max = mid;
			} else {
				// answer > mid; need to eat at faster speed
				min = mid + 1;
			}
			mid = (min + max) / 2;
		}
		return mid;
	}
	// returns true if all piles are eaten in h hours with eating speed of test bananas per hour
	private static boolean allEaten(int[] piles, int h, int test) {
		// do all bananas get eaten by time h?
		int eatHr = 0; // hours spent eating
		for (int p = 0; p < piles.length; p++) {
			eatHr += piles[p] / test; // hrs to eat the bananas in pile p
			if (piles[p] % test > 0) {
				eatHr++; // if remainder, takes additional hour to finish the pile
			}
			if (eatHr > h) return false; // spent longer eating than allowed; did not finish bananas in time
		}
		return eatHr <= h;
	}
	// worst case O(n*m) where n = piles.length and m is max possible value of a number in piles
	public static int minEatingSpeedSimple(int[] piles, int h) {
		if (h < piles.length) return -1;

		// get sum/h and max of array, minimum eating speed is sum/h (rounded up to nearest int), max speed is max
		int max = Integer.MIN_VALUE;
		double sum = 0.0;
		for (int p : piles) {
			sum += p;
			if (max < p) max = p;
		}
		int min = (int) Math.ceil(sum / h);
//		System.out.println("min " + min + " max " + max);

		if (h == piles.length) return max; // corner case
		if (h >= sum) return 1;

		// check possible answers
		for (int i = min; i <= max; i++) {
			// do all bananas get eaten by time h?
			int hr = 0; // hours spent eating
			for (int p = 0; p < piles.length; p++) {
				int t = piles[p] / i; // hrs to eat the bananas in pile p
				int r = piles[p] % i; // remainder
				hr += t;
				if (r > 0) {
					hr++;
				}
				if (hr > h) break; // spent longer eating than allowed; did not finish bananas in time
			}
			if (hr <= h) return i; // found answer
		}
//		for (int i = min; i <= max; i++) {
//			// do all bananas get eaten by time h?
//			int curPos = 0;
//			int curPile = piles[0];
//			for (int hr = 0; hr < h; hr++) {
//				curPile -= i;
//				if (curPile <= 0) { // finished a pile, move to next one
//					curPos++;
//					if (curPos >= piles.length) return i; // best solution found
//					curPile = piles[curPos];
//				}
//			}
//		}
		return -1;
	}

	// https://leetcode.com/problems/generate-parentheses
	// n = 2 => [()(), (())]
	// n = 3 => [((())), (()()), (())(), ()(()), ()()()]
	public static List<String> generateParenthesis(int n) {
		List<String> result = new ArrayList<>();
		generateParenthesis(result, n, 0, 0, "");
		return result;
	}
	private static void generateParenthesis(List<String> result, int max, int open, int close, String s) {
		if (s.length() == 2*max) {
			result.add(s);
		} else {
			if (open < max) // it is legal to add a "("
				generateParenthesis(result, max, open+1, close, s+"(");
			if (open > close) // it is legal to add a ")"
				generateParenthesis(result, max, open, close+1, s+")");
		}
	}

	// Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
	// Integers in each row are sorted from left to right.
	// The first integer of each row is greater than the last integer of the previous row.
	// https://leetcode.com/problems/search-a-2d-matrix/description/
	public static boolean searchMatrix(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
		int low = 0;
		int high = matrix.length - 1;
		while (high >= low) { // binary search on columns
			int mid = low + (high-low) / 2;
			if (matrix[mid][0] <= target && (mid == matrix.length-1 || matrix[mid+1][0] > target)) {
				return ArrayQuestions.indexOfBinarySearch(matrix[mid], target) != -1; // binary search on row
			} else if (matrix[mid][0] < target) {
				low = mid + 1;
			} else { // arr[mid] > key
				high = mid - 1;
			}
		}
		return false;
	}
	public boolean searchMatrixSimpler(int[][] matrix, int target) { // simpler, cleaner solution to above
		int i = 0, j = matrix[0].length - 1;
		while (i < matrix.length && j >= 0) {
			if (matrix[i][j] == target) {
				return true;
			} else if (matrix[i][j] > target) {
				j--;
			} else {
				i++;
			}
		}
		return false;
	}


	public static class Interval {
		int start; 
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
		public String toString() {
			return "["+Integer.toString(start)+","+Integer.toString(end)+"]";
		}
	}
	// merges overlapping intervals in given list
	// https://leetcode.com/problems/merge-intervals/description/
	public static List<Interval> merge(List<Interval> intervals) {
		List<Interval> result = new ArrayList<>();
		Collections.sort(intervals, new Comparator<Interval>() {
			public int compare(Interval a, Interval b) {
				return a.start - b.start;
			}
		});
		for (int i = 1; i < intervals.size(); i++) {
			Interval prev = intervals.get(i-1);
			Interval cur = intervals.get(i);
			if (prev.end >= cur.start) {
				intervals.set(i, new Interval(prev.start, Math.max(prev.end, cur.end)));
				intervals.remove(i-1);
				i--;
			}
		}
		return intervals;
	}

	// Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], 
	// reconstruct the itinerary in order. Always depart from JFK.
	// https://leetcode.com/problems/reconstruct-itinerary/description/
	public static List<String> findItinerary(String[][] tickets) {
		List<String> it = new LinkedList<String>();
		Map<String, PriorityQueue<String>> graph = new HashMap<>();
		for (String[] t : tickets) {
			graph.putIfAbsent(t[0], new PriorityQueue<>());
			graph.get(t[0]).add(t[1]);
		}
		//		System.out.println(graph);
		Stack<String> toVisit = new Stack<>();
		toVisit.add("JFK");
		while (!toVisit.isEmpty()) {
			while (graph.containsKey(toVisit.peek()) && !graph.get(toVisit.peek()).isEmpty()) {
				toVisit.push(graph.get(toVisit.peek()).remove());
				// subsequent additions to the stack will be visited after completing a cycle of the graph
			}
			it.add(0, toVisit.pop());
		}
		return it;
	}

	// Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path
	// https://leetcode.com/problems/minimum-path-sum/description/
	public static int minPathSum(int[][] grid) {
		// modify grid to put the smallest path sum to each spot in each spot
		int xLen = grid[0].length, yLen = grid.length;
		for (int y = 0; y < yLen; y++) {
			for (int x = 0; x < xLen; x++) {
				if (x == 0 && y != 0) // across left side, sum from above
					grid[y][x] = grid[y-1][x] + grid[y][x];
				else if	(x != 0 && y == 0) // across top, sum from left
					grid[y][x] = grid[y][x-1] + grid[y][x];
				else if (x != 0 && y != 0) // sum the min of left and up cells
					grid[y][x] = Math.min(grid[y-1][x], grid[y][x-1]) + grid[y][x];
			}
		}
		return grid[yLen-1][xLen-1];
	}
	// INEFFICIENT BRUTE FORCE RECURSION SOLUTION:
	//	public static int minPathSum(int[][] grid) {
	//        return minPathSum(grid, 0, 0, 0);
	//    }
	//	private static int minPathSum(int[][] grid, int x, int y, int sum) {
	//        if (x == grid[0].length-1 && y == grid.length-1) {
	//        	return sum + grid[y][x];
	//        } else if (x < grid[0].length-1 && y < grid.length-1) {
	//        	int rightSum = minPathSum(grid, x+1, y, sum + grid[y][x]);
	//        	int downSum = minPathSum(grid, x, y+1, sum + grid[y][x]);
	//        	return Math.min(rightSum, downSum);
	//        } else if (x < grid[0].length-1) {
	//        	return minPathSum(grid, x+1, y, sum + grid[y][x]);
	//        } else { // (y < grid.length-1)
	//        	return minPathSum(grid, x, y+1, sum + grid[y][x]);
	//        }
	//    }

	// n = strs.length, m = avg length of string in strs. Generally m << n.  Runtime = O(n * m * log(m))  Space = O(n * m)
	// https://leetcode.com/problems/group-anagrams/description/
	public static List<List<String>> groupAnagramsSort(String[] strs) {
		Map<String, List<String>> map = new HashMap<>();
		for (String s : strs) {
			String sSorted = sortString(s);
			List<String> val = map.get(sSorted);
			if (val == null) val = new ArrayList<>();
			val.add(s);
			map.put(sSorted, val);
		}
		return new ArrayList<>(map.values());
	}
	private static String sortString(String s) {
		char[] c = s.toCharArray();
		Arrays.sort(c);
		return String.valueOf(c);
	}
	// DOES NOT WORK - map keys are not equal when int[] contents are, can't override hashmap equals/hashcode function, could make wrapper class for int[]
	public List<List<String>> groupAnagramsMap(String[] strs) {
		Map<int[], List<String>> anagrams = new HashMap<>();
		for(String str : strs) {
			int[] anagram = new int[26];
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				anagram[c-'a']++;
			}
			List<String> val = anagrams.get(anagram);
			if (val == null) {
				List<String> newVal = new ArrayList<>();
				newVal.add(str);
				anagrams.put(anagram, newVal);
			} else {
				val.add(str);
				// don't need to re-put since this is just a reference to array already in the map
			}
		}
		List<List<String>> result = new ArrayList<>();
		result.addAll(anagrams.values());
		return result;
	}
	// improved version using counts instead of sorting, use hashcode instead of comparing count arrays, not 100%
	// accurate since hashcode may collide with other non-equal arrays.
	// O(n(w + 26)) = O(n*w) time, O(n) extra space for counts
	public static List<List<String>> groupAnagrams(String[] strs) {
		Map<Integer, List<String>> m = new HashMap<>(); // hashcode of char count array -> list of words
		for (String word : strs){
			int hash = Arrays.hashCode(getCounts(word));
			List<String> anagrams = m.get(hash);
			if (anagrams == null) {
				anagrams = new ArrayList<>();
				m.put(hash, anagrams);
			}
			anagrams.add(word);
		}
		return new ArrayList<>(m.values());
	}
	// slow version, barely passes
	// O(n*w + n^2) time where n is len(strs) and w is total number of chars in strs
	// O(n) space (since input limited to lowercase letters)
	public static List<List<String>> groupAnagramsSlow(String[] strs) {
		Map<String,int[]> m = new HashMap<>(); // map strings to letter counts
		for (String s : strs) {
			m.put(s, getCounts(s));
		}
		List<List<String>> result = new ArrayList<>();
		boolean[] visited = new boolean[strs.length];
		for (int i = 0; i < strs.length; i++) {
			if (visited[i]) continue;
			visited[i] = true;
			List<String> anagrams = new ArrayList<>();
			anagrams.add(strs[i]);
			// technically O(n^2) even though we flag to never re-check once it's been looked at, better if we could do O(1) remove
			for (int j = i+1; j < strs.length; j++) {
				if (!visited[j] && Arrays.equals(m.get(strs[i]), m.get(strs[j]))) {
					// anagrams
					anagrams.add(strs[j]);
					visited[j] = true;
				}
			}
			result.add(anagrams);
		}
		return result;
	}
	private static int[] getCounts(String s) {
		int[] counts = new int[26];
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			counts[c-'a']++;
		}
		return counts;
	}



}
