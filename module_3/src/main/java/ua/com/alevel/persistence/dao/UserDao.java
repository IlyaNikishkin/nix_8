package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User>{

    DataTableResponse<Account> findAllAccountsByUser(DataTableRequest request, Long userId);

    List<Account> findListAccounts(Long userId);
}
