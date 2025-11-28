# 📱 Student Form Application

> A full-stack Android application for student registration with document management, featuring
> automatic PDF generation and MySQL database storage.

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Java](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot%203.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/Database-MySQL%208.0-blue.svg)](https://www.mysql.com/)

---

## 🎯 Overview

Mobile application for student registration with PAN and Aadhaar document upload, featuring
automatic PDF generation and MySQL database storage. Built with Java for Android and Spring Boot
backend.

---

## ✨ Features

### 📱 **Android App**

- Student registration form (Name, ID, Email, Phone)
- Document upload (PAN & Aadhaar)
- Real-time form validation (email, phone, file size)
- Beautiful splash screen with app logo
- Responsive design for all screen sizes
- Scrolling marquee text with information
- Loading indicators and animations
- Animated success screen
- Material Design UI

### 🔧 **Backend API**

- RESTful API with Spring Boot
- Automatic folder creation per student
- File upload handling
- PDF generation with form data
- MySQL database integration
- Data validation and error handling

---

## 🛠️ Tech Stack

### Android App
- **Language:** Java
- **UI:** XML Layouts
- **Networking:** Retrofit 2.9.0
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 36

### Backend
- **Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **Database:** MySQL 8.0
- **Build Tool:** Maven
- **PDF:** iText 7.2.5

---

## 🚀 Quick Start

### Prerequisites

- Android Studio (latest version)
- JDK 17+
- MySQL 8.0+ or XAMPP
- Android device or emulator (API 24+)

### 1. Clone Repository

```bash
git clone https://github.com/Jaswanthnimmalla/Student-form.git
cd Student-form
```

### 2. Start Backend

```bash
# Windows
START-BACKEND-CORRECTLY.bat

# Or manually
cd backend
mvnw.cmd spring-boot:run
```

Wait for: `Started StudentFormBackendApplication`

### 3. Configure Android App

Update IP in `app/src/main/java/com/scan/studentformapp/network/RetrofitClient.java`:

```java
// For emulator
private static final String BASE_URL = "http://10.0.2.2:8081/";

// For real device (use your computer's IP)
private static final String BASE_URL = "http://192.168.0.4:8081/";
```

### 4. Run Android App

1. Open project in Android Studio
2. Sync Gradle
3. Run on device or emulator

---

## 📦 Build Release .aab

```bash
# Windows
gradlew.bat bundleRelease

# Output: app\build\outputs\bundle\release\app-release.aab
```

---

## 🔧 Configuration

### Backend Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/student_form_db
spring.datasource.username=root
spring.datasource.password=
```

### Database Setup

MySQL will auto-create the database. Or manually:

```sql
CREATE DATABASE student_form_db;
```

---

## 📂 Project Structure

```
Student-form/
├── app/                          # Android Application
│   ├── src/main/
│   │   ├── java/                 # Java source files
│   │   │   ├── MainActivity.java
│   │   │   ├── SplashActivity.java
│   │   │   ├── SuccessActivity.java
│   │   │   ├── model/
│   │   │   ├── network/
│   │   │   └── utils/
│   │   └── res/                  # Resources (layouts, drawables)
│   └── build.gradle
│
├── backend/                      # Spring Boot Backend
│   ├── src/main/
│   │   ├── java/                 # Backend source files
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── util/
│   │   └── resources/
│   │       └── application.properties
│   ├── uploads/                  # Uploaded files (auto-created)
│   └── pom.xml
│
├── images/                       # App resources
│   └── student_form_logo.png
│
├── START-BACKEND-CORRECTLY.bat   # Backend launcher
├── PUSH-TO-GITHUB.bat           # Git push helper
└── README.md
```

---

## 📱 API Endpoints

**Base URL:** `http://localhost:8081`

| Method | Endpoint               | Description               |
|--------|------------------------|---------------------------|
| POST   | `/api/students/create` | Create student with files |
| GET    | `/api/students/all`    | Get all students          |
| GET    | `/api/students/test`   | Test endpoint             |

---

## 📸 Screenshots

<div align="center">
  <img src="images/student_form_logo.png" alt="App Logo" width="200"/>
</div>

- Splash screen with logo animation
- Main registration form with validation
- File upload interface
- Loading indicator
- Success screen with animations

---

## ✅ Features Implemented

- ✅ Form with all required fields
- ✅ Validations (email, phone, file size)
- ✅ File uploads (PAN & Aadhaar)
- ✅ Spring Boot backend with REST API
- ✅ MySQL database integration
- ✅ Retrofit API integration
- ✅ Automatic folder creation per customer
- ✅ PDF generation with form data
- ✅ Success/failure messages
- ✅ Responsive design for all screen sizes
- ✅ Beautiful splash screen
- ✅ Loading indicators and animations
- ✅ Signed .aab release build

---

## 🧪 Testing

1. Start the backend server
2. Open the Android app
3. Fill in the registration form:
    - Name, ID, Email, Phone
    - Upload PAN file (90-120 KB)
    - Upload Aadhaar file
4. Submit and verify:
    - Success screen appears
    - Data saved in MySQL
    - Files saved in `backend/uploads/[customer_name]/`
    - PDF generated automatically

---

## 📁 File Storage

All uploaded files and generated PDFs are stored in:

```
backend/uploads/[Customer_Name]/
├── PAN_filename.pdf              # Original PAN upload
├── AADHAAR_filename.pdf          # Original Aadhaar upload
└── student_form_[id].pdf         # Auto-generated PDF
```

---

## 🔐 Security

- Input validation on frontend and backend
- SQL injection prevention (JPA)
- File size validation
- Email format validation
- Phone number validation (10 digits)
- Duplicate email/ID prevention

---

## 🚀 Deployment

The application is ready for:

- Google Play Store upload (.aab file)
- Backend deployment on cloud platforms (Heroku, AWS, Railway)
- MySQL database hosting

---

## 📝 License

This project is available for educational purposes.

---

## 👨‍💻 Author

**Jaswanth Nimmalla**

- GitHub: [@Jaswanthnimmalla](https://github.com/Jaswanthnimmalla)
- Repository: [Student-form](https://github.com/Jaswanthnimmalla/Student-form)

---

## 🙏 Acknowledgments

- Android Open Source Project
- Spring Boot Framework
- iText PDF Library
- Retrofit by Square
- Material Design Components
- MySQL Database

---

## 📞 Support

For issues or questions, please open an issue in this repository.

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

**Made with ❤️ for Student Registration Management**

</div>

