package ua.com.alevel.service;

import ua.com.alevel.entity.Group;
import ua.com.alevel.entity.Student;

import java.util.List;

public interface GroupService extends BaseService<Group> {
    List<Student> findAllStudentsByGroup(Long courseId);
}