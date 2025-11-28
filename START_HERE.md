# 🚀 START HERE - First Time Setup

Welcome to the Student Form Application! This guide will get you up and running in minutes.

---

## 📖 What is This Project?

A complete **Android + Spring Boot** application for student registration that includes:

- 📱 Modern Android app with beautiful Material 3 UI
- 🖥️ Spring Boot REST API backend
- 💾 MySQL database
- 📄 Automatic PDF generation
- 📁 Organized file storage

---

## ⚡ Quick Start (Choose Your Path)

### 🆕 New to the Project?

→ Start with Step 1 below

### 🔄 Returning to continue work?

→ Jump to **[Running the Project](#running-the-project)**

### 📚 Want detailed information?

→ See **[Full Documentation](#full-documentation)**

---

## 📋 Step 1: Prerequisites Check

Before starting, make sure you have:

### Required Software

- [ ] **Android Studio** (latest version)
    - Download: https://developer.android.com/studio

- [ ] **Java JDK 17** or higher
    - Check: `java -version`
    - Download: https://www.oracle.com/java/technologies/downloads/

- [ ] **MySQL 8.0** or higher
    - Check: `mysql --version`
    - Download: https://dev.mysql.com/downloads/

- [ ] **Maven** (optional, bundled with backend)
    - Check: `mvn -version`

### System Requirements

- **OS**: Windows 10/11, macOS, or Linux
- **RAM**: Minimum 8GB (16GB recommended)
- **Disk Space**: At least 5GB free

---

## 📥 Step 2: Initial Setup

### A. Database Setup (2 minutes)

1. **Start MySQL Service**
   ```bash
   # Windows: Start from Services app
   # Mac: brew services start mysql
   # Linux: sudo systemctl start mysql
   ```

2. **Login to MySQL**
   ```bash
   mysql -u root -p
   ```

3. **Create Database** (Optional - auto-created by app)
   ```sql
   CREATE DATABASE student_form_db;
   EXIT;
   ```

### B. Backend Configuration (1 minute)

1. **Navigate to backend**
   ```bash
   cd backend
   ```

2. **Update credentials** (if not using root/root)
    - Open: `src/main/resources/application.properties`
    - Update:
      ```properties
      spring.datasource.username=YOUR_USERNAME
      spring.datasource.password=YOUR_PASSWORD
      ```

### C. Android Studio Setup (2 minutes)

1. **Open Android Studio**
    - Click "Open" or "Import Project"
    - Select the project root folder
    - Wait for Gradle sync (this may take a few minutes first time)

2. **Update Backend URL** (only if using real device)
    - Open: `app/src/main/java/com/scan/studentformapp/network/RetrofitClient.kt`
    - Find your computer's IP:
      ```bash
      # Windows: ipconfig
      # Mac/Linux: ifconfig | grep inet
      ```
    - Update:
      ```kotlin
      private const val BASE_URL = "http://YOUR_IP:8080/"
      ```
    - **Note**: For emulator, keep `http://10.0.2.2:8080/`

---

## ▶️ Step 3: Running the Project

### 1️⃣ Start the Backend

**Option A: Using Maven Command Line**

```bash
cd backend
mvn spring-boot:run
```

**Option B: Using IDE**

- Open `StudentFormBackendApplication.java`
- Click the green play button
- Or right-click → Run

**✅ Success indicator**: You'll see:

```
Started StudentFormBackendApplication in X.XXX seconds
```

**🌐 Test it**: Open browser → `http://localhost:8080/api/students/all`

---

### 2️⃣ Run the Android App

**In Android Studio:**

1. **Select Device**
    - Click device dropdown at top
    - Choose: Emulator (recommended) or Physical Device

2. **If using Emulator (First Time)**
    - Click "Device Manager" (phone icon)
    - Click "Create Device"
    - Select "Pixel 6" → Next
    - Download "Tiramisu" or latest → Next → Finish

3. **Run App**
    - Click green "Run" button (▶️)
    - Or press `Shift + F10`

**✅ Success indicator**:

- App launches
- You see "Student Registration Form" screen

---

## 🧪 Step 4: Test the Application

### Create Test Files

You need two test files:

**PAN File (must be 90-120 KB):**

```bash
# Windows (PowerShell)
fsutil file createnew pan_test.pdf 102400

# Mac/Linux
dd if=/dev/zero of=pan_test.pdf bs=1024 count=100
```

**Aadhaar File (any size):**

```bash
# Windows (PowerShell)
fsutil file createnew aadhaar_test.pdf 51200

# Mac/Linux
dd if=/dev/zero of=aadhaar_test.pdf bs=1024 count=50
```

### Fill and Submit Form

1. **Fill in fields:**
    - Customer Name: `John Doe`
    - ID Number: `ID12345`
    - Email: `john@test.com`
    - Phone: `9876543210`

2. **Upload files:**
    - Click "Select PAN File" → Choose `pan_test.pdf`
    - Click "Select Aadhaar File" → Choose `aadhaar_test.pdf`

3. **Submit:**
    - Click "Submit Form"
    - See loading indicator
    - 🎉 Success screen appears!

### Verify Results

**Check Database:**

```bash
mysql -u root -p
USE student_form_db;
SELECT * FROM students;
```

**Check Files:**

```bash
# Navigate to backend folder
ls uploads/John_Doe/
# Should see: PAN file, Aadhaar file, and PDF
```

---

## ❌ Troubleshooting

### Problem: Backend won't start

**Error**: "Cannot connect to database"

```bash
# Solution: Check MySQL is running
# Windows: services.msc → Find MySQL → Start
# Mac: brew services start mysql
# Linux: sudo systemctl start mysql
```

### Problem: Android app can't connect

**Error**: "Failed to connect"

```
Solutions:
1. Ensure backend is running (check console)
2. For emulator: Use http://10.0.2.2:8080
3. For device: Use computer's IP address
4. Disable firewall temporarily
5. Both device and computer on same WiFi
```

### Problem: Gradle sync fails

```
Solutions:
1. File → Invalidate Caches → Restart
2. Delete .gradle folder in project root
3. Sync again
```

### Problem: File upload fails

**Error**: "PAN file must be between 90-120 KB"

```
Solution: Create file with correct size (see test files above)
```

---

## 📚 Full Documentation

Now that you have it running, explore detailed docs:

| Document | Purpose | When to Read |
|----------|---------|--------------|
| `README.md` | Complete overview | After first run |
| `QUICKSTART.md` | Detailed setup | If you have issues |
| `TESTING.md` | Test procedures | Before serious testing |
| `DEPLOYMENT.md` | Production deploy | Before going live |
| `PROJECT_SUMMARY.md` | Architecture details | For understanding code |
| `IMPLEMENTATION_COMPLETE.md` | What's included | To see all features |

---

## 🎯 What to Do Next

### For Learning

1. ✅ Explore the code
2. ✅ Read inline comments
3. ✅ Try modifying UI
4. ✅ Add a new field
5. ✅ Check logs

### For Development

1. ✅ Run all test scenarios (see TESTING.md)
2. ✅ Customize theme colors
3. ✅ Add more validations
4. ✅ Extend API endpoints
5. ✅ Add user authentication

### For Production

1. ✅ Complete security checklist
2. ✅ Set up HTTPS
3. ✅ Configure backups
4. ✅ Set up monitoring
5. ✅ Deploy (see DEPLOYMENT.md)

---

## 🆘 Getting Help

### If Something Doesn't Work

1. **Check the error message** - Often tells you exactly what's wrong
2. **Review troubleshooting** - This file & QUICKSTART.md
3. **Check logs**:
    - Backend: Look at console output
    - Android: Check Logcat in Android Studio
4. **Review documentation** - Specific guides for each topic

### Common First-Time Issues

| Issue | File to Check |
|-------|---------------|
| Can't connect to backend | QUICKSTART.md |
| Validation errors | TESTING.md |
| Database issues | application.properties |
| Build errors | build.gradle files |
| API errors | Backend console logs |

---

## ✅ Success Checklist

After following this guide, you should have:

- [x] MySQL installed and running
- [x] Backend running on port 8080
- [x] Android app installed on device/emulator
- [x] Successfully submitted a test form
- [x] Seen the success screen
- [x] Verified data in database
- [x] Found generated files in uploads folder

**All checked?** Congratulations! You're all set up! 🎉

---

## 📞 Quick Reference

### Important URLs

- Backend API: `http://localhost:8080/api/students`
- Health Check: `http://localhost:8080/api/students/all`

### Important Files

- Backend Config: `backend/src/main/resources/application.properties`
- Android API URL: `app/src/main/java/.../network/RetrofitClient.kt`
- Database Schema: `backend/database_schema.sql`

### Important Commands

```bash
# Start backend
cd backend && mvn spring-boot:run

# Build Android APK
./gradlew assembleDebug

# Check backend logs
# (just look at console where mvn is running)

# Access database
mysql -u root -p student_form_db
```

---

## 🎓 Learning Path

**Day 1**: Get it running (this guide)
**Day 2**: Understand the code (read comments)
**Day 3**: Make small changes (colors, text)
**Day 4**: Add a feature (new field)
**Day 5**: Deploy to test server

---

## 🌟 You're Ready!

The application is now running and you can:

- ✅ Submit forms
- ✅ Upload files
- ✅ Generate PDFs
- ✅ Store data in database

**Next**: Start exploring the code and documentation!

---

**Need more help?** Check the comprehensive guides:

- `QUICKSTART.md` - Detailed 5-minute setup
- `README.md` - Full project documentation
- `TESTING.md` - How to test everything

**Happy coding! 🚀**
