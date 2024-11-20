package org.CSPT.sc2001_grp1_proj1.entity;


import java.util.ArrayList;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentOutcomeRecordsDataLoader;

public class AppointmentOutcomeService {
    
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords = new ArrayList<>();
    //private List<AppointmentOutcomeRecord> pendingAppointmentOutcomeRecords = new ArrayList<>();
    private AppointmentOutcomeRecordsDataLoader dataLoader;

    public AppointmentOutcomeService() 
    {
        this.dataLoader = new AppointmentOutcomeRecordsDataLoader();
        this.appointmentOutcomeRecords = dataLoader.getAppointmentOutcomeRecords();
    }

    public void viewAppointmentOutComeRecord() {
        if (appointmentOutcomeRecords.isEmpty()) {
            System.out.println("No appointment outcome records available.");
            return;
        }
        for (AppointmentOutcomeRecord apptRecord : appointmentOutcomeRecords) {
            apptRecord.printAppointmentOutcomeRecordDetails();
        }
    }

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

    // return all appointment outcome records
    public List<AppointmentOutcomeRecord> getAllAppointmentOutcomeRecords() {
        return appointmentOutcomeRecords; 
    }

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

