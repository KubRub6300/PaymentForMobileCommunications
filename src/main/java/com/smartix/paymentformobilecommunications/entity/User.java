package com.smartix.paymentformobilecommunications.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private double balance;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "usr_info_id")
    private UserInfo userInfo;

    @OneToMany(cascade = CascadeType.ALL,
                mappedBy = "user", fetch = FetchType.LAZY)
    private List<Payment> payments;

    public User() {
        balance = 1000;
    }

    public User(String login, String password, double balance, UserInfo userInfo, List<Payment> payments) {
        this.login = login;
        this.password = password;
        this.balance = balance;
        this.userInfo = userInfo;
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", userInfo=" + userInfo +
                ", payments=" + payments +
                '}';
    }

    public void addPayment(Payment payment){
        if (payments==null){
            payments = new ArrayList();
        }
        payments.add(payment);
    }

    public boolean reduceBalance(double amount){
        if (this.balance<amount) return false;
        this.balance-=amount;
        return true;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Double.compare(user.balance, balance) == 0 && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(userInfo, user.userInfo) && Objects.equals(payments, user.payments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, balance, userInfo, payments);
    }
}
