package cs445.a5;

import java.util.Iterator;

public class TestClient {
	
	/**
	 * Test methods: TernaryTree(),
	 * 		TernaryTree(E),
	 * 		TernaryTree(E, tree, tree, tree),
	 * 		setTree(E, tree, tree, tree),
	 * 
	 * @param withLeftTree
	 * @param withMidTree
	 * @param withRightTree
	 * @return
	 */
	public static TernaryTreeInterface<Integer> setupTernaryTree(boolean withLeftTree,
		boolean withMidTree, boolean withRightTree) {
		
		TernaryTree<Integer> leftTree=null, midTree=null, rightTree=null;
		
		if (withLeftTree) {
			TernaryTree<Integer> leftTreeL = new TernaryTree<Integer>(5);
			TernaryTree<Integer> leftTreeM = new TernaryTree<Integer>(6);
			TernaryTree<Integer> leftTreeR = new TernaryTree<Integer>(7);
			leftTree = new TernaryTree<Integer>(2, leftTreeL, leftTreeM, leftTreeR);
		}
		
		if (withMidTree) {
			TernaryTreeInterface<Integer> midTreeL = new TernaryTree<Integer>(8);
			TernaryTreeInterface<Integer> midTreeM = new TernaryTree<Integer>(9);
			TernaryTreeInterface<Integer> midTreeR = new TernaryTree<Integer>(10);
			midTree = new TernaryTree<Integer>();
			midTree.setTree(3, midTreeL, midTreeM, midTreeR);
		}

		if (withRightTree) {
			TernaryTreeInterface<Integer> rightTreeL = new TernaryTree<Integer>(11);
			TernaryTreeInterface<Integer> rightTreeM = new TernaryTree<Integer>(12);
			TernaryTreeInterface<Integer> rightTreeR = new TernaryTree<Integer>(13);
			rightTree = new TernaryTree<Integer>();
			rightTree.setTree(4, rightTreeL, rightTreeM, rightTreeR);
		}
		
		TernaryTreeInterface<Integer> tree = new TernaryTree<Integer>(1, leftTree, midTree, rightTree);
		
		return tree;

	}
	
	/** 
	 * Test methods: getRootData(),
	 * 		getHeight(),
	 * 		getNumberOfNodes(),
	 * 		isEmpty()
	 * @param tree
	 */
	public static void printTreeDetails(TernaryTreeInterface<Integer> tree) {
		try {
			System.out.println("getRootData() = " + tree.getRootData());
		} catch (EmptyTreeException e) {
			System.out.println("getRootData(): EmptyTreeException is thrown and caught!");
		}
		
		System.out.println("getHeight() = " + tree.getHeight());
		System.out.println("getNumberOfNodes() = " + tree.getNumberOfNodes());
		System.out.println("isEmpty() = " + tree.isEmpty());
	}
	
	public static void printTernaryTree(Iterator treeIterator) {
		while (treeIterator.hasNext()) {
			Object obj = treeIterator.next();
			System.out.print(obj);
			System.out.print(" | ");
		}
		System.out.println();
	}
	
	public static void printTreeTraversal(TernaryTreeInterface<Integer> tree) {
		System.out.println(">> Level-order traversal of Ternary Tree: ");
		printTernaryTree(tree.getLevelOrderIterator());
        
		System.out.println(">> Preorder traversal of Ternary Tree: ");
		printTernaryTree(tree.getPreorderIterator());
        
        System.out.println(">> Postorder traversal of Ternary Tree: ");
        printTernaryTree(tree.getPostorderIterator());
	}
	
	/**
	 * Test the EmptyTreeException thrown from getRootData()
	 */
	public static void testEmptyData() {
		System.out.println("\n## Test 1:Empty Tree ++++++++++++++++++++++");
		TernaryTreeInterface<Integer> tree = new TernaryTree<Integer>();
		try {
			printTreeDetails(tree);
			printTreeTraversal(tree);
		} catch (EmptyTreeException e) {
			System.out.println("EmptyTreeException is thrown and caught!");
		}
		System.out.println("## End of Test Empty Tree ++++++++++++++++++++++\n");
	}
	
	/**
	 * Test tree with only root
	 */
	public static void testOrphanTree() {
		System.out.println("## Test 2:Orphan Tree ++++++++++++++++++++++");
		TernaryTreeInterface<Integer> tree = setupTernaryTree(false, false, false);
		printTreeDetails(tree);
		printTreeTraversal(tree);
		System.out.println("## End of Test Orphan Tree ++++++++++++++++++++++\n");
	}
	
	/**
	 * Test tree with 3 levels of left, mid and right node
	 */
	public static void testFullTree() {
		System.out.println("## Test 3:Full Tree ++++++++++++++++++++++");
		TernaryTreeInterface<Integer> tree = setupTernaryTree(true, true, true);
		// printTreeDetails(tree);
		printTreeTraversal(tree);
		System.out.println("## End of Test Full Tree ++++++++++++++++++++++\n");
	}
	
	/**
	 * Test tree with 3 levels of left, mid and EMPTY right node
	 */
	public static void testEmptyRightTree() {
		System.out.println("## Test 5:Right Empty Tree ++++++++++++++++++++++");
		TernaryTreeInterface<Integer> tree = setupTernaryTree(true, true, false);
		printTreeDetails(tree);
		printTreeTraversal(tree);
		System.out.println("## End of Test Right Empty Tree ++++++++++++++++++++++\n");
	}
	
	/**
	 * Test tree with 3 levels of left, EMPTY mid and right node
	 */
	public static void testEmptyMidTree() {
		System.out.println("## Test 4:Mid Empty Tree ++++++++++++++++++++++");
		TernaryTreeInterface<Integer> tree = setupTernaryTree(true, false, true);
		// printTreeDetails(tree);
		printTreeTraversal(tree);
		System.out.println("## End of Test Mid Empty Tree ++++++++++++++++++++++\n");
	}
	
	/**
	 * Test tree with 3 levels of EMPTY left, mid and right node
	 */

	public static void testEmptyLeftTree() {
		System.out.println("## Test 6:Left Empty Tree ++++++++++++++++++++++");
		TernaryTreeInterface<Integer> tree = setupTernaryTree(false, true, true);
		printTreeDetails(tree);
		printTreeTraversal(tree);
		System.out.println("## End of Test Left Empty Tree ++++++++++++++++++++++\n");
	}
	
	/**
	 * Test methods: clear(), setTree(E)
	 */
	public static void testClearSetTree() {
		System.out.println("## Test 7:clear(), setTree(E) ++++++++++++++++++++++");
		TernaryTreeInterface<Integer> tree = setupTernaryTree(true, true, true);
		tree.clear();
		tree.setTree(0);
		printTreeDetails(tree);
		printTreeTraversal(tree);
		System.out.println("## End of Test clear(), setTree(E) ++++++++++++++++++++++\n");
	}
	public static void main(String[] args) {
		testEmptyData();
		testOrphanTree();
		testFullTree();
		testEmptyMidTree();
		testEmptyRightTree();
		testEmptyLeftTree();
		testClearSetTree();
	}
}