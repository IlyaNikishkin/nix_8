package ua.com.alevel.dao.impl;

import ua.com.alevel.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class StudentDaoImpl implements ua.com.alevel.dao.StudentDao {

    private final EntityManagerFactory entityManagerFactory;

    public StudentDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void create(Student entity) {
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
    public void update(Student entity) {
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
        if (existById(id)) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.createQuery("delete from Student s where s.id = :id")
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
    }

    @Override
    public boolean existById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager
                .createQuery("select count(id) from Student where id = :id")
                .setParameter("id", id);
        boolean existById = (Long) query.getSingleResult() == 1;
        entityManager.close();
        return existById;
    }

    @Override
    public Student findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Student student = null;
        try {
            student = entityManager.find(Student.class, id);
        } catch (Exception e) {
        } finally {
            entityManager.close();
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Student> students = Collections.emptyList();
        try {
            students = entityManager.createQuery("select s from Student s")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return students;
    }
}
