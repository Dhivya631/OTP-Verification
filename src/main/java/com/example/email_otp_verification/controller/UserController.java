package com.example.email_otp_verification.controller;

import com.example.email_otp_verification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           Model model) {
        userService.registerUser(username, email);
        model.addAttribute("email", email);
        return "verify-otp"; // view to enter OTP
    }
    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp,
                            Model model) {
        boolean verified = userService.verifyOtp(email, otp);
        if (verified) {
            model.addAttribute("message", "Registration successful and verified!");
            return "success"; // success page
        } else {
            model.addAttribute("error", "Invalid or expired OTP.");
            model.addAttribute("email", email);
            return "verify-otp";
        }
    }
}
