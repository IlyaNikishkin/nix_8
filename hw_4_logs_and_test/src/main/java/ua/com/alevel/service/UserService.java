package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.persistence.dao.PostDao;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.entities.Post;
import ua.com.alevel.entities.User;
import ua.com.alevel.util.DynamicArray;

public class UserService {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    private final UserDao userDao = new UserDao();
    private final PostDao postDao = new PostDao();

    public void create(User user) {
        userDao.create(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(String id) {
        LOGGER_INFO.info("start UserService.delete, userId=" + id);
        DynamicArray userPosts = postDao.findAllByUserId(id);
        for (int i = 0; i < userPosts.size(); i++) {
            Post post = (Post) userPosts.getElement(i);
            LOGGER_INFO.info("related post found, postId=" + post.getId());
            postDao.delete(post.getId());
        }
        userDao.delete(id);
    }

    public User findById(String id) {
        LOGGER_INFO.info("run UserService.findById");
        return userDao.findById(id);
    }

    public DynamicArray findAll() {
        LOGGER_INFO.info("run UserService.findAll");
        return userDao.findAll();
    }
}
