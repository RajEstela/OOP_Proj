package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.entity.Appointment;
import org.CSPT.sc2001_grp1_proj1.entity.AppointmentStatusEnum;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class manages appointment data by loading, updating, and retrieving appointment records from an Excel file.
 */
public class AppointmentsDataLoader {
    /**
     * Path to the Excel file containing appointment data.
     */
    private final String EXCEL_FILE_PATH = "./data/AppointmentList.xlsx";
    /**
     * Static list of all loaded appointments.
     */
    private static List<Appointment> appointments = new ArrayList<>();
    /**
     * Constructor that initializes the loader and loads appointment data.
     */    
    public AppointmentsDataLoader() {
        loadAppointments();
    }
    /**
     * Loads appointment data from the Excel file into the static list of appointments.
     */
    public void loadAppointments() {
        // Clear appointments
        appointments.clear();
        // Load excel sheet into appointments
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                String appointmentID = row.getCell(0).getStringCellValue();
                String appointmentDateTime = row.getCell(1).getStringCellValue();
                String patientID = row.getCell(2).getStringCellValue();
                String doctorID = row.getCell(3).getStringCellValue();
                String appointmentStatus = row.getCell(4).getStringCellValue();
                String appointmentOutcomeRecordID = row.getCell(5).getStringCellValue();

                Appointment appointment = new Appointment(appointmentID, appointmentDateTime, patientID, doctorID, appointmentStatus, appointmentOutcomeRecordID);
                appointments.add(appointment);
            }

        } catch(IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Retrieves all loaded appointments.
     *
     * @return A list of all appointments.
     */
    public static List<Appointment> getAppointments() {
        return appointments;
    }
    /**
     * Retrieves all available appointment slots.
     *
     * @return A list of available appointments.
     */
    public List<Appointment> getAvailableAppointments() {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> appointment.getAppointmentStatus().equals("Available")).toList();
        return filteredAppointments;
    }
    /**
     * Retrieves appointments filtered by a specific status.
     *
     * @param status The status to filter appointments by.
     * @return A list of appointments with the specified status.
     */
    public List<Appointment> getAppointmentsByStatus(AppointmentStatusEnum status) {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> appointment.getAppointmentStatus().equals(status.toString())).toList();
        return filteredAppointments;
    }
    /**
     * Retrieves all scheduled appointments for a specific patient.
     *
     * @param patientID The ID of the patient.
     * @return A list of scheduled appointments for the patient.
     */
    public List<Appointment> getScheduledAppointments(String patientID) {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> appointment.getAppointmentStatus().equals("Pending") ||  appointment.getAppointmentStatus().equals("Confirmed") && appointment.getPatientID().equals(patientID)).toList();
        return filteredAppointments;
    }
    /**
     * Retrieves appointments associated with a specific doctor.
     *
     * @param doctorID The ID of the doctor.
     * @return A list of appointments for the doctor.
     */
    public List<Appointment> getAppointmentsByDoctor(String doctorID) {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> 
            appointment.getDoctorID().equals(doctorID) ||  
            appointment.getAppointmentStatus().equals(AppointmentStatusEnum.Approved.toString()) ||
            appointment.getAppointmentStatus().equals(AppointmentStatusEnum.Completed.toString())
        ).toList();
        return filteredAppointments;
    }
    /**
     * Retrieves a mapping of appointment outcome record IDs to their corresponding appointments.
     *
     * @return A HashMap where keys are outcome record IDs and values are appointments.
     */
    public HashMap<String, Appointment> getAppointmentsByOutcomeRecordID() {
        HashMap<String, Appointment> hashedAppointmentsByOutcomeRecordID = new HashMap<>();
        appointments.forEach(appointment -> {
            hashedAppointmentsByOutcomeRecordID.put(appointment.getAppointmentOutcomeRecordID(), appointment);
        });
        return hashedAppointmentsByOutcomeRecordID;
    }
    /**
     * Schedules an appointment for a patient by updating the Excel file.
     *
     * @param appointmentID The ID of the appointment to be scheduled.
     * @param patientID The ID of the patient scheduling the appointment.
     */
    public void scheduleAppointment(String appointmentID, String patientID) {
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                if(row.getCell(0).getStringCellValue().equals(appointmentID)) {
                    row.getCell(2).setCellValue(patientID); //Patient ID
                    row.getCell(4).setCellValue("Pending"); //Appointment Status
                    break;
                }
            }
            try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(outFile);
            }

        } catch(IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Cancels an appointment by updating the Excel file.
     *
     * @param appointmentID The ID of the appointment to be canceled.
     */
    public void cancelAppointment(String appointmentID) {
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                if(row.getCell(0).getStringCellValue().equals(appointmentID)) {
                    row.getCell(2).setCellValue("N/A"); //Patient ID
                    row.getCell(4).setCellValue("Available"); //Appointment Status
                    break;
                }
            }
            try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(outFile);
            }

        } catch(IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Confirms an appointment for a doctor by updating the Excel file.
     *
     * @param appointmentID The ID of the appointment to be confirmed.
     * @param doctorID The ID of the doctor confirming the appointment.
     */
    public void confirmAppointment(String appointmentID, String doctorID) {
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                if(row.getCell(0).getStringCellValue().equals(appointmentID)) {
                    row.getCell(4).setCellValue(AppointmentStatusEnum.Approved.toString()); //Appointment Status
                    break;
                }
            }
            try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(outFile);
            }
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Retrieves the next available appointment ID based on existing IDs in the Excel file.
     *
     * @return The next appointment ID as a string.
     */
    public String getNextAppointmentID() {
        int maxID = 0;
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                String currentID = row.getCell(0).getStringCellValue(); // Appointment ID
                if (currentID.startsWith("AP")) {
                    int idNumber = Integer.parseInt(currentID.substring(2));
                    maxID = Math.max(maxID, idNumber);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading appointment data: " + e.getMessage());
        }

        return String.format("AP%03d", maxID + 1); // Increment the highest ID
    }

    /**
     * Retrieves the next available outcome record ID based on existing IDs in the Excel file.
     *
     * @return The next outcome record ID as a string.
     */
    public String getNextOutcomeRecordID() {
        int maxID = 0;
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                String currentID = row.getCell(5).getStringCellValue(); // Outcome Record ID
                if (currentID.startsWith("OR")) {
                    int idNumber = Integer.parseInt(currentID.substring(2));
                    maxID = Math.max(maxID, idNumber);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading outcome record data: " + e.getMessage());
        }

        return String.format("OR%03d", maxID + 1); // Increment the highest ID
    }
    /**
     * Adds a new appointment slot to the Excel file.
     *
     * @param appointment The appointment to be added.
     */
    public void setNewAppointment(Appointment appointment) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
    
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRow + 1);
    
            // Populate the new row with appointment details
            newRow.createCell(0).setCellValue(appointment.getAppointmentID());
            newRow.createCell(1).setCellValue(appointment.getAppointmentDateTime());
            newRow.createCell(2).setCellValue(appointment.getPatientID());
            newRow.createCell(3).setCellValue(appointment.getDoctorID());
            newRow.createCell(4).setCellValue(appointment.getAppointmentStatus());
            newRow.createCell(5).setCellValue(appointment.getAppointmentOutcomeRecordID());
    
            try (FileOutputStream outFile = new FileOutputStream(new File("./data/AppointmentList.xlsx"))) {
                workbook.write(outFile);
            }
    
            System.out.println("Appointment slot added: " + appointment.getAppointmentID());
    
        } catch (IOException e) {
            System.out.println("Error updating the appointment list: " + e.getMessage());
        }
    }  
    /**
     * Updates the status of an existing appointment in the Excel file.
     *
     * @param appointmentID The ID of the appointment to be updated.
     * @param newStatus The new status to assign to the appointment.
     */
    public void updateAppointmentStatus(String appointmentID, String newStatus) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals(appointmentID)) {
                    row.getCell(4).setCellValue(newStatus); // Update Appointment Status
                    break;
                }
            }
            try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(outFile);
            }
        } catch (IOException e) {
            System.out.println("Error updating appointment status: " + e.getMessage());
        }
    }
    
}
