-- Student Form Application Database Schema
-- This file is for reference only - tables are auto-created by Spring Boot

-- Create Database
CREATE DATABASE IF NOT EXISTS student_form_db;
USE student_form_db;

-- Students Table
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    id_number VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(10) NOT NULL,
    pan_file_path VARCHAR(500),
    aadhaar_file_path VARCHAR(500),
    folder_path VARCHAR(500),
    pdf_path VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_id_number (id_number),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample Queries

-- Get all students
SELECT * FROM students ORDER BY created_at DESC;

-- Get student by email
SELECT * FROM students WHERE email = 'john@example.com';

-- Get students created today
SELECT * FROM students 
WHERE DATE(created_at) = CURDATE()
ORDER BY created_at DESC;

-- Get student count
SELECT COUNT(*) as total_students FROM students;

-- Get recent submissions (last 10)
SELECT id, customer_name, email, phone_number, created_at 
FROM students 
ORDER BY created_at DESC 
LIMIT 10;

-- Search by customer name
SELECT * FROM students 
WHERE customer_name LIKE '%John%'
ORDER BY created_at DESC;

-- Delete a student (use carefully)
-- DELETE FROM students WHERE id = 1;

-- Clear all data (use carefully in development only)
-- TRUNCATE TABLE students;
