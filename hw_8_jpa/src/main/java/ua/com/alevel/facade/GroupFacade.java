package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.GroupRequestDto;
import ua.com.alevel.view.dto.response.GroupResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

public interface GroupFacade extends BaseFacade<GroupRequestDto, GroupResponseDto> {

    PageData<StudentResponseDto> findAllStudentsByGroup(WebRequest request, Long groupId);
}
