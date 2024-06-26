package Specific_Questions;

import java.util.*;

public class Suspicious {
	public static void main(String[] argv) {
		String[][] badgeTimes = new String[][] {
				{"Paul", "1355"},
				{"Jennifer", "1910"},
				{"Jose", "835"},
				{"Jose", "830"},
				{"Paul", "1315"},
				{"Chloe", "0"},
				{"Chloe", "1910"},
				{"Jose", "1615"},
				{"Jose", "1640"},
				{"Paul", "1405"},
				{"Jose", "855"},
				{"Jose", "930"},
				{"Jose", "915"},
				{"Jose", "730"},
				{"Jose", "940"},
				{"Jennifer", "1335"},
				{"Jennifer", "730"},
				{"Jose", "1630"},
				{"Jennifer", "5"},
				{"Chloe", "1909"},
				{"Zhang", "1"},
				{"Zhang", "10"},
				{"Zhang", "109"},
				{"Zhang", "110"},
				{"Amos", "1"},
				{"Amos", "2"},
				{"Amos", "400"},
				{"Amos", "500"},
				{"Amos", "503"},
				{"Amos", "504"},
				{"Amos", "601"},
				{"Amos", "602"},
				{"Paul", "1416"},
		};
		// Expected output (in any order)
		//   Paul: 1315 1355 1405
		//   Jose: 830 835 855 915 930
		//   Zhang: 10 109 110
		//   Amos: 500 503 504
		System.out.println(getSuspiciousActivity(badgeTimes));
	}

	public static Map<String, List<Integer>> getSuspiciousActivity(String[][] badges) {
		// build map
		Map<String,List<Integer>> allTimes = new HashMap<>();
		for (String[] entry : badges) {
			List<Integer> times = allTimes.get(entry[0]);
			if (times == null) {
				times = new ArrayList<>();
			}
			times.add(Integer.valueOf(entry[1]));
			allTimes.put(entry[0], times);
		}

		// iterate through map, building result
		Map<String,List<Integer>> result = new HashMap<>();
		for (String person : allTimes.keySet()) {
			List<Integer> times = allTimes.get(person);
			Collections.sort(times);
			List<Integer> range = getTimeRange(times);
			if (range != null) {
				result.put(person, range);
			}
		}
		return result;
	}

	// if 3 or more entries are present within an hour, returns a list of those entries
	private static List<Integer> getTimeRange(List<Integer> times) {
		int lo = 0;
		int start = times.get(lo);
		int hi = 1;
		while (lo <= hi && hi < times.size()) {
			int time = times.get(hi);
			if (time - start <= 100) {
				hi++; // expand upper end of window
			} else {
				// current window is 3 or more long
				if (hi-lo >= 3) { // hi is outside 1hr range, don't include it
					List<Integer> hour = new LinkedList<>();
					for (int k = lo; k < hi; k++) {
						hour.add(times.get(k));
					}
					return hour;
				}
				// guaranteed to be true since in else branch and lo always <= hi since time is set by hi
				while (time - times.get(lo) > 100) {
					lo++;
				}
				start = times.get(lo);
			}
		}
		// last window may be 3 or more long
		if (hi-lo >= 3) {
			List<Integer> hour = new LinkedList<>();
			for (int k = lo; k < hi; k++) {
				hour.add(times.get(k));
			}
			return hour;
		}
		return null;
	}
}
