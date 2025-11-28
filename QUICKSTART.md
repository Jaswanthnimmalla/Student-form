# Quick Start Guide

Get the Student Form Application up and running in minutes!

## 🚀 Quick Setup (5 Minutes)

### Step 1: Backend Setup (2 minutes)

1. **Start MySQL**
   ```bash
   # Windows: Start MySQL service from Services
   # Mac: brew services start mysql
   # Linux: sudo systemctl start mysql
   ```

2. **Configure Database** (Optional - auto-created)
   ```bash
   mysql -u root -p
   CREATE DATABASE student_form_db;
   EXIT;
   ```

3. **Update credentials** (if not using root/root)
   Edit `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```

4. **Run Backend**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

   Wait for: `Started StudentFormBackendApplication`

### Step 2: Android App Setup (3 minutes)

1. **Open in Android Studio**
    - Open Android Studio
    - Click "Open" → Select project folder
    - Wait for Gradle sync to complete

2. **Update Backend URL** (for real device only)
    - If testing on emulator: Skip this step
    - If testing on real device:
        - Find your computer's IP address:
          ```bash
          # Windows
          ipconfig
          # Look for IPv4 Address
          
          # Mac/Linux
          ifconfig | grep inet
          ```
        - Open `app/src/main/java/com/scan/studentformapp/network/RetrofitClient.kt`
        - Change:
          ```kotlin
          private const val BASE_URL = "http://YOUR_IP_ADDRESS:8080/"
          ```

3. **Run the App**
    - Connect device or start emulator
    - Click Run button (▶️) or press Shift+F10

## ✅ Verify Everything Works

### Test Backend

Open browser: `http://localhost:8080/api/students/all`

Expected response:

```json
{
  "success": true,
  "message": "Students retrieved successfully",
  "data": []
}
```

### Test Android App

1. App opens showing "Student Registration Form"
2. Fill in all fields:
    - Customer Name: John Doe
    - ID Number: 12345
    - Email: john@example.com
    - Phone: 9876543210
    - Upload PAN file (90-120 KB)
    - Upload Aadhaar file
3. Click "Submit Form"
4. Should see success screen!

## 📂 Test Files

Create test PDF files (90-120 KB for PAN):

### Create PAN Test File (Windows)

```powershell
fsutil file createnew pan_test.pdf 102400
```

### Create PAN Test File (Mac/Linux)

```bash
dd if=/dev/zero of=pan_test.pdf bs=1024 count=100
```

### Create Aadhaar Test File

Any file will work for Aadhaar (no size restriction)

## 🔍 Check Results

### Database

```bash
mysql -u root -p
USE student_form_db;
SELECT * FROM students;
```

### File System

Check `backend/uploads/` folder:

```
uploads/
└── John_Doe/
    ├── PAN_pan_test.pdf
    ├── AADHAAR_aadhaar_test.pdf
    └── student_form_1.pdf
```

## 🐛 Common Issues

### Backend won't start

**Error**: "Cannot connect to database"

```bash
# Solution: Check MySQL is running
# Windows: services.msc → MySQL → Start
# Mac: brew services start mysql
# Linux: sudo systemctl start mysql
```

**Error**: "Port 8080 already in use"

```bash
# Solution: Change port in application.properties
server.port=8081
```

### Android app can't connect

**Error**: "Failed to connect to /10.0.2.2:8080"

```
Solution:
1. Ensure backend is running (check console)
2. For real device: Update BASE_URL with computer's IP
3. Disable Windows Firewall temporarily
4. Make sure device and computer are on same WiFi
```

### File upload fails

**Error**: "PAN file must be between 90-120 KB"

```
Solution: Create a file in the correct size range (see above)
```

## 📱 Testing Workflow

1. **Start backend** → See "Started" message
2. **Run Android app** → App opens
3. **Fill form** → All fields required
4. **Select files** → Check file sizes
5. **Submit** → See loading indicator
6. **Success!** → View success screen

## 🎯 Next Steps

- Check the database for saved records
- Explore the generated PDF in uploads folder
- Try the GET endpoints in browser/Postman
- Customize the UI theme
- Add more validations

## 📞 Need Help?

- Check full README.md for detailed documentation
- Review application logs for errors
- Verify all prerequisites are installed
- Ensure ports 8080 and 3306 are not blocked

---

**Happy Coding! 🚀**
