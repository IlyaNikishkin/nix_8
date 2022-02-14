package ua.com.alevel.facade;

import ua.com.alevel.dto.request.GroupRequestDto;
import ua.com.alevel.entity.Group;
import ua.com.alevel.entity.Student;

import java.util.List;

public interface GroupFacade extends BaseFacade<GroupRequestDto, Group> {

    List<Student> findAllStudentsByGroup(Long id);
}
