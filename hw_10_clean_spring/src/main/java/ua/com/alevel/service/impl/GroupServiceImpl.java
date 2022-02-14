package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.GroupDao;
import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.entity.Group;
import ua.com.alevel.entity.Student;
import ua.com.alevel.service.GroupService;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;
    private final StudentDao studentDao;

    public GroupServiceImpl(GroupDao groupDao, StudentDao studentDao) {
        this.groupDao = groupDao;
        this.studentDao = studentDao;
    }

    @Override
    public void create(Group entity) {
        groupDao.create(entity);
    }

    @Override
    public void update(Group entity) {
        if (groupDao.existById(entity.getId())) {
            groupDao.update(entity);
        }
    }

    @Override
    public void delete(Long id) {
        if (groupDao.existById(id)) {
            List<Student> students = findAllStudentsByGroup(id);
            System.out.println(students);
            for (int i=0; i<students.size();i++) {
                studentDao.delete(students.get(i).getId());
            }
            groupDao.delete(id);
        }
    }

    @Override
    public Group findById(Long id) {
        return groupDao.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return groupDao.findAll();
    }

    @Override
    public List<Student> findAllStudentsByGroup(Long courseId) {
        return groupDao.findById(courseId).getStudents();
    }
}
