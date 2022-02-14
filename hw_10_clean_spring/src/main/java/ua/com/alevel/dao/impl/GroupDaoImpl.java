package ua.com.alevel.dao.impl;

import ua.com.alevel.dao.GroupDao;
import ua.com.alevel.entity.Group;
import ua.com.alevel.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class GroupDaoImpl implements GroupDao {

    private final EntityManagerFactory entityManagerFactory;

    public GroupDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void create(Group entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Group entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("delete from Group g where g.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean existById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager
                .createQuery("select count(id) from Group where id = :id")
                .setParameter("id", id);
        boolean existById = (Long) query.getSingleResult() == 1;
        entityManager.close();
        return existById;
    }

    @Override
    public Group findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Group group = null;
        try {
            group = entityManager.find(Group.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return group;
    }

    @Override
    public List<Group> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Group> groups = Collections.emptyList();
        try {
            groups = entityManager.createQuery("select g from Group g")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return groups;
    }

    @Override
    public List<Student> findListStudents(Long groupId) {
        return findById(groupId).getStudents();
    }
}