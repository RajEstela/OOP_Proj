package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.entity.Appointment;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentOutcomeRecord;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentStatusEnum;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AppointmentOutcomeRecordsDataLoader {
    private final String EXCEL_FILE_PATH = "./data/AppointmentOutcomeRecordList.xlsx";
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords = new ArrayList<>();

    public AppointmentOutcomeRecordsDataLoader() {
        loadAppointmentRecords();
    }

    public void loadAppointmentRecords() {
        // Clear appointment records before loading
        appointmentOutcomeRecords.clear();
        // Load excel sheet into appointments
        AppointmentsDataLoader appointmentData = new AppointmentsDataLoader();
        HashMap<String, Appointment> hashedAppointments = appointmentData.getAppointmentsByOutcomeRecordID();
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                String appointmentID = hashedAppointments.get(row.getCell(0).getStringCellValue()).getAppointmentID();
                String appointmentDateTime = hashedAppointments.get(row.getCell(0).getStringCellValue()).getAppointmentDateTime();
                String patientID =hashedAppointments.get(row.getCell(0).getStringCellValue()).getPatientID();
                String doctorID = hashedAppointments.get(row.getCell(0).getStringCellValue()).getDoctorID();
                String appointmentStatus =hashedAppointments.get(row.getCell(0).getStringCellValue()).getAppointmentStatus();
                String appointmentOutcomeRecordID = row.getCell(0).getStringCellValue();
                String serviceType = row.getCell(1).getStringCellValue();
                String prescribedMedications = row.getCell(2).getStringCellValue();
                String prescrbiedStatus = row.getCell(3).getStringCellValue();
                String consultationNotes = row.getCell(4).getStringCellValue();
                
                AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(appointmentID, appointmentDateTime, patientID, doctorID, appointmentStatus, appointmentOutcomeRecordID, serviceType, prescribedMedications, prescrbiedStatus, consultationNotes);
                appointmentOutcomeRecords.add(appointmentOutcomeRecord);
            }

        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public void updateAppointmentOutcomeRecord(AppointmentOutcomeRecord updatedRecord) {
    boolean recordUpdated = false; // Flag to check if the record was updated

    try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
         Workbook workbook = new XSSFWorkbook(file)) {

        Sheet sheet = workbook.getSheetAt(0);
        boolean isHeader = true;

        for (Row row : sheet) {
            if (isHeader) {
                isHeader = false;
                continue; // Skip header row
            }

            // Check if the appointment outcome record ID matches
            if (row.getCell(0).getStringCellValue().equals(updatedRecord.getAppointmentOutcomeRecordID())) {
                // Update prescribed status
                row.getCell(3).setCellValue(updatedRecord.getPrescribedStatus()); 

                recordUpdated = true; // Mark that an update has been made
                break; // Exit the loop after updating
            }
        }

        // Write changes to the file only if an update was made
        if (recordUpdated) {
            try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(outFile);
                System.out.println("Appointment outcome record updated successfully.");
            }
        } else {
            System.out.println("No matching appointment outcome record found to update.");
        }

    } catch (IOException e) {
        System.err.println("Error updating appointment outcome record: " + e.getMessage());
    }
}

    public List<AppointmentOutcomeRecord> getAppointmentOutcomeRecords() {
        return appointmentOutcomeRecords;
    }

    public List<AppointmentOutcomeRecord> getPastAppointmentOutcomeRecords(String patientID) {
        List<AppointmentOutcomeRecord> filteredAppointmentOutcomeRecords = appointmentOutcomeRecords.stream().filter(appointment -> appointment.getAppointmentStatus().equals(AppointmentStatusEnum.Completed.toString()) && appointment.getPrescribedStatus().equals("Confirmed") && appointment.getPatientID().equals(patientID)).toList();
        return filteredAppointmentOutcomeRecords;
    }

    public void addNewRecord(AppointmentOutcomeRecord record) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
    
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRow + 1);
    
            // Populate the new row with appointment details
            newRow.createCell(0).setCellValue(record.getAppointmentOutcomeRecordID());
            newRow.createCell(1).setCellValue(record.getServiceType());
            newRow.createCell(2).setCellValue(record.getPrescribedMedications());
            newRow.createCell(3).setCellValue(record.getPrescribedStatus());
            newRow.createCell(4).setCellValue(record.getConsultationNotes());
            
    
            try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(outFile);
            }
    
            System.out.println("Appointment outcome recorded:");
    
        } catch (IOException e) {
            System.out.println("Error updating the appointment list: " + e.getMessage());
        }
    }  
}
