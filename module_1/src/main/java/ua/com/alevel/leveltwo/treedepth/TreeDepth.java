package ua.com.alevel.leveltwo.treedepth;

import java.io.BufferedReader;
import java.io.IOException;

public class TreeDepth {

    public void run(BufferedReader reader) throws IOException {
        System.out.println("Lvl-2.Task-2 Enter nodes (example: \"23 43 5 3 6 14\",\n" +
                "where the first number is a root, the rest are added sequentially):");
        String[] nodes = reader.readLine().split("\\s");
        try {
            TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
            for (int i = 1; i < nodes.length; i++) {
                insert(root, Integer.parseInt(nodes[i]));
            }
            System.out.println(maxDepth(root));
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    private int maxDepth(TreeNode node) {
        if (node != null)
            return 1 + Math.max(maxDepth(node.left), maxDepth(node.right));
        else
            return 0;
    }

    private void insert(TreeNode node, int value) {
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
