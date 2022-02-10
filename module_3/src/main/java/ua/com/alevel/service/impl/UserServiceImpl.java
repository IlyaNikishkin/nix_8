package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.dao.TransactionDao;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final AccountDao accountDao;
    private final TransactionDao transactionDao;
    final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public UserServiceImpl(UserDao userDao, AccountDao accountDao, TransactionDao transactionDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public void create(User entity) {
        LOGGER_INFO.info("creating, " + entity);
        userDao.create(entity);
    }

    @Override
    public void update(User entity) {
        LOGGER_INFO.info("updating, " + entity);
        checkByExist(entity.getId());
        userDao.update(entity);
        LOGGER_INFO.info("updating completed");
    }

    @Override
    public void delete(Long id) {
        LOGGER_INFO.info("deleting, userId=" + id);
        checkByExist(id);
        List<Account> accounts = userDao.findListAccounts(id);
        for (Account account : accounts) {
            accountDao.delete(account.getId());
        }
        userDao.delete(id);
        LOGGER_INFO.info("deleting completed");
    }

    @Override
    public User findById(Long id) {
        checkByExist(id);
        return userDao.findById(id);
    }

    @Override
    public DataTableResponse<User> findAll(DataTableRequest request) {
        DataTableResponse<User> dataTableResponse = userDao.findAll(request);
        long count = userDao.count();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        LOGGER_INFO.info("findAllUsers request, " + dataTableResponse);
        return dataTableResponse;
    }

    private void checkByExist(Long id) {
        if (!userDao.existById(id)) {
            LOGGER_INFO.info("user not found, userId=" + id);
            throw new EntityExistException("entity not found");
        }
    }

    @Override
    public DataTableResponse<Account> findAllAccountsByUser(DataTableRequest request, Long userId) {
        DataTableResponse<Account> dataTableResponse = userDao.findAllAccountsByUser(request, userId);
        long count = userDao.findListAccounts(userId).size();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        LOGGER_INFO.info("findAllAccountsByUser request, " + dataTableResponse);
        return dataTableResponse;
    }
}
