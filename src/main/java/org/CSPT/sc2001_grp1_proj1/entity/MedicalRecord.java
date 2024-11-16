package org.CSPT.sc2001_grp1_proj1.entity;

public class MedicalRecord {
    private String medicalRecordID;
    private String patientID;
    private String name;
    private String dob;
    private String gender;
    private String phoneNumber;
    private String email;
    private String bloodType;

    public MedicalRecord(String medicalRecordID, String patientID, String name, String dob, String gender, 
    String phoneNumber, String email, String bloodType){
        this.medicalRecordID = medicalRecordID;
        this.patientID = patientID;
        this.name = name;
        this.dob = dob;
        this.gender = gender; 
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bloodType = bloodType;
    }

    public void updateEmail(String email){
        this.email = email;
    }
    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}
