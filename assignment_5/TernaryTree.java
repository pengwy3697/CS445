package cs445.a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cs445.StackAndQueuePackage.LinkedQueue;
import cs445.StackAndQueuePackage.LinkedStack;
import cs445.StackAndQueuePackage.QueueInterface;
import cs445.StackAndQueuePackage.StackInterface;
import cs445.a5.TernaryNode;
import cs445.a5.TernaryTreeInterface;

import cs445.a5.EmptyTreeException;

public class TernaryTree<T> implements TernaryTreeInterface<T> {
	private TernaryNode<T> root;

	public TernaryTree() {
		root = null;
	}

	public TernaryTree(T rootData) {
		root = new TernaryNode<>(rootData);
	}

	public TernaryTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> midTree, TernaryTree<T> rightTree) {
		setTree(rootData, leftTree, midTree, rightTree);
	}

	public void setTree(T rootData) {
		root = new TernaryNode<>(rootData);
	}

	@Override
	public void setTree(T rootData, TernaryTreeInterface<T> leftTree, TernaryTreeInterface<T> midTree,
			TernaryTreeInterface<T> rightTree) {
		setTree(rootData, (TernaryTree<T>) leftTree, (TernaryTree<T>) midTree, (TernaryTree<T>) rightTree);
	}

	private void setTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> midTree, TernaryTree<T> rightTree) {
		root = new TernaryNode<>(rootData);
		if ((leftTree != null) && !leftTree.isEmpty()) {
			root.setLeftChild(leftTree.root);
		}
		if ((rightTree != null) && !rightTree.isEmpty()) {
			if (rightTree != leftTree) {
				root.setRightChild(rightTree.root);
			} else {
				root.setRightChild(rightTree.root.copy());
			}
		}
		
		if ((midTree != null) && !midTree.isEmpty()) {
			if (midTree != leftTree) {
				root.setMidChild(midTree.root);
			} else {
				root.setMidChild(midTree.root.copy());
			}
		}
		
		if ((leftTree != null) && (leftTree != this)) {
			leftTree.clear();
		}
		if ((rightTree != null) && (rightTree != this)) {
			rightTree.clear();
		}
	}

	public T getRootData() {
		if (isEmpty()) {
			throw new EmptyTreeException();
		} else {
			return root.getData();
		}
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void clear() {
		root = null;
	}

	protected void setRootData(T rootData) {
		root.setData(rootData);
	}

	protected void setRootNode(TernaryNode<T> rootNode) {
		root = rootNode;
	}

	protected TernaryNode<T> getRootNode() {
		return root;
	}

	public int getHeight() {
		int height = 0;
		if (!isEmpty()) {
			height = root.getHeight();
		}
		return height;
	}

	public int getNumberOfNodes() {
		int numberOfNodes = 0;
		if (!isEmpty()) {
			numberOfNodes = root.getNumberOfNodes();
		}
		return numberOfNodes;
	}

	public Iterator<T> getPreorderIterator() {
		return new PreorderIterator();
	}

	/***
	 * Ternary tree does not support inorder (Left, Root, right) because it is unknown where the mid child would be placed
	 */
	public Iterator<T> getInorderIterator() {
		throw new UnsupportedOperationException("TernaryTree does not support inorder traversal!");
	}

	public Iterator<T> getPostorderIterator() {
		return new PostorderIterator();
	}

	public Iterator<T> getLevelOrderIterator() {
		return new LevelOrderIterator();
	}

	/**
	 * 
	 * Traversal Order: Root, Left, Mid, Right
	 *
	 */
	private class PreorderIterator implements Iterator<T> {
		private StackInterface<TernaryNode<T>> nodeStack;

		public PreorderIterator() {
			nodeStack = new LinkedStack<>();
			if (root != null) {
				nodeStack.push(root);
			}
		}

		public boolean hasNext() {
			return !nodeStack.isEmpty();
		}

		public T next() {
			TernaryNode<T> nextNode;
			if (hasNext()) {
				nextNode = nodeStack.pop();
				TernaryNode<T> leftChild = nextNode.getLeftChild();
				TernaryNode<T> rightChild = nextNode.getRightChild();
				TernaryNode<T> midChild = nextNode.getMidChild();
				// Push into stack in reverse order of recursive calls
				if (rightChild != null) {
					nodeStack.push(rightChild);
				}
				if (midChild != null) {
					nodeStack.push(midChild);
				}
				if (leftChild != null) {
					nodeStack.push(leftChild);
				}
			} else {
				throw new NoSuchElementException();
			}
			return nextNode.getData();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

/*
	private class InorderIterator implements Iterator<T> {
		private StackInterface<TernaryNode<T>> nodeStack;
		private TernaryNode<T> currentNode;

		public InorderIterator() {
			nodeStack = new LinkedStack<>();
			currentNode = root;
		}

		public boolean hasNext() {
			return !nodeStack.isEmpty() || (currentNode != null);
		}

		public T next() {
			TernaryNode<T> nextNode = null;
			// Find leftmost node with no left child
			while (currentNode != null) {
				System.out.println("currentNode pushed=" + currentNode.getData());
				nodeStack.push(currentNode);
				currentNode = currentNode.getLeftChild();
			}
			// Get leftmost node, then move to its right subtree
			if (!nodeStack.isEmpty()) {
				nextNode = nodeStack.pop();
				assert nextNode != null; // Since nodeStack was not empty
				// before the pop
				System.out.println("nextNode=" + nextNode.getData());
				currentNode = nextNode.getMidChild();
			} else {
				throw new NoSuchElementException();
			}
			return nextNode.getData();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
*/
	/**
	 * 
	 * Traversal Order: Left, Mid, Right, Root
	 *
	 */
	private class PostorderIterator implements Iterator<T> {
		private StackInterface<TernaryNode<T>> nodeStack;
		private TernaryNode<T> currentNode;

		public PostorderIterator() {
			nodeStack = new LinkedStack<>();
			currentNode = root;
		}

		public boolean hasNext() {
			return !nodeStack.isEmpty() || (currentNode != null);
		}

		public T next() {
			boolean foundNext = false;
			TernaryNode<T> leftChild, midChild, rightChild, nextNode = null;
			// Find leftmost leaf
			while (currentNode != null) {
				// System.out.println("currentNode=" + currentNode.getData());
				nodeStack.push(currentNode);
				leftChild = currentNode.getLeftChild();
				if (leftChild == null) {
					midChild = currentNode.getMidChild();
					if (midChild == null) {
						rightChild = currentNode.getRightChild();
						currentNode = rightChild;
					} else {
						currentNode = midChild;
					}
				} else {
					currentNode = leftChild;
				}
			}
			// Stack is not empty either because we just pushed a node, or
			// it wasn't empty to begin with since hasNext() is true.
			// But Iterator specifies an exception for next() in case
			// hasNext() is false.
			if (!nodeStack.isEmpty()) {
				nextNode = nodeStack.pop();
				// System.out.println("nextNode=" + nextNode.getData());
				// nextNode != null since stack was not empty before pop
				TernaryNode<T> parent = null;
				if (!nodeStack.isEmpty()) {
					parent = nodeStack.peek();
					// System.out.println("parent=" + parent.getData());
					if (nextNode == parent.getLeftChild()) {
						currentNode = parent.getMidChild();
						if (currentNode == null) {
							currentNode = parent.getRightChild();
						}
					} else if (nextNode == parent.getMidChild()) {
						currentNode = parent.getRightChild();
					} else {
						currentNode = null;
					}
				} else {
					currentNode = null;
				}
			} else {
				throw new NoSuchElementException();
			}
			return nextNode.getData();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private class LevelOrderIterator implements Iterator<T> {
		private QueueInterface<TernaryNode<T>> nodeQueue;

		public LevelOrderIterator() {
			nodeQueue = new LinkedQueue<>();
			if (root != null) {
				nodeQueue.enqueue(root);
			}
		}

		public boolean hasNext() {
			return !nodeQueue.isEmpty();
		}

		public T next() {
			TernaryNode<T> nextNode;
			TernaryNode<T> leftChild, rightChild, midChild = null;
			if (hasNext()) {
				nextNode = nodeQueue.getFront();
				leftChild = nextNode.getLeftChild();
				midChild = nextNode.getMidChild();
				rightChild = nextNode.getRightChild();
				if (nextNode.hasLeftChild()) {
					nodeQueue.enqueue(leftChild);
				}
				if (nextNode.hasMidChild()) {
					nodeQueue.enqueue(midChild);
				}
				if (nextNode.hasRightChild()) {
					nodeQueue.enqueue(rightChild);
				}
				nodeQueue.dequeue();
			} else {
				throw new NoSuchElementException();
			}
			return nextNode.getData();
		}
	
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
