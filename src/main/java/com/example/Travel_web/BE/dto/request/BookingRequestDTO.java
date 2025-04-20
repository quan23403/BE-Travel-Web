package com.example.Travel_web.BE.dto.request;

import com.example.Travel_web.BE.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequestDTO {
    private Long userId;
    private Long scheduleId;
    private BookingStatus status;
    private Integer numberOfPeople;
    private Double totalPrice;
}
