package com.example.Travel_web.BE.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqDTO {
    private String question;
    private String answer;

    public FaqDTO(String answer, String question) {
        this.answer = answer;
        this.question = question;
    }

    public FaqDTO() {
    }
}
