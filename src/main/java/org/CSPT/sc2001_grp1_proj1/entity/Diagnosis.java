package org.CSPT.sc2001_grp1_proj1.entity;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;

/**
 * Represents a medical diagnosis given to a patient by a doctor.
 * The diagnosis includes details such as the doctor's ID, the patient's ID, 
 * the diagnosis description, the treatment plan, the prescription, and the date and time of diagnosis.
 */

public class Diagnosis {
    private String doctorID;
    private String patientID;
    private String diagnosis;
    private String treatmentPlan;
    private String prescription;
    private String diagnosisDateTime;

    /**
     * Creates a new Diagnosis instance with the current date and time.
     *
     * @param doctorID       The ID of the doctor providing the diagnosis.
     * @param patientID      The ID of the patient receiving the diagnosis.
     * @param diagnosis      A description of the diagnosis.
     * @param treatmentPlan  The recommended treatment plan for the patient.
     * @param prescription   The prescribed medication for the patient.
     */

    public Diagnosis(String doctorID, String patientID, String diagnosis, 
        String treatmentPlan, String prescription) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.prescription = prescription;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy h:m a");
        this.diagnosisDateTime = formatDate.format(now);
    }

    /**
     * Creates a new Diagnosis instance with a specified date and time.
     *
     * @param doctorID       The ID of the doctor providing the diagnosis.
     * @param patientID      The ID of the patient receiving the diagnosis.
     * @param diagnosis      A description of the diagnosis.
     * @param treatmentPlan  The recommended treatment plan for the patient.
     * @param prescription   The prescribed medication for the patient.
     * @param diagnosedOn    The date and time the diagnosis was made.
     */

    public Diagnosis(String doctorID, String patientID, String diagnosis, 
        String treatmentPlan, String prescription, String diagnosedOn) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.prescription = prescription;
        this.diagnosisDateTime = diagnosedOn;
    }
    
    /**
     * Gets the ID of the patient associated with this diagnosis.
     *
     * @return The patient's ID.
     */

    public String getPatientID() {
        return patientID;
    }

    /**
     * Gets the ID of the doctor who provided this diagnosis.
     *
     * @return The doctor's ID.
     */

    public String getDoctorID() {
        return doctorID;
    }
    
    /**
     * Gets the description of the diagnosis.
     *
     * @return The diagnosis description.
     */

    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Gets the recommended treatment plan for the patient.
     *
     * @return The treatment plan.
     */
    
    public String getTreatmentPlan() {
        return treatmentPlan;
    }
    /**
     * Gets the prescribed medication for the patient.
     *
     * @return The prescription.
     */

    public String getPrescription() {
        return prescription;
    }

    /**
     * Gets the date and time when the diagnosis was made.
     *
     * @return The diagnosis date and time as a formatted string.
     */

    public String getDiagnosisDateTime() {
        return diagnosisDateTime;
    }

    /**
     * Prints the details of this diagnosis to the console, 
     * including the doctor and patient names retrieved from the HospitalManagementApp.
     */

    public void printDiagnosisDetails() {
        HashMap<String, Users> validUsers = HospitalManagementApp.getValidUsersByID();
        System.out.println("-- Diagnosed On : " + this.diagnosisDateTime +" --");
        // System.out.println("Doctor ID : " + this.doctorID);
        System.out.println("Doctor Name : " + validUsers.get(this.doctorID).getname());
        // System.out.println("Patient ID : " + this.patientID);
        System.out.println("Patient Name : " + validUsers.get(this.patientID).getname());
        System.out.println("Diagnosis : " + this.diagnosis);
        System.out.println("Treatment Plan : " + this.treatmentPlan);
        System.out.println("Prescription : " + this.prescription);
    }
}
