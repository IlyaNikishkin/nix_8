package ua.com.alevel.service.impl;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.dao.TransactionDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.util.UsdUtil;
import ua.com.alevel.util.WebResponseUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final TransactionDao transactionDao;
    final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    public AccountServiceImpl(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public void create(Account entity) {
        LOGGER_INFO.info("creating, " + entity);
        accountDao.create(entity);
        LOGGER_INFO.info("creating completed");
    }

    @Override
    public void update(Account entity) {
        LOGGER_INFO.info("updating, " + entity);
        checkByExist(entity.getId());
        accountDao.update(entity);
        LOGGER_INFO.info("updating completed");
    }

    @Override
    public void delete(Long id) {
        LOGGER_INFO.info("deleting, accountId=" + id);
        checkByExist(id);
        List<Transaction> transactions = findAllTransactionsByAccount(id);
        for (Transaction transaction : transactions) {
            transactionDao.delete(transaction.getId());
        }
        accountDao.delete(id);
        LOGGER_INFO.info("deleting completed");
    }

    @Override
    public Account findById(Long id) {
        checkByExist(id);
        return accountDao.findById(id);
    }

    @Override
    public DataTableResponse<Account> findAll(DataTableRequest request) {
        DataTableResponse<Account> dataTableResponse = accountDao.findAll(request);
        long count = accountDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        LOGGER_INFO.info("findAllUsers request, " + dataTableResponse);
        return dataTableResponse;
    }

    private void checkByExist(Long id) {
        if (!accountDao.existById(id)) {
            LOGGER_INFO.info("account not found, accountId=" + id);
            throw new EntityExistException("entity not found");
        }
    }

    @Override
    public DataTableResponse<Transaction> findAllTransactionsByAccount(DataTableRequest request, Long accountId) {
        DataTableResponse<Transaction> dataTableResponse = accountDao.findAllTransactionsByAccount(request, accountId);
        long count = accountDao.findListTransactions(accountId).size();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        LOGGER_INFO.info("findAllAccountsByUser request, " + dataTableResponse);
        return dataTableResponse;
    }

    @Override
    public List<Transaction> findAllTransactionsByAccount(Long accountId) {
        return accountDao.findListTransactions(accountId);
    }

    public LocalDateTime minDateTransaction(Long id) {
        List<Transaction> transactions = accountDao.findListTransactions(id);
        Comparator<Transaction> comparator = Comparator.comparing(Transaction::getDate);
        return Objects.requireNonNull(transactions.stream().min(comparator).orElse(new Transaction())).getDate();
    }

    public LocalDateTime maxDateTransaction(Long id) {
        return LocalDateTime.now();
    }

    @Override
    public void exportToCsv(LocalDateTime minDate, LocalDateTime maxDate, Long id) {
        LOGGER_INFO.info("start running accountService.exportToCsv, accountId=" + id);
        List<String[]> writeOutList = new ArrayList<>();
        writeOutList.add(new String[]{
                "Date", "Balance, USD", "Sender name", "Sender wallet", "Amount, USD", "Receiver name", "Receiver wallet"
        });
        List<Transaction> transactions = findAllTransactionsByAccount(id)
                .stream()
                .filter((x) -> x.getDate().isAfter(minDate) && x.getDate().isBefore(maxDate))
                .collect(Collectors.toList());
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("accountId_" + id + "_transactions.csv"))) {
            for (Transaction transaction : transactions) {
                String[] transactionsString = new String[7];
                transactionsString[0] = transaction.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                transactionsString[1] = String.valueOf(UsdUtil.toUsd(findById(id).getBalance()));
                transactionsString[2] = transaction.getSender().getUser().getName();
                transactionsString[3] = transaction.getSender().getWallet();
                transactionsString[4] = String.valueOf(UsdUtil.toUsd(transaction.getAmount()));
                transactionsString[5] = transaction.getReceiver().getUser().getName();
                transactionsString[6] = transaction.getReceiver().getWallet();
                writeOutList.add(transactionsString);
            }
            csvWriter.writeAll(writeOutList);
            LOGGER_INFO.info("writing completed, file={accountId_" + id + "_transactions.csv}");
        } catch (IOException e) {
            LOGGER_WARN.warn("writing failed, file={accountId_" + id + "_transactions.csv}");
            e.printStackTrace();
        }
    }

    @Override
    public DataTableResponse<Account> findAllAccounts() {
        return accountDao.findAllAccounts();
    }
}
