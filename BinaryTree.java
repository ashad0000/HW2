/*
 * *** Abdul Shad / 272 ***
 *
 * Homework # 2 (Programming Assignment). This Java class defines a few basic
 * manipulation operations of a binary trees.
 *
 * ONLY MODIFY THIS FILE (NOT 'Main.Java')
 *
 */

import java.util.Queue;
import java.util.LinkedList;

/*
 * Class BinaryTree
 *
 * This class defines a binary tree object; it is a tree structure where every
 * node has at most two child nodes, which form the tree branches. That implies
 * that each node within the tree has a degree of 0, 1, or 2. A node of degree
 * zero (0) is called a terminal node, or leaf node.
 *
 * Each non-leaf node is often called a branch node, which will have either one or
 * two children (a left and right child node). There is no order guarantee within
 * this basic binary tree object. Given that this binary object is NOT a Binary Search Tree (BST), there is
 * no guarantee on order in the tree.
 *
 * As just stated, the insert method does NOT guarantee the order within the tree, but
 * its logic attempts to follow the rules of BSTs -- meaning the insert method will traverse
 * the binary tree searching for a location to insert the new Node using traversal
 * logic similar to BSTs. But again, this is not a BST, so there is no guarantee that
 * the tree's order maintains that defined by a BST.
 *
 * Public methods:
 *  void deleteTree()      - deletes the tree.
 *  Node insert(int data)  - inserts a new node into the tree containing value 'data'.
 *  String preOrder()      - return the tree in 'preorder' traversal in a String object.
 *
 * The following methods you will complete:
 *  void replaceValue(int k, int l) - if data value 'k' is in tree, replace with data
 *                           value 'l'; for simplicity at the moment, do not re-organize
 *                           the tree based on new value which means this operation may
 *                           violate the binary tree definition.
 *  int findMin()          - returns the small data value stored in the tree.
 *  int nodesGT(int val)   - return the number of nodes in the tree that have a data value
 *                           greater than 'val'.
 *  double average()       - return the average data value of all data values stored in
 *                           the tree.
 */

public class BinaryTree {

    public BinaryTree() {
        root = null;
    }
    public BinaryTree(Node node) {
        root = node;
    }

    static class Node {
        Node(int d) {
            data = d;
            left = null;
            right = null;
        }
        Node(int d, Node l, Node r) {
            data = d;
            left = l;
            right = r;
        }
        public int data;
        public Node left;
        public Node right;
    }

    public Node root;

    public void deleteTree() {
        root = null;
    }

    public void replaceValue(int oldVal, int newVal) {
        replaceValueHelper(root, oldVal, newVal);
    }

    public int findMin() {
        return findMinHelper(root);
    }

    public int nodesGT(int val) {
        return nodesGTHelper(root, val);
    }

    Node insert(int data) {
        Node tempNode = new Node(data);
        if (root == null)
            return root = tempNode;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node front = queue.peek();
            if (front.left == null) {
                front.left = tempNode;
                break;
            } else if (front.right == null) {
                front.right = tempNode;
                break;
            } else {
                queue.remove();
            }
            if (front.left != null)
                queue.add(front.left);
            if (front.right != null)
                queue.add(front.right);
        }
        return tempNode;
    }

    public String preOrder() {
        return preOrderHelper(root);
    }

    public String preOrderHelper(Node node) {
        if (node == null) {
            return "";
        }
        return node.data + " " + preOrderHelper(node.left) + preOrderHelper(node.right);
    }

    private void replaceValueHelper(Node node, int oldVal, int newVal) {
        if (node == null) return; // Base case: stop if node is null
        if (node.data == oldVal) node.data = newVal; // Replace matching value
        replaceValueHelper(node.left, oldVal, newVal);
        replaceValueHelper(node.right, oldVal, newVal);
    }

    private int findMinHelper(Node node) {
        if (node == null) return Integer.MAX_VALUE; // If tree is empty, return max int value
        int leftMin = findMinHelper(node.left);
        int rightMin = findMinHelper(node.right);
        return Math.min(node.data, Math.min(leftMin, rightMin)); // Return the smallest value
    }

    private int nodesGTHelper(Node node, int val) {
        if (node == null) return 0; // Base case: return 0 if node is null
        int count = (node.data > val) ? 1 : 0; // Count node if its value is greater than val
        count += nodesGTHelper(node.left, val);
        count += nodesGTHelper(node.right, val);
        return count;
    }

    public double average() {
        int[] sumAndCount = averageHelper(root);
        return sumAndCount[1] == 0 ? 0 : (double) sumAndCount[0] / sumAndCount[1]; // Compute average
    }

    private int[] averageHelper(Node node) {
        if (node == null) return new int[]{0, 0}; // Return zero if tree is empty
        int[] left = averageHelper(node.left);
        int[] right = averageHelper(node.right);
        int sum = left[0] + right[0] + node.data;
        int count = left[1] + right[1] + 1;
        return new int[]{sum, count}; // Return total sum and node count
    }
}
