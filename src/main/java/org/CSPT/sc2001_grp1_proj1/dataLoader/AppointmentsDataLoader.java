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

public class AppointmentsDataLoader {
    private final String EXCEL_FILE_PATH = "./data/AppointmentList.xlsx";
    private static List<Appointment> appointments = new ArrayList<>();
    
    public AppointmentsDataLoader() {
        loadAppointments();
    }

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
          
    public static List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Appointment> getAvailableAppointments() {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> appointment.getAppointmentStatus().equals("Available")).toList();
        return filteredAppointments;
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatusEnum status) {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> appointment.getAppointmentStatus().equals(status.toString())).toList();
        return filteredAppointments;
    }

    public List<Appointment> getScheduledAppointments(String patientID) {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> appointment.getAppointmentStatus().equals("Pending") ||  appointment.getAppointmentStatus().equals("Confirmed") && appointment.getPatientID().equals(patientID)).toList();
        return filteredAppointments;
    }

    public List<Appointment> getAppointmentsByDoctor(String doctorID) {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> 
            appointment.getDoctorID().equals(doctorID) ||  
            appointment.getAppointmentStatus().equals(AppointmentStatusEnum.Approved.toString()) ||
            appointment.getAppointmentStatus().equals(AppointmentStatusEnum.Completed.toString())
        ).toList();
        return filteredAppointments;
    }

    public HashMap<String, Appointment> getAppointmentsByOutcomeRecordID() {
        HashMap<String, Appointment> hashedAppointmentsByOutcomeRecordID = new HashMap<>();
        appointments.forEach(appointment -> {
            hashedAppointmentsByOutcomeRecordID.put(appointment.getAppointmentOutcomeRecordID(), appointment);
        });
        return hashedAppointmentsByOutcomeRecordID;
    }

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

    // Method to get the next available Outcome Record ID
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
