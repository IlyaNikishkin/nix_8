package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountService extends BaseService<Account> {

    DataTableResponse<Transaction> findAllTransactionsByAccount(DataTableRequest request, Long accountId);

    LocalDateTime minDateTransaction(Long id);

    LocalDateTime maxDateTransaction(Long id);

    List<Transaction> findAllTransactionsByAccount (Long id);

    void exportToCsv(LocalDateTime minDate, LocalDateTime maxDate, Long id);
}
