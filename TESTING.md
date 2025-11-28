# Testing Guide

Complete guide for testing the Student Form Application.

## 📋 Table of Contents

1. [Backend Testing](#backend-testing)
2. [Android App Testing](#android-app-testing)
3. [Integration Testing](#integration-testing)
4. [Test Scenarios](#test-scenarios)
5. [Creating Test Files](#creating-test-files)

## 🖥️ Backend Testing

### Using Browser

#### Test 1: Get All Students

```
URL: http://localhost:8080/api/students/all
Method: GET

Expected Response:
{
  "success": true,
  "message": "Students retrieved successfully",
  "data": []
}
```

#### Test 2: Get Student by ID

```
URL: http://localhost:8080/api/students/1
Method: GET

Expected Response (if exists):
{
  "success": true,
  "message": "Student found",
  "data": {
    "id": 1,
    "customerName": "John Doe",
    ...
  }
}
```

### Using Postman

1. **Import Collection**
    - Open Postman
    - Click Import
    - Select `backend/StudentFormAPI.postman_collection.json`

2. **Test Create Student**
    - Select "Create Student" request
    - Update file paths in Body tab
    - Click Send
    - Verify response shows success

3. **Test Get All Students**
    - Select "Get All Students" request
    - Click Send
    - Verify array of students returned

### Using cURL

#### Create Student

```bash
curl -X POST http://localhost:8080/api/students/create \
  -F "customerName=John Doe" \
  -F "idNumber=ID12345" \
  -F "email=john.doe@example.com" \
  -F "phoneNumber=9876543210" \
  -F "panFile=@/path/to/pan.pdf" \
  -F "aadhaarFile=@/path/to/aadhaar.pdf"
```

#### Get All Students

```bash
curl http://localhost:8080/api/students/all
```

#### Get Student by ID

```bash
curl http://localhost:8080/api/students/1
```

## 📱 Android App Testing

### Manual Testing Checklist

#### Form Validation Tests

**Test 1: Empty Fields**

- [ ] Leave all fields empty
- [ ] Click Submit
- [ ] Expected: Error message "Please enter a valid customer name"

**Test 2: Invalid Email**

- [ ] Enter name: John
- [ ] Enter invalid email: notanemail
- [ ] Click Submit
- [ ] Expected: Error "Please enter a valid email address"

**Test 3: Invalid Phone**

- [ ] Enter phone: 123
- [ ] Click Submit
- [ ] Expected: Error "Please enter a valid 10-digit phone number"

**Test 4: Missing PAN File**

- [ ] Fill all fields except PAN
- [ ] Click Submit
- [ ] Expected: Error "Please upload PAN file"

**Test 5: Invalid PAN Size**

- [ ] Upload PAN file < 90 KB or > 120 KB
- [ ] Expected: Error with actual file size

**Test 6: Missing Aadhaar**

- [ ] Fill all fields except Aadhaar
- [ ] Click Submit
- [ ] Expected: Error "Please upload Aadhaar file"

#### Happy Path Test

**Test 7: Successful Submission**

- [ ] Fill valid customer name (e.g., "John Doe")
- [ ] Fill valid ID (e.g., "ID12345")
- [ ] Fill valid email (e.g., "john@test.com")
- [ ] Fill valid phone (e.g., "9876543210")
- [ ] Upload PAN file (90-120 KB)
- [ ] Upload Aadhaar file
- [ ] Click Submit
- [ ] Expected: Loading indicator appears
- [ ] Expected: Success screen shows
- [ ] Expected: Customer name displayed on success screen

#### UI/UX Tests

**Test 8: File Selection**

- [ ] Click "Select PAN File"
- [ ] File picker opens
- [ ] Select file
- [ ] Expected: File name and size displayed

**Test 9: Loading State**

- [ ] Submit valid form
- [ ] Expected: Submit button disabled
- [ ] Expected: Loading spinner visible
- [ ] Expected: Text shows "Submitting..."

**Test 10: Success Screen**

- [ ] After successful submission
- [ ] Expected: Green checkmark icon visible
- [ ] Expected: "Success!" title shown
- [ ] Expected: Customer name displayed
- [ ] Expected: Information card with next steps
- [ ] Expected: Close button works

#### Error Handling Tests

**Test 11: Network Error**

- [ ] Stop backend server
- [ ] Submit form
- [ ] Expected: Error message displayed
- [ ] Expected: Form remains filled

**Test 12: Duplicate Email**

- [ ] Submit form with existing email
- [ ] Expected: Error "Student with this email already exists"

**Test 13: Duplicate ID Number**

- [ ] Submit form with existing ID
- [ ] Expected: Error "Student with this ID number already exists"

### Permission Tests

**Test 14: File Access Permission**

- [ ] First time: Click file picker
- [ ] Expected: Permission dialog appears
- [ ] Grant permission
- [ ] Expected: File picker opens
- [ ] Select file
- [ ] Expected: File selected successfully

**Test 15: Permission Denied**

- [ ] Deny permission
- [ ] Click file picker again
- [ ] Expected: Permission request appears again

## 🔄 Integration Testing

### End-to-End Test Flow

1. **Start Fresh**
   ```bash
   # Clear database
   mysql -u root -p
   USE student_form_db;
   TRUNCATE TABLE students;
   EXIT;
   
   # Clear uploads
   rm -rf backend/uploads/*
   ```

2. **Submit First Student**
    - Open Android app
    - Fill form: John Doe, john@test.com
    - Submit
    - Expected: Success

3. **Verify Database**
   ```sql
   SELECT * FROM students;
   -- Should show 1 record
   ```

4. **Verify File System**
   ```bash
   ls backend/uploads/John_Doe/
   # Should show:
   # PAN_*.pdf
   # AADHAAR_*.pdf
   # student_form_1.pdf
   ```

5. **Verify PDF Content**
    - Open `backend/uploads/John_Doe/student_form_1.pdf`
    - Verify all fields are present
    - Check formatting is correct

6. **Test Duplicate Prevention**
    - Submit same email again
    - Expected: Error message

7. **Submit Second Student**
    - Use different email: jane@test.com
    - Submit
    - Expected: Success

8. **Verify Multiple Records**
   ```bash
   curl http://localhost:8080/api/students/all
   # Should return array with 2 students
   ```

## 📂 Creating Test Files

### PAN File (90-120 KB)

**Windows (PowerShell)**

```powershell
# Create 100 KB file
fsutil file createnew pan_test.pdf 102400

# Create 95 KB file
fsutil file createnew pan_95kb.pdf 97280

# Create 110 KB file
fsutil file createnew pan_110kb.pdf 112640
```

**Mac/Linux**

```bash
# Create 100 KB file
dd if=/dev/zero of=pan_test.pdf bs=1024 count=100

# Create 95 KB file
dd if=/dev/zero of=pan_95kb.pdf bs=1024 count=95

# Create 110 KB file
dd if=/dev/zero of=pan_110kb.pdf bs=1024 count=110
```

### Aadhaar File (Any Size)

**Windows**

```powershell
fsutil file createnew aadhaar_test.pdf 51200
```

**Mac/Linux**

```bash
dd if=/dev/zero of=aadhaar_test.pdf bs=1024 count=50
```

### Test Different Sizes

```bash
# Too small (80 KB) - Should fail
dd if=/dev/zero of=pan_small.pdf bs=1024 count=80

# Just right (100 KB) - Should pass
dd if=/dev/zero of=pan_valid.pdf bs=1024 count=100

# Too large (130 KB) - Should fail
dd if=/dev/zero of=pan_large.pdf bs=1024 count=130
```

## 🧪 Test Scenarios

### Scenario 1: First Time User

```
Steps:
1. Install app
2. Open app
3. See empty form
4. Try to submit empty → See validation
5. Fill all fields correctly
6. Submit → See success screen
7. Close app

Expected: Student created successfully, files saved, PDF generated
```

### Scenario 2: Returning User

```
Steps:
1. Open app
2. Form is empty (doesn't retain data)
3. Fill with different email
4. Submit → Success

Expected: New student created
```

### Scenario 3: Network Issues

```
Steps:
1. Fill form
2. Disconnect WiFi/turn off backend
3. Submit
4. See error message
5. Reconnect
6. Submit again → Success

Expected: Graceful error handling, retry works
```

### Scenario 4: Invalid Data

```
Test cases:
- Name with 1 character → Error
- Email without @ → Error
- Phone with 9 digits → Error
- Phone with letters → Error
- PAN 50 KB → Error
- No Aadhaar → Error

Expected: All validations catch errors
```

## 📊 Test Results Template

| Test # | Description | Status | Notes |
|--------|-------------|--------|-------|
| 1 | Empty fields validation | ✅ Pass | |
| 2 | Invalid email validation | ✅ Pass | |
| 3 | Invalid phone validation | ✅ Pass | |
| 4 | Missing PAN validation | ✅ Pass | |
| 5 | Invalid PAN size | ✅ Pass | |
| 6 | Missing Aadhaar validation | ✅ Pass | |
| 7 | Successful submission | ✅ Pass | |
| 8 | File selection UI | ✅ Pass | |
| 9 | Loading state | ✅ Pass | |
| 10 | Success screen | ✅ Pass | |
| 11 | Network error handling | ✅ Pass | |
| 12 | Duplicate email check | ✅ Pass | |
| 13 | Duplicate ID check | ✅ Pass | |

## 🐛 Bug Reporting Template

```
Title: Brief description

Environment:
- Android Version: 
- Device: 
- Backend Version: 
- Database: 

Steps to Reproduce:
1. 
2. 
3. 

Expected Result:


Actual Result:


Screenshots:
(if applicable)

Logs:
(backend logs or Android logcat)
```

## ✅ Acceptance Criteria

### For Release

- [ ] All form validations working
- [ ] Files upload successfully
- [ ] Database records created correctly
- [ ] Folders created with customer name
- [ ] PDF generated with all data
- [ ] Success screen displays correctly
- [ ] Error messages are user-friendly
- [ ] No crashes on valid input
- [ ] Network errors handled gracefully
- [ ] Duplicate prevention works
- [ ] Tested on both emulator and real device
- [ ] Backend responds within 5 seconds
- [ ] All API endpoints return correct responses

---

**Happy Testing! 🧪**
