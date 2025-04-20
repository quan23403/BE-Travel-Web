package com.example.Travel_web.BE.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourResponseDTO {
    private long id;
    private String name;
    private String duration;
    private String level;
    private String type;
    private double basePrice;
    private double discount;
    private String description;
    private String thumbnail;
}
