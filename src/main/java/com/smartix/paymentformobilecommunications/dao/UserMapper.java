package com.smartix.paymentformobilecommunications.dao;

import com.smartix.paymentformobilecommunications.entity.Payment;
import com.smartix.paymentformobilecommunications.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setBalance(rs.getInt("balance"));
        user.setPayments((List<Payment>) rs.getArray("payment"));

        return user;
    }
}
