package com.example.Travel_web.BE.model;

import com.example.Travel_web.BE.enums.Level;
import com.example.Travel_web.BE.enums.Status;
import com.example.Travel_web.BE.enums.TourDuration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tour")
@Getter
@Setter
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "start_location", nullable = false)
    private String startLocation;

    @Column(name = "end_location", nullable = false)
    private String endLocation;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TourDuration duration;

    @Column(name = "base_price", nullable = false)
    private Double basePrice;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "travel_type")
    private String travelType;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double rating;

    private Double discount;

    @Column(name = "max_group_size", nullable = false)
    private int maxGroupSize;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

    // Quan hệ 1-N với Itinerary
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private List<Itinerary> itineraries;

    // Quan hệ 1-N với Faq
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private List<Faq> faqs;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageTours> imageTours;  // Mối quan hệ 1-N với ImageTour

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
