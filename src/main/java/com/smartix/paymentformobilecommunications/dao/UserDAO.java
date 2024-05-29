package com.smartix.paymentformobilecommunications.dao;

import com.smartix.paymentformobilecommunications.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);


    public List<User> index() {
        return jdbcTemplate.query("SELECT * FROM usr", userRowMapper);
    }

    public User show(int id) {
        return jdbcTemplate.query("SELECT * FROM usr WHERE id = ?", new Object[]{id}, userRowMapper)
                .stream().findAny().orElse(null);
    }

    public void save(User user){
        jdbcTemplate.update("INSERT INTO usr VALUES(?,?,?,?)",user.getLogin(), user.getPassword(), user.getBalance(), user.getUserInfo());

    }

    public void update(User user){
        jdbcTemplate.update("UPDATE usr SET login=?, balance=? WHERE id=?", user.getLogin(), user.getBalance(), user.getId());
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM usr WHERE id=?", id);
    }
}
