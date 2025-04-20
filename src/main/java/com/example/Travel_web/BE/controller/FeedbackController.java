package com.example.Travel_web.BE.controller;

import com.example.Travel_web.BE.dto.request.FeedbackDTO;
import com.example.Travel_web.BE.dto.response.ApiResponse;
import com.example.Travel_web.BE.model.Feedback;
import com.example.Travel_web.BE.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Create Feedback
    @PostMapping
    public ResponseEntity<ApiResponse> createFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        try {
            Feedback createdFeedback = feedbackService.createFeedback(feedbackDTO);
            ApiResponse response = new ApiResponse("success", "Feedback created successfully", createdFeedback);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Get All Feedbacks
    @GetMapping
    public ResponseEntity<ApiResponse> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        ApiResponse response = new ApiResponse("success", "Fetched all feedbacks", feedbacks);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get Feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getFeedbackById(@PathVariable Long id) {
        Optional<Feedback> feedback = feedbackService.getFeedbackById(id);
        if (feedback.isPresent()) {
            ApiResponse response = new ApiResponse("success", "Feedback found", feedback.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse("error", "Feedback not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Update Feedback
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateFeedback(@PathVariable Long id, @RequestBody FeedbackDTO feedbackDTO) {
        Feedback updatedFeedback = feedbackService.updateFeedback(id, feedbackDTO);
        if (updatedFeedback != null) {
            ApiResponse response = new ApiResponse("success", "Feedback updated successfully", updatedFeedback);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse("error", "Feedback not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Delete Feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        ApiResponse response = new ApiResponse("success", "Feedback deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}