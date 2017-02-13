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
		Queue<IntTreeNode> q = new LinkedList<IntTreeNode>();
		q.add(overallRoot);
		while(!q.isEmpty()) {
			IntTreeNode n = q.remove();
			if(n != null) {
				System.out.print(n.data + " ");
				q.add(n.left);
				q.add(n.right);
			}
		}
	}
	
	// returns the least-valued common ancestor of the two given nodes
	public int lca(IntTreeNode root, int v1, int v2) {
		IntTreeNode lca = getLca(root, v1, v2);
		if(lca != null) {
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
		IntTree t = new IntTree();
		for(int i = 0; i < 111; i++) t.add(i);
		t.printInorder();
		t.removeEvens();
		t.printInorder();
		
		
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
	}
}

