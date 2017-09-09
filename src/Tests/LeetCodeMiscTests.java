package Tests;

import static org.junit.Assert.*;

import java.util.*;
import General_Questions.*;
import org.junit.Test;

public class LeetCodeMiscTests {

	@Test
	public void test() {
		String[][] tickets = {
				{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"}, {"LHR", "SFO"}
		};
		List<String> result = LeetCodeMisc.findItinerary(tickets);
//		System.out.println(result);
		assertEquals(result.toString(), "[JFK, MUC, LHR, SFO, SJC]");
		
		tickets = new String[][] {{"JFK","SFO"},{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}};
		result = LeetCodeMisc.findItinerary(tickets);
		System.out.println(result);
		
		tickets = new String[][] {{"JFK", "ATL"}, {"ATL", "JFK"}};
		result = LeetCodeMisc.findItinerary(tickets);
		System.out.println(result);
		
		tickets = new String[][] {{"JFK","SFO"},{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}};
		result = LeetCodeMisc.findItinerary(tickets);
		System.out.println(result);
	}

}
