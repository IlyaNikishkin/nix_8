package ua.com.alevel;

import org.junit.jupiter.api.*;
import ua.com.alevel.entities.Post;
import ua.com.alevel.entities.User;
import ua.com.alevel.service.PostService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.DynamicArray;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest extends TestUtil {

    private static final UserService userService = new UserService();
    private static final PostService postService = new PostService();

    @Test
    @Order(1)
    public void shouldDoReturnZeroUserListSizeIfUserListIsEmpty() {
        Assertions.assertEquals(0, userService.findAll().size());
    }

    @Test
    @Order(2)
    public void shouldDoReturnCorrectUserListSize() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            User user = generateRandomUser();
            userService.create(user);
        }
        Assertions.assertEquals(DEFAULT_SIZE, userService.findAll().size());
    }

    @Test
    @Order(3)
    public void shouldDoCreateUserDoIncrementSize() {
        userService.create(generateRandomUser());
        DynamicArray users = userService.findAll();
        Assertions.assertEquals(DEFAULT_SIZE + 1, users.size());
    }

    @Test
    @Order(4)
    public void shouldDoCreateUserWithoutThrow() {
        Assertions.assertDoesNotThrow(() -> userService.create(generateRandomUser()));
    }

    @Test
    @Order(5)
    public void shouldDoCreateUserAsLastElement() {
        DynamicArray users = userService.findAll();
        User actual = generateRandomUser();
        userService.create(actual);
        User expected = (User) users.getElement(users.size() - 1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(6)
    public void shouldDoCreateUserIfUserIsEmpty() {
        Assertions.assertThrows(RuntimeException.class, () -> userService.create(null));
    }

    @Test
    @Order(7)
    public void shouldDoReturnNullIfUserNotFound() {
        Assertions.assertNull(userService.findById("null"));
    }

    @Test
    @Order(8)
    public void shouldDoReturnUserWhenUserIsFound() {
        DynamicArray users = userService.findAll();
        User user = (User) users.getElement((int) (Math.random() * users.size()));
        Assertions.assertEquals(user, userService.findById(user.getId()));
    }

    @Test
    @Order(9)
    public void shouldDoUpdateUserWithNoChangeSize() {
        DynamicArray users = userService.findAll();
        int initialSize = users.size();
        User user = (User) users.getElement((int) (Math.random() * initialSize));
        User test = generateRandomUser();
        test.setId(user.getId());
        userService.update(test);
        Assertions.assertEquals(initialSize, users.size());
    }

    @Test
    @Order(10)
    public void shouldDoUpdateUserWithoutThrow() {
        Assertions.assertDoesNotThrow(() -> userService.update(generateRandomUser()));
    }

    @Test
    @Order(11)
    public void shouldDoUpdateUserWhenUserIsEmpty() {
        Assertions.assertThrows(RuntimeException.class, () -> userService.update(null));
    }

    @Test
    @Order(12)
    public void shouldDoUpdateUserDoUpdateUserFields() {
        DynamicArray users = userService.findAll();
        int element = (int) (Math.random() * users.size());
        User user = (User) users.getElement(element);
        User test = generateRandomUser();
        String newName = test.getName();
        int newAge = test.getAge();
        test.setId(user.getId());
        userService.update(test);
        user = userService.findById(test.getId());
        Assertions.assertTrue(user.getName().equals(newName) && user.getAge() == newAge);
        user = (User) users.getElement(element);
        Assertions.assertTrue(user.getName().equals(newName) && user.getAge() == newAge);
    }

    @Test
    @Order(13)
    public void shouldDoUpdateUserIfUserNotFound() {
        User test = generateRandomUser();
        String newName = test.getName();
        int newAge = test.getAge() + 1000;
        test.setId("user.getId()");
        userService.update(test);
        User user = userService.findById(test.getId());
        Assertions.assertNull(user);
        DynamicArray users = userService.findAll();
        for (int i = 0; i < users.size(); i++) {
            user = (User) users.getElement(i);
            if (newName.equals(user.getName()) && newAge == user.getAge()) Assertions.fail();
        }
    }

    @Test
    @Order(14)
    public void shouldDoDeleteUserDoDecrementSize() {
        DynamicArray users = userService.findAll();
        int initialSize = users.size();
        User user = (User) users.getElement((int) (Math.random() * initialSize));
        userService.delete(user.getId());
        Assertions.assertEquals(--initialSize, users.size());
    }

    @Test
    @Order(15)
    public void shouldDoDeleteUserDoRemoveUser() {
        DynamicArray users = userService.findAll();
        User user = (User) users.getElement((int) (Math.random() * users.size()));
        String id = user.getId();
        userService.delete(id);
        for (int i = 0; users.size() < 0; i++) {
            user = (User) users.getElement(i);
            if (id.equals(user.getId())) Assertions.fail();
        }
    }

    @Test
    @Order(16)
    public void shouldBeUserIdUnique() {
        DynamicArray users = userService.findAll();
        User user;
        for (int i = 0; users.size() < 0; i++) {
            user = (User) users.getElement(i);
            String id = user.getId();
            for (int j = +i; users.size() < 0; j++) {
                user = (User) users.getElement(j);
                if (id.equals(user.getId())) Assertions.fail();
            }
        }
    }

    @Test
    @Order(17)
    public void shouldDoDeleteUserWhenUserNotFound() {
        DynamicArray users = userService.findAll();
        int initialSize = users.size();
        userService.delete("null");
        Assertions.assertEquals(initialSize, users.size());
    }

    @Test
    @Order(18)
    public void shouldDoDeleteUserDoRemoveRelatedPosts() {
        DynamicArray users = userService.findAll();
        for (int i = 0; i < users.size(); i++) {
            int numberOfUserPosts = (int) (Math.random() * DEFAULT_SIZE / 2 + 1);
            User user = (User) users.getElement(i);
            for (int j = 0; j < numberOfUserPosts; j++) {
                Post post = generateRandomPost();
                post.setUserId(user.getId());
                postService.create(post);
            }
        }
        int numberOfAllPosts = postService.findAll().size();
        for (int i = 0; i < users.size(); i++) {
            User user = (User) users.getElement(i);
            String userId = user.getId();
            numberOfAllPosts = numberOfAllPosts - postService.findAllByUserId(userId).size();
            userService.delete(userId);
            i--;
            Assertions.assertEquals(0, postService.findAllByUserId(userId).size());
            Assertions.assertEquals(numberOfAllPosts, postService.findAll().size());
        }
        Assertions.assertEquals(0, numberOfAllPosts);
    }

    @Test
    @Order(19)
    public void shouldDoReturnCorrectUserList() {
        DynamicArray users = createSimpleUserList();
        DynamicArray actual = userService.findAll();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Assertions.assertEquals(users.getElement(i), actual.getElement(i));
            Assertions.assertEquals(users.getElement(i), userService.findById("id" + i));
        }
    }

    @AfterAll
    public static void clear() {
        clearDB();
    }
}
