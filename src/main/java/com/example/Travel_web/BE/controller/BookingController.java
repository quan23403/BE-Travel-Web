package com.example.Travel_web.BE.controller;

import com.example.Travel_web.BE.dto.request.BookingRequestDTO;
import com.example.Travel_web.BE.dto.response.ApiResponse;
import com.example.Travel_web.BE.dto.response.BookingResponseDTO;
import com.example.Travel_web.BE.model.Booking;
import com.example.Travel_web.BE.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    // Create Booking
    @PostMapping
    public ResponseEntity<ApiResponse> createBooking(@RequestBody BookingRequestDTO booking) {
        try {
            BookingResponseDTO createdBooking = bookingService.createBooking(booking);
            ApiResponse response = new ApiResponse("success", "Booking created successfully", createdBooking);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            ApiResponse response = new ApiResponse("error", e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // Get All Bookings
    @GetMapping
    public ResponseEntity<ApiResponse> getAllBookings() {
        List<BookingResponseDTO> bookings = bookingService.getAllBookings();
        ApiResponse response = new ApiResponse("success", "Fetched all bookings", bookings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get Booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBookingById(@PathVariable Long id) {
        Optional<BookingResponseDTO> booking = bookingService.getBookingById(id);
        if (booking.isPresent()) {
            ApiResponse response = new ApiResponse("success", "Booking found", booking.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse("error", "Booking not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Update Booking
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateBooking(@PathVariable Long id, @RequestBody BookingRequestDTO bookingDetails) {
        BookingResponseDTO updatedBooking = bookingService.updateBooking(id, bookingDetails);
        if (updatedBooking != null) {
            ApiResponse response = new ApiResponse("success", "Booking updated successfully", updatedBooking);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse response = new ApiResponse("error", "Booking not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Delete Booking
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        ApiResponse response = new ApiResponse("success", "Booking deleted successfully", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
