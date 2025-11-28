# 📱 Student Form Application

A full-stack mobile application for student registration and document management, built with *
*Android (Java)** frontend and **Spring Boot** backend.

[![Android](https://img.shields.io/badge/Android-Java-green.svg)](https://developer.android.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 🎯 **Project Overview**

A complete student registration system that digitizes the enrollment process, allowing students to
register through a mobile app, upload required documents (PAN & Aadhaar), and receive instant
confirmation. The system automatically organizes files, stores data in MySQL, and generates PDF
records.

### **Live Demo**

- App works on any Android device (API 24+)
- Backend runs on `http://localhost:8081` (or deploy to cloud)

---

## ✨ **Features**

### **Mobile App (Android)**

- 📝 Student registration form with 6 fields
- 📎 Document upload (PAN & Aadhaar cards)
- ✅ Real-time validation (email, phone, file size)
- 📏 File size validation (90-120 KB for PAN)
- 📱 Modern Material Design UI
- ⚡ Loading states and error handling
- 🎉 Beautiful success screen with personalized messages

### **Backend (Spring Boot)**

- 🔄 RESTful API with multipart file upload
- 🗄️ MySQL database integration
- 📁 Automatic folder creation per student
- 💾 File storage and organization
- 📄 Automatic PDF generation with form data
- 🔍 Duplicate detection (email & ID uniqueness)
- 🛡️ Input validation and error handling

---

## 🏗️ **Architecture**

```
┌─────────────────┐
│  Android App    │  ← User Interface (Java + XML)
│   (Frontend)    │
└────────┬────────┘
         │ HTTP/REST
         │ (Retrofit)
         ↓
┌─────────────────┐
│  Spring Boot    │  ← Business Logic (Java)
│   (Backend)     │
└────────┬────────┘
         │ JDBC/JPA
         ↓
┌─────────────────┐
│     MySQL       │  ← Data Storage
│   (Database)    │
└─────────────────┘
```

---

## 🛠️ **Tech Stack**

### **Frontend (Android)**

- **Language:** Java
- **UI:** XML Layouts (Material Design)
- **API Client:** Retrofit 2.9.0
- **HTTP Client:** OkHttp 4.12.0
- **JSON Parser:** Gson 2.10.1
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 36

### **Backend**

- **Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **ORM:** Hibernate/JPA
- **Build Tool:** Maven
- **PDF Library:** iText 7.2.5

### **Database**

- **Database:** MySQL 8.0
- **Tables:** students (with auto-generated IDs)

---

## 📋 **Prerequisites**

### **For Android App:**

- Android Studio (latest version)
- JDK 11 or higher
- Android device or emulator (API 24+)

### **For Backend:**

- JDK 17
- Maven 3.6+
- MySQL 8.0+
- XAMPP (optional, for MySQL)

---

## 🚀 **Installation & Setup**

### **1. Clone the Repository**

```bash
git clone https://github.com/Jaswanthnimmalla/Student-form.git
cd Student-form
```

### **2. Setup MySQL Database**

```sql
-- Create database
CREATE DATABASE student_form_db;

-- The application will auto-create tables on first run
```

**Or use XAMPP:**

1. Start MySQL in XAMPP Control Panel
2. Database will be created automatically

### **3. Configure Backend**

**Update `backend/src/main/resources/application.properties`:**

```properties
# Server Configuration
server.port=8081

# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/student_form_db
spring.datasource.username=root
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### **4. Run Backend**

```bash
cd backend
mvn spring-boot:run
```

**Or use the batch file:**

```bash
START-BACKEND-CORRECTLY.bat
```

Wait for: `Started StudentFormBackendApplication`

### **5. Configure Android App**

**Update IP address in `app/src/main/java/com/scan/studentformapp/network/RetrofitClient.java`:**

```java
// For emulator
private static final String BASE_URL = "http://10.0.2.2:8081/";

// For real device (use your computer's IP)
private static final String BASE_URL = "http://192.168.0.4:8081/";
```

### **6. Build & Run Android App**

1. Open project in Android Studio
2. Sync Gradle
3. Connect Android device or start emulator
4. Click Run (▶️)

---

## 📱 **Usage**

### **Submit Student Registration:**

1. **Open the app** on your Android device
2. **Fill the form:**
    - Customer Name
    - ID Number
    - Email Address
    - Phone Number (10 digits)
    - Upload PAN file (90-120 KB)
    - Upload Aadhaar file
3. **Click "Submit Form"**
4. **View success screen** with confirmation

### **View Stored Data:**

**Option 1: phpMyAdmin**
```
http://localhost/phpmyadmin/
→ student_form_db → students
```

**Option 2: MySQL CLI**

```sql
mysql -u root
USE student_form_db;
SELECT * FROM students;
```

**Option 3: API Endpoint**

```
http://localhost:8081/api/students/all
```

### **View Uploaded Files:**

Navigate to:
```
backend/uploads/[customer_name]/
  ├── PAN_filename.pdf
  ├── AADHAAR_filename.pdf
  └── student_form_X.pdf (generated)
```

---

## 🔌 **API Endpoints**

### **Base URL:** `http://localhost:8081`

| Method | Endpoint               | Description                   |
|--------|------------------------|-------------------------------|
| POST   | `/api/students/create` | Create new student with files |
| GET    | `/api/students/all`    | Get all students              |
| GET    | `/api/students/{id}`   | Get student by ID             |

### **Create Student Example:**

```bash
curl -X POST http://localhost:8081/api/students/create \
  -F "customerName=John Doe" \
  -F "idNumber=ID12345" \
  -F "email=john@example.com" \
  -F "phoneNumber=9876543210" \
  -F "panFile=@pan.pdf" \
  -F "aadhaarFile=@aadhaar.pdf"
```

---

## 📂 **Project Structure**

```
StudentFormApp/
├── app/                          # Android Application
│   ├── src/main/
│   │   ├── java/.../studentformapp/
│   │   │   ├── MainActivity.java
│   │   │   ├── SuccessActivity.java
│   │   │   ├── model/
│   │   │   ├── network/
│   │   │   └── utils/
│   │   └── res/
│   │       ├── layout/
│   │       └── values/
│   └── build.gradle
│
├── backend/                      # Spring Boot Backend
│   ├── src/main/
│   │   ├── java/.../studentformbackend/
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
├── .gitignore
├── README.md
└── START-BACKEND-CORRECTLY.bat
```

---

## 🧪 **Testing**

### **Test Data:**

- **Name:** Test User
- **ID:** TEST123
- **Email:** test@example.com
- **Phone:** 9876543210
- **PAN File:** 100 KB PDF
- **Aadhaar File:** Any PDF

### **Expected Result:**

1. ✅ Success screen displays
2. ✅ Data saved in MySQL
3. ✅ Files saved in `uploads/Test User/`
4. ✅ PDF generated automatically

---

## 🎨 **Screenshots**

### **Mobile App**

- Registration Form (Main Screen)
- Success Screen with Confirmation

### **Backend**

- MySQL Database Records
- Generated PDF with Student Data
- Organized File Structure

---

## 🔧 **Troubleshooting**

### **Backend won't start:**

```bash
# Check if port 8081 is available
netstat -ano | findstr :8081

# Kill process if needed
taskkill /PID [process_id] /F
```

### **App can't connect:**

- Ensure backend is running
- Check IP address in RetrofitClient.java
- Both devices must be on same WiFi (for real device)
- For emulator, use `10.0.2.2`

### **MySQL connection failed:**

- Start MySQL in XAMPP
- Verify credentials in application.properties
- Check MySQL is running on port 3306

---

## 📈 **Future Enhancements**

- [ ] User authentication (login/register)
- [ ] View previous submissions
- [ ] Download generated PDFs from app
- [ ] Search and filter students
- [ ] Push notifications on success
- [ ] Cloud deployment (AWS, Heroku)
- [ ] Dark mode support
- [ ] Multi-language support

---

## 🤝 **Contributing**

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 **License**

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 **Author**

**Jaswanth Nimmalla**

- GitHub: [@Jaswanthnimmalla](https://github.com/Jaswanthnimmalla)
- LinkedIn: [Your LinkedIn Profile]
- Email: your.email@example.com

---

## 🙏 **Acknowledgments**

- Android Open Source Project
- Spring Boot Framework
- iText PDF Library
- Retrofit by Square
- Material Design Components

---

## 📞 **Support**

For support, email your.email@example.com or open an issue in this repository.

---

## ⭐ **Star This Repository**

If you found this project helpful, please give it a star! It helps others discover the project.

---

**Made with ❤️ by Jaswanth Nimmalla**
