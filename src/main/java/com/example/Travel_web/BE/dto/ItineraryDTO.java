package com.example.Travel_web.BE.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItineraryDTO {
    private int dayNumber;
    private String title;
    private String description;

    public ItineraryDTO(int dayNumber, String title, String description) {
        this.dayNumber = dayNumber;
        this.title = title;
        this.description = description;
    }

    public ItineraryDTO() {
    }
}
