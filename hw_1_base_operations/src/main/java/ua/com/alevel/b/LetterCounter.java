package ua.com.alevel.b;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class LetterCounter {

    public void run(BufferedReader reader) throws IOException {
        System.out.println("Task 2. Enter your line:");
        char[] chars = reader.readLine().toLowerCase().replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", "")
                .toCharArray();
        Arrays.sort(chars);
        if (chars.length != 0) {
            int lineNumeration = 1;
            for (int i = 0; i < chars.length; i++) {
                int count = 1;
                for (int j = i + 1; j < chars.length; j++) if (chars[i] == chars[j]) count++;
                System.out.println(lineNumeration + ". " + chars[i] + " - " + count);
                lineNumeration++;
                i += count - 1;
            }
        } else System.out.println("there is no letter in your line");
    }
}
