package org.CSPT.sc2001_grp1_proj1.entity;

import java.util.Date;

public class AppointmentOutcomeRecord {
    private Date dateOfAppointment;
    private String serviceType;
    private String prescribedMedications;
    private String prescribedStatus;
    private String consultationNotes;

    public AppointmentOutcomeRecord(Date dateOfAppointment, String serviceType, String prescribedMedications, String prescribedStatus, String consultationNotes) {
        this.dateOfAppointment = dateOfAppointment;
        this.serviceType = serviceType;
        this.prescribedMedications = prescribedMedications;
        this.prescribedStatus = prescribedStatus;
        this.consultationNotes = consultationNotes;
    }

    public void printAppointmentOutcomeRecordDetails() {
        //Add the details here
    }
}
