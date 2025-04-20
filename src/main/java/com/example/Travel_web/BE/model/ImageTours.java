package com.example.Travel_web.BE.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images_tour")
@Getter
@Setter
public class ImageTours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)  // Khóa ngoại tham chiếu đến Tour
    private Tour tour;
}
