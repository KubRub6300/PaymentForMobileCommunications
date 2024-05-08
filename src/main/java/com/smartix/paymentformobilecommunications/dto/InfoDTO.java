package com.smartix.paymentformobilecommunications.dto;

public class InfoDTO {
    private String info;

    public InfoDTO(String info) {
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
