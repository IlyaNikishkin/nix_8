package ua.com.alevel.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainController {

    final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    public void run() {
        LOGGER_INFO.info("start");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("User-post DB");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                switch (position) {
                    case "1" -> new UserController().run();
                    case "2" -> new PostController().run();
                    case "0" -> {
                        LOGGER_INFO.info("end");
                        System.exit(0);
                    }
                }
                runNavigation();
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println("1 - UserDB Menu | 2 - PostDB Menu | 0 - Exit");
    }
}
