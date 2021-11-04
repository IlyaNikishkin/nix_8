package ua.com.alevel.levelone;

import java.io.BufferedReader;
import java.io.IOException;

public class UniqueNumberCounter {

    public void run(BufferedReader reader) throws IOException {
        System.out.println("Lvl-1.Task-1 Enter your line (example: '\"6 7 3 3 0'\"):");
        String src = reader.readLine();
        src = src.replaceAll("\\s", "");
        try {
            String numericTest = src;
            Integer.parseInt(numericTest);
            System.out.println(src.chars().distinct().count());
        } catch (Exception e) {
            System.out.println("Line is not numeric");
        }
    }
}
