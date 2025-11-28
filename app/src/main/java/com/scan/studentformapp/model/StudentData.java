package com.scan.studentformapp.model;

public class StudentData {
    private String customerName;
    private String idNumber;
    private String email;
    private String phoneNumber;
    private String panFile;
    private String aadhaarFile;

    public StudentData(String customerName, String idNumber, String email,
                       String phoneNumber, String panFile, String aadhaarFile) {
        this.customerName = customerName;
        this.idNumber = idNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.panFile = panFile;
        this.aadhaarFile = aadhaarFile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPanFile() {
        return panFile;
    }

    public void setPanFile(String panFile) {
        this.panFile = panFile;
    }

    public String getAadhaarFile() {
        return aadhaarFile;
    }

    public void setAadhaarFile(String aadhaarFile) {
        this.aadhaarFile = aadhaarFile;
    }
}
