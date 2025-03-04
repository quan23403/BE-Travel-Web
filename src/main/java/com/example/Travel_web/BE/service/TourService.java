package com.example.Travel_web.BE.service;

import com.example.Travel_web.BE.dto.request.TourRequestDTO;
import com.example.Travel_web.BE.model.Faq;
import com.example.Travel_web.BE.model.Itinerary;
import com.example.Travel_web.BE.model.Schedule;
import com.example.Travel_web.BE.model.Tour;
import com.example.Travel_web.BE.repository.FaqRepository;
import com.example.Travel_web.BE.repository.ItineraryRepository;
import com.example.Travel_web.BE.repository.ScheduleRepository;
import com.example.Travel_web.BE.repository.TourRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TourService {
    private final TourRepository tourRepository;
    private final ScheduleRepository scheduleRepository;
    private final ItineraryRepository itineraryRepository;
    private final FaqRepository faqRepository;
    public TourService(TourRepository tourRepository, ScheduleRepository scheduleRepository,
                       ItineraryRepository itineraryRepository, FaqRepository faqRepository) {
        this.tourRepository = tourRepository;
        this.scheduleRepository = scheduleRepository;
        this.itineraryRepository = itineraryRepository;
        this.faqRepository = faqRepository;
    }

    @Transactional
    public void create(TourRequestDTO tourRequestDTO) {
        Tour tour = new Tour();
        tour.setName(tourRequestDTO.getTour().getName());
        tour.setStartLocation(tourRequestDTO.getTour().getStartLocation());
        tour.setEndLocation(tourRequestDTO.getTour().getEndLocation());
        tour.setDuration(tourRequestDTO.getTour().getDuration());
        tour.setBasePrice(tourRequestDTO.getTour().getBasePrice());
        tour.setDescription(tourRequestDTO.getTour().getDescription());
        tour.setTravelType(tourRequestDTO.getTour().getTravelType());
        tour.setLevel(tourRequestDTO.getTour().getLevel());
        tour.setDiscount(tourRequestDTO.getTour().getDiscount());
        tour.setMaxGroupSize(tourRequestDTO.getTour().getMaxGroupSize());

        //Create Schedules
        List<Schedule> scheduleList = tourRequestDTO.getSchedules().stream().map(scheduleRequestDTO -> {
            Schedule schedule = new Schedule();
            schedule.setStartDate(scheduleRequestDTO.getStartDate());
            schedule.setEndDate(scheduleRequestDTO.getEndDate());
            schedule.setSlot(tourRequestDTO.getTour().getMaxGroupSize());
            schedule.setTour(tour);  // Liên kết Schedule với Tour
            return schedule;
        }).toList();

        //Create Itinerary
        List<Itinerary> itineraryList = tourRequestDTO.getItinerarys().stream().map(itineraryDTO -> {
            Itinerary itinerary = new Itinerary();
            itinerary.setTitle(itineraryDTO.getTitle());
            itinerary.setDayNumber(itineraryDTO.getDayNumber());
            itinerary.setDescription(itineraryDTO.getDescription());
            itinerary.setTour(tour);  // Liên kết Schedule với Tour
            return itinerary;
        }).toList();

        //Create Faq
        List<Faq> faqList = tourRequestDTO.getFaqs().stream().map(faqDTO -> {
            Faq faq = new Faq();
            faq.setAnswer(faqDTO.getAnswer());
            faq.setQuestion(faqDTO.getQuestion());
            faq.setTour(tour);  // Liên kết Schedule với Tour
            return faq;
        }).toList();
        tour.setSchedules(scheduleList);
        tour.setItineraries(itineraryList);
        tour.setFaqs(faqList);
        tourRepository.save(tour);
    }
}
