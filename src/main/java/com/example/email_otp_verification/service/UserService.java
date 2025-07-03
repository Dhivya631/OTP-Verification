package com.example.email_otp_verification.service;

import com.example.email_otp_verification.entity.Users;
import com.example.email_otp_verification.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    public void registerUser(String username, String email) {
        String otp=String.valueOf(100000+ new Random().nextInt(900000));
        Users users=new Users();
        users.setUsername(username);
        users.setEmail(email);
        users.setOtp(otp);
        users.setOtpGeneratedTime(LocalDateTime.now());
        users.setVerified(false);

        userRepository.save(users);
        emailService.sendOtpEmail(email,otp);
    }

    public boolean verifyOtp(String email, String otp) {
        Optional<Users> optionalUser = userRepository.findByEmailAndOtp(email, otp);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).toMinutes() <= 10) {
                user.setVerified(true);
                user.setOtp(null); // clear OTP
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
