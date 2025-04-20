package com.example.Travel_web.BE.controller;

import com.example.Travel_web.BE.dto.request.LoginDTO;
import com.example.Travel_web.BE.dto.request.RegisterDTO;
import com.example.Travel_web.BE.dto.response.ApiResponse;
import com.example.Travel_web.BE.dto.response.LoginResponseDTO;
import com.example.Travel_web.BE.model.User;
import com.example.Travel_web.BE.repository.UserRepository;
import com.example.Travel_web.BE.service.UserService;
import com.example.Travel_web.BE.util.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtils jwtUtils;
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, JwtUtils jwtUtils,
                          UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        try {
            // Tạo đối tượng Authentication với username và password
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

            // Xử lý xác thực người dùng thông qua AuthenticationManager
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // Kiểm tra nếu xác thực thành công, trả về thông báo thành công
            if (authentication.isAuthenticated()) {
                User user = userService.handleGetUserByEmail(loginDTO.getEmail());
                LoginResponseDTO.User userLogin = new LoginResponseDTO.User(user.getId(), user.getUserName(), user.getEmail(), user.getPhone(), user.getAvatar());
                String token = jwtUtils.createToken(userLogin);
                LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, userLogin);
                ApiResponse successResponse = new ApiResponse("SUCCESS", "Đăng nhập thành công", loginResponseDTO);
                return ResponseEntity.ok(successResponse);
            }
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("FAIL", "Đăng nhập thất bại: " + e.getMessage(), null);
            return ResponseEntity.status(401).body(errorResponse);
        }
        ApiResponse defaultErrorResponse = new ApiResponse("FAIL", "Đăng nhập thất bại", null);
        return ResponseEntity.status(401).body(defaultErrorResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDTO registerDTO) {
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());

        User newUser = new User();
        newUser.setUserName(registerDTO.getUsername());
        newUser.setPassword(registerDTO.getPassword());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPhone(registerDTO.getPhone());
        userService.createUser(newUser);

        ApiResponse successResponse = new ApiResponse("SUCCESS", "Đăng ký thành công", null);
        return ResponseEntity.ok(successResponse);
    }

    @GetMapping("/api/getUserId")
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof Jwt) {
                Jwt jwt = (Jwt) principal;
                Map<String, Object> userMap = jwt.getClaim("user");
                Optional<User> user = userService.getUserById((Long) userMap.get("id"));
                return user.orElse(null).getId();
            }
        }
        return null;
    }
}
