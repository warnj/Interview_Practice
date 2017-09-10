package General_Questions;
// An IntSearchTree object represents an entire binary search tree of ints.
// Class invariant:
//    - nodes to the left of a root have smaller values
//    - nodes to the right of a root have larger values
//    - there are no duplicates

import java.util.*;

public class IntTree {	
	private IntTreeNode overallRoot;
	
	// Constructs an empty binary tree.
	public IntTree() {
		overallRoot = null;
	}
	
	// Constructs a binary tree with the given node as its root.
	// Note: this is hacky and just useful for testing
	private IntTree(IntTreeNode root) {
		overallRoot = root;
	}
	
	// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/
	public IntTree(int[] preorder, int[] inorder) {
		
	}
	
	// Returns true if the value is in this tree, false otherwise.
	public boolean contains(int value) {
		return contains(overallRoot, value);
	}	
	// Returns true if the value is in the tree starting at the specified root.
	private boolean contains(IntTreeNode root, int value) {
		if(root == null) {
			return false;
		} else if(root.data == value) {
			return true;
		} else if(root.data > value) {
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
	private IntTreeNode add(IntTreeNode root, int value) {
		if(root == null) {
			root = new IntTreeNode(value);
		} else if(root.data > value) {
			root.left = add(root.left, value);
		} else if(root.data < value) {
			root.right = add(root.right, value);
		}
		return root;
	}
	
	public int getMin() {
		if(overallRoot == null) throw new NoSuchElementException();
		return getMin(overallRoot);
	}	
	private int getMin(IntTreeNode root) {
		if(root.left == null) {
			return root.data;
		} else {
			return getMin(root.left);
		}
	}
	
	// Removes the node with the given value from this BST.
	public void remove(int value) {
		overallRoot = remove(overallRoot, value);
	}	
	// Removes the node with the given value from the given tree.
	private IntTreeNode remove(IntTreeNode root, int value) {
		if(root == null) {
			return null;
		} else if (root.data < value) {
			root.right = remove(root.right, value);
		} else if (root.data > value) {
			root.left = remove(root.left, value);
		} else { // found it! - root.data == value
			// (not necessary to have as a separate case!)
			if(root.left == null && root.right == null) {
				root = null;
			} else if(root.right == null) {
				return root.left;
			} else if(root.left == null) {
				return root.right;
			} else {
				int min = getMin(root.right);
				root.data = min;
				root.right = remove(root.right, min);
			}
		}
		return root;
	}
	
	public void removeLeafs() {
		removeLeafs(overallRoot);
	}	
	private IntTreeNode removeLeafs(IntTreeNode root) {
		if(root != null) {
			if(root.left == null && root.right == null) return null;
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
	private void printPreorder(IntTreeNode root) {
		if(root != null) {
			System.out.print(root.data + " ");
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
	private void printInorder(IntTreeNode root) {
		if(root != null) {
			printInorder(root.left);
			System.out.print(root.data + " ");
			printInorder(root.right);
		}
	}
	
	// Prints a post-order traversal of this tree.
	public void printPostorder() {
		printPostorder(overallRoot);
		System.out.println();
	}	
	// Prints a post-order traversal of the tree starting at the specified root
	private void printPostorder(IntTreeNode root) {
		if(root != null) {
			printPostorder(root.left);
			printPostorder(root.right);
			System.out.print(root.data + " ");
		}
	}
	
	
	/******** My Added Methods ***************************************************************************************************************/
	
	// this is just DFS
	public void printPreorderIterative() {
		Stack<IntTreeNode> s = new Stack<IntTreeNode>();
		s.push(overallRoot);
		
		while(!s.isEmpty()) {
			IntTreeNode cur = s.pop();
			if(cur != null) { // overallRoot was null
				System.out.print(cur.data + " ");
				if(cur.right != null) {
					s.push(cur.right);
				}
				if(cur.left != null) {
					s.push(cur.left); // left child on top of stack 
				}
			}
		}
		System.out.println();
	}
	
	public void printInorderIterative() {
		Stack<IntTreeNode> s = new Stack<IntTreeNode>();
		IntTreeNode start = overallRoot;
		// start at bottom left
		while(start != null){
			s.push(start);
			start = start.left;
		}
		
		while(!s.isEmpty()) {
			IntTreeNode cur = s.pop();
			
			System.out.print(cur.data + " ");
			IntTreeNode temp = cur.right;
			while(temp != null) {
				s.push(temp);
				temp = temp.left;
			}
		}
		System.out.println();
	}
	
	public void printPostorderIterative() {
		Stack<IntTreeNode> s = new Stack<IntTreeNode>();
		s.push(overallRoot);
		IntTreeNode temp = overallRoot; // the last node printed out,
		// initialize with overallRoot since setting to null can trigger finishedSubtrees to be initially true when it shouldn't be
		while(!s.isEmpty()) {
			IntTreeNode next = s.peek();
			
			boolean finishedSubtrees = (next.right == temp || next.left == temp);
			boolean isLeaf = (next.left == null && next.right == null);
			if (finishedSubtrees || isLeaf) {
				s.pop();
				System.out.print(next.data + " ");
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
			Queue<IntTreeNode> q = new LinkedList<>();
			q.add(overallRoot);
			while (!q.isEmpty()) {
				IntTreeNode n = q.remove();
				System.out.print(n.data + " ");
				if (n.left != null) q.add(n.left);
                if (n.right != null) q.add(n.right);
			}
		}
	}
	
	// returns list of the data at each level of the tree, top to bottom, left to right
	// https://leetcode.com/problems/binary-tree-level-order-traversal/description/
	public List<List<Integer>> levelOrder() {
        List<List<Integer>> allLevels = new LinkedList<>();
        if (overallRoot == null) return allLevels;
        
        Queue<IntTreeNode> q = new LinkedList<>(); // for BFS
        q.add(overallRoot);
        while (!q.isEmpty()) {
            int levelNum = q.size(); // everything in the q currently is on the same level of tree
            List<Integer> oneLevel = new LinkedList<>();
            for (int i = 0; i < levelNum; i++) { // add their children, which will be on same level as each other
            	IntTreeNode n = q.remove();
                if (n.left != null) q.add(n.left);
                if (n.right != null) q.add(n.right);
                oneLevel.add(n.data);
            }
            allLevels.add(oneLevel);
        }
        return allLevels;
    }
	
	// Given a binary tree, flatten it to a linked list in-place.
	// THIS IS THE MOST HARD TO WRITE 5 LINES OF CODE EVER
	// https://leetcode.com/problems/flatten-binary-tree-to-linked-list/description/
	public void flatten() {
		flatten(overallRoot);
	}
	private IntTreeNode prev = null;
	private void flatten(IntTreeNode root) {
        if (root != null) {
        	flatten(root.right);
            flatten(root.left);
            root.right = prev;
            root.left = null; // singly linked list
            prev = root;
        }
    }
	public void flattenIterative() {
        if (overallRoot == null) return;
        Stack<IntTreeNode> s = new Stack<>();
        s.push(overallRoot);
        while (!s.isEmpty()) {
        	IntTreeNode cur = s.pop(); // cur is the end of the list as we build it
            if (cur.right != null) // want right side at end of linked list, so put on bottom of stack by adding first
                 s.push(cur.right);
            if (cur.left != null) 
                 s.push(cur.left);
            if (!s.isEmpty()) // connect end of list to next node 
                 cur.right = s.peek();
            cur.left = null; // singly linked list
        }
    }
	
	// returns the least-valued common ancestor of the two given nodes
	public int lca(IntTreeNode root, int v1, int v2) {
		IntTreeNode lca = getLca(root, v1, v2);
		if (lca != null) {
			return lca.data;
		} else {
			return Integer.MIN_VALUE;
		}
    }
	private IntTreeNode getLca(IntTreeNode root, int v1, int v2) {
		// look for the two target nodes (v1 and v2)
	    if(root == null) return null;
	    if(root.data == v1 || root.data == v2) return root; 
	    IntTreeNode leftVal = getLca(root.left, v1, v2);
	    IntTreeNode rightVal = getLca(root.right, v1, v2);
	    
	    if(leftVal != null && rightVal != null) return root; // this node is the ancestor
	    
	    if (leftVal != null) return leftVal; // the ancestor was found in the left subtree
	    else return rightVal; // the ancestor was found (or not found) in the right subtree
	}
	
	public int size() {
		return size(overallRoot);
	}	
	private int size(IntTreeNode root){
		if(root != null) {
			return 1 + size(root.left) + size(root.right);
		}
		return 0;
	}
	
	// if k=1, then returns the min value, if k=2 returns the second smallest val....
	// if k = this.size() returns the max value
	// Iterative in-order traversal with a counter that decrements as look at a node. O(n) time
	public int kthSmallest(int k) {
		Stack<IntTreeNode> stack = new Stack<IntTreeNode>();
		IntTreeNode p = overallRoot;
	 
	    while(!stack.isEmpty() || p != null){
	        if(p != null){
	            stack.push(p);
	            p = p.left; // add nodes to stack as go down & left as far as possible
	        } else {
	        	// hit the lower left of a subtree
	        	IntTreeNode t = stack.pop(); // the parent node going back up tree (node added before fell off the tree)
	            k--;
	            if(k==0) return t.data;
	            p = t.right; // nowhere left to go or already done the left, so look to right
	        }
	    }
	    return Integer.MIN_VALUE; // k out of range (k > size or k <= 0)
	}
	
	// recursive in-order traversal with counter. O(n)
	public int kthSmallestRecursive(int k) {
        return kthSmallest(overallRoot, k);
    }    
    private int kthSmallest(IntTreeNode root, int k) {
        if (root == null) return Integer.MIN_VALUE;
        
        int result = kthSmallest(root.left, k);
         
        k--;
        if (k == 0) return root.data;
         
        int result2 = kthSmallest(root.right, k);
        return Math.max(result, result2);
    }
    
    // recursive version using the sizes of subtrees, O(n^2) time i think
 	public int kthSmallestRecursive2(int k) {
         return kthSmallest2(overallRoot, k);
    }    
    public int kthSmallest2(IntTreeNode root, int k) {
        if (root == null) return Integer.MIN_VALUE;
         
        int leftNodes = size(root.left);
        if(k == leftNodes + 1) {
            return root.data;
        } else if (k > leftNodes + 1) {
            return kthSmallest(root.right, k - leftNodes - 1);
        } else {
            return kthSmallest(root.left, k);
        }
    }
    
    // returns true if tree has a root to leaf path that adds to equal sum
    public boolean hasPathSum(int sum) {
    	return hasPathSum(overallRoot, sum);
    }
    private boolean hasPathSum(IntTreeNode root, int sum) {
    	if(root != null) {
    		sum -= root.data;
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
    
//    public boolean isBalanced() { // O(n^2)
//    	return isBalanced(overallRoot);
//    }
//    private boolean isBalanced(IntTreeNode root) {
//    	if(root == null) return true;
//    	
//    	if (Math.abs(height(root.left) - height(root.right)) > 1) {
//    		return false;
//    	} else {
//    		return isBalanced(root.left) && isBalanced(root.right);
//    	}
//    }
    
    // 0(N) time and 0(H) space, where H is the height of the tree
    public boolean isBalanced() {
    	return checkHeight(overallRoot) != -1;
    }
    private int checkHeight(IntTreeNode root) {
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
    private IntTreeNode removeEvens(IntTreeNode root) {
    	if(root == null) return null;
    	root.left = removeEvens(root.left);
    	root.right = removeEvens(root.right);
    	
    	if (root.data % 2 == 0) {
    		if(root.left == null) {
    			return root.right;
    		} else if (root.right == null) {
    			return root.left;
    		} else { // both children are non-null
    			int val = getMin(root.right);
    			remove(root.right, val);
    			root.data = val;
    		}
    	}
    	return root;
    }
    
    public int secondSmallest() {
    	return secondSmallest(overallRoot);
    }
    private int secondSmallest(IntTreeNode node) {
    	if(node == null) {
    		throw new IllegalArgumentException();
    	} else if(node.left == null) {
    		return getMin(node.right);
    	} else if (node.left.left == null && node.left.right == null) {
    		return node.data; // there is exactly one leaf node less than current node
    	} else {
    		return secondSmallest(node.left);
    	}
    }
	
	/******** Practice-It Methods *****************************************************************************************************************/
	
	// removes the nodes in the tree not between min and max inclusive. Tree must be a BST
	public void trimRange(int min, int max) {
		overallRoot = trimRange(overallRoot, min, max);
	}	
	private IntTreeNode trimRange(IntTreeNode root, int min, int max) {
		if(root != null) {
			if(root.data > max) {
				root = trimRange(root.left, min, max);
			} else if (root.data < min) {
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
	private int height(IntTreeNode root) {
		if(root == null) return 0;
		else {
			return 1 + Math.max(height(root.left), height(root.right));
		}
	}
	
	// fails if tree contains Integer.MIN_VALUE
	public boolean isBST() {
		return checkBST(overallRoot, Long.MIN_VALUE, Long.MAX_VALUE);
	}	
	private boolean checkBST(IntTreeNode n, long min, long max) {
		if(n == null) {
			return true;
		} else if (n.data <= min || n.data >= max) {
			return false;
		} else {
			return checkBST(n.left, min, n.data) && checkBST(n.right, n.data, max);
		}
	}
	public boolean isValidBST(IntTreeNode root) {
	    return isValidBST(root, null, null);
	}
	private boolean isValidBST(IntTreeNode p, Integer low, Integer high) { // uses null case to avoid the Integer.MIN_VALUE and MAX_VALUE cases
	    if (p == null) return true;
	    return (low == null || p.data > low) && (high == null || p.data < high) && isValidBST(p.left, low, p.data) && isValidBST(p.right, p.data, high);
	}
	
	public boolean equals(IntTree other) {
		return equals(overallRoot, other.overallRoot);
	}
	private boolean equals(IntTreeNode a, IntTreeNode b) {
		if(a!=null && b!=null){
			if(a.data == b.data){
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
	private IntTreeNode flip(IntTreeNode root) {
		if(root != null) {
			if(root.left != null || root.right != null) {
				IntTreeNode temp = root.left;
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
	private boolean hasPath(IntTreeNode root, int start, int target, boolean seen) {
		if(root == null) return false;
		else {
			if(root.data == start) 
				seen = true;
			if(seen && root.data == target) 
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
	private int countBelow(IntTreeNode root, int threshLevel, int curLevel) {
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
	private IntTreeNode removeMatchingLeaves(IntTreeNode r1, IntTreeNode r2) {
		if(r1 != null && r2 != null) {
			if(r1.data == r2.data && r1.left == null && r1.right == null){
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
	private int sumLeaves(IntTreeNode root) {
		if(root == null) return 0;
		else {
			if(root.left == null && root.right ==null) {
				return root.data;
			} else {
				return sumLeaves(root.left) + sumLeaves(root.right);
			}
		}
	}
	
	// fills in the current tree by adding nodes with 0
	public void makePerfect() {
		overallRoot = makePerfect(overallRoot, 1, height());
	}
	private IntTreeNode makePerfect(IntTreeNode root, int level, int height) {
		if(level <= height) {
			if(root == null) return new IntTreeNode(0);
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
		
//		IntTreeNode root = new IntTreeNode(5,
//			new IntTreeNode(3,
//					new IntTreeNode(2),
//					new IntTreeNode(4)
//			),
//			
//			new IntTreeNode(7,
//					new IntTreeNode(6),
//					new IntTreeNode(8)
//			)
//		);
//		
//		IntTree t = new IntTree(root);
//		
//		System.out.println(t.isBalanced());
	}
	
	
	
	//IntTreeNode objects stores a single node of a binary tree of ints.
	private static class IntTreeNode {
		public int data; // data stored at this node
		public IntTreeNode left; // reference to left subtree
		public IntTreeNode right; // reference to right subtree
		
		// Constructs a leaf node with the given data.
		public IntTreeNode(int data) {
			this(data, null, null);
		}
		
		// Constructs a leaf or branch node with the given data and links.
		public IntTreeNode(int data, IntTreeNode left, IntTreeNode right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
		
		public String toString() {
			return Integer.toString(data);
		}
	}
	
	public String toString() {
    	BTreePrinter.printNode(overallRoot);
    	return "";
    }
    static class BTreePrinter {
        public static void printNode(IntTreeNode root) {
            int maxLevel = BTreePrinter.maxLevel(root);
            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }
        private static void printNodeInternal(List<IntTreeNode> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes)) return;
            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
            BTreePrinter.printWhitespaces(firstSpaces);
            List<IntTreeNode> newNodes = new ArrayList<IntTreeNode>();
            for (IntTreeNode node : nodes) {
                if (node != null) {
                    System.out.print(node.data);
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
        private static <T extends Comparable<?>> int maxLevel(IntTreeNode node) {
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

