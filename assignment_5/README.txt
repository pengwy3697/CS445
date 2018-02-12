R E A D  M E - a u h 11
------------------------

All tests should work as expected. The Inorder Iterator returns UnsupportedOrder because
when using a binary tree the order should be Left, Root, Right however in a ternary tree
it is unknown whether the mid child would be before or after the loop.

There are 7 test cases:
________________________
------------------------
TEST CASE 1: Empty Tree
------------------------
	This test checks if the tree had no root or node and if it will throw
an EmptyTreeException.
	Level order, preorder and post order should return nothing.
	Expected Results:
		Root = EmptyTreeException
		Height= 0
		Number of Nodes = 0
		isEmpty = true
------------------------
TEST CASE 2: Orphan Tree
------------------------
	This test checks the level-order, preorder, and postorder if the tree only
contains its root.

	Diagram:
		1
	Expected Results:
		Root = 1
		Height = 1
		Number of Nodes = 1
		isEmpty = false
		level-order, preorder, postorder = 1
------------------------
TEST CASE 3: Full Tree
------------------------
	This test checks the level-order, preorder and postorder if a tree is a full 
tree.
	
	Diagram:
	     ________ 1 ____________
	    /         |             \
	   2          3             4
	 / | \     /  |  \       /  |  \
	5  6  7    8  9  10     11  12  13 
	
	Expected Results:
		Level-order traversal of Ternary Tree: 
			1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 
		Preorder traversal of Ternary Tree: 
			1 | 2 | 5 | 6 | 7 | 3 | 8 | 9 | 10 | 4 | 11 | 12 | 13 | 
		Postorder traversal of Ternary Tree: 
			5 | 6 | 7 | 2 | 8 | 9 | 10 | 3 | 11 | 12 | 13 | 4 | 1 | 

-----------------------------
TEST CASE 4: Mid Empty Tree
-----------------------------
	This test checks the level-order, preorder and post order if a tree has a left 
and right child but no mid child.

	Diagram:
	____ 1 ____
       /           \
       2            4
     / | \       /  |  \
    5  6  7    11  12  13

	Expected Results:
		Level-order traversal of Ternary Tree: 
			1 | 2 | 4 | 5 | 6 | 7 | 11 | 12 | 13 | 
		Preorder traversal of Ternary Tree: 
			1 | 2 | 5 | 6 | 7 | 4 | 11 | 12 | 13 | 
		Postorder traversal of Ternary Tree: 
			5 | 6 | 7 | 2 | 11 | 12 | 13 | 4 | 1 | 

-----------------------------
TEST CASE 5: Right Empty Tree
-----------------------------
	This test checks the level-order, preorder and postorder if a tree has a left
and mid child but no right child.

	Diagram:
	      __ 1 ___
             /        \
            2          3
          / | \      / | \
         5  6  7    8  9  10

	Expected Results:
		Root = 1
		Height = 3
		Number of Nodes = 9
		isEmpty= false	

		Level-order traversal of Ternary Tree: 
			1 | 2 | 3 | 5 | 6 | 7 | 8 | 9 | 10 | 
		Preorder traversal of Ternary Tree: 
			1 | 2 | 5 | 6 | 7 | 3 | 8 | 9 | 10 | 
		Postorder traversal of Ternary Tree: 
			5 | 6 | 7 | 2 | 8 | 9 | 10 | 3 | 1 | 

-----------------------------
TEST CASE 6: Left Empty Tree
-----------------------------
	This test checks the level-order, preorder and post order if a tree has a mid
and right child but no left child.

	Diagram:
            ___ 1 ____
   	   /          \
  	  3            4
        / | \       /  |  \
       8  9  10    11  12  13

	Expected Results:
		Root = 1
		Height = 3
		Number of Nodes = 9
		isEmpty = false

		Level-order traversal of Ternary Tree: 
			1 | 3 | 4 | 8 | 9 | 10 | 11 | 12 | 13 | 
		Preorder traversal of Ternary Tree: 
			1 | 3 | 8 | 9 | 10 | 4 | 11 | 12 | 13 | 
		Postorder traversal of Ternary Tree: 
			8 | 9 | 10 | 3 | 11 | 12 | 13 | 4 | 1 | 
-----------------------------
TEST CASE 7: Clear Tree
-----------------------------
	This test checks that if you clear() a tree, the tree should only contain a 
root that has the value 0.
	
	Diagram:
		0

	Expected Results:
		Root = 0
		Height = 1
		Number of Nodes = 1
		isEmpty = false

		Level-order traversal of Ternary Tree: 
			0 | 
		Preorder traversal of Ternary Tree: 
			0 | 
		Postord er traversal of Ternary Tree: 
			0 | 