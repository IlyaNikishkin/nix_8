package ua.com.alevel.task_a;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberSumCalculator {

    public void run(BufferedReader reader) throws IOException {
        System.out.println("Task 1. Enter your line:");
        String text = reader.readLine();
        Pattern pattern = Pattern.compile("[\\d]");
        Matcher matcher = pattern.matcher(text);
        int sum = 0; boolean digit = false;
        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group()); digit = true;
        }
        if (digit) System.out.println(sum);
        else System.out.println("there is no number in your line");
    }
}
