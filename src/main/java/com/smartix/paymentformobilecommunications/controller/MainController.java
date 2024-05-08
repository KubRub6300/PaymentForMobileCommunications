package com.smartix.paymentformobilecommunications.controller;


import com.smartix.paymentformobilecommunications.config.MyUserDetails;
import com.smartix.paymentformobilecommunications.dto.PaymentResponse;
import com.smartix.paymentformobilecommunications.dto.UserDTO;
import com.smartix.paymentformobilecommunications.dto.UserInfoDTO;
import com.smartix.paymentformobilecommunications.dto.PaymentNumberDTO;
import com.smartix.paymentformobilecommunications.entity.User;
import com.smartix.paymentformobilecommunications.service.PaymentService;
import com.smartix.paymentformobilecommunications.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;


    @PostMapping(path = "/registration")
    public UserDTO registration(@RequestBody User user){
        userService.addUser(user);
        return userService.mapToUserDTO(user);
    }

    @GetMapping(path = {"/balance","/"})
    public UserDTO getBalance(@AuthenticationPrincipal MyUserDetails myUserDetails){
        return userService.mapToUserDTO(myUserDetails.getUser());
    }

    @GetMapping(path = {"/user"})
    public UserInfoDTO getUser(@AuthenticationPrincipal MyUserDetails myUserDetails){
        User user = myUserDetails.getUser();
        return userService.mapToUserInfoDTO(user);
    }

    @PutMapping(path = {"/user"})
    public UserInfoDTO putUser(@AuthenticationPrincipal MyUserDetails myUserDetails,
                               @RequestBody UserInfoDTO userInfoDTO){
        User user = myUserDetails.getUser();
        return userService.userUpdateInfo(user, userInfoDTO);
    }


    @PostMapping(path = "/payment")
    public ResponseEntity<InfoMessage> postPayment(@AuthenticationPrincipal MyUserDetails myUserDetails,
                            @RequestBody PaymentNumberDTO paymentData){

        return userService.paymentMobileCommunication(myUserDetails,paymentData);
    }

    @GetMapping(path = "/payment")
    public PaymentResponse getPayment(@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "1", required = false) int pageSize){

        return paymentService.getPayments(pageNo,pageSize);
    }



    @ExceptionHandler
    public ResponseEntity<InfoMessage> handleException(Exception exception){
        InfoMessage infoMessage = new InfoMessage(exception.getMessage());
        return new ResponseEntity<>(infoMessage, HttpStatus.BAD_REQUEST);
    }
}
