package com.smartix.paymentformobilecommunications.dao;

import com.smartix.paymentformobilecommunications.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByLogin(String login);

    @Query(value = "SELECT user FROM User user " +
            "LEFT JOIN FETCH user.payments payments WHERE user.login = :login")
    User findByLoginFetchPayments(String login);
}
