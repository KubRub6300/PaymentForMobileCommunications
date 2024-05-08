package com.smartix.paymentformobilecommunications.service;

import com.smartix.paymentformobilecommunications.config.MyUserDetails;
import com.smartix.paymentformobilecommunications.controller.InfoMessage;
import com.smartix.paymentformobilecommunications.dao.PaymentRepository;
import com.smartix.paymentformobilecommunications.dao.UserRepository;
import com.smartix.paymentformobilecommunications.dto.UserDTO;
import com.smartix.paymentformobilecommunications.dto.UserInfoDTO;
import com.smartix.paymentformobilecommunications.entity.Payment;
import com.smartix.paymentformobilecommunications.dto.PaymentNumberDTO;
import com.smartix.paymentformobilecommunications.entity.User;
import com.smartix.paymentformobilecommunications.entity.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public ResponseEntity<InfoMessage> paymentMobileCommunication(MyUserDetails myUserDetails, PaymentNumberDTO paymentData) {
        if (paymentData.getAmount() <= 0) {
            return new ResponseEntity<>(new InfoMessage("Сумма должн быть больше 0!"), HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByLoginFetchPayments(myUserDetails.getUser().getLogin());

        InfoMessage infoMessage = new InfoMessage("");
        HttpStatus httpStatus = HttpStatus.OK;
        double startBalance = user.getBalance();

        try {
            if (user.reduceBalance(paymentData.getAmount())) {
                Payment payment = new Payment(paymentData.getPhoneNumber(), paymentData.getAmount(), user);
                user.addPayment(payment);
                userRepository.save(user);
                infoMessage.setInfo("Оплата прошла успешно!");
            } else {
                infoMessage.setInfo("Недостаточно средств!");
                httpStatus = HttpStatus.BAD_REQUEST;
            }

        } catch (Exception exception) {
            user.setBalance(startBalance);
            infoMessage.setInfo("Произошла ошибка! Повтроите попытку позже!");
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {
            myUserDetails.setUser(user);
            return new ResponseEntity(infoMessage, httpStatus);
        }
    }


    @Override
    public UserDTO mapToUserDTO(User user) {
        return new UserDTO(user.getLogin(), user.getBalance());
    }

    @Override
    public User mapToUser(UserDTO userDTO) {
        User user = new User();
        user.setBalance(userDTO.getBalance());
        user.setLogin(userDTO.getLogin());
        return user;
    }

    @Override
    public UserInfoDTO mapToUserInfoDTO(User user) {
        if (user.getUserInfo() == null) {
            user.setUserInfo(new UserInfo());
            return new UserInfoDTO();
        }
        UserInfo userInfo = user.getUserInfo();
        return new UserInfoDTO(userInfo.getFullName(), userInfo.getEmail(),
                userInfo.getSex(), userInfo.getDateOfBirth());

    }

    @Override
    public UserInfo mapToUserInfo(UserInfoDTO userInfoDTO, UserInfo userInfo) {
        userInfo.setFullName(userInfoDTO.getFullName());
        userInfo.setEmail(userInfoDTO.getEmail());
        userInfo.setSex(userInfoDTO.getSex());
        userInfo.setDateOfBirth(userInfoDTO.getDateOfBirth());
        return userInfo;
    }

    @Override
    public UserInfoDTO userUpdateInfo(User user, UserInfoDTO userInfoDTO) {
        UserInfo userInfo;
        if(user.getUserInfo()==null){
            userInfo = new UserInfo();
        }
        else {
            userInfo = user.getUserInfo();
        }
        userInfo = mapToUserInfo(userInfoDTO,userInfo);
        user.setUserInfo(userInfo);
        user.getUserInfo().setUser(user);
        userRepository.save(user);
        return mapToUserInfoDTO(user);
    }
}
