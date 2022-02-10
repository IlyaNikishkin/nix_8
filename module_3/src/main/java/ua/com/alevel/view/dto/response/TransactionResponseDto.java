package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.util.UsdUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class TransactionResponseDto extends ResponseDto{

    private LocalDateTime date;
    private long amount;
    private Account sender;
    private Account receiver;

    public TransactionResponseDto() {
    }

    public TransactionResponseDto(Transaction transaction) {
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        if (transaction.getSender() != null) {
            this.sender = transaction.getSender();
        }
        if (transaction.getReceiver() != null) {
            this.receiver = transaction.getReceiver();
        }
        super.setId(transaction.getId());
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public String getAmountDisplay() {
        NumberFormat nf = new DecimalFormat("#.##");
        return nf.format(UsdUtil.toUsd(amount));
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }
}
