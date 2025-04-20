package com.example.Travel_web.BE.controller;

import com.example.Travel_web.BE.dto.request.TourRequestDTO;
import com.example.Travel_web.BE.dto.response.ApiResponse;
import com.example.Travel_web.BE.dto.response.TourResponseDTO;
import com.example.Travel_web.BE.model.Tour;
import com.example.Travel_web.BE.service.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee/tour")
public class TourController {
    private final TourService tourService;
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TourRequestDTO tourRequestDTO) {
        try {
            Tour createdTour = tourService.create(tourRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("success", "Tour created successfully", createdTour));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("error", "Failed to create tour", e.getMessage()));
        }
    }

    // Lấy tất cả các Tour
    @GetMapping
    public ResponseEntity<?> getTours() {
        try {
            List<TourResponseDTO> tours = tourService.getAllTour();
            return ResponseEntity.ok(new ApiResponse("success", "Fetched all tours successfully", tours));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error", "Failed to fetch tours", e.getMessage()));
        }
    }

    // Lấy chi tiết Tour theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTourDetails(@PathVariable long id) {
        try {
            Optional<Tour> tour = tourService.getTourDetail(id);
            return tour.map(value -> ResponseEntity.ok(new ApiResponse("success", "Tour found", value))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("error", "Tour not found", null)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error", "Failed to fetch tour details", e.getMessage()));
        }
    }
}
