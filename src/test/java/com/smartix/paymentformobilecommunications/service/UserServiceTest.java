package com.smartix.paymentformobilecommunications.service;

import com.smartix.paymentformobilecommunications.config.MyUserDetails;
import com.smartix.paymentformobilecommunications.dao.UserRepository;
import com.smartix.paymentformobilecommunications.dto.PaymentNumberDTO;
import com.smartix.paymentformobilecommunications.dto.UserDTO;
import com.smartix.paymentformobilecommunications.dto.UserInfoDTO;
import com.smartix.paymentformobilecommunications.entity.Sex;
import com.smartix.paymentformobilecommunications.entity.User;
import com.smartix.paymentformobilecommunications.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;



    @Test
    void addUser_returnsUser() {
        User user = new User();
        user.setLogin("+7 999 999 99 99");
        user.setPassword("pass");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(Mockito.anyString())).thenReturn("hash");

        User userSaved = userService.addUser(user);

        assertNotNull(userSaved);
        assertEquals("hash",userSaved.getPassword());
    }


    @Test
    public void mapToUserDTO_returnsUserDTO() {
        User user = new User();
        user.setLogin("+7 999 999 99 99");

        UserDTO expected = new UserDTO("+7 999 999 99 99", 1000);


        UserDTO result = userService.mapToUserDTO(user);

        assertNotNull(result);
        assertEquals(expected, result);
    }


    @Test
    public void mapToUser_returnsUser() {
        UserDTO userDTO = new UserDTO("+7 999 999 99 99", 500);

        User expected = new User();
        expected.setLogin("+7 999 999 99 99");
        expected.setBalance(500);


        User result = userService.mapToUser(userDTO);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    public void mapToUserInfoDTO_returnsUserInfoDTO() {
        User user = new User();
        UserInfoDTO result = userService.mapToUserInfoDTO(user);
        assertNotNull(result);
        assertEquals(new UserInfoDTO(),result);

        UserInfo userInfo = new UserInfo();
        userInfo.setFullName("full name");
        userInfo.setEmail("email");
        userInfo.setSex(Sex.Man);
        Date date = new Date(System.currentTimeMillis());
        userInfo.setDateOfBirth(date);

        user.setUserInfo(userInfo);
        result = userService.mapToUserInfoDTO(user);

        UserInfoDTO expected = new UserInfoDTO("full name", "email", Sex.Man, date);

        assertEquals(expected, result);
    }

    @Test
    public void mapToUserInfo_returnsUserInfo() {
        Date date = new Date(System.currentTimeMillis());
        UserInfoDTO userInfoDTO = new UserInfoDTO("full name", "email", Sex.Man, date);
        UserInfo userInfo = new UserInfo();

        UserInfo result = userService.mapToUserInfo(userInfoDTO, userInfo);

        UserInfo expected = new UserInfo("full name", "email", Sex.Man, date);

        assertEquals(expected, result);
    }

    @Test
    public void userUpdateInfo_returnsUserInfoDTO() {
        Date date = new Date(System.currentTimeMillis());

        User user = new User();
        UserInfoDTO userInfoDTO = new UserInfoDTO("full name", "email", Sex.Man, date);

        User userUpdate = new User();
        userUpdate.setUserInfo(userService.mapToUserInfo(userInfoDTO, new UserInfo()));
        userUpdate.getUserInfo().setId(1);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(userUpdate);

        UserInfoDTO result = userService.userUpdateInfo(user,userInfoDTO);
        UserInfoDTO expected = userService.mapToUserInfoDTO(userUpdate);

        assertEquals(expected,result);


        user.setUserInfo(new UserInfo());
        user.getUserInfo().setId(1);

        userUpdate.setUserInfo(userService.mapToUserInfo(userInfoDTO, new UserInfo()));
        userUpdate.getUserInfo().setId(1);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(userUpdate);

        result = userService.userUpdateInfo(user,userInfoDTO);
        expected = userService.mapToUserInfoDTO(userUpdate);

        assertEquals(expected,result);
    }

    @Test
    public void paymentMobileCommunication_returnsResponseEntity(){
        User user = new User();
        user.setLogin("+7 999 999 99 99");
        MyUserDetails myUserDetails = new MyUserDetails(user);
        PaymentNumberDTO paymentNumberDTO = new PaymentNumberDTO("+7 999 999 99 99",-100);

        ResponseEntity result1 = userService.paymentMobileCommunication(myUserDetails,paymentNumberDTO);
        assertEquals(HttpStatusCode.valueOf(400), result1.getStatusCode());



        paymentNumberDTO.setAmount(100);
        when(userRepository.findByLoginFetchPayments(Mockito.any(String.class))).thenReturn(user);

        ResponseEntity result2 = userService.paymentMobileCommunication(myUserDetails,paymentNumberDTO);
        assertEquals(HttpStatusCode.valueOf(200), result2.getStatusCode());
        assertEquals(900,myUserDetails.getUser().getBalance());



        user.setBalance(50);
        myUserDetails = new MyUserDetails(user);

        ResponseEntity result4 = userService.paymentMobileCommunication(myUserDetails,paymentNumberDTO);
        assertEquals(HttpStatusCode.valueOf(400), result4.getStatusCode());
        assertEquals(50,myUserDetails.getUser().getBalance());


        user.setBalance(1000);
        myUserDetails = new MyUserDetails(user);
        when(userRepository.save(Mockito.any(User.class))).thenThrow(new RuntimeException());

        ResponseEntity result3 = userService.paymentMobileCommunication(myUserDetails,paymentNumberDTO);
        assertEquals(HttpStatusCode.valueOf(500), result3.getStatusCode());
        assertEquals(1000,myUserDetails.getUser().getBalance());






    }


}