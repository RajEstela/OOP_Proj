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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class handles loading, updating, and managing appointment outcome records from an Excel file.
 */
public class AppointmentOutcomeRecordsDataLoader {
    /**
     * Path to the Excel file storing appointment outcome records.
     */
    private final String EXCEL_FILE_PATH = "./data/AppointmentOutcomeRecordList.xlsx";
    /**
     * List to store all loaded appointment outcome records.
     */
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords = new ArrayList<>();
    /**
     * Constructor for the data loader. Initializes the loader by loading appointment records from the Excel file.
     */
    public AppointmentOutcomeRecordsDataLoader() {
        loadAppointmentRecords();
    }
    /**
     * Loads appointment outcome records from the Excel file into the internal list.
     */
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
    /**
     * Updates an appointment outcome record in the Excel file and the internal list.
     *
     * @param updatedRecord The updated appointment outcome record to be saved.
     */
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
    /**
     * Retrieves all appointment outcome records loaded into the system.
     *
     * @return A list of all appointment outcome records.
     */
    public List<AppointmentOutcomeRecord> getAppointmentOutcomeRecords() {
        return appointmentOutcomeRecords;
    }
    /**
     * Filters and retrieves completed past appointment outcome records for a specific patient.
     *
     * @param patientID The ID of the patient.
     * @return A list of past completed appointment outcome records for the given patient.
     */
    public List<AppointmentOutcomeRecord> getPastAppointmentOutcomeRecords(String patientID) {
        List<AppointmentOutcomeRecord> filteredAppointmentOutcomeRecords = appointmentOutcomeRecords.stream().filter(appointment -> appointment.getAppointmentStatus().equals(AppointmentStatusEnum.Completed.toString()) && appointment.getPrescribedStatus().equals("Confirmed") && appointment.getPatientID().equals(patientID)).toList();
        return filteredAppointmentOutcomeRecords;
    }
    /**
     * Adds a new appointment outcome record or updates an existing one in the Excel file.
     *
     * @param record The new or updated appointment outcome record.
     */
    public void addNewRecord(AppointmentOutcomeRecord record) {
    try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
         Workbook workbook = new XSSFWorkbook(file)) {

        Sheet sheet = workbook.getSheetAt(0);
        boolean recordUpdated = false;

        // Search for existing outcome record ID
        for (Row row : sheet) {
            Cell cell = row.getCell(0); // Assuming Outcome Record ID is in the first column
            if (cell != null && cell.getStringCellValue().equals(record.getAppointmentOutcomeRecordID())) {
                // Update existing record
                row.getCell(1).setCellValue(record.getServiceType());
                row.getCell(2).setCellValue(record.getPrescribedMedications());
                row.getCell(3).setCellValue(record.getPrescribedStatus());
                row.getCell(4).setCellValue(record.getConsultationNotes());
                recordUpdated = true;
                break;
            }
        }

        if (!recordUpdated) {
            // Add new record if not found
            int lastRow = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRow + 1);

            newRow.createCell(0).setCellValue(record.getAppointmentOutcomeRecordID());
            newRow.createCell(1).setCellValue(record.getServiceType());
            newRow.createCell(2).setCellValue(record.getPrescribedMedications());
            newRow.createCell(3).setCellValue(record.getPrescribedStatus());
            newRow.createCell(4).setCellValue(record.getConsultationNotes());
        }

        try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
            workbook.write(outFile);
        }

        if (recordUpdated) {
            System.out.println("Appointment outcome updated.");
        } else {
            System.out.println("Appointment outcome recorded.");
        }

    } catch (IOException e) {
        System.out.println("Error updating the appointment list: " + e.getMessage());
    }
}

}
