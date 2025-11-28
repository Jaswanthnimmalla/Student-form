package com.scan.studentformbackend.service;

import com.scan.studentformbackend.model.Student;
import com.scan.studentformbackend.repository.StudentRepository;
import com.scan.studentformbackend.util.PdfGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Transactional
    public Student createStudent(String customerName, String idNumber, String email,
                                 String phoneNumber, MultipartFile panFile,
                                 MultipartFile aadhaarFile) throws Exception {

        logger.info("Creating student: {}", customerName);

        // Validate if student already exists
        if (studentRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Student with this email already exists");
        }

        if (studentRepository.findByIdNumber(idNumber).isPresent()) {
            throw new IllegalArgumentException("Student with this ID number already exists");
        }

        // Create folder for customer
        String folderPath = fileStorageService.createCustomerFolder(customerName);
        logger.info("Customer folder created: {}", folderPath);

        // Save files
        String panFilePath = fileStorageService.saveFile(panFile, folderPath, "PAN");
        String aadhaarFilePath = fileStorageService.saveFile(aadhaarFile, folderPath, "AADHAAR");

        // Create student entity
        Student student = new Student();
        student.setCustomerName(customerName);
        student.setIdNumber(idNumber);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setPanFilePath(panFilePath);
        student.setAadhaarFilePath(aadhaarFilePath);
        student.setFolderPath(folderPath);

        // Save to database
        student = studentRepository.save(student);
        logger.info("Student saved to database with ID: {}", student.getId());

        // Generate PDF
        String pdfPath = Paths.get(folderPath, "student_form_" + student.getId() + ".pdf").toString();
        pdfGenerator.generateStudentPdf(student, pdfPath);
        student.setPdfPath(pdfPath);

        // Update with PDF path
        student = studentRepository.save(student);
        logger.info("PDF generated and saved at: {}", pdfPath);

        return student;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
    }
}
