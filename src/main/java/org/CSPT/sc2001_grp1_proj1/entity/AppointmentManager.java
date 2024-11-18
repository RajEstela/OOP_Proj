package org.CSPT.sc2001_grp1_proj1.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentsDataLoader;
import org.CSPT.sc2001_grp1_proj1.interfaces.AppointmentManagerInterface;


public class AppointmentManager implements AppointmentManagerInterface {

    protected List<Appointment> appointmentList;
    //protected List<ReplenishmentRequests> replenishmentRequests;
    protected LocalDateTime lastUpdatedTime;
    protected String lastUpdatedBy; 

    public AppointmentManager(AppointmentsDataLoader loader) {
        this.appointmentList = loader.getAppointments();
    }

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
            appointment.getOutcomeRecord()
            );
        }
    }

    

}
