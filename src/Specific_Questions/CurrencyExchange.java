package Specific_Questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyExchange {

	static class Edge {
		String src;
		String dest;
		double val;
		public Edge(String d, String s, double v) {
			dest = d;
			src = s;
			val = v;
		}
	}

	public static double getMaxExchangeValue(Map<String,double[]> tickers, String start, String end, double amt) {
		Map<String, List<Edge>> graph = new HashMap<>();
		for (Map.Entry<String, double[]> e : tickers.entrySet()) {
			String[] temp = e.getKey().split("-");
			assert temp.length == 2;
			if (!graph.containsKey(temp[0])) {
				graph.put(temp[0], new ArrayList<>());
			}
			if (!graph.containsKey(temp[1])) {
				graph.put(temp[1], new ArrayList<>());
			}

			Edge a = new Edge(temp[0], temp[1], 1.0 / e.getValue()[0]); // ask
			graph.get(temp[1]).add(a);

			Edge b = new Edge(temp[1], temp[0], e.getValue()[1]); // bid
			graph.get(temp[0]).add(b);


		}
		return 0.0;
	}
}



