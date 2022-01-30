package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.persistence.entity.Student;

import java.util.List;

public interface GroupDao extends BaseDao<Group> {

    DataTableResponse<Student> findAllStudentsByGroup(DataTableRequest request, Long groupId);
    List<Student> findListStudents(Long groupId);
}
