package ua.com.alevel.leveltwo.treedepth;

public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    public static int maxDepth(TreeNode node) {
        if (node != null)
            return 1 + Math.max(maxDepth(node.left), maxDepth(node.right));
        else
            return 0;
    }

    public static void insert(TreeNode node, int value) {
        if (value < node.val) {
            if (node.left != null) {
                insert(node.left, value);
            } else node.left = new TreeNode(value);
        } else if (value > node.val) {
            if (node.right != null) {
                insert(node.right, value);
            } else node.right = new TreeNode(value);
        }
    }
}
