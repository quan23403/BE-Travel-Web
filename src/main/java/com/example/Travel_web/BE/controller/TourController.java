package com.example.Travel_web.BE.controller;

import com.example.Travel_web.BE.dto.request.TourRequestDTO;
import com.example.Travel_web.BE.service.TourService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class TourController {
    private final TourService tourService;
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping("/tour")
    public String create(@RequestBody TourRequestDTO tourRequestDTO) {
        this.tourService.create(tourRequestDTO);
        return "Create Tour Success";
    }
}
