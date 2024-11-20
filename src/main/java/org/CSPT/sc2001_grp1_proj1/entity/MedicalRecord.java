package org.CSPT.sc2001_grp1_proj1.entity;

import java.util.List;

import org.CSPT.sc2001_grp1_proj1.dataLoader.DiagnosisDataLoader;

public class MedicalRecord {
    private String medicalRecordID;
    private String patientID;
    private String name;
    private String dob;
    private String gender;
    private int phoneNumber;
    private String email;
    private String bloodType;

    public MedicalRecord(String medicalRecordID, String patientID, String name, String dob, String gender, 
    int phoneNumber, String email, String bloodType){
        this.medicalRecordID = medicalRecordID;
        this.patientID = patientID;
        this.name = name;
        this.dob = dob;
        this.gender = gender; 
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bloodType = bloodType;
    }

    // These setter functions DO NOT update the database -
    // Pass this record into MedicalRecordDataLoader updateByMedicalRecord() after setting updated values.
    public void setName(String name){ 
        this.name = name;
    }
    public void setDob(String dob){ 
        this.dob = dob;
    }
    public void setGender(String gender){ 
        this.gender = gender;
    }
    public void setBloodType(String bloodType){ 
        this.bloodType = bloodType;
    }
    public void setEmail(String email){ 
        this.email = email;
    }
    public void setPhoneNumber(int phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPatientID() {
        return patientID;
    }
    public String getName() {
        return name;
    }
    public String getDob() {
        return dob;
    }
    public String getGender() {
        return gender;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getBloodType() {
        return bloodType;
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
        System.out.println("-- Past Diagnoses and Treatments -- ");
        DiagnosisDataLoader diagnosisDataLoader = new DiagnosisDataLoader();
        List<Diagnosis> diagnoses = diagnosisDataLoader.getDiagnosisByPatient(this.patientID);
        for (Diagnosis diagnosis: diagnoses){
            diagnosis.printDiagnosisDetails();
        }
        System.out.println("");
    }
}
