package Specific_Questions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

// map Strings to ints with global versioning
public class VersionedMap {

	private final Map<String,TreeMap<Integer, Integer>> data; // key -> {version -> val}
	private int version;
	private static final String NOT_FOUND = "<NULL>";

	public VersionedMap() {
		data = new HashMap<>();
		version = 1;
	}

	// returns the string representation of the integer in the map with the given key at the most recent version
	public String get(String key) {
		return get(key, getCurrentVersion());
	}

	// returns the string representation of the integer in the map with the given key at the given version
	public String get(String key, int version) {
		TreeMap<Integer, Integer> versions = data.get(key);
		if (versions == null) return NOT_FOUND;
		Integer value = versions.get(version);
		if (value != null) {
			// exact version match
			return value.toString();
		}
		// get the value at the version before the given version if it exists
		Map.Entry<Integer, Integer> prevValue = versions.lowerEntry(version);
		if (prevValue == null) return NOT_FOUND;
		return prevValue.getValue().toString();
	}

	// add the given key -> val mapping to the map at the next version
	public void put(String key, int val) {
		if (!data.containsKey(key)) data.put(key, new TreeMap<>());
		data.get(key).put(version++, val);
	}

	// returns most recent map version
	public int getCurrentVersion() {
		return version;
	}

	// reads file from STDIN and prints expected output to STDOUT
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		VersionedMap m = new VersionedMap();
		while (in.hasNextLine()) {
			String[] parts = in.nextLine().split(" "); // split on single ' ' space char
			if (parts.length < 2 || parts.length > 3) continue; // skip invalid lines

			if (parts[0].equals("GET")) {
				if (parts.length == 2) {
					String value = m.get(parts[1]);
					System.out.printf("GET %s = %s\n", parts[1], value);
				} else {
					int version = Integer.parseInt(parts[2]);
					String value = m.get(parts[1], version);
					System.out.printf("GET %s(#%d) = %s\n", parts[1], version, value);
				}
			} else { // PUT - assume input is well-formatted
				m.put(parts[1], Integer.parseInt(parts[2]));
				System.out.printf("PUT(#%d) %s = %s\n", m.getCurrentVersion() - 1, parts[1], parts[2]);
			}
		}
	}
}
