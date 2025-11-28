# ⚠️ Backend Keeps Stopping - Solution

## 🔍 **The Problem:**

The backend server stops running after some time or when the PowerShell window is closed. This
causes the connection error you're seeing:

```
Error: failed to connect to /192.168.0.4 (port 8081)
```

---

## ✅ **Solution: Keep Backend Running**

### **Method 1: Manual (Simplest)**

1. **Open PowerShell** in your project folder:
   ```
   D:\Jessu Files\Web Development Projects\StudentFormApp
   ```

2. **Run this command:**
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

3. **Wait for "Started StudentFormBackendApplication"** (about 1-2 minutes)

4. **IMPORTANT: Keep this PowerShell window OPEN!**
    - ❌ Don't close it
    - ❌ Don't minimize it
    - ✅ Keep it visible so you can see it's running

---

### **Method 2: Create a Desktop Shortcut**

1. **Right-click on Desktop** → New → Shortcut

2. **Enter this as location:**
   ```
   powershell.exe -NoExit -Command "cd 'D:\Jessu Files\Web Development Projects\StudentFormApp'; .\mvnw.cmd spring-boot:run"
   ```

3. **Name it:** "Start Backend Server"

4. **Double-click this shortcut** whenever you want to use the app

---

### **Method 3: Check if Backend is Running**

Before using the app, always check:

**Open browser on your phone:**

```
http://192.168.0.4:8081/api/students/all
```

**If you see JSON response** → Backend is running ✅  
**If you see error** → Backend is NOT running ❌

---

## 📋 **Quick Checklist Before Testing:**

1. [ ] **MySQL running** in XAMPP (green, started)
2. [ ] **Backend running** (PowerShell window open, showing logs)
3. [ ] **Test backend** in phone browser
4. [ ] **Both devices on same WiFi**
5. [ ] **Now submit form** in app

---

## 🔄 **Backend Start Command (Copy This):**

```powershell
cd "D:\Jessu Files\Web Development Projects\StudentFormApp"
.\mvnw.cmd spring-boot:run
```

---

## ⏱️ **Backend Startup Time:**

- **0-30 seconds:** Maven preparing
- **30-60 seconds:** Compiling
- **60-90 seconds:** Starting Spring Boot
- **90+ seconds:** ✅ **READY!** (Shows "Started StudentFormBackendApplication")

---

## 📱 **Testing Flow:**

```
1. Start MySQL (XAMPP)
   ↓
2. Start Backend (PowerShell - keep open!)
   ↓
3. Wait for "Started" message
   ↓
4. Test in phone browser: http://192.168.0.4:8081/api/students/all
   ↓
5. Open app and submit form
   ↓
6. Success! ✅
```

---

## 💡 **Pro Tip:**

**Always start backend BEFORE testing the app!**

The backend must be running continuously while you're using the app. If you close the PowerShell
window, the backend stops and the app can't connect.

---

## 🐛 **If Backend Won't Start:**

1. **Kill any existing Java processes:**
   ```powershell
   Get-Process -Name java | Stop-Process -Force
   ```

2. **Wait 5 seconds**

3. **Start backend again:**
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

---

## ✅ **Backend is Running When You See:**

In the PowerShell window:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

...

Started StudentFormBackendApplication in X.XXX seconds (JVM running for X.XXX)
```

**Keep this window open!** 🚀

---

## 📞 **Summary:**

**The backend needs to run continuously while using the app!**

1. Start backend → Wait for "Started" → Keep window open → Use app
2. Don't close PowerShell window
3. Always check backend is running before testing

---

**Your app works perfectly - just keep the backend running!** ✅
