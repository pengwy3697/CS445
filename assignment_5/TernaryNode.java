package cs445.a5;

public class TernaryNode<T> {
	
	public static final int MAX_CHILD	= 3;
	public static final int LEFT_CHILD	= 0;
	public static final int MID_CHILD	= 1;
	public static final int RIGHT_CHILD	= 2;
	
	private T data;
	
	TernaryNode <T>[] children = (TernaryNode <T>[])new TernaryNode <?>[MAX_CHILD];

	public TernaryNode() {
		this(null); // Call next constructor
	}

	public TernaryNode(T dataPortion) {
		this(dataPortion, null, null, null); // Call next constructor
	}

	public TernaryNode(T dataPortion, TernaryNode<T> newLeftChild,
		TernaryNode<T> newMidChild,	TernaryNode<T> newRightChild)
	{
		data = dataPortion;
		setLeftChild(newLeftChild);
		setMidChild(newMidChild);
		setRightChild(newRightChild);
	}

	/**
	 * Retrieves the data portion of this node.
	 * 
	 * @return The object in the data portion of the node.
	 */
	public T getData() {
		return data;
	}

	/**
	 * Sets the data portion of this node.
	 * 
	 * @param newData
	 *            The data object.
	 */
	public void setData(T newData) {
		data = newData;
	}

	/**
	 * Retrieves the left child of this node.
	 * 
	 * @return The node’s left child.
	 */
	public TernaryNode<T> getLeftChild() {
		return children[LEFT_CHILD];
	}

	/**
	 * Sets this node’s left child to a given node.
	 * 
	 * @param newLeftChild
	 *            A node that will be the left child.
	 */
	public void setLeftChild(TernaryNode<T> newLeftChild) {
		children[LEFT_CHILD] = newLeftChild;
	}

	/**
	 * Detects whether this node has a left child.
	 * 
	 * @return True if the node has a left child.
	 */
	public boolean hasLeftChild() {
		return getLeftChild() != null;
	}

	/**
	 * Retrieves the right child of this node.
	 * 
	 * @return The node’s right child.
	 */
	public TernaryNode<T> getRightChild() {
		return children[RIGHT_CHILD];
	}

	/**
	 * Sets this node’s right child to a given node.
	 * 
	 * @param newRightChild
	 *            A node that will be the right child.
	 */
	public void setRightChild(TernaryNode<T> newRightChild) {
		children[RIGHT_CHILD] = newRightChild;
	}

	/**
	 * Detects whether this node has a right child.
	 * 
	 * @return True if the node has a right child.
	 */
	public boolean hasRightChild() {
		return getRightChild() != null;
	}

	public TernaryNode<T> getMidChild() {
		return children[MID_CHILD];
	}

	public void setMidChild(TernaryNode<T> midChild) {
		this.children[MID_CHILD] = midChild;
	}

	/**
	 * Detects whether this node has a mid child.
	 * 
	 * @return True if the node has a mid child.
	 */
	public boolean hasMidChild() {
		return getMidChild() != null;
	}
	/**
	 * Detects whether this node is a leaf.
	 * 
	 * @return True if the node is a leaf.
	 */
	public boolean isLeaf() {
		return (getLeftChild() == null) && (getMidChild() == null) && (getRightChild() == null);
	}

	/**
	 * Counts the nodes in the subtree rooted at this node.
	 * 
	 * @return The number of nodes in the subtree rooted at this node.
	 */
	public int getNumberOfNodes() {
		int leftNumber = 0, rightNumber = 0, midNumber=0;
		TernaryNode<T> leftChild=getLeftChild();
		if (leftChild != null) {
			leftNumber = leftChild.getNumberOfNodes();
		}
		
		TernaryNode<T> midChild=getMidChild();
		if (midChild != null) {
			midNumber = children[MID_CHILD].getNumberOfNodes();
		}

		TernaryNode<T> rightChild=getRightChild();
		if (rightChild != null) {
			rightNumber = children[RIGHT_CHILD].getNumberOfNodes();
		}
		return 1 + leftNumber + rightNumber + midNumber;
	}

	/**
	 * Computes the height of the subtree rooted at this node.
	 * 
	 * @return The height of the subtree rooted at this node.
	 */
	public int getHeight() {
		return getHeight(this); // Call private getHeight
	}

	private int getHeight(TernaryNode<T> node) {
		int height = 0;
		if (node != null)
			height = 1 + Math.max(Math.max(getHeight(node.getLeftChild()), getHeight(node.getRightChild())),
				getHeight(node.getMidChild()));
		return height;
	}

	/**
	 * Copies the subtree rooted at this node.
	 * 
	 * @return The root of a copy of the subtree rooted at this node.
	 */
	public TernaryNode<T> copy() {
		TernaryNode<T> newRoot = new TernaryNode<>(data);
		TernaryNode<T> leftChild=getLeftChild();
		if (leftChild != null) {
			newRoot.setLeftChild(leftChild.copy());
		}
		
		TernaryNode<T> midChild=getMidChild();
		if (midChild != null) {
			newRoot.setMidChild(midChild.copy());
		}

		TernaryNode<T> rightChild=getRightChild();
		if (rightChild != null) {
			newRoot.setRightChild(rightChild.copy());
		}
		return newRoot;
	}
}
