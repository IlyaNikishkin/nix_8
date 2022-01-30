package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.dao.GroupDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.persistence.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {

    private final EntityManager entityManager;

    public GroupDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void create(Group entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void update(Group entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void delete(Long id) {
        entityManager.createQuery("delete from Group where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(id) from Group where id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    @Transactional(readOnly = true)
    public Group findById(Long id) {
        return entityManager.find(Group.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Group> findAll(DataTableRequest request) {
        int fr = (request.getCurrentPage() - 1) * request.getPageSize();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }
        List<Group> groups = entityManager.createQuery(criteriaQuery)
                .setFirstResult(fr)
                .setMaxResults(request.getPageSize())
                .getResultList();
        DataTableResponse<Group> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(groups);
        return dataTableResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        Query query = entityManager.createQuery("select count(id) from Group ");
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Student> findAllStudentsByGroup(DataTableRequest request, Long groupId) {
        List<Student> students = findById(groupId).getStudents().stream().toList();
        DataTableResponse<Student> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(students);
        return dataTableResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findListStudents(Long groupId) {
        return findById(groupId).getStudents();
    }
}
