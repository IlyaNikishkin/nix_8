package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.StudentRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.StudentResponseDto;

public interface StudentFacade extends BaseFacade<StudentRequestDto, StudentResponseDto> {

    PageData<StudentResponseDto> findAllStudentsByGroup(WebRequest request, Long groupId);
}
