package org.CSPT.sc2001_grp1_proj1.entity;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;

public class Appointment {
    private String appointmentID;
    private String appointmentDateTime;
    private String patientID;
    private String doctorID;
    private String appointmentStatus;
    private String appointmentOutcomeRecordID;

    public Appointment(String appointmentID, String appointmentDateTime, String patientID, String doctorID, String appointmentStatus, String appointmentOutcomeRecordID) {
        this.appointmentID = appointmentID;
        this.appointmentDateTime = appointmentDateTime;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentStatus = appointmentStatus;
        this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void printAppointmentDetails() {
        HashMap<String, Users> validUsers = HospitalManagementApp.getValidUsersByID();
        LocalDateTime date = LocalDateTime.parse(this.appointmentDateTime);
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:m a");
        System.out.println("");
        System.out.println("Appointment Date : " + formatDate.format(date));
        System.out.println("Appointment Time : " + formatTime.format(date));        
        System.out.println("Patient ID : " + this.patientID);
        System.out.println("Patient Name : " + validUsers.get(this.patientID).name);
        System.out.println("Doctor ID : " + this.doctorID);
        System.out.println("Doctor Name : " + validUsers.get(this.doctorID).name);
        System.out.println("Appointment Status : "+ this.appointmentStatus);
        System.out.println("");
    }
}
