package com.example.Travel_web.BE.service;

import com.example.Travel_web.BE.model.ImageTours;
import com.example.Travel_web.BE.model.Tour;
import com.example.Travel_web.BE.repository.ImagesTourRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageToursService {
    private final ImagesTourRepository imagesTourRepository;
    public ImageToursService(ImagesTourRepository imagesTourRepository) {
        this.imagesTourRepository = imagesTourRepository;
    }

    public ImageTours create(Tour tour, String nameFile, String path) {
        ImageTours imageTours = new ImageTours();
        imageTours.setTour(tour);
        imageTours.setImageName(nameFile);
        imageTours.setPath(path);
        return imagesTourRepository.save(imageTours);
    }
}
