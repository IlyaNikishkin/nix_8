package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.persistence.dao.PostDao;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.entities.Post;
import ua.com.alevel.util.DynamicArray;

public class PostService {

    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    private final PostDao postDao = new PostDao();
    private final UserDao userDao = new UserDao();

    public void create(Post post) {
        if (userDao.findById(post.getUserId()) != null) {
            postDao.create(post);
        } else {
            LOGGER_WARN.warn("post creating failed, user not found, userId=" + post.getUserId());
        }
    }

    public void update(Post post) {
        if (userDao.findById(post.getUserId()) != null) postDao.update(post);
        else
            LOGGER_WARN.warn("post updating failed, user not found, postId=" + post.getId() +
                    ", userId=" + post.getUserId());
    }

    public void delete(String id) {
        postDao.delete(id);
    }

    public Post findById(String id) {
        LOGGER_INFO.info("run PostService.findById, id=" + id);
        return postDao.findById(id);
    }

    public DynamicArray findAll() {
        LOGGER_INFO.info("run PostService.findAll");
        return postDao.findAll();
    }

    public DynamicArray findAllByUserId(String userId) {
        LOGGER_INFO.info("run PostService.findAllByUserId, userId=" + userId);
        return postDao.findAllByUserId(userId);
    }
}
