package ua.com.alevel.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entities.Post;
import ua.com.alevel.service.PostService;
import ua.com.alevel.util.DynamicArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PostController {

    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger("error");

    private final PostService postService = new PostService();

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
                1 - New Post
                2 - Update Post
                3 - Delete Post
                4 - Find Post by ID
                5 - Find All Posts
                6 - Find All Posts by User ID
                0 - Back to Main Menu""");
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1" -> create(reader);
            case "2" -> update(reader);
            case "3" -> delete(reader);
            case "4" -> findById(reader);
            case "5" -> findAll();
            case "6" -> findAllByUserId(reader);
        }
        runNavigation();
    }

    private void create(BufferedReader reader) {
        System.out.println("PostController.create");
        try {
            System.out.print("text: ");
            String text = reader.readLine();
            System.out.print("user id: ");
            String userId = reader.readLine();
            if (userId.isBlank()) System.out.println("Invalid input");
            else {
                Post post = new Post();
                post.setText(text);
                post.setUserId(userId);
                postService.create(post);
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Age must be an integer");
        }
    }

    private void update(BufferedReader reader) {
        System.out.println("PostController.update");
        try {
            System.out.print("post id: ");
            String id = reader.readLine();
            System.out.print("new text: ");
            String text = reader.readLine();
            System.out.print("userId: ");
            String userId = reader.readLine();
            if (id.isBlank() || userId.isBlank()) System.out.println("Invalid input");
            else {
                Post post = new Post();
                post.setId(id);
                post.setText(text);
                post.setUserId(userId);
                postService.update(post);
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        }
    }

    private void delete(BufferedReader reader) {
        System.out.println("PostController.delete");
        try {
            System.out.print("post id: ");
            String id = reader.readLine();
            if (id.isBlank()) System.out.println("id empty");
            else postService.delete(id);
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        }
    }

    private void findById(BufferedReader reader) {
        System.out.println("PostController.findById");
        try {
            System.out.print("post id: ");
            String id = reader.readLine();
            if (id.isBlank()) System.out.println("id empty");
            else {
                Post post = postService.findById(id);
                if (post != null) System.out.println("post = " + post);
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        }
    }

    private void findAll() {
        System.out.println("PostController.findAll");
        DynamicArray posts = postService.findAll();
        if (posts != null && posts.size() != 0) {
            posts.out();
        } else System.out.println("posts empty");
    }

    private void findAllByUserId(BufferedReader reader) {
        System.out.println("PostController.findAllByUserId");
        try {
            System.out.print("user id: ");
            String userId = reader.readLine();
            if (userId.isBlank()) System.out.println("id empty");
            else {
                DynamicArray postsByUserId = postService.findAllByUserId(userId);
                if (postsByUserId != null && postsByUserId.size() != 0) {
                    postsByUserId.out();
                } else System.out.println("posts not found");
            }
        } catch (IOException e) {
            LOGGER_ERROR.error("problem: = " + e.getMessage());
        }
    }
}
