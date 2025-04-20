package com.example.Travel_web.BE.repository;

import com.example.Travel_web.BE.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
