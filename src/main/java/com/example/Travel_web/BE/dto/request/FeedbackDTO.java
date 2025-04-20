package com.example.Travel_web.BE.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {
    private Long userId;
    private Long tourId;
    private Double rating;
    private String comment;
}
