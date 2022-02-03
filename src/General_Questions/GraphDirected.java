package General_Questions;

/*	http://www.geeksforgeeks.org/algorithms-gq/graph-traversals-gq/
		best way to identify cycle in graph: DFS - only need to know: There is an edge 
		from currently being visited node to an ancestor of currently visited node in DFS forest to find a cycle.
		
	find great graph examples: http://algs4.cs.princeton.edu/40graphs/
 */
/**
 * Directed, weighted graph data type
 * Parallel edges are not allowed
 * Self loops are allowed
 */

import java.util.*;

public class GraphDirected {
	private Map<Vertex, Map<Vertex, Double>> adjList;
	private Map<String, Vertex> vertices;
	private static final TreeMap<Vertex, Double> EMPTY_MAP = new TreeMap<>();
	private int numEdges;

	/** Construct empty Graph  */
	public GraphDirected() {
		adjList = new HashMap<Vertex, Map<Vertex, Double>>();
		vertices = new HashMap<String, Vertex>();
		numEdges = 0;
	}

	/**
	 * Add a new vertex name with no neighbors (if vertex does not yet exist)
	 * @param name Vertex to be added
	 */
	public Vertex addVertex(String name) {
		Vertex v = vertices.get(name);
		if (v == null) {
			v = new Vertex(name);
			vertices.put(name, v);
			adjList.put(v, new TreeMap<>());
		}
		return v;
	}

	/**
	 * Returns the Vertex matching v
	 * @param name a String name of a Vertex that may be in
	 * this Graph
	 * @return the Vertex with a name that matches v or null
	 * if no such Vertex exists in this Graph
	 */
	public Vertex getVertex(String name) {
		return vertices.get(name);
	}

	/**
	 * Returns true iff v is in this Graph, false otherwise
	 * @param name a String name of a Vertex that may be in
	 * this Graph
	 * @return true iff v is in this Graph
	 */
	public boolean hasVertex(String name) {
		return vertices.containsKey(name);
	}

	/**
	 * Is from-to, an edge in this Graph. The graph is 
	 * undirected so the order of from and to does not
	 * matter.
	 * @param from the name of the first Vertex
	 * @param to the name of the second Vertex
	 * @return true iff from-to exists in this Graph
	 */
	public boolean hasEdge(String from, String to) {
		if (!hasVertex(from) || !hasVertex(to))
			return false;
		return adjList.get(vertices.get(from)).containsKey(vertices.get(to));
	}

	/**
	 * Add to to from's set of neighbors, and add from to to's
	 * set of neighbors. Does not add an edge if another edge
	 * already exists
	 * @param from the name of the first Vertex
	 * @param to the name of the second Vertex
	 */
	public void addEdge(String from, String to) {
		addEdge(from, to, 0.0);
	}
	public void addEdge(String from, String to, double weight) {
		if (!hasEdge(from, to)) {
			numEdges++;
			Vertex v, w;
			if ((v = getVertex(from)) == null)
				v = addVertex(from);
			if ((w = getVertex(to)) == null)
				w = addVertex(to);
			adjList.get(v).put(w, weight);
		}
	}

	/**
	 * Return an iterator over the neighbors of Vertex named v
	 * @param name the String name of a Vertex
	 * @return an Iterator over Vertices that are adjacent
	 * to the Vertex named v, empty set if v is not in graph
	 */
	public Iterable<Map.Entry<Vertex, Double>> adjacentTo(String name) {
		if (!hasVertex(name)) {
			return EMPTY_MAP.entrySet();
		}
		return adjList.get(getVertex(name)).entrySet();
	}
	public Iterable<Map.Entry<Vertex, Double>> adjacentTo(Vertex v) {
		if (!adjList.containsKey(v)) {
			return EMPTY_MAP.entrySet();
		}
		return adjList.get(v).entrySet();
	}

	/** @return an Iterator over all Vertices in this Graph */
	public Iterable<Vertex> getVertices() {
		return vertices.values();
	}

	public int numVertices() {
		return vertices.size();
	}

	public int numEdges() {
		return numEdges;
	}

	// Returns adjacency-list representation of graph
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Vertex v : vertices.values()) {
			s.append(v + ": ");
			for (Map.Entry<Vertex, Double> entry : adjList.get(v).entrySet()) {
				s.append(entry.getKey() + " (" + entry.getValue() + ") ");
			}
			s.append("\n");
		}
		return s.toString();
	}

	public List<String> topoSort() {
		List<String> sort = new ArrayList<String>();
		Map<Vertex, Integer> inDegrees = new HashMap<>();

		for (Map.Entry<Vertex, Map<Vertex, Double>> entry : adjList.entrySet()) {
			for (Vertex v : entry.getValue().keySet()) {
				addCount(inDegrees, v);
			}
		}
		
		Queue<Vertex> worklist = new LinkedList<Vertex>();
		for (Map.Entry<Vertex, Map<Vertex, Double>> entry : adjList.entrySet()) {
			if(!inDegrees.containsKey(entry.getKey())) {
				worklist.add(entry.getKey());
			}
		}
		
		while(!worklist.isEmpty()) {
			Vertex next = worklist.remove();
			sort.add(next.toString());
			for(Map.Entry<Vertex, Double> child : adjacentTo(next)) {
				if(removeCount(inDegrees, child.getKey())) {
					worklist.add(child.getKey());
				}
			}
		}
		return sort;
	}

	private static void addCount(Map<Vertex, Integer> map, Vertex v) {
		Integer val = map.get(v);
		if (val == null) {
			map.put(v, 1);
		} else {
			map.put(v, ++val);
		}
	}
	
	private static boolean removeCount(Map<Vertex, Integer> map, Vertex v) {
		Integer val = map.get(v);
		if (val != null) {
			int newVal = --val;
			if(newVal == 0) {
				map.remove(v);
				return true;
			} else {
				map.put(v, newVal);
				return false;
			}
		}
		return false;
	}

	private String escapedVersion(String s) {
		return "\'"+s+"\'";
	}

//	public void outputGDF(String fileName) {
//		HashMap<Vertex, String> idToName = new HashMap<Vertex, String>();
//		try {
//			FileWriter out = new FileWriter(fileName);
//			int count = 0;
//			out.write("nodedef> name,label,style,distance INTEGER\n");
//			// write vertices
//			for (Vertex v: vertices.values())
//			{
//				String id = "v"+ count++;
//				idToName.put(v, id);
//				out.write(id + "," + escapedVersion(v.name));
//				out.write(",6,"+v.distance+"\n");
//			}
//			out.write("edgedef> node1,node2,color\n");
//			// write edges
//			for (Vertex v : vertices.values())
//				for (Vertex w : adjList.get(v))  
//					if (v.compareTo(w) < 0)
//					{
//						out.write(idToName.get(v)+","+ 
//								idToName.get(w) + ",");
//						if (v.predecessor == w || 
//								w.predecessor == v)
//							out.write("blue");
//						else	
//							out.write("gray");
//						out.write("\n");
//					}
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}




	public static void main(String[] args) {
		GraphDirected g = new GraphDirected();

		g.addEdge("142", "143");
		g.addEdge("143", "311");
		g.addEdge("142", "154");
		g.addEdge("311", "312");
		g.addEdge("143", "331");
		g.addEdge("143", "351");
		g.addEdge("351", "333");
		g.addEdge("143", "341");
		g.addEdge("311", "344");
		g.addEdge("311", "369");
		g.addEdge("369", "371");
		g.addEdge("331", "403");
		g.addEdge("332", "403");
		g.addEdge("311", "332");

		System.out.println(g);

		System.out.println(g.topoSort());

		//		g.addEdge("A", "B");
		//		g.addEdge("A", "C");
		//		g.addEdge("C", "D");
		//		g.addEdge("D", "E");
		//		g.addEdge("D", "G");
		//		g.addEdge("E", "G");
		//		g.addVertex("H");
		//		System.out.println(g.escapedVersion("Bacon, Kevin"));
		//		// print out graph
		//		System.out.println(G);
		//
		//		// print out graph again by iterating over vertices and edges
		//		for (Vertex v : g.getVertices()) {
		//			System.out.print(v + ": ");
		//			for (Vertex w : g.adjacentTo(v.name)) {
		//				System.out.print(w + " ");
		//			}
		//			System.out.println();
		//		}
		//		g.outputGDF("graph.gdf");
	}


	/**
	 * A C-style struct definition of a Vertex to be used with
	 * the Graph class.
	 * <p>
	 * The distance field is designed to hold the length of the
	 * shortest unweighted path from the source of the traversal
	 * <p>
	 * The predecessor field refers to the previous field on
	 * the shortest path from the source (i.e. the vertex one edge
	 * closer to the source).
	 *
	 */
	public class Vertex implements Comparable<Vertex> {
		/**
		 * label for Vertex
		 */
		public String name;  
		/**
		 * length of shortest path from source
		 */
		public int distance; 
		/**
		 * previous vertex on path from sourxe
		 */
		public Vertex predecessor; // previous vertex

		/**
		 * a measure of the structural importance of a vertex.
		 * The value should initially be set to zero. A higher
		 * centrality score should mean a Vertex is more central.
		 */
		public double centrality;
		/**
		 * Infinite distance indicates that there is no path
		 * from the source to this vertex
		 */
		public static final int INFINITY = Integer.MAX_VALUE;

		public Vertex(String v)	{
			name = v;
			distance = INFINITY; // start as infinity away
			predecessor = null;
			centrality = 0.0;
		}

		/**
		 * The name of the Vertex is assumed to be unique, so it
		 * is used as a HashCode
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return name.hashCode();
		}

		public String toString() { 
			return name;
		}
		/**
		 * Compare on the basis of distance from source first and 
		 * then lexicographically
		 */
		public int compareTo(Vertex other) {
			int diff = distance - other.distance;
			if (diff != 0)
				return diff;
			else
				return name.compareTo(other.name);
		}
	}


}