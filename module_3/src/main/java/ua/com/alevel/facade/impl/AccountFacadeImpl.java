package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.service.UserService;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.util.UsdUtil;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.TransactionResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountFacadeImpl implements AccountFacade {

    private final AccountService accountService;
    private final UserService userService;

    public AccountFacadeImpl(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void create(AccountRequestDto accountRequestDto) {
        User user = userService.findById(accountRequestDto.getUserId());
        if (user != null) {
            Account account = new Account();
            account.setUser(user);
            account.setBalance(UsdUtil.toCent(accountRequestDto.getBalance()));
            account.setWallet(accountRequestDto.getWallet());
            accountService.create(account);
        }
    }

    @Override
    public void update(AccountRequestDto accountRequestDto, Long id) {
        Account account = accountService.findById(id);
        User user = userService.findById(accountRequestDto.getUserId());
        if (account != null && user != null) {
            account.setBalance(UsdUtil.toCent(accountRequestDto.getBalance()));
            account.setWallet(accountRequestDto.getWallet());
            account.setUser(user);
            accountService.update(account);
        }
    }

    @Override
    public void delete(Long id) {
        accountService.delete(id);
    }

    @Override
    public AccountResponseDto findById(Long id) {
        return new AccountResponseDto(accountService.findById(id));
    }

    @Override
    public PageData<AccountResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Account> tableResponse = accountService.findAll(dataTableRequest);
        List<AccountResponseDto> accounts = tableResponse.getItems().stream().
                map(AccountResponseDto::new).
                collect(Collectors.toList());
        PageData<AccountResponseDto> pageData = (PageData<AccountResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(accounts);
        return pageData;
    }

    @Override
    public PageData<TransactionResponseDto> findAllTransactionsByAccount(WebRequest request, Long accountId) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Transaction> tableResponse = accountService.findAllTransactionsByAccount(dataTableRequest, accountId);
        List<TransactionResponseDto> transactions = tableResponse.getItems().stream().
                map(TransactionResponseDto::new).
                collect(Collectors.toList());
        PageData<TransactionResponseDto> pageData = (PageData<TransactionResponseDto>) WebResponseUtil.initPageData(tableResponse);
        pageData.setItems(transactions);
        return pageData;
    }

    public LocalDateTime minDateTransaction(Long id) {
        return accountService.minDateTransaction(id);
    }

    public LocalDateTime maxDateTransaction(Long id) {
        return accountService.maxDateTransaction(id);
    }

    @Override
    public void exportToCsv(AccountRequestDto dto, Long id) {
        LocalDateTime minDate = LocalDateTime.parse(dto.getMinDate() + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime maxDate = LocalDateTime.parse(dto.getMaxDate() + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        accountService.exportToCsv(minDate, maxDate, id);
    }
}
