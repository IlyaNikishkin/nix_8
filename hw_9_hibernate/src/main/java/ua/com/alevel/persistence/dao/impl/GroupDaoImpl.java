package ua.com.alevel.persistence.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.dao.GroupDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.persistence.entity.Student;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupDaoImpl implements GroupDao {

    private final SessionFactory sessionFactory;

    public GroupDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Group entity) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void update(Group entity) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void delete(Long id) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.createQuery("delete from Group where id = :id").
                    setParameter("id", id).
                    executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public boolean existById(Long id) {
        Session session = getSession();
        Query query = session
                .createQuery("select count(id) from Group where id = :id")
                .setParameter("id", id);
        boolean existById = (Long) query.getSingleResult() == 1;
        closeSession(session);
        return existById;
    }

    @Override
    public Optional<Group> findById(Long id) {
        Session session = getSession();
        Optional<Group> optionalGroup = Optional.ofNullable(session.find(Group.class, id));
        closeSession(session);
        return optionalGroup;
    }

    @Override
    public DataTableResponse<Group> findAll(DataTableRequest request) {
        int fr = (request.getCurrentPage() - 1) * request.getPageSize();
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }
        List<Group> groups = session.createQuery(criteriaQuery)
                .setFirstResult(fr)
                .setMaxResults(request.getPageSize())
                .getResultList();
        closeSession(session);
        DataTableResponse<Group> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(groups);
        return dataTableResponse;
    }

    @Override
    public long count() {
        Session session = getSession();
        Query query = getSession()
                .createQuery("select count(id) from Group");
        long count = (Long) query.getSingleResult();
        closeSession(session);
        return count;
    }

    private Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    private void closeSession(Session session) {
        try {
            session.close();
        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    @Override
    public DataTableResponse<Student> findAllStudentsByGroup(DataTableRequest request, Long groupId) {
        List<Student> students = findById(groupId).get().getStudents().stream().toList();
        DataTableResponse<Student> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(students);
        return dataTableResponse;
    }

    @Override
    public List<Student> findListStudents(Long groupId) {
        return findById(groupId).get().getStudents();
    }
}
