package General_Questions;
// An IntSearchTree object represents an entire binary search tree of ints.
// Class invariant:
//    - nodes to the left of a root have smaller values
//    - nodes to the right of a root have larger values
//    - there are no duplicates

import java.util.*;

public class IntTree {	
	private TreeNode overallRoot;

	// Constructs an empty binary tree.
	public IntTree() {
		overallRoot = null;
	}

	// Constructs a binary tree with the given node as its root.
	// Note: this is hacky and just useful for testing
	public IntTree(TreeNode root) {
		overallRoot = root;
	}
	public IntTree(String repr) {
		overallRoot = deserialize(repr);
	}

	// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/
	public IntTree(int[] pre, int[] in) {
		overallRoot = buildTree(pre, 0, in, 0, in.length-1);
	}
	private TreeNode buildTree(int[] pre, int preStart, int[] in, int inStart, int inEnd) {
		if (preStart >= pre.length || inStart > inEnd) return null;
		TreeNode node = new TreeNode(pre[preStart]);
		int inIndex = 0;
		for (int i = inStart; i <= inEnd; i++) {
			if (in[i] == pre[preStart]) {
				inIndex = i; // found the index of this new node in the inorder traversal
				break;
			}
		}
		// left node is just next in the preorder
		node.left = buildTree(pre, preStart+1, in, inStart, inIndex-1);
		// next right node in preorder is the number of nodes in the left subtree away, which = inIndex - inStart
		node.right = buildTree(pre, preStart + 1 + inIndex - inStart, in, inIndex+1, inEnd);
		return node;
	}

	// Returns true if the value is in this tree, false otherwise.
	public boolean contains(int value) {
		return contains(overallRoot, value);
	}	
	// Returns true if the value is in the tree starting at the specified root.
	private boolean contains(TreeNode root, int value) {
		if (root == null) {
			return false;
		} else if(root.val == value) {
			return true;
		} else if(root.val > value) {
			return contains(root.left, value);
		} else {
			return contains(root.right, value);
		}
	}

	// Adds the given value to this BST.
	// Post: the tree is still a valid BST
	public void add(int value) {
		overallRoot = add(overallRoot, value);
	}	
	// Adds the given value to the BST starting at the given root.
	// Post: the tree is still a valid BST
	private TreeNode add(TreeNode root, int value) {
		if (root == null) {
			root = new TreeNode(value);
		} else if (root.val > value) {
			root.left = add(root.left, value);
		} else if (root.val < value) {
			root.right = add(root.right, value);
		}
		return root;
	}

	public int getMin() {
		if (overallRoot == null) throw new NoSuchElementException();
		return getMin(overallRoot);
	}	
	private int getMin(TreeNode root) {
		if (root.left == null) {
			return root.val;
		} else {
			return getMin(root.left);
		}
	}

	// Removes the node with the given value from this BST.
	public void remove(int value) {
		overallRoot = remove(overallRoot, value);
	}	
	// Removes the node with the given value from the given tree.
	private TreeNode remove(TreeNode root, int value) {
		if (root == null) {
			return null;
		} else if (root.val < value) {
			root.right = remove(root.right, value);
		} else if (root.val > value) {
			root.left = remove(root.left, value);
		} else { // found it! - root.val == value
			// (not necessary to have as a separate case!)
			if(root.left == null && root.right == null) {
				root = null;
			} else if(root.right == null) {
				return root.left;
			} else if(root.left == null) {
				return root.right;
			} else {
				int min = getMin(root.right);
				root.val = min;
				root.right = remove(root.right, min);
			}
		}
		return root;
	}

	public void removeLeafs() {
		removeLeafs(overallRoot);
	}	
	private TreeNode removeLeafs(TreeNode root) {
		if (root != null) {
			if (root.left == null && root.right == null) return null;
			else {
				root.left = removeLeafs(root.left);
				root.right = removeLeafs(root.right);
			}
		}
		return root;
	}

	// Prints a pre-order traversal of this tree.
	public void printPreorder() {
		printPreorder(overallRoot);
		System.out.println();
	}	
	// Prints a pre-order traversal of the tree starting at the specified root
	private void printPreorder(TreeNode root) {
		if (root != null) {
			System.out.print(root.val + " ");
			printPreorder(root.left);
			printPreorder(root.right);
		}
	}

	// Prints an in-order traversal of this tree.
	public void printInorder() {
		printInorder(overallRoot);
		System.out.println();
	}	
	// Prints an in-order traversal of the tree starting at the specified root
	private void printInorder(TreeNode root) {
		if (root != null) {
			printInorder(root.left);
			System.out.print(root.val + " ");
			printInorder(root.right);
		}
	}

	// Prints a post-order traversal of this tree.
	public void printPostorder() {
		printPostorder(overallRoot);
		System.out.println();
	}	
	// Prints a post-order traversal of the tree starting at the specified root
	private void printPostorder(TreeNode root) {
		if (root != null) {
			printPostorder(root.left);
			printPostorder(root.right);
			System.out.print(root.val + " ");
		}
	}


	/******** My Added Methods ***************************************************************************************************************/

	// this is just DFS
	public void printPreorderIterative() {
		if (overallRoot == null) {
			System.out.println("null");
			return;
		}
		Stack<TreeNode> s = new Stack<>();
		s.push(overallRoot);
		while (!s.isEmpty()) {
			TreeNode cur = s.pop();
			System.out.print(cur.val + " ");
			if (cur.right != null) s.push(cur.right);
			if (cur.left != null) s.push(cur.left); // left child on top of stack
		}
		System.out.println();
	}

	public void printInorderIterative() {
		Stack<TreeNode> s = new Stack<>();
		TreeNode start = overallRoot;
		// start at bottom left
		while (start != null) {
			s.push(start);
			start = start.left;
		}
		while (!s.isEmpty()) {
			TreeNode cur = s.pop();
			System.out.print(cur.val + " ");
			TreeNode temp = cur.right;
			while (temp != null) {
				s.push(temp);
				temp = temp.left;
			}
		}
		System.out.println();
	}

	public void printPostorderIterative() {
		Stack<TreeNode> s = new Stack<>();
		s.push(overallRoot);
		TreeNode temp = overallRoot; // the last node printed out,
		// initialize with overallRoot since setting to null can trigger finishedSubtrees to be initially true when it shouldn't be
		while(!s.isEmpty()) {
			TreeNode next = s.peek();

			boolean finishedSubtrees = (next.right == temp || next.left == temp);
			boolean isLeaf = (next.left == null && next.right == null);
			if (finishedSubtrees || isLeaf) {
				s.pop();
				System.out.print(next.val + " ");
				temp = next;
			} else {
				if (next.right != null) {
					s.push(next.right);
				}
				if (next.left != null) {
					s.push(next.left); // want the left child on top of stack
				}
			}
		}
		System.out.println();
	}

	// this is just BFS
	public void printLevelOrder() {
		if (overallRoot != null) {
			Queue<TreeNode> q = new LinkedList<>();
			q.add(overallRoot);
			while (!q.isEmpty()) {
				TreeNode n = q.remove();
				System.out.print(n.val + " ");
				if (n.left != null) q.add(n.left);
				if (n.right != null) q.add(n.right);
			}
		}
	}

	// returns list of the val at each level of the tree, top to bottom, left to right
	// https://leetcode.com/problems/binary-tree-level-order-traversal
	public List<List<Integer>> levelOrder() {
		List<List<Integer>> allLevels = new LinkedList<>();
		if (overallRoot == null) return allLevels;

		Queue<TreeNode> q = new LinkedList<>(); // for BFS
		q.add(overallRoot);
		while (!q.isEmpty()) {
			int levelNum = q.size(); // everything in the q currently is on the same level of tree
			List<Integer> oneLevel = new LinkedList<>();
			for (int i = 0; i < levelNum; i++) { // add their children, which will be on same level as each other
				TreeNode n = q.remove();
				if (n.left != null) q.add(n.left);
				if (n.right != null) q.add(n.right);
				oneLevel.add(n.val);
			}
			allLevels.add(oneLevel);
		}
		return allLevels;
	}

	// Given a binary tree, flatten it to a linked list in-place.
	// https://leetcode.com/problems/flatten-binary-tree-to-linked-list
	public void flatten() {
		flatten(overallRoot);
	}
	private TreeNode prev = null;
	private void flatten(TreeNode root) {
		if (root != null) { // THIS IS THE MOST HARD TO WRITE 5 LINES OF CODE EVER
			flatten(root.right);
			flatten(root.left);
			root.right = prev;
			root.left = null; // singly linked list
			prev = root;
		}
	}
	public void flattenIterative() {
		if (overallRoot == null) return;
		Stack<TreeNode> s = new Stack<>();
		s.push(overallRoot);
		while (!s.isEmpty()) {
			TreeNode cur = s.pop(); // cur is the end of the list as we build it
			if (cur.right != null) // want right side at end of linked list, so put on bottom of stack by adding first
				s.push(cur.right);
			if (cur.left != null) 
				s.push(cur.left);
			if (!s.isEmpty()) // connect end of list to next node 
				cur.right = s.peek();
			cur.left = null; // singly linked list
		}
	}
	public void flattenEasy() {
		List<TreeNode> pre = new ArrayList<>();
		getPreorder(pre, overallRoot);
		for (int i = 0; i < pre.size()-1; i++) {
			TreeNode cur = pre.get(i);
			TreeNode next = pre.get(i+1);
			cur.left = null;
			cur.right = next;
			if (i == pre.size()-2) {
				next.left = null;
			}
		}
	}
	private void getPreorder(List<TreeNode> nodes, TreeNode cur) {
		if (cur != null) {
			nodes.add(cur);
			getPreorder(nodes, cur.left);
			getPreorder(nodes, cur.right);
		}
	}

	// LCA of BST: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree
	public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
		int max = Math.max(p.val, q.val);
		int min = Math.min(p.val, q.val);
		if (root.val > max) return lowestCommonAncestor(root.left, p, q);
		if (root.val < min) return lowestCommonAncestor(root.right, p, q);
		return root;
	}

	// returns the least-valued common ancestor of the two given nodes. A node can be a descendant of itself.
	public int lca(TreeNode root, int v1, int v2) {
		TreeNode lca = getLca(root, v1, v2);
		if (lca != null) {
			return lca.val;
		} else {
			return Integer.MIN_VALUE;
		}
	}
	// LCA for any tree recursive: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree
	private TreeNode getLca(TreeNode root, int v1, int v2) {
		// look for the two target nodes (v1 and v2)
		if (root == null) return null;
		if (root.val == v1 || root.val == v2) return root;
		TreeNode leftVal = getLca(root.left, v1, v2);
		TreeNode rightVal = getLca(root.right, v1, v2);

		if (leftVal != null && rightVal != null) return root; // this node is the ancestor
		if (leftVal != null) return leftVal; // the ancestor was found in the left subtree
		else return rightVal; // the ancestor was found (or not found) in the right subtree
	}
	// iterative impl of the above. O(n + height(root)) time and space
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		Map<TreeNode,TreeNode> parents = new HashMap<>(); // parent pointers: node -> parent
		// bfs to build the map
		Queue<TreeNode> worklist = new LinkedList<>();
		worklist.add(root);
		while (!worklist.isEmpty()) {
			TreeNode cur = worklist.remove();
			if (cur.left != null) {
				parents.put(cur.left, cur);
				worklist.add(cur.left);
			}
			if (cur.right != null) {
				parents.put(cur.right, cur);
				worklist.add(cur.right);
			}
		}
		// start at p & q and traverse up
		Set<TreeNode> seen = new HashSet<>();
		TreeNode cur = p;
		seen.add(cur);
		while (parents.containsKey(cur)) {
			seen.add(parents.get(cur));
			cur = parents.get(cur);
		}
		cur = q;
		if (seen.contains(cur)) return cur;
		while (parents.containsKey(cur)) {
			if (seen.contains(parents.get(cur))) {
				return parents.get(cur);
			}
			cur = parents.get(cur);
		}
		return root; // root not part of parents
	}

	public int size() {
		return size(overallRoot);
	}	
	private int size(TreeNode root){
		if(root != null) {
			return 1 + size(root.left) + size(root.right);
		}
		return 0;
	}

	// https://leetcode.com/problems/diameter-of-binary-tree
	// O(n) time and O(H) space
	private int maxDiam=0;
	public int diameterOfBinaryTree(TreeNode root) {
		heightDiam(root);
		return maxDiam;
	}
	// return the height of given node and save max while doing it to avoid nested recursion and duplicate effort
	private int heightDiam(TreeNode root) {
		if (root==null) return 0;
		int left = heightDiam(root.left);
		int right = heightDiam(root.right);
		maxDiam = Math.max(left+right, maxDiam);
		return Math.max(left,right)+1;
	}

	// if k=1, then returns the min value, if k=2 returns the second smallest val....
	// if k = this.size() returns the max value
	// Iterative in-order traversal with a counter that decrements as look at a node. O(n) time
	public int kthSmallest(int k) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode p = overallRoot;

		while(!stack.isEmpty() || p != null){
			if(p != null){
				stack.push(p);
				p = p.left; // add nodes to stack as go down & left as far as possible
			} else {
				// hit the lower left of a subtree
				TreeNode t = stack.pop(); // the parent node going back up tree (node added before fell off the tree)
				k--;
				if(k==0) return t.val;
				p = t.right; // nowhere left to go or already done the left, so look to right
			}
		}
		return Integer.MIN_VALUE; // k out of range (k > size or k <= 0)
	}

	// recursive in-order traversal with counter. O(n)
	public int kthSmallestRecursive(int k) {
		return kthSmallest(overallRoot, k);
	}    
	private int kthSmallest(TreeNode root, int k) {
		if (root == null) return Integer.MIN_VALUE;

		int result = kthSmallest(root.left, k);

		if (--k == 0) return root.val;

		int result2 = kthSmallest(root.right, k);
		return Math.max(result, result2);
	}
	// https://leetcode.com/problems/kth-smallest-element-in-a-bst/submissions/
	int globalK;
	public int kthSmallest3(TreeNode root, int k) {
		globalK = k;
		return inorder(root);
	}
	public Integer inorder(TreeNode root) {
		if (root == null) return null;
		Integer left = inorder(root.left);
		if (left != null) return left;

		if (globalK-- == 1) {
			return root.val;
		}
		return inorder(root.right);
	}

	// recursive version using the sizes of subtrees
	public int kthSmallestRecursive2(int k) {
		if (overallRoot == null) return Integer.MIN_VALUE;
		return kthSmallest2(overallRoot, k);
	}    
	public int kthSmallest2(TreeNode root, int k) {
		int leftNodes = size(root.left);
		if (k <= leftNodes) {
			return kthSmallest(root.left, k);
		} else if (k > leftNodes + 1) {
			return kthSmallest(root.right, k-1-leftNodes); // remove the left side of the tree & the parent (root) from k to find target in root.right's subtree
		} else { // k = leftNodes + 1
			return root.val;
		}
	}

	// Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
	// An example is the root-to-leaf path 1->2->3 which represents the number 123.
	// Find the total sum of all root-to-leaf numbers.
	public int sumNumbers() {
		return sumNumbers(overallRoot, 0);
	}
	private int sumNumbers(TreeNode root, int sum) { // sum is the intermediate number from concatenating the parent numbers above root
		if (root == null) return 0;
		if (root.left == null && root.right == null) return sum*10 + root.val;
		// working from top of tree down, so shift existing sum (multiply by 10) add root.val to sum
		return sumNumbers(root.left, sum*10 + root.val) + sumNumbers(root.right, sum*10 + root.val);
	}

	// https://leetcode.com/problems/path-sum-ii/
	public List<List<Integer>> pathSum(int targetSum) {
		List<List<Integer>> paths = new ArrayList<>();
		if (overallRoot == null) return paths;
		pathSum(paths, new ArrayList<>(), overallRoot, overallRoot.val, targetSum);
		return paths;
	}
	public void pathSum(List<List<Integer>> paths, List<Integer> path, TreeNode n, int sum, int targetSum) {
		path.add(n.val);
		if (n.left == null && n.right == null && sum == targetSum) { // is a leaf with target sum
			paths.add(new ArrayList<>(path));
		} else {
			if (n.left != null) // check out left side if it may sum to targetSum
				pathSum(paths, path, n.left, sum + n.left.val, targetSum);
			if (n.right != null) // explore right side
				pathSum(paths, path, n.right, sum + n.right.val, targetSum);
		}
		path.remove(path.size()-1);
	}

	// returns true if tree has a root to leaf path that adds to equal sum
	public boolean hasPathSum(int sum) {
		return hasPathSum(overallRoot, sum);
	}
	private boolean hasPathSum(TreeNode root, int sum) {
		if (root != null) {
			sum -= root.val;
			if(sum == 0 && root.left == null && root.right == null) {
				return true;
			}

			boolean answer = false;
			if (root.left != null) {
				answer = hasPathSum(root.left, sum);
			} 
			if (root.right != null){
				answer = answer || hasPathSum(root.right, sum);
			}
			return answer;
		}
		return sum == 0;
	}

	// https://leetcode.com/problems/balanced-binary-tree
	// naive O(n^2) time and O(H) stack space
	public boolean isBalanced(TreeNode root) {
		if (root != null) {
			int l = height(root.left);
			int r = height(root.right);
			if (Math.abs(l-r) > 1) return false;
			return isBalanced(root.left) && isBalanced(root.right);
		}
		return true;
	}
	// best 0(N) time and 0(H) space, where H is the height of the tree
	public boolean isBalanced() {
		return checkHeight(overallRoot) != -1;
	}
	private int checkHeight(TreeNode root) {
		if(root == null) return 0;

		int leftHeight = checkHeight(root.left);
		if(leftHeight == -1) return -1;
		int rightHeight = checkHeight(root.right);
		if(rightHeight == -1) return -1;

		if (Math.abs(leftHeight - rightHeight) > 1) {
			return -1;
		} else {
			return 1 + Math.max(leftHeight, rightHeight);
		}
	}

	public void removeEvens() {
		overallRoot = removeEvens(overallRoot);
	}
	private TreeNode removeEvens(TreeNode root) {
		if(root == null) return null;
		root.left = removeEvens(root.left);
		root.right = removeEvens(root.right);

		if (root.val % 2 == 0) {
			if(root.left == null) {
				return root.right;
			} else if (root.right == null) {
				return root.left;
			} else { // both children are non-null
				int val = getMin(root.right);
				remove(root.right, val);
				root.val = val;
			}
		}
		return root;
	}

	public int secondSmallest() {
		return secondSmallest(overallRoot);
	}
	private int secondSmallest(TreeNode node) {
		if(node == null) {
			throw new IllegalArgumentException();
		} else if(node.left == null) {
			return getMin(node.right);
		} else if (node.left.left == null && node.left.right == null) {
			return node.val; // there is exactly one leaf node less than current node
		} else {
			return secondSmallest(node.left);
		}
	}

	// return the length of the longest path between any two nodes in a tree
	// https://leetcode.com/problems/diameter-of-binary-tree/description/
	public int diameter() {
		return diameter(overallRoot);
	}
	private int diameter(TreeNode root) {
		if (root == null) return 0;
		int heightLeft = height(root.left);
		int heightRight = height(root.right);
		return Math.max(diameter(root.right), Math.max(diameter(root.left), heightLeft + heightRight));
	}

	// https://leetcode.com/problems/maximum-width-of-binary-tree/
	// recursive solution tricky; sum of the widths of left and right don't work since they may have been on different levels
	// level-order traversal / bfs seems best; tricky part is how to count any nulls in the middle
	// want: left most node on each level, right most node on each level & distance between them
	// use hashmap to save the corresponding position of each node if the tree was implemented as an array
	// O(n) time and space where n is number of nodes in the tree
	public int widthOfBinaryTree() {
		if (this.overallRoot == null) return 0;
		Map<TreeNode, Integer> positions = new HashMap<>();
		Queue<TreeNode> workList = new LinkedList<>();
		workList.add(overallRoot);
		positions.put(overallRoot, 1);
		int maxWidth = 1; // root non-null is width of 1
		while (!workList.isEmpty()) {
			int levelSize = workList.size();
			// check nodes in current level
			int startPosition = 0; // array representation index of first node in the row
			for (int i = 0; i < levelSize; i++) {
				TreeNode cur = workList.remove();
				int curPosition = positions.get(cur);
				if (i == 0) {
					startPosition = curPosition;
				}
				if (cur.left != null) {
					workList.add(cur.left);
					positions.put(cur.left, 2*curPosition);
				}
				if (cur.right != null) {
					workList.add(cur.right);
					positions.put(cur.right, 2*curPosition+1);
				}
				if (i == levelSize-1) { // last node in the row, find the width of this level
					int width = curPosition - startPosition + 1; // need +1 to also count the first node i.e. [1,2,3] -> 3-1 = 2 but there are 3 nodes
					maxWidth = Math.max(maxWidth, width);
				}
			}
		}
		return maxWidth;
	}

	public boolean isSymmetric() {
		if (overallRoot == null) return true;
		Stack<TreeNode> left = new Stack<>(); // could also use queue, could also use single datastructure and push/pop two at a time
		Stack<TreeNode> right = new Stack<>();
		left.add(overallRoot.left);
		right.add(overallRoot.right);
		while (!left.isEmpty() && !right.isEmpty()) {
			TreeNode l = left.pop();
			TreeNode r = right.pop();
			if (l == null && r == null) continue;
			if (l == null || r == null || l.val != r.val) return false;
			left.add(l.left); // from root node, compare the left child lefts with right child rights
			right.add(r.right);
			left.add(l.right);
			right.add(r.left);
		}
		return right.isEmpty() && left.isEmpty();
	}

	// https://leetcode.com/problems/binary-tree-paths
	public List<String> binaryTreePaths() {
		List<String> result = new ArrayList<>();
		if (overallRoot == null) return result;
		if (overallRoot.left == null && overallRoot.right == null) {
			result.add(String.valueOf(overallRoot.val));
			return result;
		}
		StringBuilder sb = new StringBuilder(""+overallRoot.val);
		if (overallRoot.left != null) findLeaf(overallRoot.left, result, sb);
		if (overallRoot.right != null) findLeaf(overallRoot.right, result, sb);
		return result;
	}
	private void findLeaf(TreeNode n, List<String> result, StringBuilder path) {
		String s = "->" + n.val;
		path.append(s);
		if (n.left == null && n.right == null) {
			result.add(path.toString());
		} else {
			if (n.left != null) findLeaf(n.left, result, path);
			if (n.right != null) findLeaf(n.right, result, path);
		}
		path.delete(path.length()-s.length(), path.length());
	}

	// https://leetcode.com/problems/same-tree
	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null || q == null) return p == q;

		// do iterative dfs on both at same time
		Stack<TreeNode> s = new Stack<>();
		s.add(p);
		s.add(q);
		while (!s.isEmpty()) {
			TreeNode qN = s.pop();
			TreeNode pN = s.pop();
			if (pN.val != qN.val) {
				return false;
			}
			if (pN.left != null) {
				if (qN.left == null) return false;
				s.add(pN.left);
				s.add(qN.left);
			} else {
				if (qN.left != null) return false;
			}
			if (pN.right != null) {
				if (qN.right == null) return false;
				s.add(pN.right);
				s.add(qN.right);
			} else {
				if (qN.right != null) return false;
			}
		}
		return true;
	}
	public boolean isSameTreeR(TreeNode p, TreeNode q) {
		if (p == null || q == null) return p == q;
		if (p.val != q.val) return false;
		return isSameTreeR(p.left, q.left) && isSameTreeR(p.right, q.right);
	}

	// https://leetcode.com/problems/invert-binary-tree
	public TreeNode invertTree(TreeNode root) {
		if (root != null) {
			TreeNode temp = invertTree(root.left);
			root.left = invertTree(root.right);
			root.right = temp;
		}
		return root;
	}

	// https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
	// fails for large test cases with unbalanced tree (height > 32)!
	// Encodes a tree to a single string.
	public String serialize() {
		if (overallRoot == null) return "null, null";
		// convert tree to array representation
		int height = height(overallRoot);
		int size = 1 << height; // 2^height is 1 more than the number of elements in the tree
//		System.out.println("Using size: " + size);
		Integer[] a = new Integer[size]; // null = null node, 0 = node with val 0
		// bfs traversal saving vals to corresponding index
		Queue<TreeNode> q = new LinkedList<>();
		Queue<Integer> indexes = new LinkedList<>();
		q.add(overallRoot);
		indexes.add(1);
		while (!q.isEmpty()) {
			TreeNode n = q.remove();
			Integer index = indexes.remove();
			a[index] = n.val;
			if (n.left != null) {
				q.add(n.left);
				indexes.add(index * 2);
			}
			if (n.right != null) {
				q.add(n.right);
				indexes.add(index * 2 + 1);
			}
		}
		String result = Arrays.toString(a);
		return result.substring(1, result.length()-1);
	}
	// Decodes your encoded data to tree.
	public TreeNode deserialize(String data) {
		// convert data to array representation of tree
		String[] nums = data.split(", ");
		Integer[] a = new Integer[nums.length];
		// String[] to Integer[]
		for (int i = 0; i < nums.length; i++) {
			if (!nums[i].equals("null")) {
				a[i] = Integer.parseInt(nums[i]);
			}
		}
		// convert array to tree
		if (a[1] == null) return null;
		TreeNode root = new TreeNode(a[1]);
		Queue<TreeNode> q = new LinkedList<>();
		Queue<Integer> indexes = new LinkedList<>();
		q.add(root);
		indexes.add(1);
		while (!q.isEmpty()) {
			TreeNode n = q.remove(); // parent node, is already part of the tree
			int index = indexes.remove(); // parent index
			int leftChild = 2*index;
			if (leftChild < a.length && a[leftChild] != null) {
				n.left = new TreeNode(a[leftChild]);
				q.add(n.left);
				indexes.add(leftChild);
			}
			int rightChild = 2*index+1;
			if (rightChild < a.length && a[rightChild] != null) {
				n.right = new TreeNode(a[rightChild]);
				q.add(n.right);
				indexes.add(rightChild);
			}
		}
		return root;
	}

	// https://leetcode.com/problems/binary-tree-right-side-view
	// level order traversal, add the ending of each level
	// O(n) time and space - should be able to get this down to O(log(n)) with better solution
	public List<Integer> rightSideView() {
		List<Integer> result = new ArrayList<>();
		Queue<TreeNode> worklist = new LinkedList<>();
		worklist.add(overallRoot);
		while (!worklist.isEmpty()) {
			int levelSize = worklist.size();
			for (int i = 0; i < levelSize; i++) {
				TreeNode n = worklist.remove();
				if (n.left != null) worklist.add(n.left);
				if (n.right != null) worklist.add(n.right);
				if (i == levelSize-1) {
					result.add(n.val);
				}
			}
		}
		return result;
	}
	public List<Integer> rightSideViewRecursive() {
		List<Integer> result = new ArrayList<>();
		rightView(overallRoot, result, 0);
		return result;
	}
	private void rightView(TreeNode cur, List<Integer> result, int depth) {
		if (cur == null) return;
		if (depth == result.size()) result.add(cur.val);
		rightView(cur.right, result, depth + 1);
		rightView(cur.left, result, depth + 1);
	}

	// https://leetcode.com/problems/count-complete-tree-nodes/
	public int countNodes() {
		return countNodes(overallRoot);
	}
	private int countNodes(TreeNode n) {
		int leftDepth = leftDepth(n);
		int rightDepth = rightDepth(n);
		// one side will always be full, so runtime is O(log(n) * log(n))
		if (leftDepth == rightDepth)
			return (1 << leftDepth) - 1; // size of a full binary tree = 2^depth - 1
		else
			return 1 + countNodes(n.left) + countNodes(n.right);
	}
	private int rightDepth(TreeNode root) {
		int dep = 0;
		while (root != null) {
			root = root.right;
			dep++;
		}
		return dep;
	}
	private int leftDepth(TreeNode root) {
		int dep = 0;
		while (root != null) {
			root = root.left;
			dep++;
		}
		return dep;
	}

	/******** Practice-It Methods *****************************************************************************************************************/

	// removes the nodes in the tree not between min and max inclusive. Tree must be a BST
	public void trimRange(int min, int max) {
		overallRoot = trimRange(overallRoot, min, max);
	}	
	private TreeNode trimRange(TreeNode root, int min, int max) {
		if(root != null) {
			if(root.val > max) {
				root = trimRange(root.left, min, max);
			} else if (root.val < min) {
				root = trimRange(root.right, min, max);
			}
			if(root != null) {
				root.left = trimRange(root.left, min, max);
				root.right = trimRange(root.right, min, max);
			}
		}
		return root;
	}

	public int height() {
		return height(overallRoot);
	}	
	private int height(TreeNode root) {
		if (root == null) return 0;
		return 1 + Math.max(height(root.left), height(root.right));
	}

	public boolean isBST() {
		return checkBST(overallRoot, Long.MIN_VALUE, Long.MAX_VALUE);
	}	
	private boolean checkBST(TreeNode n, long min, long max) {
		if(n == null) {
			return true;
		} else if (n.val <= min || n.val >= max) {
			return false;
		} else {
			return checkBST(n.left, min, n.val) && checkBST(n.right, n.val, max);
		}
	}
	public boolean isValidBST(TreeNode root) {
		return isValidBST(root, null, null);
	}
	private boolean isValidBST(TreeNode p, Integer low, Integer high) { // uses null case to avoid the Integer.MIN_VALUE and MAX_VALUE cases
		if (p == null) return true;
		return (low == null || p.val > low) && (high == null || p.val < high) && isValidBST(p.left, low, p.val) && isValidBST(p.right, p.val, high);
	}
	// iterative in-order traversal with check against prev node
	public boolean isValidBSTIter(TreeNode root) {
		if (root == null) return true;
		Stack<TreeNode> stack = new Stack<>();
		TreeNode pre = null;
		while (root != null || !stack.isEmpty()) {
			while (root != null) {
				stack.push(root);
				root = root.left;
			}
			root = stack.pop();
			if(pre != null && root.val <= pre.val) return false;
			pre = root;
			root = root.right;
		}
		return true;
	}

	public boolean equals(IntTree other) {
		return equals(overallRoot, other.overallRoot);
	}
	private boolean equals(TreeNode a, TreeNode b) {
		if(a!=null && b!=null){
			if(a.val == b.val){
				return equals(a.left, b.left) || equals (a.right, b.right);
			} else {
				return false;
			}
		}
		else return (a==null && b==null);
	}

	// swaps all the child nodes of each node in the tree, BST no longer holds
	public void flip() {
		overallRoot = flip(overallRoot);
	}	
	private TreeNode flip(TreeNode root) {
		if(root != null) {
			if(root.left != null || root.right != null) {
				TreeNode temp = root.left;
				root.left = root.right;
				root.right = temp;
			}
			root.left = flip(root.left);
			root.right = flip(root.right);
		}
		return root;
	}

	// recursively searches for a path from the start to the target, returning true if a
	// path exists and false otherwise
	public boolean hasPath(int start, int target) {
		return hasPath(overallRoot, start, target, false);
	}	
	private boolean hasPath(TreeNode root, int start, int target, boolean seen) {
		if(root == null) return false;
		else {
			if(root.val == start) 
				seen = true;
			if(seen && root.val == target) 
				return true;
			else 
				return hasPath(root.left, start, target, seen) || hasPath(root.right, start, target, seen);
		}
	}

	// returns the size of this entire tree if n = 1 
	public int countBelow(int n) {
		if(n < 1) throw new IllegalArgumentException();
		return countBelow(overallRoot, n, 1);
	}	
	private int countBelow(TreeNode root, int threshLevel, int curLevel) {
		if(root != null) {
			if(curLevel >= threshLevel) 
				return 1 + countBelow(root.left, threshLevel, curLevel+1) + countBelow(root.right, threshLevel, curLevel+1);
			else
				return countBelow(root.left, threshLevel, curLevel+1) + countBelow(root.right, threshLevel, curLevel+1);
		} else {
			return 0;
		}		
	}

	public void removeMatchingLeaves(IntTree other) {
		overallRoot = removeMatchingLeaves(overallRoot, other.overallRoot);
	}	
	private TreeNode removeMatchingLeaves(TreeNode r1, TreeNode r2) {
		if(r1 != null && r2 != null) {
			if(r1.val == r2.val && r1.left == null && r1.right == null){
				return null;
			} else {
				r1.left = removeMatchingLeaves(r1.left, r2.left);
				r1.right = removeMatchingLeaves(r1.right, r2.right);
			}
		}
		return r1;
	}

	public int sumLeaves() {
		return sumLeaves(overallRoot);
	}	
	private int sumLeaves(TreeNode root) {
		if(root == null) return 0;
		else {
			if(root.left == null && root.right ==null) {
				return root.val;
			} else {
				return sumLeaves(root.left) + sumLeaves(root.right);
			}
		}
	}

	// fills in the current tree by adding nodes with 0
	public void makePerfect() {
		overallRoot = makePerfect(overallRoot, 1, height());
	}
	private TreeNode makePerfect(TreeNode root, int level, int height) {
		if(level <= height) {
			if(root == null) return new TreeNode(0);
			else {
				root.left = makePerfect(root.left, level+1, height);
				root.right = makePerfect(root.right, level+1, height);
			}
		}
		return root;
	}







	public static void main(String[] args) {
		Random r = new Random();
		IntTree t = new IntTree();
		for(int i = 0; i < 11; i++) t.add(i % 6);
		System.out.println(t);

		//		TreeNode root = new TreeNode(5,
		//			new TreeNode(3,
		//					new TreeNode(2),
		//					new TreeNode(4)
		//			),
		//			
		//			new TreeNode(7,
		//					new TreeNode(6),
		//					new TreeNode(8)
		//			)
		//		);
		//		
		//		IntTree t = new IntTree(root);
		//		
		//		System.out.println(t.isBalanced());
	}



	//TreeNode objects stores a single node of a binary tree of ints.
	public static class TreeNode {
		public int val; // val stored at this node
		public TreeNode left; // reference to left subtree
		public TreeNode right; // reference to right subtree

		// Constructs a leaf node with the given val.
		public TreeNode(int val) {
			this(val, null, null);
		}

		// Constructs a leaf or branch node with the given val and links.
		public TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}

		public String toString() {
			return Integer.toString(val);
		}
	}

	public String toString() {
		BTreePrinter.printNode(overallRoot);
		return "";
	}
	static class BTreePrinter {
		public static void printNode(TreeNode root) {
			int maxLevel = BTreePrinter.maxLevel(root);
			printNodeInternal(Collections.singletonList(root), 1, maxLevel);
		}
		private static void printNodeInternal(List<TreeNode> nodes, int level, int maxLevel) {
			if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes)) return;
			int floor = maxLevel - level;
			int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
			int firstSpaces = (int) Math.pow(2, (floor)) - 1;
			int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
			BTreePrinter.printWhitespaces(firstSpaces);
			List<TreeNode> newNodes = new ArrayList<TreeNode>();
			for (TreeNode node : nodes) {
				if (node != null) {
					System.out.print(node.val);
					newNodes.add(node.left);
					newNodes.add(node.right);
				} else {
					newNodes.add(null);
					newNodes.add(null);
					System.out.print(" ");
				}
				BTreePrinter.printWhitespaces(betweenSpaces);
			}
			System.out.println("");
			for (int i = 1; i <= endgeLines; i++) {
				for (int j = 0; j < nodes.size(); j++) {
					BTreePrinter.printWhitespaces(firstSpaces - i);
					if (nodes.get(j) == null) {
						BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
						continue;
					}
					if (nodes.get(j).left != null)
						System.out.print("/");
					else
						BTreePrinter.printWhitespaces(1);
					BTreePrinter.printWhitespaces(i + i - 1);
					if (nodes.get(j).right != null)
						System.out.print("\\");
					else
						BTreePrinter.printWhitespaces(1);
					BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
				}
				System.out.println("");
			}
			printNodeInternal(newNodes, level + 1, maxLevel);
		}
		private static void printWhitespaces(int count) {
			for (int i = 0; i < count; i++)
				System.out.print(" ");
		}
		private static <T extends Comparable<?>> int maxLevel(TreeNode node) {
			if (node == null) return 0;
			return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
		}
		private static <T> boolean isAllElementsNull(List<T> list) {
			for (Object object : list) {
				if (object != null) return false;
			}
			return true;
		}
	}
}

