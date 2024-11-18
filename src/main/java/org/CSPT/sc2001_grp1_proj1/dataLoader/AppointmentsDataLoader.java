package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.entity.Appointment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class AppointmentsDataLoader {
    private final String EXCEL_FILE_PATH = "./data/AppointmentList.xlsx";
    private List<Appointment> appointments = new ArrayList<>();

    public AppointmentsDataLoader() {
        loadAppointments();
    }

    public void loadAppointments() {
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

    
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Appointment> getAvailableAppointments() {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> appointment.getAppointmentStatus().equals("Available")).toList();
        return filteredAppointments;
    }

    public List<Appointment> getScheduledAppointments(String patientID) {
        List<Appointment> filteredAppointments = appointments.stream().filter(appointment -> appointment.getAppointmentStatus().equals("Pending") ||  appointment.getAppointmentStatus().equals("Confirmed") && appointment.getPatientID().equals(patientID)).toList();
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

    
}
