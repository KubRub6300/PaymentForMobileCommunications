package com.smartix.paymentformobilecommunications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartix.paymentformobilecommunications.config.MyUserDetails;
import com.smartix.paymentformobilecommunications.dto.*;
import com.smartix.paymentformobilecommunications.entity.Sex;
import com.smartix.paymentformobilecommunications.entity.User;
import com.smartix.paymentformobilecommunications.service.PaymentService;
import com.smartix.paymentformobilecommunications.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MainController.class)
class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PaymentService paymentService;


    @Autowired
    private ObjectMapper objectMapper;

    MyUserDetails myUserDetails = new MyUserDetails(new User());

    @BeforeEach
    public void authorize() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities()));
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    public void registration_returnsUserDTO() throws Exception {
        User user = new User();
        UserDTO userDTO = new UserDTO("+7 999 999 99 99", 1000);
        when(userService.mapToUserDTO(Mockito.any(User.class))).thenReturn(userDTO);

        mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void putUser_returnsUserInfoDTO() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO("Full name", "email", Sex.Man, new Date(System.currentTimeMillis()));

        when(userService.userUpdateInfo(Mockito.any(User.class), Mockito.any(UserInfoDTO.class))).thenReturn(userInfoDTO);

        mockMvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userInfoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$.fullName").value("Full name"))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.sex").value("Man"));
    }


    @Test
    public void postPayment_returnsResponseEntity() throws Exception {
        PaymentNumberDTO paymentNumberDTO = new PaymentNumberDTO("+7 999 999 99 99", 100);
        InfoDTO infoDTO = new InfoDTO("Nice");

        when(userService.paymentMobileCommunication(Mockito.any(MyUserDetails.class), Mockito.any(PaymentNumberDTO.class)))
                .thenReturn(new ResponseEntity<>(infoDTO, HttpStatus.OK));

        mockMvc.perform(post("/payment").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(paymentNumberDTO)))
                .andExpect(status().isOk());
    }


    @Test
    public void getBalance_returnsUserDTO() throws Exception {
        when(userService.mapToUserDTO(Mockito.any(User.class)))
                .thenReturn(new UserDTO("+7 999 999 99 99", 1000));

        mockMvc.perform(get("/balance").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("+7 999 999 99 99"))
                .andExpect(jsonPath("$.balance").value(1000));

        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value("+7 999 999 99 99"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    public void getUser_returnsUserInfoDTO() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO("Full name", "email", Sex.Man, new Date(System.currentTimeMillis()));

        when(userService.mapToUserInfoDTO(Mockito.any(User.class)))
                .thenReturn(userInfoDTO);

        mockMvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Full name"))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.sex").value("Man"));
    }

    @Test
    public void getPayment_returnsPaymentResponse() throws Exception {
        when(paymentService.getPayments(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(new PaymentResponse());

        mockMvc.perform(get("/payment?pageNo=1&pageSize=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void handleException_returnsResponseEntity(){
        MainController mainController = new MainController();
        ResponseEntity<InfoDTO> response = mainController.handleException(new Exception("error"));

        InfoDTO infoDTO = new InfoDTO("error");
        ResponseEntity<InfoDTO> expected = new ResponseEntity<>(infoDTO, HttpStatus.BAD_REQUEST);

        assertEquals(expected.getStatusCode(),response.getStatusCode());
        assertEquals(expected.getBody().getInfo(),response.getBody().getInfo());
    }

}


