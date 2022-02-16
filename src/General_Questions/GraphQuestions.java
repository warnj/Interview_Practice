package General_Questions;

import java.util.*;

public class GraphQuestions {

    // https://leetcode.com/problems/minimum-height-trees/
    // This is a tree, so if V = n, then E = n-1
    // best memory representation, instead of a bool[][] with wasted space, used List<HashSet<Bool>>
    // otherwise same impl as the boolean[][] below
    public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (edges.length == 0) return Collections.singletonList(0);

        List<Set<Integer>> graph = new ArrayList<>(n); // graph[n] contains set of connected nodes
        for (int i = 0; i < n; i++) graph.add(new HashSet<>());
        for (int[] edge : edges) {
            // undirected, so set both coords as true
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) { // look at all nodes
            if (graph.get(i).size() == 1) leaves.add(i);
        }
        // topological sort
        while (n > 2) { // end condition is always 1 or 2 nodes in the middle, if the longest chain is even = 2, odd = 1
            n -= leaves.size();
            List<Integer> newLeaves = new ArrayList<>();
            for (int leaf : leaves) {
                Set<Integer> connection = graph.get(leaf); // set size always 1
                int c = connection.iterator().next(); // c is the single node that the leaf was connected to
                // connection.remove(c); // empty the set, actually not needed for the algo to work since already know this is a leaf

                Set<Integer> connectionConnections = graph.get(c);
                connectionConnections.remove(leaf); // undirected, so c also had a connection to leaf that is now gone
                if (connectionConnections.size() == 1) { // c may now be a leaf
                    newLeaves.add(c);
                }
            }
            leaves = newLeaves;
        }
        return leaves;
    }
    // space = O(n^2), time = O(e + n^2 + n^2) = O(n^2) - hits memory limit
    // with a different graph representation runtime could get down to O(n)
    public static List<Integer> findMinHeightTreesBoolArray(int n, int[][] edges) {
        if (edges.length == 0) {
            List<Integer> result = new ArrayList<>(1);
            result.add(0);
            return result;
        }

        boolean[][] graph = new boolean[n][n];
        for (int[] edge : edges) {
            // undirected, so set both coords as true
            graph[edge[0]][edge[1]] = true;
            graph[edge[1]][edge[0]] = true;
        }
        // start pointers at leaves, move inward, when two pointers meet, merge them, continue until 1 or 2 pointers
        // remain, this is the answer, same as topological sort

        Map<Integer, Integer> leaves = new HashMap<>(); // map a leaf to its single connection
        for (int i = 0; i < n; i++) { // look at all nodes
            int connection = isLeaf(graph, i);
            if (connection != -1) {
                leaves.put(i, connection);
            }
        }
        // topological sort
        while (!connected(leaves)) { // end condition is always 1 or 2 nodes in the middle, if the longest chain is even = 2, odd = 1
            Iterator<Map.Entry<Integer,Integer>> itr = leaves.entrySet().iterator();
            Map<Integer, Integer> temp = new HashMap<>(); // used to avoid adding to the map we are iterating through, concurrent modification exception
            while (itr.hasNext()) {
                Map.Entry<Integer,Integer> e = itr.next();
                int leaf = e.getKey();
                int connection = e.getValue();
                itr.remove(); // need iterator remove; foreach loop + remove call hits ConcurrentModificationException
                // remove the leaf, check if connection is a leaf or not
                graph[leaf][connection] = false;
                graph[connection][leaf] = false;
                int remainingConnection = isLeaf(graph, connection);
                if (remainingConnection != -1) {
                    temp.put(connection, remainingConnection);
                }
            }
            leaves.putAll(temp);
        }
        return new ArrayList<>(leaves.keySet());
    }
    // returns true if map contains one entry or if it contains two entries and the entries are connected
    private static boolean connected(Map<Integer, Integer> m) {
        if (m.size() == 1) return true;
        if (m.size() > 2) return false;
        int firstKey = m.keySet().iterator().next();
        int firstKeyConnection = m.get(firstKey);
        return m.containsKey(firstKeyConnection);
    }
    // returns the single connection to n if n is a leaf node (only one connection), otherwise -1
    private static int isLeaf(boolean[][] graph, int n) {
        int connection = -1;
        for (int i = 0; i < graph.length; i++) { // look at all possible connections to node n
            if (graph[n][i]) {
                if (connection == -1) { // might be a leaf
                    connection = i;
                } else { // multiple connections are present, not a leaf
                    return -1;
                }
            }
        }
        return connection;
    }

    // runtime = O(n^2 dfs * n nodes) = O(n^3) and space = O(n^2 + n) = O(n^2)
    public static List<Integer> findMinHeightTreesBrute(int n, int[][] edges) {
        // pick each node to start from
        // do a dfs, recording longest path
        // save and return the smallest paths (min heights)

        // may be able to optimize the node selection: instead of doing a dfs on every starting node, traverse graph
        // once and find the longest path, then return the node that's in the middle of it.

        // trying a different graph impl to get familiar with it and since given n, it's easy to create initially, O(V^2) space
        // likely to used less memory with more classic O(V+E) space Map<Node, List> impl.
        boolean[][] graph = new boolean[n][n];
        for (int[] edge : edges) {
            // undirected, so set both coords as true
            graph[edge[0]][edge[1]] = true;
            graph[edge[1]][edge[0]] = true;
        }

        int minHeight = Integer.MAX_VALUE;
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // i = node that we are measuring the height from
            int height = dfsHeight(i, graph, new boolean[n]);
            if (height < minHeight) {
                results.clear();
                results.add(i);
                minHeight = height;
            } else if (height == minHeight) {
                results.add(i);
            }
        }
        return results;
    }
    private static int dfsHeight(int start, boolean[][] graph, boolean[] visited) {
        visited[start] = true;
        int maxChild = 1; // height = number of nodes in the longest path.
        for (int i = 0; i < graph.length; i++) {
            // look at the child nodes (i) that start is connected to. If start has no child nodes, return height of 1
            // could also go across graph[i][start], graph is symmetrical down diagonal
            if (graph[start][i] && !visited[i]) {
                int height = 1 + dfsHeight(i, graph, visited);
                if (height > maxChild) {
                    maxChild = height;
                }
            }
        }
        return maxChild;
    }


    // https://leetcode.com/problems/course-schedule
    // detect cycle in directed graph - use topological sort, Runtime = O(V+E), space = O(V+E)
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        // build a directed graph, if no cycles return true, otherwise false
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>(); // could use array of size numCourses
        for (int[] edge : prerequisites) {
            int source = edge[1];
            int dest = edge[0];
            inDegree.merge(dest, 1, Integer::sum);
            List<Integer> connections = graph.get(source);
            if (connections == null) {
                connections = new ArrayList<>();
                connections.add(dest);
                graph.put(source, connections);
            } else {
                connections.add(dest);
            }
        }
        // traverse graph, starting from root nodes, remove them making more roots
        // when working from root nodes, entire graph should be traversable (topological sort) with no cycles present
        Queue<Integer> roots = new LinkedList<>();
        for (Integer n : graph.keySet()) {
            if (inDegree.get(n) == null) roots.add(n);
        }
        int visits = 0;
        while (!roots.isEmpty()) {
            Integer root = roots.remove();
            visits++;

            List<Integer> children = graph.get(root);
            if (children == null) continue;
            for (Integer child : children) {
                if (graph.containsKey(child)) {
                    Integer degs = inDegree.get(child);
                    if (degs == 1) { // never null since children always start with at least one incoming edge
                        inDegree.remove(child);
                        roots.add(child);
                    } else {
                        inDegree.put(child, degs - 1);
                    }
                }
            }
        }
        return visits == graph.size();
    }
    // Runtime = O(V+E), Space = O(V+E)
    public static boolean canFinishDFS(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int[] edge : prerequisites) {
            int source = edge[1];
            int dest = edge[0];
            inDegree.merge(dest, 1, Integer::sum);
            List<Integer> connections = graph.get(source);
            if (connections == null) {
                connections = new ArrayList<>();
                connections.add(dest);
                graph.put(source, connections);
            } else {
                connections.add(dest);
            }
        }
        // traverse graph, starting from root nodes
        Queue<Integer> roots = new LinkedList<>();
        for (Integer n : graph.keySet()) {
            if (inDegree.get(n) == null) roots.add(n);
        }
//		System.out.println("In degree 0: " + roots);
        // when working from root nodes, entire graph should be traversable and no cycles should be present
        boolean[] recursionStack = new boolean[numCourses];
        boolean[] visited = new boolean[numCourses];
        while (!roots.isEmpty()) {
            Integer root = roots.remove();
//			System.out.println("looking at root: " + root);
            // do DFS on each root node
            if (isCyclicUtil(root, visited, recursionStack, graph)) {
                return false;
            }
        }
        int visits = 0;
        for (int i = 0; i < numCourses; i++) {
            if (visited[i]) visits++;
        }
//		System.out.printf("Visits: %d Size: %d\n", visits, graph.size());
        return visits == graph.size();
    }
    // https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
    private static boolean isCyclicUtil(int i, boolean[] visited, boolean[] stack, Map<Integer, List<Integer>> g) {
        // Mark the current node as visited and part of recursion stack
//		System.out.println("Checking: " + i);
        if (stack[i]) return true;
        if (visited[i]) return false;
        List<Integer> children = g.get(i);
        if (children == null) return false;
        visited[i] = true;
        stack[i] = true;
        for (Integer c: children)
            if (isCyclicUtil(c, visited, stack, g))
                return true;
        stack[i] = false;
        return false;
    }

}
