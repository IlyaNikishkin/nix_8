package ua.com.alevel.persistence.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    private LocalDateTime date;
    private Long amount;

    @ManyToOne()
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private Account sender;

    @ManyToOne()
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private Account receiver;

    public Transaction() {
        super();
        this.date = LocalDateTime.now();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + super.getId() +
                ", date=" + date +
                ", amountLong=" + amount +
                ", senderId=" + sender.getId() +
                ", receiverId=" + receiver.getId() +
                '}';
    }
}
