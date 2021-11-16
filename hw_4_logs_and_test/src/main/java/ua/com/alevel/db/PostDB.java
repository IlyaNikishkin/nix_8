package ua.com.alevel.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entities.Post;
import ua.com.alevel.util.DynamicArray;

import java.util.UUID;

public class PostDB {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    private final DynamicArray posts;
    private static PostDB instance;

    private PostDB() {
        posts = new DynamicArray();
    }

    public static PostDB getInstance() {
        if (instance == null) {
            instance = new PostDB();
        }
        return instance;
    }

    public void create(Post post) {
        post.setId(generateId());
        posts.add(post);
        LOGGER_INFO.info("post added, postId=" + post.getId() + ", userId=" + post.getUserId());
    }

    public void update(Post post) {
        Post current = findById(post.getId());
        if (current != null) {
            current.setText(post.getText());
            current.setUserId(post.getUserId());
            LOGGER_INFO.info("post updated, postId=" + post.getId() +
                    ", text=" + cut(post.getText()) +
                    ", userId=" + post.getUserId());
        } else {
            LOGGER_WARN.warn("post updating failed, post not found, postId=" + post.getId());
        }
    }

    public void delete(String id) {
        try {
            posts.delete(findPositionById(id));
            LOGGER_INFO.info("post removed, postId=" + id);
        } catch (Exception e) {
            System.out.println("post not found");
            LOGGER_WARN.warn("post deleting failed, post not found, postId=" + id);
        }
    }

    public Post findById(String id) {
        Post post;
        try {
            post = (Post) posts.getElement(findPositionById(id));
        } catch (Exception e) {
            System.out.println("post not found");
            post = null;
        }
        return post;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (findPositionById(id) != -1) {
            return generateId();
        }
        return id;
    }

    public DynamicArray findAll() {
        return posts;
    }

    public DynamicArray findAllByUserId(String userId) {
        Post post;
        DynamicArray userPosts = new DynamicArray();
        for (int i = 0; i < posts.size(); i++) {
            post = (Post) posts.getElement(i);
            if (userId.equals(post.getUserId())) {
                userPosts.add(post);
            }
        }
        return userPosts;
    }

    private int findPositionById(String id) {
        int position = -1;
        Post post;
        for (int i = 0; i < posts.size(); i++) {
            post = (Post) posts.getElement(i);
            if (post.getId().equals(id)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public static String cut(String text) {
        final int LIMIT = 10;
        if (text.length() > LIMIT) return text.substring(0, LIMIT) + "...";
        else return text;
    }
}