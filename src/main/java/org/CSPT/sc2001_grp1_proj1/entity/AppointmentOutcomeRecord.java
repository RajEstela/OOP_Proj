package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the outcome of an appointment, including details about the type of service, 
 * prescribed medications, their status, and consultation notes.
 * Extends the {@link Appointment} class to inherit appointment-related properties.
 */

public class AppointmentOutcomeRecord extends Appointment{

    /**
     * The type of service provided during the appointment.
     */
    private String serviceType;
     /**
     * Medications prescribed during the appointment.
     */
    private String prescribedMedications;
    /**
     * The status of the prescribed medications (e.g., pending, dispensed).
     */
    private String prescribedStatus;
     /**
     * Notes taken during the consultation.
     */
    private String consultationNotes;

    /**
     * Constructs an {@code AppointmentOutcomeRecord} with the specified details.
     * 
     * @param appointmentID            the unique ID of the appointment.
     * @param appointmentDateTime      the date and time of the appointment.
     * @param patientID                the unique ID of the patient.
     * @param doctorID                 the unique ID of the doctor.
     * @param appointmentStatus        the status of the appointment.
     * @param appointmentOutcomeRecordID the unique ID of the appointment outcome record.
     * @param serviceType              the type of service provided.
     * @param prescribedMedications    the medications prescribed during the appointment.
     * @param prescribedStatus         the status of the prescribed medications.
     * @param consultationNotes        notes taken during the consultation.
     */
    public AppointmentOutcomeRecord(String appointmentID, String appointmentDateTime, String patientID, String doctorID, String appointmentStatus, String appointmentOutcomeRecordID, String serviceType, String prescribedMedications, String prescribedStatus, String consultationNotes) {
        super(appointmentID, appointmentDateTime, patientID, doctorID, appointmentStatus, appointmentOutcomeRecordID);
        this.serviceType = serviceType;
        this.prescribedMedications = prescribedMedications;
        this.prescribedStatus = prescribedStatus;
        this.consultationNotes = consultationNotes;
    }

    /**
     * Prints the details of the appointment outcome record, including date, time, 
     * service type, prescribed medications, status, and consultation notes.
     * Formats the date and time into a human-readable format.
     */
    public void printAppointmentOutcomeRecordDetails() {
        LocalDateTime date = LocalDateTime.parse(getAppointmentDateTime());
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:m a");
        System.out.println("Appointment Date : " + formatDate.format(date));
        System.out.println("Appointment Time : " + formatTime.format(date));
        System.out.println("Type of Service : " + this.serviceType);
        System.out.println("Prescribed Medications : " + this.prescribedMedications);
        System.out.println("Prescribed Status : " + this.prescribedStatus);
        System.out.println("Consultation Notes : " + this.consultationNotes+"\n");
    }

    /**
     * Gets the type of service provided during the appointment.
     * 
     * @return the service type.
     */

    public String getServiceType() {
        return serviceType;
    }

    /**
     * Gets the medications prescribed during the appointment.
     * 
     * @return the prescribed medications.
     */

    public String getPrescribedMedications() {
        return prescribedMedications;
    }

    /**
     * Sets the status of the prescribed medications.
     * 
     * @param prescribedStatus the prescribed status to set.
     */

    public void setPrescribedStatus(String prescribedStatus) 
    {
        this.prescribedStatus = prescribedStatus;
    }

    /**
     * Sets the type of service provided during the appointment.
     * 
     * @param serviceType the service type to set.
     */

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
    /**
     * Sets the medications prescribed during the appointment.
     * 
     * @param prescribedMedications the prescribed medications to set.
     */

    public void setPrescribedMedications(String prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }

    /**
     * Sets the consultation notes from the appointment.
     * 
     * @param consultationNotes the consultation notes to set.
     */
    
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /**
     * Gets the status of the prescribed medications.
     * 
     * @return the prescribed status.
     */

    public String getPrescribedStatus() {
        return prescribedStatus;
    }

    /**
     * Gets the consultation notes from the appointment.
     * 
     * @return the consultation notes.
     */

    public String getConsultationNotes() {
        return consultationNotes;
    }

    

}
