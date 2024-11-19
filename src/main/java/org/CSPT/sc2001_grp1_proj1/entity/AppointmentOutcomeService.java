package org.CSPT.sc2001_grp1_proj1.entity;


import java.util.ArrayList;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.dataLoader.AppointmentOutcomeRecordsDataLoader;

public class AppointmentOutcomeService {
    
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords = new ArrayList<>();
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

    public List<AppointmentOutcomeRecord> getAllAppointmentOutcomeRecords() {
        return appointmentOutcomeRecords; // Return all records if needed
    }
}

