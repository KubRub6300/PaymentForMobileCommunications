package com.smartix.paymentformobilecommunications.service;

import com.smartix.paymentformobilecommunications.dao.UserRepository;
import com.smartix.paymentformobilecommunications.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyUserDetailsServiceTest {

    @InjectMocks
    MyUserDetailsService myUserDetailsService;

    @Mock
    UserRepository userRepository;

    @Test
    public void loadUserByUsername_returnsUserDetails(){

        User user = new User();
        user.setLogin("login");
        when(userRepository.findByLogin(Mockito.anyString())).thenReturn(Optional.of(user));
        UserDetails result = myUserDetailsService.loadUserByUsername("login");

        assertEquals(user.getLogin(), result.getUsername());
    }

}