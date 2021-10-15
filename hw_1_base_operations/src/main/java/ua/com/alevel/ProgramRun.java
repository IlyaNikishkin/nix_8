package ua.com.alevel;

import ua.com.alevel.task_a.NumberSumCalculator;
import ua.com.alevel.task_b.LetterCounter;
import ua.com.alevel.task_c.LessonTimeCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProgramRun {

    public static void run() {
        preview();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String event;
        try {
            while ((event = reader.readLine()) != null) {
                switch (event) {
                    case "1" : {
                        new NumberSumCalculator().run(reader); preview();
                    } break;
                    case "2" : {
                        new LetterCounter().run(reader); preview();
                    } break;
                    case "3" : {
                        new LessonTimeCalculator().run(reader); preview();
                    } break;
                    case "0" : {
                        System.exit(0);
                    } break;
                    default:{
                        preview();
                    } break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void preview() {
        System.out.println("Enter 1-3 to select a task, or 0 to exit:");
    }
}
