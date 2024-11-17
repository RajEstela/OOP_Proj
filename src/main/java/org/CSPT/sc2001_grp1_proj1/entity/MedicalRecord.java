package org.CSPT.sc2001_grp1_proj1.entity;

public class MedicalRecord {
    private String medicalRecordID;
    private String patientID;
    private String name;
    private String dob;
    private String gender;
    private int phoneNumber;
    private String email;
    private String bloodType;
    private String pastDiagnosesAndTreatments;

    public MedicalRecord(String medicalRecordID, String patientID, String name, String dob, String gender, 
    int phoneNumber, String email, String bloodType, String pastDiagnosesAndTreatments){
        this.medicalRecordID = medicalRecordID;
        this.patientID = patientID;
        this.name = name;
        this.dob = dob;
        this.gender = gender; 
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bloodType = bloodType;
        this.pastDiagnosesAndTreatments = pastDiagnosesAndTreatments;
    }

    public void updateEmail(String email){
        this.email = email;
    }
    public void updatePhoneNumber(int phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPatientID() {
        return patientID;
    }

    public void printMedicalRecord() {
        System.out.println("");
        System.out.println("Patient ID - "+this.patientID);
        System.out.println("Name - "+ this.name);
        System.out.println("Date of Birth - "+this.dob);
        System.out.println("Gender - "+this.gender);
        System.out.println("Phone - "+this.phoneNumber);
        System.out.println("Email - "+this.email);
        System.out.println("Blood Type - "+this.bloodType);
        System.out.println("Past Diagnoses and Treatments - "+this.pastDiagnosesAndTreatments);
        System.out.println("");
    }
}
