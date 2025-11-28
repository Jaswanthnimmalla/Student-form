package com.scan.studentformbackend.controller;

import com.scan.studentformbackend.dto.ApiResponse;
import com.scan.studentformbackend.model.Student;
import com.scan.studentformbackend.service.StudentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createStudent(
            @RequestParam("customerName") String customerName,
            @RequestParam("idNumber") String idNumber,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("panFile") MultipartFile panFile,
            @RequestParam("aadhaarFile") MultipartFile aadhaarFile) {

        logger.info("Received request to create student: {}", customerName);

        try {
            // Validate required fields
            if (customerName == null || customerName.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Customer name is required"));
            }

            if (idNumber == null || idNumber.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "ID number is required"));
            }

            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Email is required"));
            }

            if (phoneNumber == null || phoneNumber.trim().isEmpty() || phoneNumber.length() != 10) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Valid 10-digit phone number is required"));
            }

            if (panFile == null || panFile.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "PAN file is required"));
            }

            if (aadhaarFile == null || aadhaarFile.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Aadhaar file is required"));
            }

            // Validate PAN file size (90KB - 120KB)
            long panFileSizeKB = panFile.getSize() / 1024;
            if (panFileSizeKB < 90 || panFileSizeKB > 120) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false,
                                "PAN file size must be between 90-120 KB. Current size: " + panFileSizeKB + " KB"));
            }

            Student student = studentService.createStudent(
                    customerName, idNumber, email, phoneNumber, panFile, aadhaarFile);

            logger.info("Student created successfully: {}", student.getId());

            return ResponseEntity.ok(new ApiResponse(true,
                    "Student registration completed successfully! Files saved and PDF generated.",
                    student));

        } catch (IllegalArgumentException e) {
            logger.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));

        } catch (Exception e) {
            logger.error("Error creating student: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error processing request: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(new ApiResponse(true, "Students retrieved successfully", students));
        } catch (Exception e) {
            logger.error("Error retrieving students: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error retrieving students"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Student found", student));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            logger.error("Error retrieving student: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error retrieving student"));
        }
    }
}
