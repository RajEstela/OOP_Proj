package org.CSPT.sc2001_grp1_proj1.entity;

import java.util.List;

import org.CSPT.sc2001_grp1_proj1.dataLoader.DiagnosisDataLoader;

/**
 * Represents a medical record for a patient, containing personal details, medical history,
 * and methods for displaying and updating the record.
 */

public class MedicalRecord {
    /** The unique ID of the medical record. */
    private String medicalRecordID;

    /** The unique ID of the patient. */
    private String patientID;

    /** The name of the patient. */
    private String name;

    /** The date of birth of the patient. */
    private String dob;

    /** The gender of the patient. */
    private String gender;

    /** The phone number of the patient. */
    private int phoneNumber;

    /** The email address of the patient. */
    private String email;

    /** The blood type of the patient. */
    private String bloodType;

    /**
     * Constructs a new MedicalRecord with the specified details.
     * 
     * @param medicalRecordID The unique ID of the medical record.
     * @param patientID The unique ID of the patient.
     * @param name The name of the patient.
     * @param dob The date of birth of the patient.
     * @param gender The gender of the patient.
     * @param phoneNumber The phone number of the patient.
     * @param email The email address of the patient.
     * @param bloodType The blood type of the patient.
     */
    
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

    /**
     * Sets the name of the patient. This method does not update the database.
     * 
     * @param name The new name of the patient.
     */
    public void setName(String name) { 
        this.name = name;
    }

    /**
     * Sets the date of birth of the patient. This method does not update the database.
     * 
     * @param dob The new date of birth of the patient.
     */
    public void setDob(String dob) { 
        this.dob = dob;
    }

    /**
     * Sets the gender of the patient. This method does not update the database.
     * 
     * @param gender The new gender of the patient.
     */
    public void setGender(String gender) { 
        this.gender = gender;
    }

    /**
     * Sets the blood type of the patient. This method does not update the database.
     * 
     * @param bloodType The new blood type of the patient.
     */
    public void setBloodType(String bloodType) { 
        this.bloodType = bloodType;
    }

    /**
     * Sets the email address of the patient. This method does not update the database.
     * 
     * @param email The new email address of the patient.
     */
    public void setEmail(String email) { 
        this.email = email;
    }

    /**
     * Sets the phone number of the patient. This method does not update the database.
     * 
     * @param phoneNumber The new phone number of the patient.
     */
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the patient ID.
     * 
     * @return The patient ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Gets the name of the patient.
     * 
     * @return The name of the patient.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the date of birth of the patient.
     * 
     * @return The date of birth of the patient.
     */
    public String getDob() {
        return dob;
    }

    /**
     * Gets the gender of the patient.
     * 
     * @return The gender of the patient.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the phone number of the patient.
     * 
     * @return The phone number of the patient.
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the email address of the patient.
     * 
     * @return The email address of the patient.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the blood type of the patient.
     * 
     * @return The blood type of the patient.
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Prints the details of the medical record, including patient information and past diagnoses/treatments.
     */

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
