package com.shekkahmeng.fypapplication.user;

/**
 * Created by shekkahmeng on 8/3/2015.
 */
public class UserDetailsTable {

    private String Email;
    private String Username;
    private String Title;
    private String FullName;
    private String Gender;
    private String Instituition;
    private String Faculty;
    private String Department;
    private String ResearchField;
    private String Address;
    private String State;
    private String PostalCode;
    private String Country;
    private String PhoneNumber;
    private String FaxNumber;
    private String RegDate;
    private String encryptedPassword;

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getInstituition() {
        return Instituition;
    }

    public void setInstituition(String instituition) {
        Instituition = instituition;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getResearchField() {
        return ResearchField;
    }

    public void setResearchField(String researchField) {
        ResearchField = researchField;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return FaxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        FaxNumber = faxNumber;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
