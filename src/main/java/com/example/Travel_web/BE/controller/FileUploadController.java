package com.example.Travel_web.BE.controller;

import com.example.Travel_web.BE.model.Tour;
import com.example.Travel_web.BE.repository.TourRepository;
import com.example.Travel_web.BE.service.ImageToursService;
import com.example.Travel_web.BE.util.FileUploadUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;


@RestController
public class FileUploadController {
    // Thư mục lưu ảnh thumbnail và ảnh chi tiết
    private static final String THUMBNAIL_DIR = "thumbnails";
    private static final String DETAIL_DIR = "details";
    private final TourRepository tourRepository;
    private final ImageToursService imageToursService;
    public FileUploadController(TourRepository tourRepository, ImageToursService imageToursService) {
        this.tourRepository = tourRepository;
        this.imageToursService = imageToursService;
    }
    @PostMapping("/uploadImages/{tourID}")
    public ResponseEntity<String> uploadImages( @RequestParam("thumbnailFiles") MultipartFile thumbnailFiles,
                                                @RequestParam("detailFiles[]") MultipartFile[] detailFiles,
                                                @PathVariable long tourID) throws IOException {
        Tour tour = tourRepository.findById(tourID).orElse(null);
        if (tour == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour not found.");
        }
        try {
            if(!thumbnailFiles.isEmpty()) {
                String fileName = thumbnailFiles.getOriginalFilename();
                tour.setThumbnailUrl(FileUploadUtil.saveFile(fileName, thumbnailFiles, THUMBNAIL_DIR));
            }
            // Lưu ảnh chi tiết
            for (MultipartFile file : detailFiles) {
                String fileName = file.getOriginalFilename();
                String path = FileUploadUtil.saveFile(fileName, file, DETAIL_DIR);
                imageToursService.create(tour, fileName, path);
            }

            return ResponseEntity.ok("Files uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading files.");
        }
    }

    // API GET để lấy ảnh từ server theo đường dẫn
    @GetMapping("/getImage/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        // Tạo đường dẫn đầy đủ đến ảnh
        Path path = Paths.get("Files-Upload/" + fileName);

        // Kiểm tra xem file có tồn tại không
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();  // Trả về lỗi nếu file không tồn tại
        }

        // Đọc file dưới dạng byte array
        byte[] imageBytes = Files.readAllBytes(path);

        // Trả về ảnh với đúng Content-Type
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")  // Thay đổi Content-Type nếu cần (PNG, GIF, v.v.)
                .body(imageBytes);
    }
}
