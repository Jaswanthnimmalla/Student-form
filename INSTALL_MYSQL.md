# 🗄️ MySQL Installation & Setup Guide

## ⚠️ MySQL is REQUIRED (As per project requirements)

---

## 🚀 **Option 1: Install MySQL Standalone (Recommended)**

### Step 1: Download MySQL

Download from: https://dev.mysql.com/downloads/installer/

Choose: **MySQL Installer for Windows**

### Step 2: Install MySQL

1. Run the installer
2. Choose **"Developer Default"** or **"Server only"**
3. Click Next through the installation
4. **Set root password:** `root` (or remember your password)
5. Complete the installation

### Step 3: Start MySQL Service

**Option A: Using Services**

1. Press `Windows + R`
2. Type: `services.msc`
3. Find **MySQL80** (or MySQL)
4. Right-click → **Start**

**Option B: Using Command**

```cmd
net start MySQL80
```

---

## 🚀 **Option 2: Install XAMPP (Easier for Beginners)**

### Step 1: Download XAMPP

Download from: https://www.apachefriends.org/download.html

Choose the version for Windows

### Step 2: Install XAMPP

1. Run installer
2. Install to `C:\xampp`
3. Complete installation

### Step 3: Start MySQL

1. Open **XAMPP Control Panel**
2. Click **Start** next to **MySQL**
3. Wait for it to turn green

---

## ✅ **Verify MySQL is Running**

Run this in PowerShell:

```powershell
mysql -u root -p
```

Enter password: `root` (or your password)

If it opens MySQL prompt, it's working! Type `exit` to quit.

---

## 🔧 **Update Backend Configuration**

If you used a different password, update `backend/src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

---

## 📊 **Create Database (Optional - Auto-created)**

The app will automatically create the database, but you can do it manually:

```sql
mysql -u root -p
CREATE DATABASE student_form_db;
EXIT;
```

---

## 🎯 **After MySQL is Installed**

1. ✅ MySQL service is running
2. ✅ Run: `RUN-BACKEND.bat`
3. ✅ Wait for: "Started StudentFormBackendApplication"
4. ✅ Test Android app

---

## ⚠️ **Troubleshooting**

### MySQL won't start

- Another MySQL instance might be running
- Port 3306 might be in use
- Restart computer and try again

### Can't connect to MySQL

```powershell
# Check if MySQL is running
sc query MySQL80

# Start MySQL
net start MySQL80
```

### Wrong password error

Update the password in `application.properties` to match your MySQL root password

---

## 📋 **Quick MySQL Commands**

```bash
# Start MySQL
net start MySQL80

# Stop MySQL
net stop MySQL80

# Check status
sc query MySQL80

# Login to MySQL
mysql -u root -p

# Show databases
SHOW DATABASES;

# Use our database
USE student_form_db;

# Show tables
SHOW TABLES;

# See student data
SELECT * FROM students;
```

---

## 🎉 **Once MySQL is Running**

Simply run: `RUN-BACKEND.bat`

The backend will connect to MySQL automatically! 🚀

---

**Download MySQL now:** https://dev.mysql.com/downloads/installer/

**Or XAMPP:** https://www.apachefriends.org/download.html
