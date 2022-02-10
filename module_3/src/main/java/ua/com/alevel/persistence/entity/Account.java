package ua.com.alevel.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{

    private Long balance;
    private String wallet;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> sending;

    @OneToMany(mappedBy = "receiver")
    private List<Transaction> receiving;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Account() {
        super();
        this.sending = new ArrayList<>();
        this.receiving = new ArrayList<>();
    }

    public List<Transaction> getTransactions() {
        List<Transaction> merge = new ArrayList<>();
        merge.addAll(sending); merge.addAll(receiving);
        return merge;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + super.getId() +
                ", balanceLong=" + balance +
                ", wallet='" + wallet + '\'' +
                ", userId=" + user.getId() +
                '}';
    }
}
