package ua.com.alevel.service;

import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.entities.User;
import ua.com.alevel.util.DynamicArray;

public class UserService {

    private final UserDao userDao = new UserDao();

    public void create(User user) {
        userDao.create(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(String id) {
        userDao.delete(id);
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public DynamicArray findAll() {
        return userDao.findAll();
    }
}
