package ua.com.alevel.dao;

import ua.com.alevel.entity.Group;
import ua.com.alevel.entity.Student;

import java.util.List;

public interface GroupDao extends BaseDao<Group> {

    List<Student> findListStudents(Long groupId);
}