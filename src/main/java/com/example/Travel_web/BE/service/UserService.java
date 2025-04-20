package com.example.Travel_web.BE.service;

import com.example.Travel_web.BE.dto.response.UserResponseDTO;
import com.example.Travel_web.BE.model.User;
import com.example.Travel_web.BE.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    // Create
    public User createUser(User user) {
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email đã tồn tại");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    // Read (Find all users)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Read (Find by ID)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User handleGetUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    // Update
    public User updateUser(Long id, User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Kiểm tra nếu email đã tồn tại và không phải là email của chính người dùng
            if (userRepository.findByEmail(userDetails.getEmail()) != null &&
                    !userDetails.getEmail().equals(user.getEmail())) {
                throw new RuntimeException("Email đã tồn tại");
            }

            user.setUserName(userDetails.getUserName());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            user.setAvatar(userDetails.getAvatar());
            user.setUpdatedBy(userDetails.getUpdatedBy());
            return userRepository.save(user);
        }
        return null;
    }

    // Delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponseDTO convert(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUserName(user.getUserName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setAvatar(user.getAvatar());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setUpdatedAt(user.getUpdatedAt());
        userResponseDTO.setUpdatedBy(user.getUpdatedBy());
        userResponseDTO.setCreatedBy(user.getCreatedBy());
        return userResponseDTO;
    }
}