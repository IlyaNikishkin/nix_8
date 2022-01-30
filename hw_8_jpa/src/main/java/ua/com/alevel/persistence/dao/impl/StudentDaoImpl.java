package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.StudentDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {

    private final EntityManager entityManager;

    public StudentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void create(Student entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void update(Student entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void delete(Long id) {
        entityManager.createQuery("delete from Student where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(id) from Student where id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        int fr = (request.getCurrentPage() - 1) * request.getPageSize();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> from = criteriaQuery.from(Student.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }
        List<Student> students = entityManager.createQuery(criteriaQuery)
                .setFirstResult(fr)
                .setMaxResults(request.getPageSize())
                .getResultList();
        DataTableResponse<Student> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(students);
        return dataTableResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        Query query = entityManager.createQuery("select count(id) from Student");
        return (Long) query.getSingleResult();
    }
}
