package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.StudentFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.persistence.entity.Student;
import ua.com.alevel.service.GroupService;
import ua.com.alevel.service.StudentService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.StudentRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentFacadeImpl implements StudentFacade {

    private final StudentService studentService;
    private final GroupService groupService;

    public StudentFacadeImpl(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }


    @Override
    public void create(StudentRequestDto studentRequestDto) {
        Group group = groupService.findById(studentRequestDto.getGroupId());
        if (group != null) {
            Student student = new Student();
            student.setGroup(group);
            student.setName(studentRequestDto.getName());
            student.setAge(studentRequestDto.getAge());
            studentService.create(student);
        }
    }

    @Override
    public void update(StudentRequestDto studentRequestDto, Long id) {
        Student student = studentService.findById(id);
        Group group = groupService.findById(studentRequestDto.getGroupId());
        if (student != null && group != null) {
            student.setName(studentRequestDto.getName());
            student.setAge(studentRequestDto.getAge());
            student.setGroup(group);
            studentService.update(student);
        }
    }

    @Override
    public void delete(Long id) {
        studentService.delete(id);
    }

    @Override
    public StudentResponseDto findById(Long id) {
        return new StudentResponseDto(studentService.findById(id));
    }

    @Override
    public PageData<StudentResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Student> tableResponse = studentService.findAll(dataTableRequest);

        List<StudentResponseDto> students = tableResponse.getItems().stream().
                map(StudentResponseDto::new).
                collect(Collectors.toList());

        PageData<StudentResponseDto> pageData = (PageData<StudentResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(students);

        return pageData;
    }


    @Override
    public PageData<StudentResponseDto> findAllStudentsByGroup(WebRequest request, Long groupId) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Student> tableResponse = studentService.findAllStudentsByGroup(dataTableRequest, groupId);

        List<StudentResponseDto> students = tableResponse.getItems().stream().
                map(StudentResponseDto::new).
                collect(Collectors.toList());

        PageData<StudentResponseDto> pageData = (PageData<StudentResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(students);

        return pageData;
    }
}
