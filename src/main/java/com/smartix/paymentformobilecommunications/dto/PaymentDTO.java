package com.smartix.paymentformobilecommunications.dto;

import java.sql.Date;
import java.util.Objects;

public class PaymentDTO {

    private int id;
    private String phoneNumber;
    private double amount;
    private Date date;

    public PaymentDTO() {
    }

    public PaymentDTO(int id, String phoneNumber, double amount, Date date) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDTO that = (PaymentDTO) o;
        return id == that.id && Double.compare(that.amount, amount) == 0 && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, amount, date);
    }
}
