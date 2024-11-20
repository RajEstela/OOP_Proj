package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.CSPT.sc2001_grp1_proj1.HospitalManagementApp;

/**
 * This class represents an appointment in the hospital management system.
 * It contains information about the appointment ID, date and time, patient ID, doctor ID, status, and outcome record ID.
 */
public class Appointment {
    /**
     * The unique identifier for the appointment.
     */
    private String appointmentID;

    /**
     * The date and time of the appointment.
     */
    private String appointmentDateTime;

    /**
     * The ID of the patient associated with the appointment.
     */
    private String patientID;

    /**
     * The ID of the doctor associated with the appointment.
     */
    private String doctorID;

    /**
     * The current status of the appointment (e.g., scheduled, in progress, completed, canceled).
     */
    private String appointmentStatus;

    /**
     * The ID of the record associated with the outcome of the appointment.
     */
    private String appointmentOutcomeRecordID;

    /**
     * Constructs an Appointment object with the specified parameters.
     *
     * @param appointmentID           The unique identifier for the appointment.
     * @param appointmentDateTime     The date and time of the appointment.
     * @param patientID               The ID of the patient associated with the appointment.
     * @param doctorID                The ID of the doctor associated with the appointment.
     * @param appointmentStatus       The current status of the appointment.
     * @param appointmentOutcomeRecordID The ID of the record associated with the outcome of the appointment.
     */
    public Appointment(String appointmentID, String appointmentDateTime, String patientID, String doctorID, String appointmentStatus, String appointmentOutcomeRecordID) {
        this.appointmentID = appointmentID;
        this.appointmentDateTime = appointmentDateTime;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentStatus = appointmentStatus;
        this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
    }

    /**
     * Gets the unique identifier for the appointment.
     *
     * @return The appointment ID.
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Gets the date and time of the appointment.
     *
     * @return The appointment date and time.
     */
    public String getAppointmentDateTime() {
        return appointmentDateTime;
    }

    /**
     * Gets the ID of the patient associated with the appointment.
     *
     * @return The patient ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Gets the ID of the doctor associated with the appointment.
     *
     * @return The doctor ID.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Gets the current status of the appointment.
     *
     * @return The appointment status.
     */
    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    /**
     * Gets the ID of the record associated with the outcome of the appointment.
     *
     * @return The appointment outcome record ID.
     */
    public String getAppointmentOutcomeRecordID() {
        return appointmentOutcomeRecordID;
    }

    /**
     * Prints the details of the appointment, including date, time, doctor's name, and status.
     */
    public void printAppointmentDetails() {
        HashMap<String, Users> validUsers = HospitalManagementApp.getValidUsersByID();
        LocalDateTime date = LocalDateTime.parse(this.appointmentDateTime);
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:m a");
        System.out.println("Appointment Date : " + formatDate.format(date));
        System.out.println("Appointment Time : " + formatTime.format(date));
        System.out.println("Doctor ID : " + this.doctorID);
        System.out.println("Doctor Name : " + validUsers.get(this.doctorID).getname());
        System.out.println("Appointment Status : " + this.appointmentStatus + "\n");
    }
}