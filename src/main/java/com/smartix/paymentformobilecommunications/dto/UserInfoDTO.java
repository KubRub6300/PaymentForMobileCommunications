package com.smartix.paymentformobilecommunications.dto;

import com.smartix.paymentformobilecommunications.entity.Sex;

import java.sql.Date;
import java.util.Objects;

public class UserInfoDTO {
    private String fullName;
    private String email;
    private Sex sex;
    private Date dateOfBirth;

    public UserInfoDTO() {
    }

    public UserInfoDTO(String fullName, String email, Sex sex, Date dateOfBirth) {
        this.fullName = fullName;
        this.email = email;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfoDTO that = (UserInfoDTO) o;
        return Objects.equals(fullName, that.fullName) && Objects.equals(email, that.email) && sex == that.sex && Objects.equals(dateOfBirth, that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, sex, dateOfBirth);
    }
}
