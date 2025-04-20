package com.example.Travel_web.BE.repository;

import com.example.Travel_web.BE.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh nếu cần
}
