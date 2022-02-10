package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.UserRequestDto;
import ua.com.alevel.view.dto.response.UserResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.AccountResponseDto;

import java.util.List;

public interface UserFacade extends BaseFacade<UserRequestDto, UserResponseDto> {

    PageData<AccountResponseDto> findAllAccountsByUser(WebRequest request, Long userId);
}
