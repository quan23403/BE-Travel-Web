package com.example.Travel_web.BE.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedbackResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String userAvatar;
    private Long tourId;
    private String scheduleName;
    private Double rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
