package com.example.Travel_web.BE.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponseDTO {
    private Long id;
    private Long userId;
    private String userName;// Tên người dùng
    private Long tourId;
    private String tourName;  // Tên tour
    private String bookingDate;  // Ngày đặt tour
    private String status;  // Trạng thái booking
    private Double totalPrice;  // Tổng giá tiền
    private Integer numberOfPeople;  // Số lượng người tham gia
    private String createdAt;  // Thời gian tạo
    private String updatedAt;  // Thời gian cập nhật
}
