package com.smartix.paymentformobilecommunications.service;

import com.smartix.paymentformobilecommunications.config.MyUserDetails;
import com.smartix.paymentformobilecommunications.controller.InfoMessage;
import com.smartix.paymentformobilecommunications.dto.UserDTO;
import com.smartix.paymentformobilecommunications.dto.UserInfoDTO;
import com.smartix.paymentformobilecommunications.dto.PaymentNumberDTO;
import com.smartix.paymentformobilecommunications.entity.User;
import com.smartix.paymentformobilecommunications.entity.UserInfo;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public User addUser(User user);

    public ResponseEntity<InfoMessage> paymentMobileCommunication(MyUserDetails myUserDetails, PaymentNumberDTO paymentData);

    public UserDTO mapToUserDTO(User user);

    public User mapToUser(UserDTO userDTO);

    public UserInfoDTO mapToUserInfoDTO(User user);

    public UserInfo mapToUserInfo(UserInfoDTO userInfoDTO, UserInfo userInfo);

    public UserInfoDTO userUpdateInfo(User user, UserInfoDTO userInfoDTO);
}
