package com.example.B2BHotelBookingSystem.services;


import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.config.utils.Utilities;
import com.example.B2BHotelBookingSystem.dtos.User.Auth.ForgetPasswordRequest;
import com.example.B2BHotelBookingSystem.models.User;
import com.example.B2BHotelBookingSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository repository;

    private final PasswordEncoder encoder;


    public void forgetPassword(String emailOrPhone){
        User user = findUserByEmailOrPhone(emailOrPhone);

        String code = Utilities.generateRandomString(6);
        //store in cache key: emailOrPhone , value: code
        // send code via sms or email
    }

    public void resetPassword(ForgetPasswordRequest request){

        if (!request.resetCode().isEmpty() && !request.emailOrPhoneNumber().isEmpty()) {
            String code = "";//retrieve code from cache
            if (code.equals(request.resetCode())){
                User user = findUserByEmailOrPhone(request.emailOrPhoneNumber());
               if (user != null) {
                   user.setPassword(encoder.encode("12345678"));
                   repository.save(user);
                   //delete code from cache
               }
            }
        }
    }

    private User findUserByEmailOrPhone(String emailOrPhone){
        User user = null;

        if(!emailOrPhone.isEmpty() && Utilities.isEmail(emailOrPhone)){
            user = repository.findByEmail(emailOrPhone).orElseThrow(() -> new NotFoundException("User"));
        } else if (!emailOrPhone.isEmpty() && Utilities.isPhoneNumber(emailOrPhone)){
            user = repository.findByPhone(emailOrPhone).orElseThrow(() -> new NotFoundException("User"));
        }
        return user;
    }


}
