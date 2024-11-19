package org.CSPT.sc2001_grp1_proj1.entity;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;

public class Diagnosis {
    private String doctorID;
    private String patientID;
    private String diagnosis;
    private String treatmentPlan;
    private String prescription;
    private String diagnosisDateTime;


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

    public Diagnosis(String doctorID, String patientID, String diagnosis, 
        String treatmentPlan, String prescription, String diagnosedOn) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.prescription = prescription;
        this.diagnosisDateTime = diagnosedOn;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public String getPrescription() {
        return prescription;
    }

    public String getDiagnosisDateTime() {
        return diagnosisDateTime;
    }

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
