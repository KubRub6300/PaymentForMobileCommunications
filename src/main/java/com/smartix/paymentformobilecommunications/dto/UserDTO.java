package com.smartix.paymentformobilecommunications.dto;


import java.util.Objects;

public class UserDTO {

    private String login;
    private double balance;


    public UserDTO(String login, double balance) {
        this.login = login;
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "login='" + login + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Double.compare(userDTO.balance, balance) == 0 && Objects.equals(login, userDTO.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, balance);
    }
}
