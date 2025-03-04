package com.example.Travel_web.BE.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private int slot;

    public ScheduleDTO() {
    }

    public ScheduleDTO(LocalDate startDate, LocalDate endDate, int slot) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.slot = slot;
    }
}
