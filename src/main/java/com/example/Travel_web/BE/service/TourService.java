package com.example.Travel_web.BE.service;

import com.example.Travel_web.BE.dto.request.TourRequestDTO;
import com.example.Travel_web.BE.dto.response.TourResponseDTO;
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
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Tour create(TourRequestDTO tourRequestDTO) {
        Tour tour = new Tour();
        tour.setName(tourRequestDTO.getTourInfo().getName());
        tour.setStartLocation(tourRequestDTO.getTourInfo().getStartLocation());
        tour.setEndLocation(tourRequestDTO.getTourInfo().getEndLocation());
        tour.setDuration(tourRequestDTO.getTourInfo().getDuration());
        tour.setBasePrice(tourRequestDTO.getTourInfo().getBasePrice());
        tour.setDescription(tourRequestDTO.getTourInfo().getDescription());
        tour.setTravelType(tourRequestDTO.getTourInfo().getTravelType());
        tour.setLevel(tourRequestDTO.getTourInfo().getLevel());
        tour.setDiscount(tourRequestDTO.getTourInfo().getDiscount());
        tour.setMaxGroupSize(tourRequestDTO.getTourInfo().getMaxGroupSize());

        //Create Schedules
        List<Schedule> scheduleList = tourRequestDTO.getSchedules().stream().map(scheduleRequestDTO -> {
            Schedule schedule = new Schedule();
            LocalDate startDate = scheduleRequestDTO.getStartDate();
            // Calculate endDate by adding duration (days) to the startDate
            LocalDate endDate = startDate.plusDays(tourRequestDTO.getTourInfo().getDuration().getDays());

            schedule.setStartDate(scheduleRequestDTO.getStartDate());
            schedule.setEndDate(endDate);
            schedule.setSlot(tourRequestDTO.getTourInfo().getMaxGroupSize());
            schedule.setTour(tour);  // Liên kết Schedule với Tour
            return schedule;
        }).toList();

        //Create Itinerary
        List<Itinerary> itineraryList = tourRequestDTO.getItinerary().stream().map(itineraryDTO -> {
            Itinerary itinerary = new Itinerary();
            itinerary.setTitle(itineraryDTO.getTitle());
            itinerary.setDayNumber(itineraryDTO.getDayNumber());
            itinerary.setDescription(itineraryDTO.getDescription());
            itinerary.setTour(tour);  // Liên kết Schedule với Tour
            return itinerary;
        }).toList();

        //Create Faq
        List<Faq> faqList = tourRequestDTO.getFaq().stream().map(faqDTO -> {
            Faq faq = new Faq();
            faq.setAnswer(faqDTO.getAnswer());
            faq.setQuestion(faqDTO.getQuestion());
            faq.setTour(tour);  // Liên kết Schedule với Tour
            return faq;
        }).toList();
        tour.setSchedules(scheduleList);
        tour.setItineraries(itineraryList);
        tour.setFaqs(faqList);
        return tourRepository.save(tour);
    }

    public List<TourResponseDTO> getAllTour() {
        // Fetch all Tour entities
        List<Tour> tours = tourRepository.findAll();

        // Transform List<Tour> to List<TourResponseDTO> using stream
        return tours.stream().map(tour -> {
            TourResponseDTO dto = new TourResponseDTO();
            dto.setId(tour.getId());
            dto.setName(tour.getName());
            dto.setDuration(tour.getDuration() != null ? tour.getDuration().getLabel() : null);
            dto.setLevel(tour.getLevel() != null ? tour.getLevel().toString() : null);
            dto.setType(tour.getTravelType());
            dto.setBasePrice(tour.getBasePrice() != null ? tour.getBasePrice() : 0);
            dto.setDiscount(tour.getDiscount() != null ? tour.getDiscount() : 0);
            dto.setDescription(tour.getDescription());
            dto.setThumbnail(tour.getThumbnailUrl());
            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<Tour> getTourDetail(long id) {
        return this.tourRepository.findById(id);
    }
}
