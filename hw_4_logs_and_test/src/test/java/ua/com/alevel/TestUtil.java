package ua.com.alevel;

import org.apache.commons.lang3.RandomStringUtils;
import ua.com.alevel.entities.Post;
import ua.com.alevel.entities.User;
import ua.com.alevel.service.PostService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.DynamicArray;

public class TestUtil {

    public static final int DEFAULT_SIZE = 7;
    public static final int STRING_LENGTH = 5;
    public static final int MAX_AGE = 100;

    private static final UserService userService = new UserService();
    private static final PostService postService = new PostService();

    public static User generateRandomUser() {
        User user = new User();
        user.setName(RandomStringUtils.randomAlphabetic(STRING_LENGTH));
        user.setAge((int) (Math.random() * MAX_AGE) + 1);
        return user;
    }

    public static Post generateRandomPost() {
        Post post = new Post();
        post.setText(RandomStringUtils.randomAlphabetic(STRING_LENGTH));
        post.setUserId(RandomStringUtils.randomAlphabetic(STRING_LENGTH));
        return post;
    }

    public static Post generateRandomPost(String userId) {
        Post post = new Post();
        post.setText(RandomStringUtils.randomAlphabetic(STRING_LENGTH));
        post.setUserId(userId);
        return post;
    }

    public static DynamicArray createSimpleUserList() {
        DynamicArray users = userService.findAll();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            User user = new User();
            user.setId("id" + i);
            user.setName("name" + i);
            user.setAge(i);
            users.add(user);
        }
        return users;
    }

    public static void clearDB() {
        DynamicArray posts = postService.findAll();
        for (int i = 0; i < posts.size(); i++) {
            Post post = (Post) posts.getElement(i);
            postService.delete(post.getId());
            i--;
        }
        DynamicArray users = userService.findAll();
        for (int i = 0; i < users.size(); i++) {
            User user = (User) users.getElement(i);
            userService.delete(user.getId());
            i--;
        }
    }
}
