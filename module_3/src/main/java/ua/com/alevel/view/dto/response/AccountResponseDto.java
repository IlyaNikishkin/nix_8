package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.util.UsdUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class AccountResponseDto extends ResponseDto {

    private Long balance;
    private String wallet;
    private User user;

    public AccountResponseDto() {
    }

    public AccountResponseDto(Account account) {
        this.balance = account.getBalance();
        this.wallet = account.getWallet();
        if (account.getUser() != null) {
            this.user = account.getUser();
        }
        super.setId(account.getId());
    }

    public Long getBalance() {
        return balance;
    }

    public String getBalanceDisplay() {
        NumberFormat nf = new DecimalFormat("#.##");
        return nf.format(UsdUtil.toUsd(balance));
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
