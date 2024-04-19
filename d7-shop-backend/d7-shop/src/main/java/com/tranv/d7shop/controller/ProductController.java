package com.tranv.d7shop.controller;

import com.tranv.d7shop.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("All Product" + page + "-" + limit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable long id) {
        return ResponseEntity.ok("Product with id " + id + " found");
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
//            @RequestPart("file") MultipartFile file,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessage);
            }
            MultipartFile file = productDTO.getFile();
            if (!file.isEmpty()) {
                // kiểm tra kích thước file và định dạng
                if (file.getSize() > 10 + 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).
                            body("File is too large! Maximum size is 10Mb");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("file must be an image");
                }
                //lưu đối tượng vào db
                String fileName = storeFile(file);
                productDTO.setThumbnail(fileName);
            }
            return ResponseEntity.ok("Product created successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id) {
        return ResponseEntity.ok("Product updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        return ResponseEntity.ok("Product deleted");
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // thêm uuid vào tên để tên là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;
        // Đường dẫn đến file
        Path uploadDir = Paths.get("upload");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường ẫn đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        //Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
