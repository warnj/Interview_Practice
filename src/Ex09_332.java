public class Ex09_332 {

	public static void main(String[] args) {
		AVLNode a = new AVLNode(5);
		a.left = new AVLNode(4);
		a.left.left = new AVLNode(3);
		
		
	}
	
	public static boolean verifyAVL(AVLNode node) {
		return verify(node, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	private static boolean verify(AVLNode node, int min, int max) {
		if(node == null) {
			return true;
		}
		int lHeight = (node.left == null) ? -1 : node.left.height;
		int rHeight = (node.right == null) ? -1 : node.right.height;
		if(node.key < min || node.key > max || Math.abs(lHeight - rHeight) > 1) {
			return false;
		}
		return verify(node.left, min, node.key) && verify(node.right, node.key, max);
	}

	
	

}
