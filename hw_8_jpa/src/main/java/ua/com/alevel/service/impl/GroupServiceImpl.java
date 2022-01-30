package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.dao.GroupDao;
import ua.com.alevel.persistence.dao.StudentDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.service.GroupService;
import ua.com.alevel.util.WebResponseUtil;

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
        checkByExist(entity.getId());
        groupDao.update(entity);
    }

    @Override
    public void delete(Long id) {
        checkByExist(id);
        List<Student> students = groupDao.findListStudents(id);
        for (Student student : students) {
            studentDao.delete(student.getId());
        }
        groupDao.delete(id);
    }

    @Override
    public Group findById(Long id) {
        checkByExist(id);
        return groupDao.findById(id);
    }

    @Override
    public DataTableResponse<Group> findAll(DataTableRequest request) {
        DataTableResponse<Group> dataTableResponse = groupDao.findAll(request);
        long count = groupDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    private void checkByExist(Long id) {
        if (!groupDao.existById(id)) {
            throw new EntityExistException("entity not found");
        }
    }

    @Override
    public DataTableResponse<Student> findAllStudentsByGroup(DataTableRequest request, Long groupId) {
        DataTableResponse<Student> dataTableResponse = groupDao.findAllStudentsByGroup(request, groupId);
        long count = groupDao.findListStudents(groupId).size();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }
}
