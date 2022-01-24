package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Student;

public interface StudentDao extends BaseDao<Student> {

    DataTableResponse<Student> findAllStudentsByGroup(DataTableRequest request, Long groupId);

    void createRelationGroupStudent(Long studentId, Long groupId);

    long countByGroupId(Long groupId);

    void deleteAllByGroupId(Long groupId);
}
