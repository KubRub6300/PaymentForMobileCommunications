package com.smartix.paymentformobilecommunications.dto;

public class PaymentNumberDTO {

    private String phoneNumber;

    private double amount;

    public PaymentNumberDTO(String phoneNumber, double amount) {
        this.phoneNumber = phoneNumber;
        this.amount = amount;
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
}
