package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.TransactionResponseDto;

import java.time.LocalDateTime;

public interface AccountFacade extends BaseFacade<AccountRequestDto, AccountResponseDto> {

    PageData<TransactionResponseDto> findAllTransactionsByAccount(WebRequest request, Long accountId);

    LocalDateTime minDateTransaction(Long id);

    LocalDateTime maxDateTransaction(Long id);

    void exportToCsv(AccountRequestDto dto, Long id);

    PageData<AccountResponseDto> findAllAccounts();
}
