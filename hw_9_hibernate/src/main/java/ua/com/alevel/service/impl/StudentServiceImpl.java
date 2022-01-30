package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.dao.StudentDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.service.StudentService;
import ua.com.alevel.util.WebResponseUtil;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void create(Student entity) {
        studentDao.create(entity);
    }

    @Override
    public void update(Student entity) {
        checkByExist(entity.getId());
        studentDao.update(entity);
    }

    @Override
    public void delete(Long id) {
        checkByExist(id);
        studentDao.delete(id);
    }

    @Override
    public Student findById(Long id) {
        checkByExist(id);
        return studentDao.findById(id).get();
    }

    @Override
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        DataTableResponse<Student> dataTableResponse = studentDao.findAll(request);
        long count = studentDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    private void checkByExist(Long id) {
        if (!studentDao.existById(id)) {
            throw new EntityExistException("entity not found");
        }
    }
}
