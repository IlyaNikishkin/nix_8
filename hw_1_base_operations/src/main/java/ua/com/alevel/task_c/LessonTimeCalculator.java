package ua.com.alevel.task_c;

import java.io.BufferedReader;
import java.io.IOException;

public class LessonTimeCalculator {

    public void run(BufferedReader reader) throws IOException {
        final int H = 60;
        final int START = 9; final int LESSON = 45; final int BREAK_1 = 5; final int BREAK_2 = 15;

        System.out.println("Task 3. Enter your line:");
        String text = null;
        while (true) {
            try {
                text = reader.readLine();
                int result = Integer.parseInt(text);
                if (result > 0 && result < 11) {
                    result = result * LESSON + (result / 2) * BREAK_1 + ((result + 1) / 2 - 1) * BREAK_2;
                    System.out.println((result / H + START) + " " + result % H);
                    break;
                } else System.out.println("Task 3. Enter a number from 1 to 10:");
            } catch (NumberFormatException e) {
                if (text.isEmpty()) System.out.println("Task 3. Enter your line:");
                else System.out.println("Task 3. Enter a valid input:");
            }
        }
    }
}
