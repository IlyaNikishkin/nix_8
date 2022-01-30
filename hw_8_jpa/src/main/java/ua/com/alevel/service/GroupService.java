package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.persistence.entity.Student;

public interface GroupService extends BaseService<Group> {

    DataTableResponse<Student> findAllStudentsByGroup(DataTableRequest request, Long groupId);
}