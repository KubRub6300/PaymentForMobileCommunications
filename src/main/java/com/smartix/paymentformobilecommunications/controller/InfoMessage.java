package com.smartix.paymentformobilecommunications.controller;

public class InfoMessage {
    private String info;

    public InfoMessage(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "InfoMessage{" +
                "info='" + info + '\'' +
                '}';
    }
}
