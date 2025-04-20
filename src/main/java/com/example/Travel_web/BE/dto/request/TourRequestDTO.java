package com.example.Travel_web.BE.dto.request;

import com.example.Travel_web.BE.dto.FaqDTO;
import com.example.Travel_web.BE.dto.ItineraryDTO;
import com.example.Travel_web.BE.dto.ScheduleDTO;
import com.example.Travel_web.BE.dto.TourDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TourRequestDTO {
  private TourDTO tourInfo;
  private List<ScheduleDTO> schedules;
  private List<ItineraryDTO> itinerary;
  private List<FaqDTO> faq;
}
