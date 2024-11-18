package org.CSPT.sc2001_grp1_proj1.entity;

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
        //Add the details here
    }
}
