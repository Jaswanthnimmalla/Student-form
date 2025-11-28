# 📱 How to Install App on Connected Device

## ✅ **Quick Steps to Install & Run**

### **Method 1: Using Android Studio (Easiest)**

1. **Open Android Studio**
2. **Open Project:** `D:\Jessu Files\Web Development Projects\StudentFormApp`
3. **Wait for Gradle Sync** to complete (bottom status bar)
4. **Connect your Android device** via USB
    - Enable "USB Debugging" on your phone (Settings → Developer Options)
5. **Click the Run button** (▶️) or press `Shift + F10`
6. **Select your connected device** from the list
7. **Wait for installation** - App will auto-launch!

---

### **Method 2: Using Gradle Command**

1. **Connect your device** via USB
2. **Enable USB Debugging** on phone
3. **Open PowerShell** in project folder:
   ```powershell
   cd "D:\Jessu Files\Web Development Projects\StudentFormApp"
   ```
4. **Run install command:**
   ```powershell
   .\gradlew.bat installDebug
   ```
5. **Open the app** manually on your phone (look for "StudentFormApp")

---

### **Method 3: Manual APK Installation**

1. **Build the APK:**
   ```powershell
   cd "D:\Jessu Files\Web Development Projects\StudentFormApp"
   .\gradlew.bat assembleDebug
   ```

2. **Find the APK:**
   ```
   app\build\outputs\apk\debug\app-debug.apk
   ```

3. **Transfer to phone:**
    - Copy via USB cable
    - Or use ADB: `adb install app\build\outputs\apk\debug\app-debug.apk`

4. **Install on phone** - Allow installation from unknown sources if prompted

---

## 🔧 **Prerequisites:**

### **Before Running:**

1. ✅ **MySQL is running** (XAMPP → Start MySQL)
2. ✅ **Backend is running** (`RUN-BACKEND.bat`)
3. ✅ **Phone & PC on same WiFi** network
4. ✅ **USB Debugging enabled** on phone

---

## 📱 **Enable USB Debugging:**

### **On Your Android Phone:**

1. Go to **Settings** → **About Phone**
2. Tap **Build Number** 7 times (becomes "You are now a developer!")
3. Go back → **System** → **Developer Options**
4. Enable **USB Debugging**
5. Connect USB cable
6. Allow debugging when prompted

---

## ⚡ **Quick Test:**

### **After App Installs:**

1. **Open the app** on your phone
2. **Fill the form:**
    - Customer Name: `Test User`
    - ID: `12345`
    - Email: `test@example.com`
    - Phone: `9876543210`
    - Select PAN file (must be 90-120 KB)
    - Select Aadhaar file
3. **Click Submit**
4. **See success screen!** ✅

---

## 🐛 **Troubleshooting:**

### **"Device not found"**

- Check USB cable connection
- Enable USB Debugging
- Allow computer on phone prompt

### **"Installation failed"**

- Uninstall old version first
- Check phone storage space
- Allow installation from unknown sources

### **"App not connecting to backend"**

- Make sure backend is running
- Check both devices on same WiFi
- Verify IP address is `192.168.0.4`

---

## 📊 **What Happens:**

1. ✅ Android Studio builds APK
2. ✅ Installs on your connected device
3. ✅ App launches automatically
4. ✅ You can fill form and submit
5. ✅ Data saves to MySQL database
6. ✅ Files saved in `uploads/` folder
7. ✅ PDF generated automatically

---

## 🎯 **Recommended Method:**

**Use Method 1 (Android Studio)** - It's the easiest and most reliable!

Just:

1. Open project in Android Studio
2. Wait for Gradle sync
3. Click Run (▶️)
4. Select your device
5. Done! 🎉

---

**Your Java + Spring Boot + MySQL app is ready to install and test!** 🚀
