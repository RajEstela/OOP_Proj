package org.CSPT.sc2001_grp1_proj1.entity;

import java.util.Date;

public class Appointment {
    private String appointmentID;
    private Date appointmentDate;
    private String patientID;
    private String doctorID;
    private String appointmentStatus;
    private AppointmentOutcomeRecord applAppointmentOutcomeRecord;

    public Appointment(String appointmentID, Date appointmentDate, String patientID, String doctorID, String appointmentStatus) {
        this.appointmentID = appointmentID;
        this.appointmentDate = appointmentDate;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentStatus = appointmentStatus;
    }
}
