package org.CSPT.sc2001_grp1_proj1.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing appointment outcome records. 
 * Provides functionality to view, update, and retrieve appointment outcome records.
 */

import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentOutcomeRecordsDataLoader;

public class AppointmentOutcomeService {
    
    /**
     * List of all appointment outcome records.
     */
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords = new ArrayList<>();

    /**
     * Data loader for appointment outcome records, responsible for loading and saving data.
     */
    private AppointmentOutcomeRecordsDataLoader dataLoader;

    /**
     * Constructs an {@code AppointmentOutcomeService} and initializes the appointment outcome records
     * by loading them using {@link AppointmentOutcomeRecordsDataLoader}.
     */

    public AppointmentOutcomeService() 
    {
        this.dataLoader = new AppointmentOutcomeRecordsDataLoader();
        this.appointmentOutcomeRecords = dataLoader.getAppointmentOutcomeRecords();
    }

    /**
     * Displays all appointment outcome records. 
     * If no records are available, a message is displayed.
     */

    public void viewAppointmentOutComeRecord() {
        if (appointmentOutcomeRecords.isEmpty()) {
            System.out.println("No appointment outcome records available.");
            return;
        }
        for (AppointmentOutcomeRecord apptRecord : appointmentOutcomeRecords) {
            apptRecord.printAppointmentOutcomeRecordDetails();
        }
    }

     /**
     * Displays only the pending appointment outcome records.
     * If no pending records exist, a clearance message is displayed.
     */

    public void viewPendingAppointmentOutcomeRecords()
    {
        List<AppointmentOutcomeRecord> pendingAppointmentOutcomeRecords = getPendingAppointmentOutcomeRecords();
        if(pendingAppointmentOutcomeRecords.isEmpty())
        {
            System.out.println("No pending appointment outcome records available. You're cleared!");
            return;
        }
        for(AppointmentOutcomeRecord apptRecord : pendingAppointmentOutcomeRecords)
        {
            apptRecord.printAppointmentOutcomeRecordDetails();
        }
    }

    /**
     * Updates the prescribed status of an appointment outcome record with the specified record ID.
     * The record is also updated in the underlying data source.
     * 
     * @param recordID   the ID of the record to be updated.
     * @param newStatus  the new status to set for the prescription.
     * @return {@code true} if the record was successfully updated, {@code false} otherwise.
     */

    public boolean updatePrescriptionStatus(String recordID, String newStatus) {
        for (AppointmentOutcomeRecord apptRecord : appointmentOutcomeRecords) {
            if (apptRecord.getAppointmentOutcomeRecordID().equals(recordID)) {
                apptRecord.setPrescribedStatus(newStatus); 
                
                // Update the Excel file with the modified record
                dataLoader.updateAppointmentOutcomeRecord(apptRecord);
                
                return true; // Update was successful
            }
        }
        return false; // No record found or update failed
    }

      /**
     * Retrieves all appointment outcome records.
     * 
     * @return a list of all appointment outcome records.
     */

    // return all appointment outcome records
    public List<AppointmentOutcomeRecord> getAllAppointmentOutcomeRecords() {
        return appointmentOutcomeRecords; 
    }

    /**
     * Retrieves only the pending appointment outcome records, 
     * identified by their prescribed status being "Pending".
     * 
     * @return a list of pending appointment outcome records.
     */
    // return only pending appointment outcome records
    public List<AppointmentOutcomeRecord> getPendingAppointmentOutcomeRecords() {
        List<AppointmentOutcomeRecord> pendingAppointmentOutcomeRecords = new ArrayList<>();
        for (AppointmentOutcomeRecord apptRecord : appointmentOutcomeRecords) {
            if ("Pending".equalsIgnoreCase(apptRecord.getPrescribedStatus())) {
                pendingAppointmentOutcomeRecords.add(apptRecord);
            }
        }
        return pendingAppointmentOutcomeRecords;
    }


}

