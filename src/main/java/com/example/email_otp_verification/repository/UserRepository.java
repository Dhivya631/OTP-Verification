package com.example.email_otp_verification.repository;

import com.example.email_otp_verification.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmailAndOtp(String email, String otp);
}
