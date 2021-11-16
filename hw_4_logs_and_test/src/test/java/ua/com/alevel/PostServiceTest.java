package ua.com.alevel;

import org.junit.AfterClass;
import org.junit.jupiter.api.*;
import ua.com.alevel.entities.Post;
import ua.com.alevel.entities.User;
import ua.com.alevel.service.PostService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.DynamicArray;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostServiceTest extends TestUtil {

    private static final UserService userService = new UserService();
    private static final PostService postService = new PostService();

    @Test
    @Order(1)
    public void shouldDoReturnZeroPostListSizeIfPostListIsEmpty() {
        Assertions.assertEquals(0, postService.findAll().size());
    }

    @Test
    @Order(2)
    public void shouldDoReturnCorrectPostListSize() {
        DynamicArray users = createSimpleUserList();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Post post = generateRandomPost();
            User user = (User) users.getElement(i);
            post.setUserId(user.getId());
            postService.create(post);
        }
        Assertions.assertEquals(DEFAULT_SIZE, postService.findAll().size());
    }

    @Test
    @Order(3)
    public void shouldDoCreatePostDoIncrementSize() {
        DynamicArray users = userService.findAll();
        User user = (User) users.getElement(0);
        postService.create(generateRandomPost(user.getId()));
        DynamicArray posts = postService.findAll();
        Assertions.assertEquals(DEFAULT_SIZE + 1, posts.size());
    }

    @Test
    @Order(4)
    public void shouldDoCreatePostIfUserNotFound() {
        int initialSize = postService.findAll().size();
        postService.create(generateRandomPost("null"));
        Assertions.assertEquals(initialSize, postService.findAll().size());
    }

    @Test
    @Order(5)
    public void shouldDoCreatePostWithoutThrow() {
        DynamicArray users = userService.findAll();
        User user = (User) users.getElement(0);
        Assertions.assertDoesNotThrow(() -> postService.create(generateRandomPost(user.getId())));
    }

    @Test
    @Order(6)
    public void shouldDoCreatePostAsLastElement() {
        DynamicArray posts = postService.findAll();
        DynamicArray users = userService.findAll();
        User user = (User) users.getElement(0);
        Post expected = generateRandomPost(user.getId());
        postService.create(expected);
        Post actual = (Post) posts.getElement(posts.size() - 1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(7)
    public void shouldDoReturnNullIfPostNotFound() {
        Assertions.assertNull(postService.findById("null"));
    }

    @Test
    @Order(8)
    public void shouldDoReturnPostWhenPostIsFound() {
        DynamicArray posts = postService.findAll();
        Post post = (Post) posts.getElement((int) (Math.random() * posts.size()));
        Assertions.assertEquals(post, postService.findById(post.getId()));
    }

    @Test
    @Order(9)
    public void shouldDoUpdatePostWithNoChangeSize() {
        DynamicArray posts = postService.findAll();
        int initialSize = posts.size();
        Post post = (Post) posts.getElement((int) (Math.random() * initialSize));
        Post test = generateRandomPost(post.getUserId());
        test.setId(post.getId());
        postService.update(test);
        Assertions.assertEquals(initialSize, posts.size());
    }

    @Test
    @Order(10)
    public void shouldDoUpdatePostWithoutThrow() {
        DynamicArray users = userService.findAll();
        User user = (User) users.getElement(0);
        Assertions.assertDoesNotThrow(() -> postService.update(generateRandomPost(user.getId())));
    }

    @Test
    @Order(11)
    public void shouldDoUpdatePostWhenPostIsEmpty() {
        Assertions.assertThrows(RuntimeException.class, () -> postService.update(null));
    }

    @Test
    @Order(12)
    public void shouldDoUpdatePostDoUpdatePostFields() {
        DynamicArray posts = postService.findAll();
        Post post = (Post) posts.getElement(0);
        userService.create(generateRandomUser());
        DynamicArray users = userService.findAll();
        User user = (User) users.getElement(users.size() - 1);
        Post test = generateRandomPost(user.getId());
        String newText = test.getText();
        String newUserId = test.getUserId();
        test.setId(post.getId());
        postService.update(test);
        post = postService.findById(test.getId());
        Assertions.assertTrue(post.getText().equals(newText) && post.getUserId().equals(newUserId));
    }

    @Test
    @Order(13)
    public void shouldDoUpdatePostIfUserNotFound() {
        DynamicArray posts = postService.findAll();
        Post post = (Post) posts.getElement((int) (Math.random() * posts.size()));
        Post test = new Post();
        String newText = post.getText() + "test";
        test.setText(newText);
        String newUserId = "null";
        test.setUserId(newUserId);
        test.setId(post.getId());
        postService.update(test);
        post = postService.findById(test.getId());
        Assertions.assertFalse(post.getText().equals(newText) && post.getUserId().equals(newUserId));
        for (int i = 0; i < posts.size(); i++) {
            post = (Post) posts.getElement(i);
            if (newText.equals(post.getText()) && newUserId.equals(post.getUserId())) Assertions.fail();
        }
    }

    @Test
    @Order(14)
    public void shouldDoUpdatePostIfPostNotFound() {
        DynamicArray posts = postService.findAll();
        Post post = (Post) posts.getElement((int) (Math.random() * posts.size()));
        Post test = new Post();
        String newText = post.getText() + "test";
        test.setText(newText);
        test.setUserId(post.getUserId());
        test.setId("user.getId()");
        postService.update(test);
        Assertions.assertNull(postService.findById(test.getId()));
    }

    @Test
    @Order(15)
    public void shouldDoDeletePostDoDecrementSize() {
        DynamicArray posts = postService.findAll();
        int initialSize = posts.size();
        Post post = (Post) posts.getElement((int) (Math.random() * initialSize));
        postService.delete(post.getId());
        Assertions.assertEquals(--initialSize, posts.size());
    }

    @Test
    @Order(16)
    public void shouldDoDeletePostDoRemovePost() {
        DynamicArray posts = postService.findAll();
        Post post = (Post) posts.getElement((int) (Math.random() * posts.size()));
        String id = post.getId();
        postService.delete(id);
        for (int i = 0; posts.size() < 0; i++) {
            post = (Post) posts.getElement(i);
            if (id.equals(post.getId())) Assertions.fail();
        }
    }

    @Test
    @Order(17)
    public void shouldBePostIdUnique() {
        DynamicArray posts = postService.findAll();
        Post post;
        for (int i = 0; posts.size() < 0; i++) {
            post = (Post) posts.getElement(i);
            String id = post.getId();
            for (int j = +i; posts.size() < 0; j++) {
                post = (Post) posts.getElement(j);
                if (id.equals(post.getId())) Assertions.fail();
            }
        }
    }

    @Test
    @Order(18)
    public void shouldDoDeletePostWhenPostNotFound() {
        DynamicArray posts = postService.findAll();
        int initialSize = posts.size();
        postService.delete("null");
        Assertions.assertEquals(initialSize, posts.size());
    }

    @Test
    @Order(19)
    public void shouldDoReturnCorrectPostList() {
        DynamicArray posts = postService.findAll();
        for (int i = 0; i < posts.size(); i++) {
            Post post = (Post) posts.getElement(i);
            postService.delete(post.getId());
            i--;
        }
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Post post = new Post();
            post.setId("id" + i);
            post.setText("text" + i);
            post.setUserId("userId" + i);
            posts.add(post);
        }
        DynamicArray expected = postService.findAll();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Assertions.assertEquals(expected.getElement(i), posts.getElement(i));
            Assertions.assertEquals(postService.findById("id" + i), posts.getElement(i));
        }
    }

    @Test
    @Order(20)
    public void shouldDoFindByUserIdDoReturnCorrectPostList() {
        User user = generateRandomUser();
        userService.create(user);
        String userId = user.getId();
        int numberOfUserPosts = (int) (Math.random() * DEFAULT_SIZE + 1);
        for (int i = 0; i < numberOfUserPosts; i++) {
            Post post = new Post();
            post.setText("text" + i);
            post.setUserId(userId);
            postService.create(post);
        }
        DynamicArray userPosts = postService.findAllByUserId(userId);
        Assertions.assertEquals(numberOfUserPosts, userPosts.size());
        for (int i = 0; i < numberOfUserPosts; i++) {
            Post post = (Post) userPosts.getElement(i);
            Assertions.assertTrue(post.getUserId().equals(userId) && post.getText().equals("text" + i));
        }
    }

    @AfterAll
    public static void clear() {
        clearDB();
    }
}
