package com.example.Travel_web.BE.service;

import com.example.Travel_web.BE.dto.request.BookingRequestDTO;
import com.example.Travel_web.BE.dto.response.BookingResponseDTO;
import com.example.Travel_web.BE.model.Booking;
import com.example.Travel_web.BE.model.Schedule;
import com.example.Travel_web.BE.model.User;
import com.example.Travel_web.BE.repository.BookingRepository;
import com.example.Travel_web.BE.repository.ScheduleRepository;
import com.example.Travel_web.BE.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, ScheduleRepository scheduleRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository  = userRepository;
    }
    // Create Booking
    public BookingResponseDTO createBooking(BookingRequestDTO bookingDTO) {
        // Kiểm tra xem schedule_id và user_id có tồn tại không
        Optional<User> userOptional = userRepository.findById(bookingDTO.getUserId());
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(bookingDTO.getScheduleId());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        if (scheduleOptional.isEmpty()) {
            throw new RuntimeException("Schedule not found");
        }

        // Tạo booking và gán user và schedule vào
        Booking booking = new Booking();
        booking.setUser(userOptional.get());
        booking.setSchedule(scheduleOptional.get());
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setNumberOfPeople(bookingDTO.getNumberOfPeople());

        return convert(bookingRepository.save(booking));
    }

    // Get All Bookings
    public List<BookingResponseDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        // Chuyển đổi danh sách bookings thành danh sách BookingResponseDTO
        return bookings.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    // Get Booking by ID
    public Optional<BookingResponseDTO> getBookingById(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        // Nếu có booking, chuyển đổi thành BookingResponseDTO
        return bookingOptional.map(this::convert);
    }

    public BookingResponseDTO updateBooking(Long id, BookingRequestDTO bookingDTO) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            // Cập nhật booking
            booking.setStatus(bookingDTO.getStatus());
            // Lưu và chuyển đổi booking thành BookingResponseDTO
            return convert(bookingRepository.save(booking));
        }
        return null;
    }

    // Delete Booking
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public BookingResponseDTO convert(Booking booking) {
        // Tạo một đối tượng BookingResponseDTO mới
        BookingResponseDTO responseDTO = new BookingResponseDTO();

        // Ánh xạ dữ liệu từ đối tượng Booking sang BookingResponseDTO
        responseDTO.setId(booking.getId());
        responseDTO.setUserId(booking.getUser().getId());
        responseDTO.setUserName(booking.getUser().getUserName()); // Lấy userName từ đối tượng User
        responseDTO.setTourId(booking.getSchedule().getTour().getId());
        responseDTO.setTourName(booking.getSchedule().getTour().getName());  // Lấy tourName từ đối tượng Schedule
        responseDTO.setBookingDate(booking.getSchedule().getStartDate().toString());  // Chuyển đổi BookingDate thành String
        responseDTO.setStatus(booking.getStatus().name());  // Chuyển status từ Enum thành String
        responseDTO.setTotalPrice(booking.getTotalPrice());
        responseDTO.setNumberOfPeople(booking.getNumberOfPeople());
        responseDTO.setCreatedAt(booking.getCreatedAt().toString());  // Chuyển đổi thành String
        responseDTO.setUpdatedAt(booking.getUpdatedAt().toString());  // Chuyển đổi thành String
        return responseDTO;
    }
}
