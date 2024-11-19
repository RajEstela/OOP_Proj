package org.CSPT.sc2001_grp1_proj1.dataLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.CSPT.sc2001_grp1_proj1.entity.Diagnosis;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DiagnosisDataLoader {
    private final String EXCEL_FILE_PATH = "./data/DiagnosisList.xlsx";
    private static List<Diagnosis> diagnoses = new ArrayList<>();
    
    public DiagnosisDataLoader() {
        loadDiagnosis();
    }

    public void loadDiagnosis() {
        // Clear diagnosis
        diagnoses.clear();
        // Load excel sheet into diagnosis
        try(FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH)); Workbook workbook = new XSSFWorkbook(file); ){
            // Get the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                String doctorID = row.getCell(0).getStringCellValue();
                String patientID = row.getCell(1).getStringCellValue();
                String diagnosisDetails = row.getCell(2).getStringCellValue();
                String treatmentPlan = row.getCell(3).getStringCellValue();
                String prescription = row.getCell(4).getStringCellValue();
                String diagnosisDateTime = row.getCell(5).getStringCellValue();

                Diagnosis diagnosis = new Diagnosis(doctorID, patientID, diagnosisDetails, treatmentPlan, prescription, diagnosisDateTime);
                diagnoses.add(diagnosis);
            }

        } catch(IOException e) {
            System.out.println(e);
        }
    }
          
    public static List<Diagnosis> getDiagnosis() {
        return diagnoses;
    }

    public List<Diagnosis> getDiagnosisByPatient(String patientID) {
        List<Diagnosis> filteredDiagnosis = diagnoses.stream().filter(diagnosis -> 
            diagnosis.getPatientID().equals(patientID)
        ).toList();
        return filteredDiagnosis;
    }

    public List<Diagnosis> getDiagnosisByDoctor(String doctorID) {
        List<Diagnosis> filteredDiagnosis = diagnoses.stream().filter(diagnosis -> 
            diagnosis.getDoctorID().equals(doctorID)
        ).toList();
        return filteredDiagnosis;
    }

    public void setNewDiagnosis(Diagnosis diagnosis) {
        try (FileInputStream file = new FileInputStream(new File(EXCEL_FILE_PATH));
             Workbook workbook = new XSSFWorkbook(file)) {
    
            Sheet sheet = workbook.getSheetAt(0);
            int lastRow = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRow + 1);
    
            // Populate the new row with diagnosis details
            newRow.createCell(0).setCellValue(diagnosis.getDoctorID());
            newRow.createCell(1).setCellValue(diagnosis.getPatientID());
            newRow.createCell(2).setCellValue(diagnosis.getDiagnosis());
            newRow.createCell(3).setCellValue(diagnosis.getTreatmentPlan());
            newRow.createCell(4).setCellValue(diagnosis.getPrescription());
            newRow.createCell(5).setCellValue(diagnosis.getDiagnosisDateTime());
    
            try (FileOutputStream outFile = new FileOutputStream(new File("./data/DiagnosisList.xlsx"))) {
                workbook.write(outFile);
            }
    
            System.out.println("Diagnosis added");
    
        } catch (IOException e) {
            System.out.println("Error updating the diagnosis list: " + e.getMessage());
        }
    }  
    
}
