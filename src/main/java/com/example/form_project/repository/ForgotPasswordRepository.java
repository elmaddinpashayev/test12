package com.example.form_project.repository;

import com.example.form_project.model.ForgotPassword;
import com.example.form_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {


    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user.id = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);
}
