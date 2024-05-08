package com.smartix.paymentformobilecommunications.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private Date date;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "amount")
    private double amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private User user;

    public Payment() {
        this.date = new Date(System.currentTimeMillis());
    }

    public Payment(String phoneNumber, double amount, User user) {
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.user = user;
        this.date = new Date(System.currentTimeMillis());
    }

    public Payment(Date date, String phoneNumber, double amount, User user) {
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
