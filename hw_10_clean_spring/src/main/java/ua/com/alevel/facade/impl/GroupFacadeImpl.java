package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.request.GroupRequestDto;
import ua.com.alevel.entity.Group;
import ua.com.alevel.entity.Student;
import ua.com.alevel.facade.GroupFacade;
import ua.com.alevel.service.GroupService;

import java.util.List;

@Service
public class GroupFacadeImpl implements GroupFacade {

    private final GroupService groupService;

    public GroupFacadeImpl(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void create(GroupRequestDto groupRequestDto) {
        Group group = new Group();
        group.setName(groupRequestDto.getName());
        groupService.create(group);
    }

    @Override
    public void update(GroupRequestDto groupRequestDto, Long id) {
        Group group = groupService.findById(id);
        if (group != null) {
            group.setName(groupRequestDto.getName());
            groupService.update(group);
        }
    }

    @Override
    public void delete(Long id) {
        groupService.delete(id);
    }

    @Override
    public Group findById(Long id) {
        return groupService.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return groupService.findAll();
    }

    @Override
    public List<Student> findAllStudentsByGroup(Long id) {
        return groupService.findAllStudentsByGroup(id);
    }
}
