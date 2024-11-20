package org.CSPT.sc2001_grp1_proj1.entity;

/**
 * This class represents a doctor associated with an appointment in the hospital management system.
 * It stores the ID of the doctor for reference in appointment-related operations.
 */
public class AppointmentDoctor {

    /**
     * The ID of the doctor associated with the appointment.
     */
    private String appointmentDoctorID;

    /**
     * Constructs a new `AppointmentDoctor` instance with the specified doctor ID.
     *
     * @param doctorID The ID of the doctor.
     */
    public AppointmentDoctor(String doctorID) {
        this.appointmentDoctorID = doctorID;
    }
}
