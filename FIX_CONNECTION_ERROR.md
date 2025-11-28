# 🔧 Fix Connection Error - Complete Guide

## ✅ What I Fixed

1. ✅ Updated `RetrofitClient.kt` with your correct IP: **192.168.0.4**
2. ✅ Added Windows Firewall rule for port 8080
3. ✅ Created `start-backend.bat` for easy backend startup
4. ✅ Created Maven Wrapper files

---

## 🚀 Steps to Run (Follow in Order)

### Step 1: Start MySQL (IMPORTANT!)

**Option A: Using Services**

1. Press `Windows + R`
2. Type: `services.msc`
3. Find "MySQL80" or "MySQL"
4. Right-click → Start

**Option B: Command Line**

```cmd
net start MySQL80
```

---

### Step 2: Start the Backend

**Double-click: `start-backend.bat`**

Or manually in PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

**Wait for this message:**

```
Started StudentFormBackendApplication in X.XXX seconds
```

---

### Step 3: Test Backend in Browser

Open browser: http://192.168.0.4:8080/api/students/all

**Expected:**

```json
{"success":true,"message":"Students retrieved successfully","data":[]}
```

---

### Step 4: Rebuild Android App

**In Android Studio:**

1. Build → Clean Project
2. Build → Rebuild Project
3. Click Run (▶️)

---

### Step 5: Test the App

Fill the form and submit!

---

## ⚠️ If Still Not Working

### Check 1: MySQL Running?

```powershell
sc query MySQL80
```

Should show: **STATE: RUNNING**

### Check 2: Backend Running?

```powershell
netstat -ano | findstr :8080
```

Should show a line with :8080

### Check 3: Firewall?

**Temporarily disable Windows Firewall:**

- Windows Settings → Update & Security → Windows Security
- Firewall & network protection → Private network → Turn off

### Check 4: Same WiFi?

- Computer WiFi: Check WiFi name
- Phone WiFi: Must be same network
- Both should be on 192.168.0.X network

### Check 5: Test with Browser on Phone

Open Chrome on your phone: http://192.168.0.4:8080/api/students/all

If this works, app should work too!

---

## 📊 Network Configuration

| Device | IP Address | Network |
|--------|-----------|---------|
| Computer | 192.168.0.4 | WiFi |
| Phone | 192.168.0.5 | WiFi |
| Backend URL | http://192.168.0.4:8080 | ✅ Correct |

---

## 🐛 Common Issues

### "Cannot connect to database"

- MySQL not running
- Run: `net start MySQL80`

### "Port 8080 already in use"

- Something else using port 8080
- Check: `netstat -ano | findstr :8080`
- Kill process or change port

### "Connection timeout"

- Firewall blocking
- Disable Windows Firewall temporarily
- Or run (as Admin):
  ```powershell
  New-NetFirewallRule -DisplayName "Spring Boot" -Direction Inbound -Protocol TCP -LocalPort 8080 -Action Allow
  ```

---

## ✅ Quick Checklist

Before testing app:

- [ ] MySQL is running
- [ ] Backend started (see "Started" message)
- [ ] Backend accessible in browser
- [ ] Both devices on same WiFi
- [ ] Windows Firewall allows port 8080
- [ ] Android app rebuilt after IP change

---

## 🎯 Final Test

1. Backend running: ✅
2. Browser test: http://192.168.0.4:8080/api/students/all ✅
3. Phone browser test: Same URL ✅
4. Android app: Submit form ✅

If all pass, it will work!

---

**Need help? Check the error message and refer to this guide.**
