package com.example.Travel_web.BE.dto;

import com.example.Travel_web.BE.enums.Level;
import com.example.Travel_web.BE.enums.TourDuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourDTO {
    private String name;
    private String startLocation;
    private String endLocation;
    private TourDuration duration;
    private Double basePrice;
    private String description;
    private String travelType;
    private Level level;
    private Double discount;
    private int maxGroupSize;

    public TourDTO() {
    }

    public TourDTO(String name, String startLocation, String endLocation, TourDuration duration, Double basePrice, String description, String travelType, Level level, Double discount, int maxGroupSize) {
        this.name = name;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.duration = duration;
        this.basePrice = basePrice;
        this.description = description;
        this.travelType = travelType;
        this.level = level;
        this.discount = discount;
        this.maxGroupSize = maxGroupSize;
    }
}
