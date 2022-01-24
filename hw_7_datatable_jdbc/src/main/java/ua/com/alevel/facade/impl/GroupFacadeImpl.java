package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.GroupFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;
import ua.com.alevel.service.GroupService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.GroupRequestDto;
import ua.com.alevel.view.dto.response.GroupResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.List;
import java.util.stream.Collectors;

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
        group.setGrade(groupRequestDto.getGrade());
        groupService.create(group);
    }

    @Override
    public void update(GroupRequestDto groupRequestDto, Long id) {
        Group group = groupService.findById(id);
        if (group != null) {
            group.setName(groupRequestDto.getName());
            group.setGrade(groupRequestDto.getGrade());
            groupService.update(group);
        }
    }

    @Override
    public void delete(Long id) {
        groupService.delete(id);
    }

    @Override
    public GroupResponseDto findById(Long id) {
        return new GroupResponseDto(groupService.findById(id));
    }

    @Override
    public PageData<GroupResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Group> tableResponse = groupService.findAll(dataTableRequest);

        List<GroupResponseDto> products = tableResponse.getItems().stream().
                map(GroupResponseDto::new).
                collect(Collectors.toList());

        PageData<GroupResponseDto> pageData = (PageData<GroupResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(products);
        return pageData;
    }
}
