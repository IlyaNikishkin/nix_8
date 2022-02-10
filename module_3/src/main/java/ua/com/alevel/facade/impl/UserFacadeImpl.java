package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.UserRequestDto;
import ua.com.alevel.view.dto.response.UserResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.AccountResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void create(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        userService.create(user);
    }

    @Override
    public void update(UserRequestDto userRequestDto, Long id) {
        User user = userService.findById(id);
        if (user != null) {
            user.setName(userRequestDto.getName());
            userService.update(user);
        }
    }

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }

    @Override
    public UserResponseDto findById(Long id) {
        return new UserResponseDto(userService.findById(id));
    }

    @Override
    public PageData<UserResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<User> tableResponse = userService.findAll(dataTableRequest);
        List<UserResponseDto> users = tableResponse.getItems().stream().
                map(UserResponseDto::new).
                collect(Collectors.toList());
        PageData<UserResponseDto> pageData = (PageData<UserResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(users);
        return pageData;
    }

    @Override
    public PageData<AccountResponseDto> findAllAccountsByUser(WebRequest request, Long userId) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Account> tableResponse = userService.findAllAccountsByUser(dataTableRequest, userId);
        List<AccountResponseDto> accounts = tableResponse.getItems().stream().
                map(AccountResponseDto::new).
                collect(Collectors.toList());
        PageData<AccountResponseDto> pageData = (PageData<AccountResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(accounts);
        return pageData;
    }
}
