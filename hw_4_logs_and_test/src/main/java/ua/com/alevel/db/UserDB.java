package ua.com.alevel.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.entities.User;
import ua.com.alevel.util.DynamicArray;

import java.util.UUID;

public class UserDB {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    private final DynamicArray users;
    private static UserDB instance;

    private UserDB() {
        users = new DynamicArray();
    }

    public static UserDB getInstance() {
        if (instance == null) {
            instance = new UserDB();
        }
        return instance;
    }

    public void create(User user) {
        user.setId(generateId());
        users.add(user);
        LOGGER_INFO.info("user added, userId=" + user.getId());
    }

    public void update(User user) {
        User current = findById(user.getId());
        if (current != null) {
            current.setAge(user.getAge());
            current.setName(user.getName());
            LOGGER_INFO.info("user updated, userId=" + user.getId());
        } else {
            LOGGER_WARN.warn("user updating failed, user not found, userId=" + user.getId());
        }
    }

    public void delete(String id) {
        try {
            users.delete(findPositionById(id));
            LOGGER_INFO.info("user deleted, userId=" + id);
        } catch (Exception e) {
            System.out.println("user not found");
            LOGGER_WARN.warn("user deleting failed, user not found, userId=" + id);
        }
    }

    public User findById(String id) {
        User user;
        try {
            user = (User) users.getElement(findPositionById(id));
        } catch (Exception e) {
            System.out.println("user not found");
            user = null;
        }
        return user;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (findPositionById(id) != -1) {
            return generateId();
        }
        return id;
    }

    public DynamicArray findAll() {
        return users;
    }

    private int findPositionById(String id) {
        int position = -1;
        User user;
        for (int i = 0; i < users.size(); i++) {
            user = (User) users.getElement(i);
            if (user.getId().equals(id)) {
                position = i;
                break;
            }
        }
        return position;
    }
}