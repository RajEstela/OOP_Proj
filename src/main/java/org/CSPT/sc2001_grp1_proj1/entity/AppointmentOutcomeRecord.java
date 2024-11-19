package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentOutcomeRecord extends Appointment{

    private String serviceType;
    private String prescribedMedications;
    private String prescribedStatus;
    private String consultationNotes;

    public AppointmentOutcomeRecord(String appointmentID, String appointmentDateTime, String patientID, String doctorID, String appointmentStatus, String appointmentOutcomeRecordID, String serviceType, String prescribedMedications, String prescribedStatus, String consultationNotes) {
        super(appointmentID, appointmentDateTime, patientID, doctorID, appointmentStatus, appointmentOutcomeRecordID);
        this.serviceType = serviceType;
        this.prescribedMedications = prescribedMedications;
        this.prescribedStatus = prescribedStatus;
        this.consultationNotes = consultationNotes;
    }

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

    public String getServiceType() {
        return serviceType;
    }

    public String getPrescribedMedications() {
        return prescribedMedications;
    }

    public void setPrescribedStatus(String prescribedStatus) 
    {
        this.prescribedStatus = prescribedStatus;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
    public void setPrescribedMedications(String prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }
    
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public String getPrescribedStatus() {
        return prescribedStatus;
    }

    
    public String getConsultationNotes() {
        return consultationNotes;
    }

    

}
