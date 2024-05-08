package com.smartix.paymentformobilecommunications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartix.paymentformobilecommunications.config.MyUserDetails;
import com.smartix.paymentformobilecommunications.dto.PaymentNumberDTO;
import com.smartix.paymentformobilecommunications.dto.UserDTO;
import com.smartix.paymentformobilecommunications.dto.UserInfoDTO;
import com.smartix.paymentformobilecommunications.entity.Sex;
import com.smartix.paymentformobilecommunications.entity.User;
import com.smartix.paymentformobilecommunications.service.PaymentService;
import com.smartix.paymentformobilecommunications.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(controllers = MainController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    public void registration_returnsUserDTO() throws Exception {
        User user = new User();
        UserDTO userDTO = new UserDTO("+7 999 999 99 99", 1000);
        when(userService.mapToUserDTO(Mockito.any(User.class))).thenReturn(userDTO);

        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }



}