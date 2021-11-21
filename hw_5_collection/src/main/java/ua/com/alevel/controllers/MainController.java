package ua.com.alevel.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainController {

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                switch (position) {
                    case "1" -> new IntegerController().run();
                    case "2" -> new DoubleController().run();
                    case "0" -> {
                        System.exit(0);
                    }
                }
                runNavigation();
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println("1 - Integer MathSet | 2 - Double MathSet | 0 - Exit");
    }
}
