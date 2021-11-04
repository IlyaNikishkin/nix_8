package ua.com.alevel.leveltwo.treedepth;

import java.io.BufferedReader;
import java.io.IOException;

public class TreeDepth {

    public static void run(BufferedReader reader) throws IOException {
        System.out.println("Lvl-2.Task-2 Enter nodes (example: \"23 43 5 3 6 14\",\n" +
                "where the first number is a root, the rest are added sequentially):");
        String[] nodes = reader.readLine().split(" ");
        try {
            TreeNode root = new TreeNode(Integer.parseInt(nodes[0]));
            for (int i = 1; i < nodes.length; i++) {
                TreeNode.insert(root, Integer.parseInt(nodes[i]));
            }
            System.out.println(TreeNode.maxDepth(root));
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }
}
