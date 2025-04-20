package com.example.Travel_web.BE.repository;

import com.example.Travel_web.BE.model.ImageTours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesTourRepository extends JpaRepository<ImageTours, Long> {
}
