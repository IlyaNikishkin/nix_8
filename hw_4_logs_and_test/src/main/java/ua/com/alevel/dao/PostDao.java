package ua.com.alevel.dao;

import ua.com.alevel.db.PostDB;
import ua.com.alevel.entities.Post;
import ua.com.alevel.util.DynamicArray;

public class PostDao {

    public void create(Post post) {
        PostDB.getInstance().create(post);
    }

    public void update(Post post) {
        PostDB.getInstance().update(post);
    }

    public void delete(String id) {
        PostDB.getInstance().delete(id);
    }

    public Post findById(String id) {
        return PostDB.getInstance().findById(id);
    }

    public DynamicArray findAll() {
        return PostDB.getInstance().findAll();
    }

    public DynamicArray findAllByUserId(String userId) {
        return PostDB.getInstance().findAllByUserId(userId);
    }
}