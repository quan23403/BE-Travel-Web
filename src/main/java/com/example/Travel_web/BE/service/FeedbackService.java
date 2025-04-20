package com.example.Travel_web.BE.service;

import com.example.Travel_web.BE.dto.request.FeedbackDTO;
import com.example.Travel_web.BE.dto.response.FeedbackResponseDTO;
import com.example.Travel_web.BE.model.Feedback;
import com.example.Travel_web.BE.model.Tour;
import com.example.Travel_web.BE.model.User;
import com.example.Travel_web.BE.repository.FeedbackRepository;
import com.example.Travel_web.BE.repository.TourRepository;
import com.example.Travel_web.BE.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final FeedbackRepository feedbackRepository;
    public FeedbackService(UserRepository userRepository, FeedbackRepository feedbackRepository,TourRepository tourRepository) {
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }
    // Create Feedback
    public Feedback createFeedback(FeedbackDTO feedbackDTO) {
        // Kiểm tra xem userId và tourId có tồn tại trong cơ sở dữ liệu không
        Optional<User> userOptional = userRepository.findById(feedbackDTO.getUserId());
        Optional<Tour> tourOptional = tourRepository.findById(feedbackDTO.getTourId());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        if (tourOptional.isEmpty()) {
            throw new RuntimeException("Tour not found");
        }

        // Tạo và lưu feedback
        Feedback feedback = new Feedback();
        feedback.setUser(userOptional.get());
        feedback.setTour(tourOptional.get());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setComment(feedbackDTO.getComment());

        return feedbackRepository.save(feedback);
    }

    // Get All Feedbacks
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Get Feedback by ID
    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    // Update Feedback
    public Feedback updateFeedback(Long id, FeedbackDTO feedbackDTO) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isPresent()) {
            Feedback feedback = feedbackOptional.get();
            feedback.setRating(feedbackDTO.getRating());
            feedback.setComment(feedbackDTO.getComment());
            return feedbackRepository.save(feedback);
        }
        return null;
    }

    // Delete Feedback
    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

//    public FeedbackResponseDTO convert(Feedback feedback) {
//
//    }
}
