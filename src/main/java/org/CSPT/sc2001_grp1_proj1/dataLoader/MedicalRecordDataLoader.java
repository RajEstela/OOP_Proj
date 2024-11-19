package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.entity.MedicalRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MedicalRecordDataLoader {
    private final String EXCEL_FILE_PATH = "./data/MedicalRecordList.xlsx";

    public void populateMedicalRecords(List<MedicalRecord> medicalRecords) {
        medicalRecords.clear();
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }

            String medicalRecordID = row.getCell(0).getStringCellValue();
            String patientID = row.getCell(1).getStringCellValue();
            String name = row.getCell(2).getStringCellValue();
            String dob = row.getCell(3).getStringCellValue();
            String gender = row.getCell(4).getStringCellValue();
            int phone = (int) row.getCell(5).getNumericCellValue();
            String email = row.getCell(6).getStringCellValue();
            String bloodType = row.getCell(7).getStringCellValue();
            String pastDianosesAndTreatments = row.getCell(8).getStringCellValue();

            MedicalRecord medicalRecord = new MedicalRecord(medicalRecordID, patientID, name, dob, gender, phone, email, bloodType, pastDianosesAndTreatments);
            medicalRecords.add(medicalRecord);
            
            }

        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public void updatePhoneNumber(String patientID, int phoneNumber) {
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                if(row.getCell(1).getStringCellValue().equals(patientID)) {
                    row.getCell(5).setCellValue(phoneNumber);
                }
            }
            try (FileOutputStream outFile = new FileOutputStream(new File(EXCEL_FILE_PATH))) {
                workbook.write(outFile);
            }
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public void updateEmail(String patientID, String email) {
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;

            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                if(row.getCell(1).getStringCellValue().equals(patientID)) {
                    row.getCell(6).setCellValue(email);
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
