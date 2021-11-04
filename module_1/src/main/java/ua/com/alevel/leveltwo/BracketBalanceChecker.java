package ua.com.alevel.leveltwo;

import java.io.BufferedReader;
import java.io.IOException;

public class BracketBalanceChecker {

    public void run(BufferedReader reader) throws IOException {
        int parentheses = 0;
        int boxBrackets = 0;
        int braces = 0;
        boolean balance = true;

        System.out.println("Lvl-2.Task-1 Enter your line:");
        String src = reader.readLine();
        for (int i = 0; i < src.length(); i++) {
            if (src.charAt(i) == '(') parentheses++;
            if (src.charAt(i) == ')') parentheses--;
            if (src.charAt(i) == '[') boxBrackets++;
            if (src.charAt(i) == ']') boxBrackets--;
            if (src.charAt(i) == '{') braces++;
            if (src.charAt(i) == '}') braces--;
            if (parentheses < 0 || boxBrackets < 0 || braces < 0) {
                balance = false;
                break;
            }
        }
        if (parentheses == 0 && boxBrackets == 0 && braces == 0 && balance) {
            System.out.println("Correct");
        } else System.out.println("Incorrect");
    }
}
