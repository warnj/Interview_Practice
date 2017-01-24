
public class AVLNode
{
	// Constructors
	AVLNode( int theElement )
	{
		this( theElement, null, null );
	}

	AVLNode( int theElement, AVLNode lt, AVLNode rt )
	{
		key  = theElement;
		left     = lt;
		right    = rt;
		height   = 0;
	}

	// Friendly data; accessible by other package routines
	int key;      // The data in the node
	AVLNode    left;         // Left child
	AVLNode    right;        // Right child
	int        height;       // Height
}



//class AVLNode {
//    int key;
//    Object value;
//    int height;
//    AVLNode left;
//    AVLNode right;
//    
//    public AVLNode(int key) {
//    	this.key = key;
//    }
//}