package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentsDataLoader;
import org.CSPT.sc2001_grp1_proj1.interfaces.AppointmentManagerInterface;

/**
 * Manages appointments within the system. 
 * Provides functionalities to view and manage a list of appointments.
 * Implements {@link AppointmentManagerInterface}.
 */

public class AppointmentManager implements AppointmentManagerInterface {

    /**
     * The list of appointments managed by this class.
     */
    protected List<Appointment> appointmentList;
    //protected List<ReplenishmentRequests> replenishmentRequests;
    /**
     * The timestamp of the last update made to the appointments list.
     */
    protected LocalDateTime lastUpdatedTime;
    /**
     * The user who made the last update to the appointments list.
     */
    protected String lastUpdatedBy; 

     /**
     * Constructs an instance of {@code AppointmentManager} by loading appointments 
     * from the specified {@link AppointmentsDataLoader}.
     * 
     * @param loader the data loader used to initialize the list of appointments.
     */
    public AppointmentManager(AppointmentsDataLoader loader) {
        this.appointmentList = loader.getAppointments();
    }
    /**
     * Displays the list of appointments in a formatted table on the console.
     * Retrieves appointment data from {@link AppointmentsDataLoader}.
     * The table includes details such as Appointment ID, Date & Time, 
     * Patient ID, Doctor ID, Status, and Outcome Record ID.
     */
    @Override
    public void viewAppointments() {
        System.out.printf("\n%-20s %-40s %-20s %-20s %-20s %-20s%n", "Appointment ID", "Date & Time", "Patient ID", "Doctor ID","Status","Outcome Record ID");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

        for (Appointment appointment : AppointmentsDataLoader.getAppointments()) {
            System.out.printf("%-20s %-40s %-20s %-20s %-20s %-20s%n", 
            appointment.getAppointmentID(), 
            appointment.getAppointmentDateTime(), 
            appointment.getPatientID(),
            appointment.getDoctorID(),
            appointment.getAppointmentStatus(),
            appointment.getAppointmentOutcomeRecordID()
            );
        }
    }

    

}
