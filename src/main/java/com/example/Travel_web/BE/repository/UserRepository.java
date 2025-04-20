package com.example.Travel_web.BE.repository;


import com.example.Travel_web.BE.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Bạn có thể thêm các truy vấn tùy chỉnh nếu cần
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
