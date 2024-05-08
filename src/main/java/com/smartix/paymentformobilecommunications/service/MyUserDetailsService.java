package com.smartix.paymentformobilecommunications.service;

import com.smartix.paymentformobilecommunications.config.MyUserDetails;
import com.smartix.paymentformobilecommunications.dao.UserRepository;
import com.smartix.paymentformobilecommunications.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;



@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(login);//Получаем пользователя по имени из БД
        UserDetails userDetails = user.map(MyUserDetails::new).orElse(new MyUserDetails(new User()));

        return userDetails;
    }
}