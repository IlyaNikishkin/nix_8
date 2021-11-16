package ua.com.alevel.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entities.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.DynamicArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserController {

    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    private final UserService userService = new UserService();

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null && !position.equals("0")) {
                crud(position, reader);
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println("""
                1 - New User
                2 - Update User
                3 - Delete User
                4 - Find User by ID
                5 - Find All Users
                0 - Back to Main Menu""");
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1" -> create(reader);
            case "2" -> update(reader);
            case "3" -> delete(reader);
            case "4" -> findById(reader);
            case "5" -> findAll();
        }
        runNavigation();
    }

    private void create(BufferedReader reader) {
        System.out.println("UserController.create");
        try {
            System.out.print("user name: ");
            String name = reader.readLine();
            System.out.print("user age: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            if (name.isBlank() || age < 0) System.out.println("Invalid input");
            else {
                User user = new User();
                user.setAge(age);
                user.setName(name);
                userService.create(user);
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Age must be an integer");
        }
    }

    private void update(BufferedReader reader) {
        System.out.println("UserController.update");
        try {
            System.out.print("user id: ");
            String id = reader.readLine();
            System.out.print("new user name: ");
            String name = reader.readLine();
            System.out.print("new user age: ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            if (name.isBlank() || age < 0 || id.isBlank()) System.out.println("Invalid input");
            else {
                User user = new User();
                user.setId(id);
                user.setAge(age);
                user.setName(name);
                userService.update(user);
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Age must be an integer");
        }
    }

    private void delete(BufferedReader reader) {
        System.out.println("UserController.delete");
        try {
            System.out.print("user id: ");
            String id = reader.readLine();
            if (id.isBlank()) System.out.println("id empty");
            else userService.delete(id);
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {
        System.out.println("UserController.findById");
        try {
            System.out.print("user id: ");
            String id = reader.readLine();
            if (id.isBlank()) System.out.println("id empty");
            else {
                User user = userService.findById(id);
                if (user != null) System.out.println("user = " + user);
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        }
    }

    private void findAll() {
        System.out.println("UserController.findAll");
        DynamicArray users = userService.findAll();
        if (users != null && users.size() != 0) {
            users.out();
        } else System.out.println("users empty");
    }
}
